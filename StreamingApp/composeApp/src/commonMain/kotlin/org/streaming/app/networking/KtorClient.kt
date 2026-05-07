package org.streaming.app.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.streaming.app.networking.model.AddMyListRequest
import org.streaming.app.networking.model.AuthResponse
import org.streaming.app.networking.model.ListType
import org.streaming.app.networking.model.Movie
import org.streaming.app.networking.model.LoginRequest
import org.streaming.app.networking.model.MyListRequest
import org.streaming.app.networking.model.ProfileImageRequest
import org.streaming.app.networking.model.Search
import org.streaming.app.networking.model.SearchRequest
import org.streaming.app.networking.model.SignUpRequest
import org.streaming.app.networking.model.TokenPair
import org.streaming.app.networking.model.User

class KtorClient{
    fun getClient(): HttpClient {
        return HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP_LOG: $message")
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout){
                socketTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                requestTimeoutMillis = 15000
            }

            //https://localhost:
//            defaultRequest {
//                url("http://192.168.0.12:8080/")
//                contentType(ContentType.Application.Json)
//            }

            install(DefaultRequest){
                url {

                    protocol = URLProtocol.HTTP
                    host = "192.168.0.12"
                    port =8222
                    headers {
                        append(HttpHeaders.Authorization,"hghjgjhghghhh")
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    //http://localhost:8080/auth/login
    suspend fun login(email: String, password: String): AuthResponse {
        val res: AuthResponse = getClient()
            .post{
                url{
                    path("/api/v1/auth/authenticate")
                }
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }.body<AuthResponse>()
        return res
    }

    suspend fun register(email: String, password: String, fullName: String, phone: String): AuthResponse {
        val response = getClient().post{
            url{
                path("/api/v1/auth/register")
            }
            contentType(ContentType.Application.Json)
            setBody(SignUpRequest(email, password, fullName, phone))
        }.body<AuthResponse>()
        return response
    }

    //user
    suspend fun updateProfileImage(email: String, profileImage: String): Boolean {
        val response = getClient().post{
            url{
                path("/api/v1/user/update-profile-image")
            }
            contentType(ContentType.Application.Json)
            setBody(ProfileImageRequest(email, profileImage))
        }.body<Boolean>()
        return response
    }

    //movie
    suspend fun searchMovies(title: String, email: String): List<Movie> {
        return getClient().get("api/v1/catalog/search") {
            parameter("title", title)
            parameter("email", email)
        }.body()
    }

    suspend fun searchHistoryTop10(email: String): List<Search>{
        val response = getClient().post{
            url{
                path("/api/v1/user/search-history-top")
            }
            contentType(ContentType.Application.Json)
            setBody(SearchRequest(email))
        }.body<List<Search>>()
        return response
    }

    suspend fun releaseYearTop5Movies(): List<Movie>{
        return getClient().get("api/v1/catalog/yearTop5").body()
    }

    suspend fun genreTop5Movies(genre: String): List<Movie>{
        return getClient().get("api/v1/catalog/top5ByGenreOrderByYear"){
            parameter("genre", genre)
        }.body()
    }


    suspend fun myListMovie(email: String, type: ListType): List<Movie>{
        val response = getClient().post{
            url{
                path("/api/v1/catalog/my-list")
            }
            contentType(ContentType.Application.Json)
            setBody(MyListRequest(email = email, type = type))
        }.body<List<Movie>>()
        return response
    }

    suspend fun addMyList(email: String, movieId:Long, type: ListType): List<Movie>{
        val response = getClient().post{
            url{
                path("/api/v1/catalog/add-my-list")
            }
            contentType(ContentType.Application.Json)
            setBody(AddMyListRequest(email = email, movieId = movieId, type = type))
        }.body<List<Movie>>()
        return response
    }

    suspend fun getHomeData(): Map<String,List<Movie>>{
        return getClient().get("api/v1/catalog/home").body()
    }
}