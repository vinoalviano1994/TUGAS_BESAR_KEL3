package com.alviano.ugd_kel3.api

class CustomerApi {
    companion object{
        val  BASE_URl = "http://192.168.2.25/salon-apiserver/public/api/"

        val GET_ALL_URL = BASE_URl+ "customer/"
        val GET_BY_ID_URL = BASE_URl+"customer/"
        val ADD_URL = BASE_URl+"customer"
        val UPDATE_URL = BASE_URl+"customer/"
        val DELETE_URL = BASE_URl+"customer/"

        val LOGIN_URL = BASE_URl + "login"
        val REGISTER_URL = BASE_URl + "register"

    }
}