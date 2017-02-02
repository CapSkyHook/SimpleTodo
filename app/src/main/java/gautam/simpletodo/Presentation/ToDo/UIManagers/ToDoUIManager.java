package gautam.simpletodo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Gautam on 1/21/17.
 */

public class ToDoUIManager {

    private ArrayList<gautam.simpletodo.ToDo> todosListViewItems;
    private gautam.simpletodo.ToDoManager todoManager;

    public ToDoUIManager(Context context) {
        this.todoManager = new gautam.simpletodo.ToDoManager(context);
        todosListViewItems = createAndPopulateToDoList();
    }

    private ArrayList<gautam.simpletodo.ToDo> createAndPopulateToDoList() {
        ArrayList<gautam.simpletodo.ToDo> todoListItems = new ArrayList<>();

        for (gautam.simpletodo.ToDo todo : todoManager.getAllToDos()) {
            todoListItems.add(todo);
        }

        Collections.sort(todoListItems);
        return todoListItems;
    }

    public ArrayList<gautam.simpletodo.ToDo> getItems() {
        return todosListViewItems;
    }

    public boolean removeTodo(int pos) {
        if (todoManager.removeToDo(pos)) {
            todosListViewItems.remove(pos);
            Collections.sort(todosListViewItems);
            return true;
        }
        return false;
    }

    public boolean addTodo(gautam.simpletodo.ToDo todo) {
        if (todoManager.addToDo(todo)) {
            todosListViewItems.add(todo);
            Collections.sort(todosListViewItems);
            return true;
        }
        return false;
    }

    public boolean updateTodo(int pos, gautam.simpletodo.ToDo todo) {
        if (todoManager.updateToDo(pos, todo)) {
            todosListViewItems.set(pos, todo);
            Collections.sort(todosListViewItems);
            return true;
        }
        return false;
    }
}
