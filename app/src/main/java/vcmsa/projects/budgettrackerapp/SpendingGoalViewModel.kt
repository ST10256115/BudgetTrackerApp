package vcmsa.projects.budgettrackerapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpendingGoalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpendingGoalRepository(application)

    private val _spendingGoal = MutableLiveData<SpendingGoalEntity?>()
    val spendingGoal: LiveData<SpendingGoalEntity?> get() = _spendingGoal

    // Insert a new spending goal
    fun insertGoal(goal: SpendingGoalEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertGoal(goal)
            loadGoal()  // Refresh after inserting
        }
    }

    // Update an existing spending goal
    fun updateGoal(goal: SpendingGoalEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateGoal(goal)
            loadGoal()  // Refresh after updating
        }
    }

    // Load the current goal
    fun loadGoal() {
        CoroutineScope(Dispatchers.IO).launch {
            val currentGoal = repository.getSpendingGoal()
            _spendingGoal.postValue(currentGoal)
        }
    }
}
