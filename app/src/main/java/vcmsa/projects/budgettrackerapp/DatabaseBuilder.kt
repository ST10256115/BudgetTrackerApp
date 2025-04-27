package vcmsa.projects.budgettrackerapp

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        // Return existing database instance or create a new one
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "budget_tracker_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}