package vcmsa.projects.budgettrackerapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class, SpendingGoalEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun spendingGoalDao(): SpendingGoalDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // This creates the new spending_goals table if migrating from version 2 to 3
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS spending_goals (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        minAmount REAL NOT NULL,
                        maxAmount REAL NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
