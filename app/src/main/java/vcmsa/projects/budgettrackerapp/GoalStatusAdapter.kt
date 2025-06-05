package vcmsa.projects.budgettrackerapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

data class GoalStatusData(
    val name: String,
    val totalSpent: Float,
    val minGoal: Float?,
    val maxGoal: Float?
)

class GoalStatusAdapter :
    ListAdapter<GoalStatusData, GoalStatusAdapter.GoalStatusViewHolder>(DIFF_CALLBACK) {

    inner class GoalStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryText: TextView = itemView.findViewById(R.id.textCategory)
        val spentText: TextView = itemView.findViewById(R.id.textSpent)
        val goalText: TextView = itemView.findViewById(R.id.textGoalRange)
        val statusDot: View = itemView.findViewById(R.id.statusDot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalStatusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal_status, parent, false)
        return GoalStatusViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalStatusViewHolder, position: Int) {
        val item = getItem(position)

        holder.categoryText.text = item.name
        holder.spentText.text = "Spent: R${item.totalSpent.toInt()}"

        val min = item.minGoal ?: 0f
        val max = item.maxGoal ?: Float.MAX_VALUE
        holder.goalText.text = "Goal: R${min.toInt()} – R${if (item.maxGoal != null) max.toInt() else "∞"}"

        // Determine goal status color
        val color = when {
            item.totalSpent < min -> Color.YELLOW
            item.totalSpent > max -> Color.RED
            else -> Color.GREEN
        }
        holder.statusDot.setBackgroundColor(color)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GoalStatusData>() {
            override fun areItemsTheSame(oldItem: GoalStatusData, newItem: GoalStatusData) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: GoalStatusData, newItem: GoalStatusData) =
                oldItem == newItem
        }
    }
}
