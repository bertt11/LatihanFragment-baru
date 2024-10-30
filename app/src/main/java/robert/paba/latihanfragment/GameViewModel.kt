package robert.paba.latihanfragment

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var score: Int = 50
    var matchedIndices = mutableListOf<Int>()
}