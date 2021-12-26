package md.meral.smsreader.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import md.meral.smsreader.MainActivity
import md.meral.smsreader.domain.model.MessageModel
import md.meral.smsreader.presentation.util.Messages
import md.meral.smsreader.presentation.util.Screen

@Composable
fun HomeScreen(navController: NavController) {
    val messages = Messages.messages

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {

            }
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Permissions") },
                onClick = { navController.navigate(Screen.PermissionScreen.route) }
            )
        },

        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = {
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                items(messages.size) {
                    MessageListItem(message = messages[it])
                }
            }
        }
    )
}

@Composable
fun MessageListItem(message: MessageModel) {
    Card(

        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxSize(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {

        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            ) {

                Text(text = message.from)
                Text(text = message.text)
            }
        }
    }
}
