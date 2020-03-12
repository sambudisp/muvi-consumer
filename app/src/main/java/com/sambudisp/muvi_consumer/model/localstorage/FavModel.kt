package com.sambudisp.muvi.model.localstorage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavModel(
    var id : Int = 0,
    var savedId : String? = null,
    var date : String?  = null,
    var title : String?  = null,
    var category : String?  = null,
    var poster : String?  = null,
    var desc : String?  = null
) : Parcelable