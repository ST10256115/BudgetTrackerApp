package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class SetSpendingGoalActivity : ComponentActivity() {

    private lateinit var spendingGoalViewModel: SpendingGoalViewModel

    private lateinit var seekBarMinGoal: SeekBar
    private lateinit var seekBarMaxGoal: SeekBar
    private lateinit var textMinGoalLabel: TextView
    private lateinit var textMaxGoalLabel: TextView
    private lateinit var buttonSaveGoal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_spending_goal)

        // Link UI elements
        seekBarMinGoal = findViewById(R.id.seekBarMinGoal)
        seekBarMaxGoal = findViewById(R.id.seekBarMaxGoal)
        textMinGoalLabel = findViewById(R.id.textMinGoalLabel)
        textMaxGoalLabel = findViewById(R.id.textMaxGoalLabel)
        buttonSaveGoal = findViewById(R.id.buttonSaveGoal)

        spendingGoalViewModel = ViewModelProvider(this)[SpendingGoalViewModel::class.java]

        // Update TextViews when SeekBars move
        seekBarMinGoal.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textMinGoalLabel.text = "Minimum Goal: R$progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarMaxGoal.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textMaxGoalLabel.text = "Maximum Goal: R$progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Pre-fill SeekBars if goal exists
        spendingGoalViewModel.spendingGoal.observe(this) { goal ->
            goal?.let {
                seekBarMinGoal.progress = it.minAmount.toInt()
                seekBarMaxGoal.progress = it.maxAmount.toInt()
            }
        }

        spendingGoalViewModel.loadGoal()

        // Save button clicked
        buttonSaveGoal.setOnClickListener {
            val minGoal = seekBarMinGoal.progress.toDouble()
            val maxGoal = seekBarMaxGoal.progress.toDouble()

            if (minGoal > maxGoal) {
                Toast.makeText(this, "Minimum goal cannot be greater than maximum goal.", Toast.LENGTH_SHORT).show()
            } else {
                val goal = SpendingGoalEntity(
                    id = 0,
                    minAmount = minGoal,
                    maxAmount = maxGoal
                )
                spendingGoalViewModel.insertGoal(goal)

                Toast.makeText(this, "Spending goals saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
