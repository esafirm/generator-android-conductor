package <%= appPackage %>.data

import com.google.gson.annotations.SerializedName

data class UserAgent(
        @SerializedName("user-agent") val userAgent: String
)
