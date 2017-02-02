package gautam.simpletodo;

/**
 * Created by Gautam on 1/21/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ToDoDatabaseManager {
    private gautam.simpletodo.ToDoDatabaseWrapper database;
    private HashMap<Integer, gautam.simpletodo.ToDo> toDos;


    public ToDoDatabaseManager(Context contextForDatabase ) {
        this.database = gautam.simpletodo.ToDoDatabaseWrapper.getInstance(contextForDatabase);
        toDos = getAllToDos();
        // Uncomment following line to clear database
//        database.deleteAllToDos();
    }

     private HashMap<Integer, gautam.simpletodo.ToDo> getAllToDos() {
        HashMap<Integer, gautam.simpletodo.ToDo> loadedToDos = new HashMap<Integer, gautam.simpletodo.ToDo>();
        List<gautam.simpletodo.ToDo> todosFromDatabase = database.getAllTodos();

         for (gautam.simpletodo.ToDo todo: todosFromDatabase) {
             loadedToDos.put(todo.id, todo);
         }

        return loadedToDos;
    }

    public HashMap<Integer, gautam.simpletodo.ToDo> getToDos() {
        return toDos;
    }

    public boolean addToDo(gautam.simpletodo.ToDo todo) {
        if (database.addToDo(todo)) {
            todo.addedToDatabase = true;
            toDos.put(todo.id, todo);
            return true;
        }

        return false;
    }

    public boolean removeToDo(gautam.simpletodo.ToDo todo) {
        boolean success = database.deleteToDo(todo);
        if (success) {
            toDos.remove(todo.id);
        }

        return success;



    }

    public boolean updateToDo(gautam.simpletodo.ToDo todo) {
        int v = database.updateToDo(todo);
        if (v >= 1) {
            toDos.put(todo.id, todo);
            return true;
        }
        return false;
    }

}
