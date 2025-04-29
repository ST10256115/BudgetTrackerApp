package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class SetSpendingGoalActivity : ComponentActivity() {

    private lateinit var spendingGoalViewModel: SpendingGoalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_spending_goal)

        val editMinGoal = findViewById<EditText>(R.id.editMinGoal)
        val editMaxGoal = findViewById<EditText>(R.id.editMaxGoal)
        val buttonSaveGoal = findViewById<Button>(R.id.buttonSaveGoal)

        spendingGoalViewModel = ViewModelProvider(this)[SpendingGoalViewModel::class.java]

        // Pre-fill the input fields if a goal already exists
        spendingGoalViewModel.spendingGoal.observe(this) { goal ->
            goal?.let {
                editMinGoal.setText(it.minAmount.toString())
                editMaxGoal.setText(it.maxAmount.toString())
            }
        }

        spendingGoalViewModel.loadGoal()

        buttonSaveGoal.setOnClickListener {
            val minGoalText = editMinGoal.text.toString()
            val maxGoalText = editMaxGoal.text.toString()

            if (minGoalText.isEmpty() || maxGoalText.isEmpty()) {
                Toast.makeText(this, "Please enter both minimum and maximum goals.", Toast.LENGTH_SHORT).show()
            } else {
                val minAmount = minGoalText.toDouble()
                val maxAmount = maxGoalText.toDouble()

                val goal = SpendingGoalEntity(
                    id = 0,
                    minAmount = minAmount,
                    maxAmount = maxAmount
                )

                spendingGoalViewModel.insertGoal(goal)

                Toast.makeText(this, "Spending goals saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
