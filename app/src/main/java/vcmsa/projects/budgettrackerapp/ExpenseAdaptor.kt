package vcmsa.projects.budgettrackerapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private var expenses: List<ExpenseEntity>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionTextView: TextView = itemView.findViewById(R.id.textDescription)
        val amountTextView: TextView = itemView.findViewById(R.id.textAmount)
        val dateTextView: TextView = itemView.findViewById(R.id.textDate)
        val imageViewPhoto: ImageView = itemView.findViewById(R.id.imageViewPhoto) // New ImageView for the photo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.descriptionTextView.text = expense.description
        holder.amountTextView.text = "Amount: ${expense.amount}"
        holder.dateTextView.text = "Date: ${expense.date}"

        // Check if the photoPath is not null or empty
        if (!expense.photoPath.isNullOrEmpty()) {
            holder.imageViewPhoto.setImageURI(Uri.parse(expense.photoPath))  // Set image URI
            holder.imageViewPhoto.visibility = View.VISIBLE  // Make it visible
        } else {
            holder.imageViewPhoto.visibility = View.GONE  // Hide it if no photo
        }
    }

    override fun getItemCount(): Int = expenses.size

    fun updateExpenses(newExpenses: List<ExpenseEntity>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}
