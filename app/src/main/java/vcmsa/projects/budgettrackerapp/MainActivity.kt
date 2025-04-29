package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button
import android.widget.Toast

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

        val btnAddExpense: Button = findViewById(R.id.btnAddExpense)
        btnAddExpense.setOnClickListener {
            val intent = Intent(this, NewExpenseActivity::class.java)
            startActivity(intent)
        }

        val btnAddCategory: Button = findViewById(R.id.btnAddCategory)
        btnAddCategory.setOnClickListener {
            val intent = Intent(this, NewCategoryActivity::class.java)
            startActivity(intent)
        }

        val btnSortByTimePeriod: Button = findViewById(R.id.btnSortByTimePeriod)
        btnSortByTimePeriod.setOnClickListener {
            showDateRangeDialog()
        }

        val btnResetExpenses: Button = findViewById(R.id.btnResetExpenses)
        btnResetExpenses.setOnClickListener {
            expenseViewModel.getAllExpenses()
            Toast.makeText(this, "Showing all expenses", Toast.LENGTH_SHORT).show()
        }

        val btnViewCategoryTotals: Button = findViewById(R.id.btnViewCategoryTotals)
        btnViewCategoryTotals.setOnClickListener {
            showCategoryTotalsDialog()
        }

        val btnFilterByCategoryAndTime: Button = findViewById(R.id.btnFilterByCategoryAndTime)
        btnFilterByCategoryAndTime.setOnClickListener {
            showCategoryAndTimeDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        expenseViewModel.getAllExpenses()
    }

    private fun showDateRangeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_date_range, null)
        val editStartDate = dialogView.findViewById<EditText>(R.id.editStartDate)
        val editEndDate = dialogView.findViewById<EditText>(R.id.editEndDate)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Select Time Period")
            .setView(dialogView)
            .setPositiveButton("Filter") { _, _ ->
                val startDate = editStartDate.text.toString()
                val endDate = editEndDate.text.toString()

                if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    expenseViewModel.getExpensesBetweenDates(startDate, endDate)
                } else {
                    Toast.makeText(this, "Please enter both start and end dates.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun showCategoryTotalsDialog() {
        expenseViewModel.categoryTotals.observe(this) { totals ->
            if (totals.isEmpty()) {
                Toast.makeText(this, "No data to display.", Toast.LENGTH_SHORT).show()
            } else {
                val totalsMessage = totals.joinToString("\n") { "${it.category}: R${it.total}" }
                AlertDialog.Builder(this)
                    .setTitle("Category Totals")
                    .setMessage(totalsMessage)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Fetch totals from 2000 to 2099 to include everything
        expenseViewModel.getTotalByCategory("2000-01-01 00:00:00", "2099-12-31 23:59:59")
    }

    private fun showCategoryAndTimeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_category_time_range, null)
        val editCategoryName = dialogView.findViewById<EditText>(R.id.editCategoryName)
        val editStartDate = dialogView.findViewById<EditText>(R.id.editStartDate)
        val editEndDate = dialogView.findViewById<EditText>(R.id.editEndDate)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Filter by Category and Time")
            .setView(dialogView)
            .setPositiveButton("Filter") { _, _ ->
                val categoryName = editCategoryName.text.toString()
                val startDate = editStartDate.text.toString()
                val endDate = editEndDate.text.toString()

                if (categoryName.isNotEmpty() && startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    // Filter manually after fetching expenses
                    expenseViewModel.getExpensesBetweenDates(startDate, endDate)

                    expenseViewModel.expenses.observe(this) { expenses ->
                        val filtered = expenses.filter { it.categoryId.toString() == categoryName }
                        expenseAdapter.updateExpenses(filtered)
                    }
                } else {
                    Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
