package com.example.randomuserlist.models

data class UsersResponse(
    val results: Set<User>,
)
data class User(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: BirthDate,
    val registered: Register,
    val phone: String,
    val cell: String,
    val id: ID,
    val picture: Picture,
    val nat: String
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as User
        if (email != user.email) return false
        return true
    }
}

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)

data class Street(
    val number: String,
    val name: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Timezone(
    val offset: String,
    val description: String
)

data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

data class BirthDate(
    val date: String,
    val age: String
)

data class Register(
    val date: String,
    val age: String
)

data class ID(
    val name: String,
    val value: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)