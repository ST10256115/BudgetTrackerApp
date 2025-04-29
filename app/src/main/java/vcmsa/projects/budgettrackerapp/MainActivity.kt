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

        expenseViewModel.expenses.observe(this, Observer { expenses ->
            if (expenses.isEmpty()) {
                Toast.makeText(this, "No expenses found", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Loaded ${expenses.size} expenses", Toast.LENGTH_SHORT).show()
                expenses.forEach {
                    println("Expense Loaded -> Description: ${it.description}, Amount: ${it.amount}, Date: ${it.date}")
                }
            }
            expenseAdapter.updateExpenses(expenses)
        })

        expenseViewModel.getAllExpenses()

        findViewById<Button>(R.id.btnAddExpense).setOnClickListener {
            startActivity(Intent(this, NewExpenseActivity::class.java))
        }

        findViewById<Button>(R.id.btnAddCategory).setOnClickListener {
            startActivity(Intent(this, NewCategoryActivity::class.java))
        }

        findViewById<Button>(R.id.btnSortByTimePeriod).setOnClickListener {
            showDateRangeDialog()
        }

        findViewById<Button>(R.id.btnResetExpenses).setOnClickListener {
            expenseViewModel.getAllExpenses()
            Toast.makeText(this, "Showing all expenses", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnViewCategoryTotals).setOnClickListener {
            showCategoryTotalsDialog()
        }

        findViewById<Button>(R.id.btnFilterByCategoryAndTime).setOnClickListener {
            showCategoryAndTimeDialog()
        }

        findViewById<Button>(R.id.btnSetSpendingGoal).setOnClickListener {
            startActivity(Intent(this, SetSpendingGoalActivity::class.java))
        }

        findViewById<Button>(R.id.btnDeleteAllExpenses).setOnClickListener {
            showConfirmDeleteDialog()
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
                    expenseViewModel.getExpensesBetweenDates(startDate, endDate)

                    expenseViewModel.expenses.observe(this) { expenses ->
                        expenseViewModel.categories.observe(this) { categories ->
                            val matchedCategory = categories.find { it.name.equals(categoryName, ignoreCase = true) }
                            val matchedCategoryId = matchedCategory?.id

                            if (matchedCategoryId != null) {
                                val filteredExpenses = expenses.filter { it.categoryId == matchedCategoryId }
                                expenseAdapter.updateExpenses(filteredExpenses)
                            } else {
                                Toast.makeText(this, "No matching category found.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun showConfirmDeleteDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete All Expenses")
            .setMessage("Are you sure you want to delete ALL expenses? This action cannot be undone.")
            .setPositiveButton("Yes") { _, _ ->
                expenseViewModel.deleteAllExpenses()
                Toast.makeText(this, "All expenses deleted successfully!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .create()

        dialog.show()
    }
}
