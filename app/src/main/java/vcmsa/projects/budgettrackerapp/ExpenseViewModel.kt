package vcmsa.projects.budgettrackerapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseRepository: ExpenseRepository = ExpenseRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)

    private val _expenses = MutableLiveData<List<ExpenseEntity>>()
    val expenses: LiveData<List<ExpenseEntity>> get() = _expenses

    private val _categoryTotals = MutableLiveData<List<CategoryTotal>>()
    val categoryTotals: LiveData<List<CategoryTotal>> get() = _categoryTotals

    private val _categories = MutableLiveData<List<CategoryEntity>>()
    val categories: LiveData<List<CategoryEntity>> get() = _categories

    fun insertExpense(expense: ExpenseEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                expenseRepository.insertExpense(expense)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllExpenses() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val expensesList = expenseRepository.getAllExpenses()
                _expenses.postValue(expensesList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getExpensesBetweenDates(startDate: String, endDate: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val expensesList = expenseRepository.getExpensesBetweenDates(startDate, endDate)
                _expenses.postValue(expensesList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getTotalByCategory(startDate: String, endDate: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val totals = expenseRepository.getTotalByCategory(startDate, endDate)
                _categoryTotals.postValue(totals)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categoriesList = categoryRepository.getAllCategories()
                _categories.postValue(categoriesList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteAllExpenses() {
        CoroutineScope(Dispatchers.IO).launch {
            expenseRepository.deleteAllExpenses()
            getAllExpenses() // Refresh the list after deleting
        }
    }

    //Firestore Test
    fun testWriteToFirestore() {
        val db = FirebaseFirestore.getInstance()
        val testData = hashMapOf(
            "testField" to "Hello Firestore!",
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("testCollection")
            .add(testData)
            .addOnSuccessListener {
                Log.d("FirestoreTest", "Document added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreTest", "Error adding document", e)
            }
    }
}
