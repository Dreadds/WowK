package pe.com.dreads.wowk.view

import pe.com.dreads.wowk.model.ComicEntity

/**
 * Created by Dreads on 19/11/2017.
 */
interface ComicContract {
    interface View{
        fun renderComics(comics:List<ComicEntity>, offset:Int, text:String)
        fun isLoading():Boolean
        fun setLoadingIndicator(visible:Boolean)
        fun setError(msg:String)
        fun setPresenter(presenter: Presenter)
    }
    interface Presenter{
        fun getComic(offset: Int =0, s:String = "")
        fun start()
        fun stop()
    }
}