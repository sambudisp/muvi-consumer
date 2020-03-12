package com.sambudisp.muvi.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.sambudisp.muvi"
    const val SCHEME = "content"

    class FavColumns : BaseColumns {
        companion object{
            const val TABLE_NAME = "table_fav"
            const val _ID = "_id"
            const val ID_FAV = "id_fav"
            const val DATE = "date"
            const val TITLE = "title"
            const val CATEGORY = "category"
            const val DESC = "desc"
            const val POSTER = "poster"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}