package pe.com.dreads.wowk.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import pe.com.dreads.wowk.R
import pe.com.dreads.wowk.model.ComicEntity

/**
 * Created by Dreads on 19/11/2017.
 */
class ComicAdapter(var items: MutableList<ComicEntity>, val listener: (ComicEntity)-> Unit) : RecyclerView.Adapter<ComicAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_comic,parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            name.text= items[position].title
            Glide.with(img.context)
                    .load(items[position].thumbnail.getFullImage())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logocomic)
                    .error(R.drawable.logocomic)
                    .into(holder.img)
            holder.itemView.setOnClickListener {
                listener(items[position])
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var img: ImageView = view.findViewById(R.id.img_cover)
        var name: TextView = view.findViewById(R.id.tv_name)
    }

    fun clear(){
        items.clear()
        notifyDataSetChanged()
    }
    fun addComic(comic:ComicEntity){
        items.add(comic)
        notifyItemInserted(items.size-1)

    }
}