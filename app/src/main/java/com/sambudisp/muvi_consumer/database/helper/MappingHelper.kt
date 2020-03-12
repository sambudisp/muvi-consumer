package com.sambudisp.muvi.database.helper

import android.database.Cursor
import com.sambudisp.muvi.database.DatabaseContract
import com.sambudisp.muvi.model.localstorage.FavModel

object MappingHelper {

    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavModel> {
        val favList = ArrayList<FavModel>()
        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavColumns._ID))
                val idFav = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.ID_FAV))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.DATE))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.TITLE))
                val category =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.CATEGORY))
                val poster = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.POSTER))
                val desc = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.DESC))
                favList.add(FavModel(id, idFav, date, title, category, poster, desc))
            }
        }
        return favList
    }

    fun mapCursorToObject(notesCursor: Cursor?): FavModel {
        var fav = FavModel()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavColumns._ID))
            val idFav = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.ID_FAV))
            val date = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.DATE))
            val title = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.TITLE))
            val category = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.CATEGORY))
            val poster = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.POSTER))
            val desc = getString(getColumnIndexOrThrow(DatabaseContract.FavColumns.DESC))
            fav = FavModel(id, idFav, date, title, category, poster, desc)
        }
        return fav
    }
}