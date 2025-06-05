package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class ChartActivity : AppCompatActivity() {

    private val expenseViewModel: ExpenseViewModel by viewModels()
    private lateinit var chartView: CustomBarChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        chartView = findViewById(R.id.customBarChart)

        // Observe real category totals
        expenseViewModel.categoryTotals.observe(this) { totals ->
            val chartData = totals.map {
                it.category to it.total.toFloat()
            }
            chartView.setData(chartData)
        }

        // Load totals for all time (you can later filter by date if needed)
        expenseViewModel.getTotalByCategory(
            startDate = "2000-01-01 00:00:00",
            endDate = "2099-12-31 23:59:59"
        )
    }
}
