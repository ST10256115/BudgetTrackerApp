package vcmsa.projects.budgettrackerapp
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpenseEntity>

    @Query("SELECT c.name AS category, SUM(e.amount) FROM expenses e " +
            "JOIN categories c ON e.categoryId = c.id " +
            "WHERE e.date BETWEEN :startDate AND :endDate " +
            "GROUP BY c.name")
    suspend fun getTotalAmountByCategory(startDate: String, endDate: String): List<CategoryTotal>
}
