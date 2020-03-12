package com.sambudisp.muvi_consumer

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sambudisp.muvi.database.DatabaseContract
import com.sambudisp.muvi.database.helper.MappingHelper
import com.sambudisp.muvi.model.localstorage.FavModel
import com.sambudisp.muvi_consumer.adapter.FavAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FavAdapter

    private var fav: FavModel? = null

    companion object {
        val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()

        fav = FavModel()

        rv_fav.layoutManager = LinearLayoutManager(this)
        rv_fav.setHasFixedSize(true)
        adapter = FavAdapter()
        rv_fav.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavAsync()
            }
        }
        contentResolver?.registerContentObserver(DatabaseContract.FavColumns.CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<FavModel>(MainActivity.EXTRA_STATE)
            if (list != null) {
                adapter.listFav = list
            }
        }

    }

    private fun loadFavAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredFavs = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(DatabaseContract.FavColumns.CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressbar.visibility = View.INVISIBLE
            val fav = deferredFavs.await()
            if (fav.size > 0) {
                adapter.listFav.clear()
                adapter.listFav = fav
                adapter.notifyDataSetChanged()
            } else {
                adapter.listFav.clear()
                adapter.notifyDataSetChanged()
                showSnackbarMessage("Data Empty")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFav)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(root_layout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun setupView() {
        supportActionBar?.title = "Consumer Muvi"
    }
}
