package vcmsa.projects.budgettrackerapp

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpendingGoalRepository(context: Context) {

    private val spendingGoalDao: SpendingGoalDao = DatabaseBuilder.getDatabase(context).spendingGoalDao()

    // Insert a new goal
    suspend fun insertGoal(goal: SpendingGoalEntity) {
        withContext(Dispatchers.IO) {
            spendingGoalDao.insertGoal(goal)
        }
    }

    // Update an existing goal
    suspend fun updateGoal(goal: SpendingGoalEntity) {
        withContext(Dispatchers.IO) {
            spendingGoalDao.updateGoal(goal)
        }
    }

    // Get the current goal
    suspend fun getSpendingGoal(): SpendingGoalEntity? {
        return withContext(Dispatchers.IO) {
            spendingGoalDao.getSpendingGoal()
        }
    }
}
