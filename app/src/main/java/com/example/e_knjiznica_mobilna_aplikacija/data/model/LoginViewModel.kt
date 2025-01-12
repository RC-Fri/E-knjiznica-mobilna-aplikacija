import DatabaseClient.initializeConnection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val userId : Int = -1,
    val username : String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)


class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                //For some reason you have to login twice for this to work TODO: Fix!
                if(initializeConnection()){
                    val isLoggedIn = DatabaseClient.validateCredentials(username, password)
                    if (isLoggedIn == -1) {
                        _uiState.value = LoginUiState(errorMessage = "Invalid credentials!")
                    } else {
                        _uiState.value = _uiState.value.copy(isLoggedIn = true, userId = isLoggedIn,
                            username = username)
                    }
                }
                else
                    _uiState.value = LoginUiState(errorMessage = "Connection not established!")
            } catch (e: Exception) {
                _uiState.value = LoginUiState(errorMessage = e.message) // Use message from DatabaseClient
            }

        }
    }
}
