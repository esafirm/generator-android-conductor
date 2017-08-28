package <%= appPackage %>.network

import io.reactivex.Single
import <%= appPackage %>.data.UserAgent
import retrofit2.http.GET

interface ApiService {

    @GET("user-agent")
    fun getUserAgent(): Single<UserAgent>

}
