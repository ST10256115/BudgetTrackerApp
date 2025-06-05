package vcmsa.projects.budgettrackerapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CustomBarChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val barPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val labelPaint = Paint().apply {
        color = Color.BLACK
        textSize = 36f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    private val valuePaint = Paint().apply {
        color = Color.DKGRAY
        textSize = 32f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    private var categoryData: List<Pair<String, Float>> = emptyList()

    // Optional: Colors to cycle through
    private val barColors = listOf(
        Color.parseColor("#2196F3"),
        Color.parseColor("#4CAF50"),
        Color.parseColor("#FFC107"),
        Color.parseColor("#F44336"),
        Color.parseColor("#9C27B0")
    )

    fun setData(data: List<Pair<String, Float>>) {
        categoryData = data
        invalidate() // Redraw the chart
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (categoryData.isEmpty()) return

        val barSpacing = 40f
        val barWidth = (width - (categoryData.size + 1) * barSpacing) / categoryData.size
        val maxBarHeight = height * 0.6f
        val maxValue = categoryData.maxOf { it.second }

        categoryData.forEachIndexed { index, (category, amount) ->
            val left = barSpacing + index * (barWidth + barSpacing)
            val right = left + barWidth
            val top = height - (amount / maxValue) * maxBarHeight - 80f
            val bottom = height - 120f

            // Alternate bar colors
            barPaint.color = barColors[index % barColors.size]

            // Draw bar
            canvas.drawRoundRect(RectF(left, top, right, bottom), 16f, 16f, barPaint)

            // Draw value label above bar
            val centerX = (left + right) / 2
            canvas.drawText("R${amount.toInt()}", centerX, top - 10f, valuePaint)

            // Draw category label below bar
            canvas.drawText(category, centerX, height - 60f, labelPaint)
        }
    }
}
