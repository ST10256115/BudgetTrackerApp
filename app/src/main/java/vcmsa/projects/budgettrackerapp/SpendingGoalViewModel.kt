package vcmsa.projects.budgettrackerapp

import android.app.Application
import android.util.Log
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

    // Insert a new spending goal with error handling
    fun insertGoal(goal: SpendingGoalEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertGoal(goal)
                loadGoal()  // Refresh after inserting
            } catch (e: Exception) {
                Log.e("SpendingGoalViewModel", "Error inserting spending goal", e)
            }
        }
    }

    // Update an existing spending goal with error handling
    fun updateGoal(goal: SpendingGoalEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.updateGoal(goal)
                loadGoal()  // Refresh after updating
            } catch (e: Exception) {
                Log.e("SpendingGoalViewModel", "Error updating spending goal", e)
            }
        }
    }

    // Load the current goal with error handling
    fun loadGoal() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val currentGoal = repository.getSpendingGoal()
                _spendingGoal.postValue(currentGoal)
            } catch (e: Exception) {
                Log.e("SpendingGoalViewModel", "Error loading spending goal", e)
            }
        }
    }
}
