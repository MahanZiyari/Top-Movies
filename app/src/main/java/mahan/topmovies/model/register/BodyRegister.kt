package mahan.topmovies.model.register

import com.google.gson.annotations.SerializedName

// Server accepts these parameters in body
data class BodyRegister(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("password")
    var password: String = "",
)
