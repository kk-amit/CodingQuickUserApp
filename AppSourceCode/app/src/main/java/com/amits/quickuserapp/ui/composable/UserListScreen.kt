package com.amits.quickuserapp.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.amits.quickuserapp.R
import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.presentation.UserUiState
import com.amits.quickuserapp.presentation.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onUserClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.str_users)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is UserUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is UserUiState.Error -> {
                    ErrorScreen {
                        // First, remove the error state by setting Loading
                        viewModel.setLoadingState()
                        // Load the data using API calls 
                        viewModel.loadUsers()
                    }

                }

                is UserUiState.Success -> {
                    LazyColumn {
                        items((state as UserUiState.Success).users) { user ->
                            Column {
                                ListItem(user = user, onClick = { onUserClick(user.id) })
                                HorizontalDivider(
                                    thickness = dimensionResource(id = R.dimen.thick_ness),
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserListContent(users: List<User>, onUserClick: (Int) -> Unit) {
    LazyColumn {
        items(users) { user ->
            ListItem(user = user, onClick = { onUserClick(user.id) })
        }
    }
}


@Composable
@Preview(showBackground = true)
fun UserListContentPreview() {
    // Hard coded raw data, Only needed for UI design purpose.
    val users = listOf(
        User(
            id = 1,
            name = "Amit Srivastava",
            company = "Arris INC",
            username = "amits",
            email = "kks@example.com",
            address = "456 Oak Street",
            zip = "221002",
            state = "Utter Pradesh",
            country = "India",
            phone = "123-456-789",
            photo = "https://picsum.photos/200"
        ),
        User(
            id = 2,
            name = "Amit Friend",
            company = "Test",
            username = "friendsAmit",
            email = "friendsAmit@example.com",
            address = "789 Pine Avenue",
            zip = "200102",
            state = "UP",
            country = "India",
            phone = "123-456-789",
            photo = "https://picsum.photos/200"
        )
    )

    UserListContent(users = users, onUserClick = {})
}

