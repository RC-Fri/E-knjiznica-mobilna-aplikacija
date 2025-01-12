import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)

data class Member(val id: Int? = null, val username: String? = null)

//This is supposed to be the whole apps viewmodel

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    private val _member = MutableStateFlow(Member())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(username: String, password: String) {
        _uiState.value = LoginUiState(isLoading = true)

        viewModelScope.launch {
            try {
                var isLoggedIn = DatabaseClient.validateCredentials(username, password)
                if (!isLoggedIn) {
                    _uiState.value = LoginUiState(errorMessage = "Invalid credentials!")
                } else {
                    //TODO: ID
                    _member.update { Member(0, username) }
                    _uiState.value = LoginUiState(isLoggedIn = true)
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState(errorMessage = e.message) // Use message from DatabaseClient
            }

        }
    }
}
