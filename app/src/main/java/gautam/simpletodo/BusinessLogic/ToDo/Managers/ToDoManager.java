package gautam.simpletodo;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Gautam on 1/21/17.
 */

public class ToDoManager {

    private ArrayList<ToDo> toDoCollection;
    private ToDoDatabaseManager databaseManager;

    public ToDoManager(Context context) {
        this.databaseManager = new ToDoDatabaseManager(context);
        toDoCollection = createAndPopulateToDoCollection();
    }

    private ArrayList<ToDo> createAndPopulateToDoCollection() {
        ArrayList<ToDo> todoList = new ArrayList<>();
        HashMap<Integer, ToDo> allTodos = databaseManager.getAllToDos();
        Iterator<Integer> iterator = allTodos.keySet().iterator();

        while(iterator.hasNext()) {
            todoList.add(allTodos.get(iterator.next()));
        }
        return todoList;
    }

    public ArrayList<ToDo> getAllToDos() {
        return toDoCollection;
    }

    public boolean removeToDo(int pos) {
         if (databaseManager.removeToDo(toDoCollection.get(pos))) {
             toDoCollection.remove(pos);
             return true;
         }
         return false;
    }

    public boolean addToDo(ToDo newTodo) {
        if (databaseManager.addToDo(newTodo)) {
            toDoCollection.add(newTodo);
            return true;
        }
        return false;
    }

    public boolean updateToDo(int pos, ToDo newTodo) {
        ToDo todoInQuestion = toDoCollection.get(pos);
        newTodo.id = todoInQuestion.id;
        newTodo.setTodoID(todoInQuestion.getTodoID());

        if (databaseManager.updateToDo(newTodo)) {
            toDoCollection.set(pos, newTodo);
            return true;
        }
        return false;
    }
}
