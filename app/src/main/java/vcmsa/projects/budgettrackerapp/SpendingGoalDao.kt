package vcmsa.projects.budgettrackerapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SpendingGoalDao {

    @Insert
    suspend fun insertGoal(goal: SpendingGoalEntity)

    @Update
    suspend fun updateGoal(goal: SpendingGoalEntity)

    @Query("SELECT * FROM spending_goals LIMIT 1")
    suspend fun getSpendingGoal(): SpendingGoalEntity?


}
