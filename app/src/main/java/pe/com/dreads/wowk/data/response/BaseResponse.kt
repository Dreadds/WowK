package pe.com.dreads.wowk.data.response

/**
 * Created by Dreads on 18/11/2017.
 * DataWrapper
 */
data class BaseResponse<out T> (val code:Int, val status: String, val etag:String, val data: DataResponse<T>)