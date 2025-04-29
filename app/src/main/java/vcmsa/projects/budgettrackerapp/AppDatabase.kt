package vcmsa.projects.budgettrackerapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class, SpendingGoalEntity::class], // Added SpendingGoalEntity
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao  //added expensedao
    abstract fun categoryDao(): CategoryDao //added category dao
    abstract fun spendingGoalDao(): SpendingGoalDao // Added the SpendingGoalDao
}
