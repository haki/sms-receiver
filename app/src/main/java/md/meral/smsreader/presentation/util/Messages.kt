package md.meral.smsreader.presentation.util

import com.google.firebase.firestore.FirebaseFirestore
import md.meral.smsreader.domain.model.MessageModel
import md.meral.smsreader.domain.model.PermissionModel

object Messages {
    var messages: MutableList<MessageModel> = mutableListOf()
}