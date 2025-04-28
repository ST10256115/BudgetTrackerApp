package vcmsa.projects.budgettrackerapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import android.view.View
import android.widget.AdapterView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

class NewExpenseActivity : ComponentActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var spinnerCategory: Spinner
    private var selectedCategoryId: Long = 0

    private lateinit var buttonChoosePhoto: Button
    private var photoPath: String? = null

    // Using the new way to get image (no deprecated methods)
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            photoPath = uri.toString()
            Toast.makeText(this, "Photo selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        val editTextStartTime = findViewById<EditText>(R.id.editTextStartTime)
        val editTextEndTime = findViewById<EditText>(R.id.editTextEndTime)
        val buttonSaveExpense = findViewById<Button>(R.id.buttonSaveExpense)
        buttonChoosePhoto = findViewById(R.id.buttonChoosePhoto)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Observe categories and populate spinner
        categoryViewModel.categories.observe(this) { categories ->
            val categoryNames = categories.map { it.name }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter

            spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedCategoryId = categories[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    Toast.makeText(this@NewExpenseActivity, "Please select a category", Toast.LENGTH_SHORT).show()
                }
            }
        }

        categoryViewModel.getAllCategories()

        // Handle choosing a photo
        buttonChoosePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Handle saving the expense
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
                    id = 0,
                    description = description,
                    amount = amount,
                    date = System.currentTimeMillis().toString(),
                    startTime = startTime,
                    endTime = endTime,
                    categoryId = selectedCategoryId,
                    photoPath = photoPath // Save selected photo path
                )

                expenseViewModel.insertExpense(newExpense)

                Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }
}
