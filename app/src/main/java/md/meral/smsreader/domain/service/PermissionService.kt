package md.meral.smsreader.domain.service

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import md.meral.smsreader.MainActivity
import md.meral.smsreader.domain.model.PermissionModel
import md.meral.smsreader.presentation.util.Permissions

class PermissionService {
    val db = Firebase.firestore

    fun create(permission: Any) {
        // Add a new document with a generated ID
        db.collection("permissions")
            .add(permission)
            .addOnFailureListener { e ->
                Log.w("Permission", "Error adding permission", e)
            }
    }

    fun deleteById(id: String) {
        db.collection("permissions").document(id)
            .delete()
            .addOnFailureListener { e ->
                Log.w(
                    "PermissionDelete",
                    "Error deleting permission",
                    e
                )
            }
    }

    fun getAll() {
        db.collection("permissions")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (permission in result) {
                    Log.d("Permission", "${permission.id} => ${permission.data}")
                    val perm = PermissionModel(permission.data["text"].toString(), permission.id)
                    Permissions.permissions.add(perm)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Message", "Error getting messages.", exception)
            }
    }
}