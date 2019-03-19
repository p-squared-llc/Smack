package com.psquaredllc.smack.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.psquaredllc.smack.R
import com.psquaredllc.smack.model.Message
import com.psquaredllc.smack.services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MessageRecycleViewAdapter(val context: Context, val messages: ArrayList<Message>) :
    RecyclerView.Adapter<MessageRecycleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.message_adapter_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder?.bindMessage(context,messages[position])    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage = itemView.findViewById<ImageView>(R.id.userImage)
        val userName = itemView.findViewById<TextView>(R.id.userNameText)
        val timeStamp = itemView.findViewById<TextView>(R.id.dateStampText)
        val messageBudy = itemView.findViewById<TextView>(R.id.userMessageText)

        fun bindMessage(context: Context, message:Message){
            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userImage.setImageResource(resourceId)
            userImage.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            userName.text = message.userName
            timeStamp.text = returnDateString(message.timeStamp)
            messageBudy.text = message.message
        }

        fun returnDateString(isoString: String) : String{

            val isoFormater = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            isoFormater.timeZone = TimeZone.getTimeZone("UTC")
            var convertedDate = Date()
            try {
                convertedDate = isoFormater.parse(isoString)
            } catch (e: ParseException){
                Log.d("PARSE", "Cannot parse date")
            }

            val outDateString = SimpleDateFormat("EEE, h:mm a", Locale.getDefault())
            return outDateString.format(convertedDate)
        }
    }
}