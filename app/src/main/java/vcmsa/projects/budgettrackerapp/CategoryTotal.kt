package vcmsa.projects.budgettrackerapp

import androidx.room.ColumnInfo

data class CategoryTotal(
    @ColumnInfo(name = "category") val category: String,  // This matches the "category" column in the query
    @ColumnInfo(name = "SUM(e.amount)") val total: Double  // This matches the SUM(e.amount) column in the query
)