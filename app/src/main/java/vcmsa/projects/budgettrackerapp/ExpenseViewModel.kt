package vcmsa.projects.budgettrackerapp


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseRepository: ExpenseRepository = ExpenseRepository(application)

    // LiveData for expenses
    private val _expenses = MutableLiveData<List<ExpenseEntity>>()
    val expenses: LiveData<List<ExpenseEntity>> get() = _expenses

    // LiveData for category totals
    private val _categoryTotals = MutableLiveData<List<CategoryTotal>>()
    val categoryTotals: LiveData<List<CategoryTotal>> get() = _categoryTotals

    // Insert an expense
    fun insertExpense(expense: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            expenseRepository.insertExpense(expense)
        }
    }

    // Get all expenses
    fun getAllExpenses() {
        CoroutineScope(Dispatchers.IO).launch {
            val expensesList = expenseRepository.getAllExpenses()
            _expenses.postValue(expensesList)
        }
    }

    // Get expenses within a date range
    fun getExpensesBetweenDates(startDate: String, endDate: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val expensesList = expenseRepository.getExpensesBetweenDates(startDate, endDate)
            _expenses.postValue(expensesList)
        }
    }

    // Get total by category within a date range
    fun getTotalByCategory(startDate: String, endDate: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val totals = expenseRepository.getTotalByCategory(startDate, endDate)
            _categoryTotals.postValue(totals)
        }
    }

}