package pe.com.dreads.wowk.model

/**
 * Created by Dreads on 18/11/2017.
 */

data class Url(val type:String, val url:String)

data class Thumbnail(val path: String, val extension:String){
    fun getFullImage():String= "$path.$extension"
}

