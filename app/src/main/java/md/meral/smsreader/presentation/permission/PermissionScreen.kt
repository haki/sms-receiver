package md.meral.smsreader.presentation.permission

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import md.meral.smsreader.MainActivity
import md.meral.smsreader.domain.model.PermissionModel
import md.meral.smsreader.domain.service.PermissionService
import md.meral.smsreader.presentation.util.Messages
import md.meral.smsreader.presentation.util.Permissions
import md.meral.smsreader.presentation.util.Screen

@Composable
fun PermissionScreen(navController: NavController) {
    val permissions = Permissions.permissions

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {

            }
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Home") },
                onClick = {
                    Messages.messages.clear()
                    PermissionService().getAll()
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        },

        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = {
            var text by remember { mutableStateOf("") }

            Column() {
                Row() {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Permission") },
                        modifier = Modifier.padding(start = 14.dp)
                    )

                    Button(onClick = {
                        val permission = hashMapOf(
                            "text" to text,
                            "timestamp" to Timestamp.now()
                        )

                        PermissionService().create(permission = permission)

                        Permissions.permissions.clear()
                        PermissionService().getAll()
                        navController.navigate(Screen.PermissionScreen.route)

                    }, modifier = Modifier.padding(top = 14.dp, start = 14.dp)) {
                        Text(text = "+")
                    }

                }

                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                    items(permissions.size) {
                        PermissionListItem(permission = permissions[it])
                    }
                }
            }
        }
    )
}

@Composable
fun PermissionListItem(permission: PermissionModel) {
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
                Text(text = permission.text)
                Text(text = permission.id)
                Button(
                    onClick = {
                        PermissionService().deleteById(permission.id)

                        Permissions.permissions.clear()
                        PermissionService().getAll()
                    }
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}