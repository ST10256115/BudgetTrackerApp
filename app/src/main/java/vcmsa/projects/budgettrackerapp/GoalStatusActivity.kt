package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GoalStatusActivity : AppCompatActivity() {

    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GoalStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_status)

        recyclerView = findViewById(R.id.recyclerViewGoalStatus)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GoalStatusAdapter()
        recyclerView.adapter = adapter

        // Observe totals and categories
        expenseViewModel.categoryTotals.observe(this) { totals ->
            expenseViewModel.categories.observe(this) { categories ->
                val goalStatusList = totals.mapNotNull { total ->
                    val category = categories.find { it.name.equals(total.category, ignoreCase = true) }
                    category?.let {
                        GoalStatusData(
                            name = it.name,
                            totalSpent = total.total.toFloat(),
                            minGoal = it.minGoal,
                            maxGoal = it.maxGoal
                        )
                    }
                }
                adapter.submitList(goalStatusList)
            }
        }

        // Load data (totals + categories)
        expenseViewModel.getTotalByCategory("2000-01-01 00:00:00", "2099-12-31 23:59:59")
        expenseViewModel.getAllCategories()
    }
}
