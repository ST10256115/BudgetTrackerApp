package vcmsa.projects.budgettrackerapp

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(context: Context) {

    private val categoryDao: CategoryDao = DatabaseBuilder.getDatabase(context).categoryDao()

    // Get all categories from the database
    suspend fun getAllCategories(): List<CategoryEntity> {
        return withContext(Dispatchers.IO) {
            categoryDao.getAllCategories()
        }
    }

    // Insert a category into the database
    suspend fun insertCategory(category: CategoryEntity) {
        withContext(Dispatchers.IO) {
            categoryDao.insertCategory(category)
        }
    }

}
