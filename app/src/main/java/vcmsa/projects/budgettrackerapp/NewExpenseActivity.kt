package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class NewExpenseActivity : ComponentActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        val editTextCategory = findViewById<EditText>(R.id.editTextCategory)
        val editTextStartTime = findViewById<EditText>(R.id.editTextStartTime)
        val editTextEndTime = findViewById<EditText>(R.id.editTextEndTime)
        val buttonSaveExpense = findViewById<Button>(R.id.buttonSaveExpense)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        buttonSaveExpense.setOnClickListener {
            val description = editTextDescription.text.toString()
            val amountText = editTextAmount.text.toString()
            val category = editTextCategory.text.toString()
            val startTime = editTextStartTime.text.toString()
            val endTime = editTextEndTime.text.toString()

            if (description.isEmpty() || amountText.isEmpty() || category.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val amount = amountText.toDouble()

                val newExpense = ExpenseEntity(
                    id = 0,
                    description = description,
                    amount = amount,
                    date = System.currentTimeMillis().toString(), // We can improve formatting later
                    startTime = startTime,
                    endTime = endTime,
                    category = category
                )

                expenseViewModel.insertExpense(newExpense)

                Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }
}