package vcmsa.projects.budgettrackerapp

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ExpenseRepository(context: Context) {

    private val expenseDao: ExpenseDao = DatabaseBuilder.getDatabase(context).expenseDao()
    private val categoryDao: CategoryDao = DatabaseBuilder.getDatabase(context).categoryDao()

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
        // Ensure dates are in the proper format
        val start = formatDate(startDate)
        val end = formatDate(endDate)

        return withContext(Dispatchers.IO) {
            expenseDao.getExpensesBetweenDates(start, end)
        }
    }

    // Get total amount by category in a date range
    suspend fun getTotalByCategory(startDate: String, endDate: String): List<CategoryTotal> {
        val start = formatDate(startDate)
        val end = formatDate(endDate)

        return withContext(Dispatchers.IO) {
            expenseDao.getTotalAmountByCategory(start, end)
        }
    }

    // Format date to ensure it matches the format in the database (e.g., yyyy-MM-dd HH:mm:ss)
    private fun formatDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)
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
