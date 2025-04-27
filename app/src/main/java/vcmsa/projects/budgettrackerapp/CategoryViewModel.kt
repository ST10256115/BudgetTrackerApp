package vcmsa.projects.budgettrackerapp

import android.app.Application
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

    // Fetch all categories
    fun getAllCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val categoryList = categoryRepository.getAllCategories()
            _categories.postValue(categoryList)
        }
    }

    // Insert a new category
    fun insertCategory(category: CategoryEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            categoryRepository.insertCategory(category)
            getAllCategories() // Refresh categories after inserting a new one
        }
    }
}
