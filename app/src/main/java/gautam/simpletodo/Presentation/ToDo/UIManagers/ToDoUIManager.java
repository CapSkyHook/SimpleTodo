package gautam.simpletodo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Gautam on 1/21/17.
 */

public class ToDoUIManager {

    private ArrayList<ToDo> todosListViewItems;
    private ToDoManager todoManager;

    public ToDoUIManager(Context context) {
        this.todoManager = new ToDoManager(context);
        todosListViewItems = createAndPopulateToDoList();
    }

    private ArrayList<ToDo> createAndPopulateToDoList() {
        ArrayList<ToDo> todoListItems = new ArrayList<>();

        Iterator<ToDo> iterator = todoManager.getAllToDos().iterator();

        while (iterator.hasNext()) {
            todoListItems.add(iterator.next());
        }

        return todoListItems;
    }

    public ArrayList<ToDo> getItems() {
        return todosListViewItems;
    }

    public boolean removeTodo(int pos) {
        if (todoManager.removeToDo(pos)) {
            todosListViewItems.remove(pos);
            return true;
        }
        return false;
    }

    public boolean addTodo(ToDo todo) {
        if (todoManager.addToDo(todo)) {
            todosListViewItems.add(todo);
            return true;
        }
        return false;
    }

    public boolean updateTodo(int pos, ToDo todo) {
        if (todoManager.updateToDo(pos, todo)) {
            todosListViewItems.set(pos, todo);
            return true;
        }
        return false;
    }
}
