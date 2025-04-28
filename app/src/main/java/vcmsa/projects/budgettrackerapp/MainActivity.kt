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
    }

    override fun onResume() {
        super.onResume()
        expenseViewModel.getAllExpenses()
    }

    // This function shows a dialog to enter start and end dates
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
}
