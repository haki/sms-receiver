package md.meral.smsreader.domain.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Telephony
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import md.meral.smsreader.domain.model.MessageModel
import md.meral.smsreader.presentation.util.Messages
import md.meral.smsreader.presentation.util.Permissions

class MessageService {
    val db = Firebase.firestore

    fun getAll() {
        db.collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (message in result) {
                    Log.d("Message", "${message.id} => ${message.data}")
                    val msg = MessageModel(message.data["from"].toString(), message.data["text"].toString())
                    Messages.messages.add(msg)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Message", "Error getting messages.", exception)
            }
    }

    fun receiveMessage(): BroadcastReceiver {
        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                    val from = sms.displayOriginatingAddress
                    val text = sms.displayMessageBody
                    val timestamp = Timestamp.now()

                    val message = hashMapOf(
                        "from" to from,
                        "text" to text,
                        "timestamp" to timestamp
                    )

                    for (permission in Permissions.permissions) {
                        if (permission.text.lowercase().equals(from.lowercase())) {
                            // Add a new document with a generated ID
                            create(message)

                            break
                        }
                    }
                }
            }
        }

        return br
    }

    private fun create(message: Any) {
        db.collection("messages")
            .add(message)
            .addOnFailureListener { e ->
                Log.w("Message", "Error adding message", e)
            }
    }
}