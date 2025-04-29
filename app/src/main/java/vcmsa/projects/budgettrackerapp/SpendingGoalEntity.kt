package vcmsa.projects.budgettrackerapp

import androidx.room.Entity
import androidx.room.PrimaryKey

// Developed with guidance and assistance using best practices from Android documentation and community standards.

@Entity(tableName = "spending_goals")
data class SpendingGoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val minAmount: Double,
    val maxAmount: Double
)
