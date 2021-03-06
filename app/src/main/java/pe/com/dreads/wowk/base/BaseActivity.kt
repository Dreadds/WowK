package pe.com.dreads.wowk.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

/**
 * Created by Dreads on 19/11/2017.
 */
open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showMessage(container: View?, message:String){
        if(container!=null){
            val snackBar = Snackbar.make(container, message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }else{
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        }
    }
}