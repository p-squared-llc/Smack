package com.psquaredllc.smack.services


import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.psquaredllc.smack.controller.App
import com.psquaredllc.smack.utilities.URL_FIND_ALL_CHANNELS
import com.psquaredllc.smack.model.Channel
import com.psquaredllc.smack.model.Message
import com.psquaredllc.smack.utilities.URL_FIND_ALL_MESSAGES
import org.json.JSONException

object MessageService {

    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()

    fun getChannels (complete: (Boolean) -> Unit){

        val channelsRequest = object : JsonArrayRequest(Method.GET,
            URL_FIND_ALL_CHANNELS,null, Response.Listener {response ->
                if (channels.isEmpty()) {


                    try {

                        for (x in 0 until response.length()) {
                            val channel = response.getJSONObject(x)
                            val name = channel.getString("name")
                            val chanDesc = channel.getString("description")
                            val channelId = channel.getString("_id")

                            val newChannel = Channel(name, chanDesc, channelId)
                            this.channels.add(newChannel)
                        }

                        complete(true)

                    } catch (e: JSONException) {
                        Log.d("JSON", "EXC ${e.localizedMessage}")
                        complete(false)
                    }
                }

            }, Response.ErrorListener {
                Log.d("ERROR", "Could not retrieve channels")
                complete(false)
            }){

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }
        App.prefs.requestQueue.add(channelsRequest)
    }

    fun getMessages (channelId: String, complete: (Boolean) -> Unit){

        val messagesRequest = object : JsonArrayRequest(Method.GET,
            "${URL_FIND_ALL_MESSAGES}$channelId",null, Response.Listener {response ->

                    try {
                        for (x in 0 until response.length()) {
                            val message = response.getJSONObject(x)
                            val id = message.getString("_id")
                            val messageBody = message.getString("messageBody")
                            println(messageBody)
//                            val channelId = message.getString("channelId")    //coming from function call
                            val userName = message.getString("userName")
                            val userAvatar = message.getString("userAvatar")
                            val userAvatarColor = message.getString("userAvatarColor")
                            val timeStamp = message.getString("timeStamp")

                            val newMessage = Message( messageBody,  userName,  channelId,  userAvatar,
                                userAvatarColor, id, timeStamp)
                            this.messages.add(newMessage)
                        }

                        complete(true)

                    } catch (e: JSONException) {
                        Log.d("JSON", "EXC ${e.localizedMessage}")
                        complete(false)
                    }


            }, Response.ErrorListener {
                Log.d("ERROR", "Could not retrieve channels")
                complete(false)
            }){

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${App.prefs.authToken}"
                return headers
            }
        }
        App.prefs.requestQueue.add(messagesRequest)
    }

}