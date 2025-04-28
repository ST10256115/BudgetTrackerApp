package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        expenseAdapter = ExpenseAdapter(emptyList())
        recyclerView.adapter = expenseAdapter

        // Observe the expenses LiveData
        expenseViewModel.expenses.observe(this, Observer { expenses ->
            expenseAdapter.updateExpenses(expenses)
        })

        // Load all expenses initially
        expenseViewModel.getAllExpenses()

        // Handle "Add Expense" button click
        val btnAddExpense: Button = findViewById(R.id.btnAddExpense)
        btnAddExpense.setOnClickListener {
            val intent = Intent(this, NewExpenseActivity::class.java)
            startActivity(intent)
        }

        // Handle "Add Category" button click
        val btnAddCategory: Button = findViewById(R.id.btnAddCategory)
        btnAddCategory.setOnClickListener {
            val intent = Intent(this, NewCategoryActivity::class.java)
            startActivity(intent)
        }

        // Handle "Sort by Time Period" button click
        val btnSortByTimePeriod: Button = findViewById(R.id.btnSortByTimePeriod)
        btnSortByTimePeriod.setOnClickListener {
            // Show a simple message that sorting is applied
            Toast.makeText(this, "Sorting by Time Period", Toast.LENGTH_SHORT).show()

            // Get expenses for "today"
            val todayStartDate = getTodayStartDate()
            val todayEndDate = getTodayEndDate()
            expenseViewModel.getExpensesBetweenDates(todayStartDate, todayEndDate)
        }
    }

    override fun onResume() {
        super.onResume()
        expenseViewModel.getAllExpenses()
    }

    // Get the start date of today (midnight)
    private fun getTodayStartDate(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    // Get the end date of today (11:59:59 PM)
    private fun getTodayEndDate(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
