package com.adriandp.marvel.model

import java.io.Serializable

data class Result(
    val title : String,
    val comics: Comics?,
    val description: String,
    val id: Int,
    val name: String?=null,
    val resourceURI: String,
    val thumbnail: Thumbnail,
    val urls: List<Url>,
    val characters: Characters
    ):Serializable