package vcmsa.projects.budgettrackerapp

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpenseRepository(context: Context) {

    private val expenseDao: ExpenseDao = DatabaseBuilder.getDatabase(context).expenseDao()
    private val categoryDao: CategoryDao = DatabaseBuilder.getDatabase(context).categoryDao()  // Accessing CategoryDao

    // Insert an expense into the database
    suspend fun insertExpense(expense: ExpenseEntity) {
        withContext(Dispatchers.IO) {
            expenseDao.insertExpense(expense)
        }
    }

    // Get all expenses from the database
    suspend fun getAllExpenses(): List<ExpenseEntity> {
        return withContext(Dispatchers.IO) {
            expenseDao.getAllExpenses()
        }
    }

    // Get expenses by date range
    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenseEntity> {
        return withContext(Dispatchers.IO) {
            expenseDao.getExpensesBetweenDates(startDate, endDate)
        }
    }

    // Get total amount by category in a date range
    suspend fun getTotalByCategory(startDate: String, endDate: String): List<CategoryTotal> {
        return withContext(Dispatchers.IO) {
            expenseDao.getTotalAmountByCategory(startDate, endDate)
        }
    }

    // Insert a category into the database
    suspend fun insertCategory(category: CategoryEntity) {
        withContext(Dispatchers.IO) {
            categoryDao.insertCategory(category)
        }
    }

    // Get all categories from the database
    suspend fun getAllCategories(): List<CategoryEntity> {
        return withContext(Dispatchers.IO) {
            categoryDao.getAllCategories()
        }
    }
}
