package pe.com.dreads.wowk.model

import java.io.Serializable

/**
 * Created by Dreads on 18/11/2017.
 */
data class ComicEntity(val id:Int, val title:String?, val description:String?, val thumbnail:Thumbnail, val urls:List<Url>):Serializable