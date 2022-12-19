package com.alviano.ugd_kel3.api

class ProdukApi {
    companion object{
        val  BASE_URl = "http://192.168.2.25/salon-apiserver/public/api/"

        val GET_ALL_URL = BASE_URl+ "produk/"
        val GET_BY_ID_URL = BASE_URl+"produk/"
        val ADD_URL = BASE_URl+"produk"
        val UPDATE_URL = BASE_URl+"produk/"
        val DELETE_URL = BASE_URl+"produk/"
    }
}