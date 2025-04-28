package vcmsa.projects.budgettrackerapp

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
import java.text.SimpleDateFormat
import java.util.*

class NewExpenseActivity : ComponentActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var spinnerCategory: Spinner
    private var selectedCategoryId: Long = 0
    private lateinit var buttonChoosePhoto: Button
    private var photoPath: String? = null

    // Launcher for picking an image
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            photoPath = it.toString()
            Toast.makeText(this, "Photo selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        // Initialize UI components
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        val editTextStartDate = findViewById<EditText>(R.id.editTextStartDate)  // Start Date EditText
        val editTextEndDate = findViewById<EditText>(R.id.editTextEndDate)      // End Date EditText
        val buttonSaveExpense = findViewById<Button>(R.id.buttonSaveExpense)
        buttonChoosePhoto = findViewById(R.id.buttonChoosePhoto)

        // Initialize ViewModels
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

        // Fetch all categories
        categoryViewModel.getAllCategories()

        // Handle choosing a photo
        buttonChoosePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Handle saving the expense
        buttonSaveExpense.setOnClickListener {
            val description = editTextDescription.text.toString()
            val amountText = editTextAmount.text.toString()
            val startDate = editTextStartDate.text.toString()  // Get the Start Date input
            val endDate = editTextEndDate.text.toString()      // Get the End Date input

            if (description.isEmpty() || amountText.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val amount = amountText.toDouble()

                // Store the raw date as entered by the user (no formatting)
                val newExpense = ExpenseEntity(
                    id = 0,
                    description = description,
                    amount = amount,
                    date = startDate,  // Directly store the entered start date as `date`
                    startTime = startDate,  // Use the Start Date as startTime
                    endTime = endDate,      // Use the End Date as endTime
                    categoryId = selectedCategoryId,
                    photoPath = photoPath
                )

                expenseViewModel.insertExpense(newExpense)
                Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
