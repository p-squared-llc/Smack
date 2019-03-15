package com.psquaredllc.smack.utilities

const val BASE_URL = "https://chat-app-devslopes-class.herokuapp.com/v1/"
//const val BASE_URL = "http://10.0.2.2:3005/v1/"  //BASE_URL for local DB
const val SOCKET_URL = "https://chat-app-devslopes-class.herokuapp.com/"
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_CREATE_USER = "${BASE_URL}user/add"
const val URL_FIND_ALL_USERS = "${BASE_URL}users"
const val URL_FIND_USER_BY_ID = "${BASE_URL}user"
const val URL_UPDATE_USER = "${BASE_URL}user"
const val URL_DELETE_USER = "${BASE_URL}user"
const val URL_AUTH_FIND_ME = "${BASE_URL}account/me"
const val URL_FIND_USER_BY_EMAIL = "${BASE_URL}user/byEmail/"
const val URL__ADD_CHANNEL = "${BASE_URL}channel/add"
const val URL_FIND_ALL_CHANNELS = "${BASE_URL}channel"
const val URL_FIND_ALL_MESSAGES = "${BASE_URL}message/byChannel"

//Broadcast Constants
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"