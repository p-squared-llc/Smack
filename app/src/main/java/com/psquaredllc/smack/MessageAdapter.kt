package com.psquaredllc.smack
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.psquaredllc.smack.model.Message
import com.psquaredllc.smack.services.UserDataService


class MessageAdapter(context: Context, messages: List<Message>) : BaseAdapter() {

    val context = context
    val messages = messages
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.message_adapter_view, null, true)

            holder.userMessageText = convertView!!.findViewById(R.id.userMessageText) as TextView
            holder.userNameText = convertView.findViewById(R.id.userNameText) as TextView
            holder.dateStampText = convertView.findViewById(R.id.dateStampText) as TextView
            holder.userAvatar = convertView.findViewById(R.id.userImage) as ImageView
            holder.userAvatarColor = messages[position].userAvatarColor


            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.userMessageText!!.text = messages[position].message
        holder.userNameText!!.text = messages[position].userName
        holder.dateStampText!!.text = messages[position].timeStamp
        val resourceId =  this.context.resources.getIdentifier(messages[position].userAvatar, "drawable", context.packageName)
        holder.userAvatar!!.setImageResource(resourceId)
        holder.userAvatar!!.setBackgroundColor(UserDataService.returnAvatarColor(messages[position].userAvatarColor))

        return convertView

    }


    private inner class ViewHolder {

        var userMessageText: TextView? = null
        var userNameText: TextView? = null
        var dateStampText: TextView? = null
        var userAvatar: ImageView? = null
        var userAvatarColor: String = ""

    }
    override fun getItem(position: Int): Any {
        return messages[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {

        return messages.count()
    }
}