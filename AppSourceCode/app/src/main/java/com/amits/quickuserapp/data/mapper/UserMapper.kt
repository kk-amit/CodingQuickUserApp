package com.amits.quickuserapp.data.mapper

import com.amits.quickuserapp.data.model.UserRemote
import com.amits.quickuserapp.domain.model.User

fun UserRemote.toDomain(): User {
    return User(
        id = this.id,
        name = this.name,
        company = this.company,
        username = this.username,
        email = this.email,
        address = this.address,
        zip = this.zip,
        state = this.state,
        country = this.country,
        phone = this.phone,
        photo = this.photo
    )
}