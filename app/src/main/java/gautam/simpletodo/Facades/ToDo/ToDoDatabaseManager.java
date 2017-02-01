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
    }

     private HashMap<Integer, gautam.simpletodo.ToDo> getAllToDos() {
        HashMap<Integer, gautam.simpletodo.ToDo> loadedToDos = new HashMap<Integer, gautam.simpletodo.ToDo>();
        List<gautam.simpletodo.ToDo> todosFromDatabase = database.getAllTodos();

        Iterator<gautam.simpletodo.ToDo> iterator = todosFromDatabase.iterator();

        while (iterator.hasNext()) {
            gautam.simpletodo.ToDo todo = iterator.next();
            loadedToDos.put(todo.getTodoID(), todo);
        }

        return loadedToDos;
    }

    public HashMap<Integer, gautam.simpletodo.ToDo> getToDos() {
        return toDos;
    }

    public boolean addToDo(gautam.simpletodo.ToDo todo) {
        if (database.addToDo(todo)) {
            todo.addedToDatabase = true;
            toDos.put(todo.getTodoID(), todo);
            return true;
        }

        return false;
    }

    public boolean removeToDo(gautam.simpletodo.ToDo todo) {
        boolean success = database.deleteToDo(todo);
        if (success) {
            toDos.remove(todo.getTodoID());
        }

        return success;



    }

    public boolean updateToDo(gautam.simpletodo.ToDo todo) {
        if (database.updateToDo(todo) == 1) {
            toDos.put(todo.getTodoID(), todo);
            return true;
        }
        return false;
    }

}
