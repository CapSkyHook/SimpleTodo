package gautam.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by Gautam on 1/21/17.
 */

public class ToDoDatabaseWrapper extends SQLiteOpenHelper {

    private static ToDoDatabaseWrapper sharedInstance;

    // Database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TODOS = "todos";

    // Post Table Columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_TRACKING_ID = "trackingId";
    private static final String KEY_TODO_TITLE = "title";
    private static final String KEY_TODO_DESCRIPTION = "description";
    private static final String KEY_TODO_DUE_DATE = "duedate";
    private static final String KEY_TODO_SIZE = "size";
    private static final String KEY_TODO_PRIORITY = "priority";

    public static synchronized ToDoDatabaseWrapper getInstance(Context context) {
        if (sharedInstance == null) {
            sharedInstance = new ToDoDatabaseWrapper(context.getApplicationContext());
        }
        return sharedInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private ToDoDatabaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODOS_TABLE = "CREATE TABLE " + TABLE_TODOS +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TODO_TRACKING_ID + " INTEGER," + // Define a primary key
                KEY_TODO_TITLE + " TEXT," +
                KEY_TODO_DESCRIPTION + " TEXT," +
                KEY_TODO_DUE_DATE + " TEXT," +
                KEY_TODO_PRIORITY + " INTEGER," +
                KEY_TODO_SIZE + " INTEGER" +
                ")";

        db.execSQL(CREATE_TODOS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
            onCreate(db);
        }
    }

    public boolean addToDo(gautam.simpletodo.ToDo todo) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        Boolean isSuccessful = true;
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_TITLE, todo.title);
            values.put(KEY_TODO_DESCRIPTION, todo.description);
            values.put(KEY_TODO_DUE_DATE, todo.dueDate.toString());
            values.put(KEY_TODO_PRIORITY, todo.priority.toString());
            values.put(KEY_TODO_SIZE, todo.size);
            values.put(KEY_TODO_TRACKING_ID, todo.getTodoID());

            db.insertOrThrow(TABLE_TODOS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add todo to database");
            isSuccessful = false;
        } finally {
            db.endTransaction();
        }
        return isSuccessful;
    }

    public List<gautam.simpletodo.ToDo> getAllTodos() {
        List<gautam.simpletodo.ToDo> todos = new ArrayList<>();

        String POSTS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TODOS);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    gautam.simpletodo.ToDo newTodo = new gautam.simpletodo.ToDo();
                    newTodo.title = cursor.getString(cursor.getColumnIndex(KEY_TODO_TITLE));
                    newTodo.description =
                            cursor.getString(cursor.getColumnIndex(KEY_TODO_DESCRIPTION));
                    newTodo.dueDate = new Date(cursor.getString(cursor.getColumnIndex(KEY_TODO_DUE_DATE)));
                    newTodo.priority = gautam.simpletodo.Priority.determinePriority(cursor.getInt(cursor.getColumnIndex(KEY_TODO_PRIORITY)));
                    newTodo.size = cursor.getInt(cursor.getColumnIndex(KEY_TODO_SIZE));
                    todos.add(newTodo);
                    System.out.println(newTodo.title);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return todos;
    }

    public void deleteAllToDos() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        onCreate(db);
    }

    public int updateToDo(gautam.simpletodo.ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO_TITLE, todo.title);
        values.put(KEY_TODO_DESCRIPTION, todo.description);
        values.put(KEY_TODO_DUE_DATE, todo.dueDate.toString());
        values.put(KEY_TODO_PRIORITY, todo.priority.toString());
        values.put(KEY_TODO_SIZE, todo.size);

        // Updating profile picture url for user with that userName
        return db.update(TABLE_TODOS, values, KEY_TODO_TRACKING_ID + " = ?",
                new String[] { String.valueOf(todo.getTodoID()) });
    }

    public boolean deleteToDo(gautam.simpletodo.ToDo todo) {
        SQLiteDatabase db = getWritableDatabase();
        Boolean isSuccessful = true;
        db.beginTransaction();
        try {

            db.delete(TABLE_TODOS, KEY_TODO_TRACKING_ID + " = ?",
                    new String[] { String.valueOf(todo.getTodoID()) });
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
            isSuccessful = false;
        } finally {
            db.endTransaction();
        }
        return isSuccessful;
    }
}
