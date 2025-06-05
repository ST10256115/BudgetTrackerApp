package vcmsa.projects.budgettrackerapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonHelp: Button
    private lateinit var checkboxRememberMe: CheckBox
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonHelp = findViewById(R.id.buttonHelp)
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe)

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Restore saved credentials
        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")
        val rememberMeChecked = sharedPreferences.getBoolean("rememberMe", false)

        if (rememberMeChecked) {
            editTextUsername.setText(savedUsername)
            editTextPassword.setText(savedPassword)
            checkboxRememberMe.isChecked = true
        }

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (validateLogin(username, password)) {
                if (checkboxRememberMe.isChecked) {
                    sharedPreferences.edit()
                        .putString("username", username)
                        .putString("password", password)
                        .putBoolean("rememberMe", true)
                        .apply()
                } else {
                    sharedPreferences.edit().clear().apply()
                }

                // Login success: go to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        buttonHelp.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        return username == "shivar" && password == "password"
    }
}
