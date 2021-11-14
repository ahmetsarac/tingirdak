package com.ahmetsarac.tingirdak.models

import android.graphics.Bitmap
import android.net.Uri

data class MusicCardModel(
    var contentUri: Uri?,
    val songId: Long?,
    val cover: Bitmap?,
    val songTitle: String?,
    var artist: String?,
    val duration: String?
)