package vcmsa.projects.budgettrackerapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val categoryRepository: CategoryRepository = CategoryRepository(application)

    private val _categories = MutableLiveData<List<CategoryEntity>>()
    val categories: LiveData<List<CategoryEntity>> get() = _categories

    // Fetch all categories with error handling
    fun getAllCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categoryList = categoryRepository.getAllCategories()
                _categories.postValue(categoryList)
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error fetching categories", e)
            }
        }
    }

    // Insert a new category with error handling
    fun insertCategory(category: CategoryEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                categoryRepository.insertCategory(category)
                getAllCategories() // Refresh categories after inserting
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error inserting category", e)
            }
        }
    }
}
