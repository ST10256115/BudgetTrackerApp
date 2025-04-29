package vcmsa.projects.budgettrackerapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

// Developed with guidance and assistance using best practices from Android documentation and community standards.

class NewCategoryActivity : AppCompatActivity() {

    // Get the viewModel to interact with the CategoryDao
    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_category)

        // Initialize the UI elements
        val etCategoryName: EditText = findViewById(R.id.etCategoryName)
        val btnSaveCategory: Button = findViewById(R.id.btnSaveCategory)

        // Save category when the button is clicked
        btnSaveCategory.setOnClickListener {
            val categoryName = etCategoryName.text.toString().trim()

            if (categoryName.isNotEmpty()) {
                // Save the category
                saveCategory(categoryName)
            }
        }
    }

    private fun saveCategory(categoryName: String) {
        // Save the category in the database using ViewModel and coroutines
        lifecycleScope.launch {
            val category = CategoryEntity(name = categoryName)
            categoryViewModel.insertCategory(category)

            finish()
        }
    }
}
