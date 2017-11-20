package pe.com.dreads.wowk.view

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pe.com.dreads.wowk.BuildConfig
import pe.com.dreads.wowk.data.request.ComicRequest
import pe.com.dreads.wowk.data.request.ServiceGeneratorSimple
import pe.com.dreads.wowk.data.response.BaseResponse
import pe.com.dreads.wowk.model.ComicEntity
import pe.com.dreads.wowk.utils.md5

/**
 * Created by Dreads on 19/11/2017.
 */
class ComicPresenter(internal var mView: ComicContract.View) : ComicContract.Presenter {

    var subscriptions: CompositeDisposable = CompositeDisposable()

    override fun getComic(offset: Int, s: String) {
        if ( !mView.isLoading()){
            mView.setLoadingIndicator(true)
            val request: ComicRequest = ServiceGeneratorSimple.createServiceRx(ComicRequest::class.java)
            val public_key:String = BuildConfig.PUBLIC_KEY
            val private_key:String = BuildConfig.PRIVATE_KEY
            val ts = (System.currentTimeMillis()/ 1000).toString()
            val hash = md5("$ts$private_key$public_key" )
            val options:MutableMap<String,String> = mutableMapOf()
            options.put("apikey",public_key)
            options.put("ts", ts)
            options.put("hash", hash)
            options.put("offset", offset.toString())
            options.put("orderBy", ComicRequest.OrderBy.TITLE.value)
            options.put("limit", 15.toString())
            if (!s.isEmpty())
                options.put("nameStartsWith", s)

            subscriptions.add(request.getComics(options)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { t:BaseResponse<ComicEntity>? ->
                        if (t?.code==200){
                            Pair(t.data,t.code)
                        }else{
                            Pair(null, t?.code?:500)
                        }
                    }
                    .subscribe({
                        (d,_)->
                            if (d!=null){
                                val comics: List<ComicEntity> = d.results
                                mView.renderComics(comics, d.offset+15,s)
                            }else{
                                mView.setError("I am Sorry: ( ")
                            }
                    },{
                        mView.setLoadingIndicator(false)
                        mView.setError("Fail")
                    },{
                        mView.setLoadingIndicator(false)
                    }))
        }
    }

    override fun start() {
        mView.setPresenter(this)
    }

    override fun stop() {
        if (subscriptions.size()>0)
            subscriptions.clear()
    }


}