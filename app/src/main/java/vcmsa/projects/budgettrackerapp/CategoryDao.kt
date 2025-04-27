package vcmsa.projects.budgettrackerapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {

    // Get all categories
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryEntity>

    // Insert a category
    @Insert
    suspend fun insertCategory(category: CategoryEntity)
}
