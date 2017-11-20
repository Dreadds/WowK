package pe.com.dreads.wowk.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import io.reactivex.disposables.Disposable
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import pe.com.dreads.wowk.R
import pe.com.dreads.wowk.base.BaseActivity
import pe.com.dreads.wowk.model.ComicEntity
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity(), ComicContract.View {

    lateinit var rvComics:RecyclerView
    lateinit var comicAdapter: ComicAdapter

    var comicPresenter : ComicContract.Presenter ?=null
    var loading: Boolean = false
    var offset: Int = 0
    var mText: String = ""
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //showToolbar()

        rvComics = findViewById<View>(R.id.rv_comics ) as RecyclerView
        val layoutManager = GridLayoutManager(this,2)
        rvComics.layoutManager = layoutManager
        comicAdapter = ComicAdapter(mutableListOf()){
            comic ->
            val i:Intent= Intent(this, ComicDetailActivity::class.java)
            var strObject: String = Gson().toJson(comic)
            i.putExtra("comic", strObject)
            startActivity(i)
        }
        rvComics.adapter = comicAdapter
        comicPresenter = ComicPresenter(this)
        comicPresenter?.start()
        comicPresenter?.getComic(offset)
        rvComics.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy>0){
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!loading){
                        if(visibleItemCount + pastVisibleItems >= totalItemCount){
                            comicPresenter?.getComic(offset,mText)
                        }
                    }
                }
            }
        })

        if (savedInstanceState != null){
            offset = savedInstanceState.getInt("OFFSET")
            mText = savedInstanceState.getString("TEXT")
            comicPresenter = ComicPresenter(this)
        }
    }
    private fun showToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false)
            ab.setDisplayShowHomeEnabled(false)
            ab.setTitle("Marvel Comics")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val iSearch = menu?.findItem(R.id.action_search)
        val vSearch = iSearch?.actionView as SearchView

        disposable = Observable.create(ObservableOnSubscribe<String> {
            subscriber ->
            vSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (!vSearch.isIconified) {
                        vSearch.isIconified = true
                    }
                    iSearch.collapseActionView()
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    subscriber.onNext(newText)
                    return true
                }
            })
        }).debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    text ->
                    comicAdapter.clear()
                    comicPresenter?.getComic(s=text)
                }


        return super.onCreateOptionsMenu(menu)
    }

    override fun renderComics(comics: List<ComicEntity>, offset: Int, text: String) {
        this.offset = offset
        this.mText = text
        for (comic in comics)
            comicAdapter.addComic(comic)
    }

    override fun isLoading(): Boolean {
        return loading
    }

    override fun setLoadingIndicator(visible: Boolean) {
        loading = visible
    }

    override fun setError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
    }

    override fun setPresenter(presenter: ComicContract.Presenter) {
        comicPresenter = presenter
    }

    override fun onDestroy() {
        if(disposable?.isDisposed ?:false)
            disposable?.dispose()
        comicPresenter?.stop()
        super.onDestroy()
    }


}
