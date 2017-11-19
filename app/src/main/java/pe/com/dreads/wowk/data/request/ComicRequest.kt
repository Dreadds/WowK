package pe.com.dreads.wowk.data.request

import pe.com.dreads.wowk.data.response.BaseResponse
import pe.com.dreads.wowk.model.ComicEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

/**
 * Created by Dreads on 18/11/2017.
 */
interface ComicRequest {

    @GET("comics")
    fun getComics()
            //@QueryMap options:Map<String, String>): Observable<BaseResponse<ComicEntity>>

    enum class  OrderBy(val value:String){
        TITLE("title"), MODIFIED("modified")
    }
}