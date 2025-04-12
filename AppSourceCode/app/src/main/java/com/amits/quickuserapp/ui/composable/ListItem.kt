package com.amits.quickuserapp.ui.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.amits.quickuserapp.R
import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.util.MAX_LINES

@Composable
fun ListItem(user: User, onClick: () -> Unit) {
    val TAG = "ListItem"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        AsyncImage(
            model = user.photo,
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.avatar_size))
                .clip(CircleShape),
            onLoading = {
                // Log the Loading info
                Log.d(TAG, "Loading image")
            },
            onError = {
                // Log Error Message
                Log.d(TAG, "Error loading image: ${it.result.throwable}")
            },
            error = painterResource(R.drawable.ic_launcher_background),
            fallback = painterResource(R.drawable.ic_launcher_background),
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_width)))

        Column {
            Text(text = user.name, fontWeight = FontWeight.Bold, maxLines = MAX_LINES)
            Text(text = user.email)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ListItemPreview() {
    val sampleUser = User(
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
    )

    ListItem(user = sampleUser, onClick = {})
}