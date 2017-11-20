package pe.com.dreads.wowk.view

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import pe.com.dreads.wowk.R
import pe.com.dreads.wowk.base.BaseActivity
import pe.com.dreads.wowk.model.ComicEntity

/**
 * Created by Dreads on 19/11/2017.
 */
class ComicDetailActivity : BaseActivity(){
    var entity: ComicEntity?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_detail)
        showToolbar()
        if (intent.extras!=null){
            val str = intent.extras.getString("comic")
            entity = Gson().fromJson(str, ComicEntity::class.java)
            val description:TextView = findViewById<View>(R.id.tv_comic_description) as TextView
            val image:ImageView = findViewById<View>(R.id.img_back) as ImageView

            description.text = entity?.description ?:""
            Glide.with(image.context)
                    .load(entity?.thumbnail?.getFullImage()?:"")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logocomic)
                    .error(R.drawable.logocomic)
                    .into(image)
            val collapsingToolbar : CollapsingToolbarLayout = findViewById(R.id.collapsing)
            collapsingToolbar.title = entity?.title?: ""
        }
    }

    private fun showToolbar(){
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true)
            ab.setDisplayShowHomeEnabled(true)
            ab.setTitle("Detail Comics")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}