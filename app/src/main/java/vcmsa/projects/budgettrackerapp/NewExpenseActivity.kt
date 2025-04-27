package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import android.view.View
import android.widget.AdapterView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class NewExpenseActivity : ComponentActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var spinnerCategory: Spinner
    private var selectedCategoryId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        val editTextStartTime = findViewById<EditText>(R.id.editTextStartTime)
        val editTextEndTime = findViewById<EditText>(R.id.editTextEndTime)
        val buttonSaveExpense = findViewById<Button>(R.id.buttonSaveExpense)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Observe categories and populate spinner
        categoryViewModel.categories.observe(this, { categories ->
            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter

            // Set the item selected listener for the spinner
            spinnerCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedCategoryId = categories[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(this@NewExpenseActivity, "Please select a category", Toast.LENGTH_SHORT).show()
                }
            })
        })

        // Fetch categories when the activity starts
        categoryViewModel.getAllCategories()

        buttonSaveExpense.setOnClickListener {
            val description = editTextDescription.text.toString()
            val amountText = editTextAmount.text.toString()
            val startTime = editTextStartTime.text.toString()
            val endTime = editTextEndTime.text.toString()

            if (description.isEmpty() || amountText.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val amount = amountText.toDouble()

                val newExpense = ExpenseEntity(
                    id = 0,  // ID will be auto-generated
                    description = description,
                    amount = amount,
                    date = System.currentTimeMillis().toString(),
                    startTime = startTime,
                    endTime = endTime,
                    categoryId = selectedCategoryId  // Save the selected category ID
                )

                expenseViewModel.insertExpense(newExpense)

                Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }
}
