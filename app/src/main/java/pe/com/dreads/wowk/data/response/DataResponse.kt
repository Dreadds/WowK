package pe.com.dreads.wowk.data.response

/**
 * Created by Dreads on 18/11/2017.
 * DataContainer
 */
data class DataResponse<out T>(val offset:Int, val limit:Int, val total:Int, val count:Int, val results:List<T>)