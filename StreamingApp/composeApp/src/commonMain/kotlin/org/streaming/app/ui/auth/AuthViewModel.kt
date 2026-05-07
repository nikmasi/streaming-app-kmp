package org.streaming.app.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.lib.ksafe.KSafe
import eu.anifantakis.lib.ksafe.compose.mutableStateOf
import kotlinx.coroutines.launch
import org.streaming.app.networking.KtorClient
import org.streaming.app.networking.model.TokenPair
import org.streaming.app.networking.model.User
import org.streaming.app.networking.model.UserInformation

class AuthViewModel(
    private val ktorClient: KtorClient,
    private val ksafe: KSafe
) : ViewModel() {
    
    var counter by ksafe.mutableStateOf(0)
        private set
    
    var userProfile by ksafe.mutableStateOf(
        User("","","","",null)
    )
        private set

    var token by ksafe.mutableStateOf(
        TokenPair("","")
    )
    
    fun incrementCounter(){
        counter++
    }

    
    fun updateProfile(email: String, password: String, fullName: String, phone: String){
        userProfile = User(
            email = email, hashedPassword = password,
            fullName = fullName,
            phone = phone,
            profileImage = userProfile.profileImage
        )
    }

    fun updateToken(refresh: String, access: String){
        token = TokenPair(access,refresh)
    }
    fun updateUserProfileImage(profileImage: String) {
        userProfile = userProfile.copy(profileImage = profileImage)
    }
    
    var loginState by mutableStateOf<LoginResult?>(null)
        private set

    var registerState by mutableStateOf<UserInformation?>(null)
        private set

//    fun login(email: String, password: String) {
//        viewModelScope.launch {
//            try {
//                val authResponse = ktorClient.login(email, password)
//                loginState = LoginResult.Success(authResponse.tokens)
//
//                updateToken(
//                    access = authResponse.tokens.accessToken,
//                    refresh = authResponse.tokens.refreshToken
//                )
//
//                updateProfile(authResponse.userInformation.email,
//                    "",authResponse.userInformation.fullName,
//                    authResponse.userInformation.phone
//                )
//
//            } catch (e: Exception) {
//                loginState = LoginResult.Error(e.message ?: "Nepoznata greška")
//            }
//        }
//    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val authResponse = ktorClient.login(email, password)
                loginState = LoginResult.Success(TokenPair(authResponse.access_token,authResponse.refresh_token))

                updateToken(
                    access = authResponse.access_token,
                    refresh = authResponse.refresh_token
                )

//                updateProfile(authResponse.userInformation.email,
//                    "",authResponse.userInformation.fullName,
//                    authResponse.userInformation.phone
//                )

            } catch (e: Exception) {
                loginState = LoginResult.Error(e.message ?: "Nepoznata greška")
            }
        }
    }

    fun resetState() { loginState = null }
    fun resetRegisterState() { registerState = null }

    fun logout() {
        viewModelScope.launch {
            token = TokenPair("", "")
            userProfile = User( "", "", "", "",null)

            loginState = null
            registerState = null
        }
    }

//    fun register(email: String, password: String, fullName: String, phone: String) {
//        viewModelScope.launch {
//            try {
//                val authResponse = ktorClient.register(email, password, fullName, phone)
//                registerState = authResponse.userInformation
//
//                updateToken(
//                    access = authResponse.tokens.accessToken,
//                    refresh = authResponse.tokens.refreshToken
//                )
//
//                updateProfile(authResponse.userInformation.email,
//                    "",authResponse.userInformation.fullName,
//                    authResponse.userInformation.phone
//                )
//            } catch (e: Exception) {
//                registerState = null
//            }
//        }
//    }

    fun register(email: String, password: String, fullName: String, phone: String) {
        viewModelScope.launch {
            try {
                val authResponse = ktorClient.register(email, password, fullName, phone)
               // registerState = authResponse.userInformation

                updateToken(
                    access = authResponse.access_token,
                    refresh = authResponse.refresh_token
                )

//                updateProfile(authResponse.userInformation.email,
//                    "",authResponse.userInformation.fullName,
//                    authResponse.userInformation.phone
//                )
            } catch (e: Exception) {
                registerState = null
            }
        }
    }

    fun updateProfileImage(profileImage: String) {
        viewModelScope.launch {
            try {
                val success = ktorClient.updateProfileImage(userProfile.email, profileImage)
                if (success) {
                    updateUserProfileImage(profileImage)
                }
            } catch (e: Exception) {
                println("Greška pri updejtu slike: ${e.message}")
            }
        }
    }
}

sealed class LoginResult {
    data class Success(val tokens: TokenPair) : LoginResult()
    data class Error(val message: String) : LoginResult()
}