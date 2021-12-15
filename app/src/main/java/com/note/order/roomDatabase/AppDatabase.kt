package com.note.order.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.note.order.DAO.RoomDao
import com.note.order.entity.BrandItem
import com.note.order.entity.ColorItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [ColorItem::class,BrandItem::class],//TaskEntry::class
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun roomDao(): RoomDao
    //abstract fun TaskDao(): TaskDao

//    companion object {
//        @Volatile private var instance: AppDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
//            instance ?: buildDatabase(context).also { instance = it}
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "main.db").build()
//    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "main_db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                       // populateDatabase(database.wordDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
//        suspend fun populateDatabase(wordDao: WordDao) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            wordDao.deleteAll()
//
//            var word = Word("Hello")
//            wordDao.insert(word)
//            word = Word("World!")
//            wordDao.insert(word)
//        }
    }
}