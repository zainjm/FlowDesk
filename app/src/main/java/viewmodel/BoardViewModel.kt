package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.Board

class BoardViewModel(private val boardRepository: BoardRepository) : ViewModel() {

    // The UI collects from this StateFlow to get its state updates
    private val _boardState = MutableStateFlow<Resource<Board>>(Resource.Loading())
    val boardState: StateFlow<Resource<Board>> = _boardState

    init {
        fetchBoard()
    }

    private fun fetchBoard() {
        viewModelScope.launch {
            _boardState.value = Resource.Loading()
            try {
                // Assuming boardRepository.getBoard() makes a network or database call to get the board
                val board = boardRepository.getBoard()
                _boardState.value = Resource.Success(board)
            } catch (e: Exception) {
                _boardState.value = Resource.Error("Unable to fetch board")
            }
        }
    }

    // Add any other logic needed for your BoardScreen here, such as adding, moving or deleting tasks
}
