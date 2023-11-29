package viewmodel

import android.util.Log
import model.FlowDeckRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(private val repository: FlowDeckRepository) : ViewModel() {

    private val _userRegistrationState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val userRegistrationState = _userRegistrationState.asStateFlow()

    private val _userLoginState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val userLoginState = _userLoginState.asStateFlow()

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _userRegistrationState.value = Resource.Loading()
            try {
                val result = repository.registerUser(email, password).await()
                _userRegistrationState.value = Resource.Success(result.user?.uid ?: "")
                Log.d("AuthViewModel", "Registration successful: ${result.user?.uid}")
            } catch (e: Exception) {
                _userRegistrationState.value = Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
                Log.e("AuthViewModel", "Registration failed with exception: ${e.message}", e)
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                _userLoginState.value = Resource.Loading()
                val result = repository.loginUser(email, password).await()
                _userLoginState.value = Resource.Success(result.user?.uid ?: "")
            } catch (e: Exception) {
                _userLoginState.value = Resource.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}

// A generic resource class for handling loading, success, and error states
sealed class Resource<out T> {
    class Idle<out T> : Resource<T>()
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val message: String) : Resource<T>()
}