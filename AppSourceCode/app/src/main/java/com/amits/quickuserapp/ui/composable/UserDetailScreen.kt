package com.amits.quickuserapp.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.amits.quickuserapp.R
import com.amits.quickuserapp.presentation.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel(),
    userId: Int
) {
    val user by viewModel.user.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUser(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { user?.name?.let { Text(text = it) } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                // Profile Picture
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.image_height))
                        .padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = user?.photo),
                        contentDescription = "User Profile Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // User Information
                Text(
                    text = "${stringResource(id = R.string.str_company)}: ${user?.company}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )

                Text(
                    text = "${stringResource(id = R.string.str_user_name)}: ${user?.username}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )

                Text(
                    text = "${stringResource(id = R.string.str_email)}: ${user?.email}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )

                Text(
                    text = "${stringResource(id = R.string.str_address)}: ${user?.address}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )

                Text(
                    text = "${stringResource(id = R.string.str_zip)}: ${user?.zip}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )

                Text(
                    text = "${stringResource(id = R.string.str_state)}: ${user?.state}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )

                Text(
                    text = "${stringResource(id = R.string.str_country)}: ${user?.country}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )

                Text(
                    text = "${stringResource(id = R.string.str_phone)}: ${user?.phone}",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    )
}
