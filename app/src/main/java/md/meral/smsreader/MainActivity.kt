package md.meral.smsreader

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import md.meral.smsreader.domain.model.MessageModel
import md.meral.smsreader.domain.model.PermissionModel
import md.meral.smsreader.domain.service.MessageService
import md.meral.smsreader.domain.service.PermissionService
import md.meral.smsreader.presentation.util.Messages
import md.meral.smsreader.presentation.util.Navigation
import md.meral.smsreader.presentation.util.Permissions
import md.meral.smsreader.ui.theme.SMSReaderTheme

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val br = MessageService().receiveMessage()
                registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MessageService().getAll()
        PermissionService().getAll()

        setContent {
            val navController = rememberNavController()

            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)

            SMSReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}