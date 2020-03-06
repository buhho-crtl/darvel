package com.adriandp.marvel.config

object Constants {
    const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"

    object sizeImage {
        const val portrait_medium = "portrait_medium" // 	100x150px
        const val portrait_xlarge = "portrait_incredible" //150x225px
    }

    object pathUrl {
        const val CHARACTERS = "characters"
        const val COMICS = "comics"
    }
}