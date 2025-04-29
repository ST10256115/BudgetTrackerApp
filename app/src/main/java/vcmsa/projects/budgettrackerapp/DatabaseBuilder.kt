package vcmsa.projects.budgettrackerapp

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "budget_tracker_db"
            )
                .addMigrations(AppDatabase.MIGRATION_2_3) // Register migration here
                .fallbackToDestructiveMigrationOnDowngrade() // Optional safety if downgrading
                .build()
            INSTANCE = instance
            instance
        }
    }
}
