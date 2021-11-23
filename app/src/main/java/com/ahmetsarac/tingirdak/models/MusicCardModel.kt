package com.ahmetsarac.tingirdak.models

import android.graphics.Bitmap
import android.net.Uri

data class MusicCardModel(
    val contentUri: Uri?,
    val songId: Long?,
    val songTitle: String?,
    val artist: String?,
    val duration: String?
)