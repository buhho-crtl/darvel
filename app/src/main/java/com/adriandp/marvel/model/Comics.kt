package com.adriandp.marvel.model

import java.io.Serializable

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
): Serializable