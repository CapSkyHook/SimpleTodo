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
    private ToDoDatabaseWrapper database;
    private HashMap<Integer, ToDo> toDos;


    public ToDoDatabaseManager(Context contextForDatabase ) {
        this.database = ToDoDatabaseWrapper.getInstance(contextForDatabase);
        toDos = getAllToDos();
    }

    public HashMap<Integer, ToDo> getAllToDos() {
        HashMap<Integer, ToDo> loadedToDos = new HashMap<Integer, ToDo>();
        List<ToDo> todosFromDatabase = database.getAllTodos();

        Iterator<ToDo> iterator = todosFromDatabase.iterator();

        while (iterator.hasNext()) {
            ToDo todo = iterator.next();
            loadedToDos.put(todo.getTodoID(), todo);
        }

        return loadedToDos;
    }

    public boolean addToDo(ToDo todo) {
        if (database.addToDo(todo)) {
            todo.addedToDatabase = true;
            toDos.put(todo.getTodoID(), todo);
            return true;
        }

        return false;
    }

    public boolean removeToDo(ToDo todo) {
        boolean success = database.deleteToDo(todo);
        if (success) {
            toDos.remove(todo.getTodoID());
        }

        return success;



    }

    public boolean updateToDo(ToDo todo) {
        if (database.updateToDo(todo) == 1) {
            toDos.put(todo.getTodoID(), todo);
            return true;
        }
        return false;
    }

}
