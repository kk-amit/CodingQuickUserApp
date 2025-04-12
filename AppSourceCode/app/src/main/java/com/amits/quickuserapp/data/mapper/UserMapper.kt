package com.amits.quickuserapp.data.mapper

import com.amits.quickuserapp.data.model.UserEntity
import com.amits.quickuserapp.domain.model.User

/**
 * Extension function to map a `UserEntity` object to a `User` object.
 * This function is used to convert the database entity representation of a user
 * into the domain model representation.
 *
 * @receiver UserEntity The `UserEntity` instance to be converted.
 * @return User The domain model representation of the user.
 */
fun UserEntity.toDomain(): User {
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

fun User.toEntity(): UserEntity{
    return UserEntity(
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