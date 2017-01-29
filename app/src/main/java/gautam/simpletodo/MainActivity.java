package gautam.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.media.CamcorderProfile.get;
import static gautam.simpletodo.R.id.size;

/**
    Main Activity class for Todo Application allowing for CRUD functionality for ToDos
 */
public class MainActivity extends AppCompatActivity implements AddTodoDialogFragment.AddTodoDialogFragmentListener {

    /**
     * Constants
     */
    ToDo SEED_TODO_1 = new ToDo("Fly to the moon", "Build a rocket ship in factorio and fly to the moon", new Date(), 2, 3);
    ToDo SEED_TODO_2 = new ToDo("Tuck and Roll", "Practice evasive maneuvers", new Date(), 10, 1);

    /**
     * View Management Data
     */
    ArrayAdapter<ToDo> itemsAdapter;
    ListView lvItems;
    private Integer currentlyEdittedItemPosition = null;
    private final int REQUEST_CODE = 20;

    /**
     * Todo Management Data
     */
    private ToDoUIManager todoUIManager;



    /**
     * Entry point for MainActivity, sets up ToDo app list view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoUIManager = new ToDoUIManager(getBaseContext());
        lvItems = (ListView) findViewById(R.id.lvItems);
        ArrayList<ToDo> todoItems = todoUIManager.getItems();
        if (todoItems.size() == 0) {
            todoUIManager.addTodo(SEED_TODO_1);
            todoUIManager.addTodo(SEED_TODO_2);
        }

        itemsAdapter = new ToDosAdapter(getBaseContext(), todoUIManager.getItems());
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    /**
     * Sets up ToDo actions
     */
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

         @Override
        public boolean onItemLongClick(AdapterView<?> adapter,
           View item, int pos, long id) {
             removeItem(pos);
             return true;
         }

        });

        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                currentlyEdittedItemPosition = position;
                Intent switchToEdit = new Intent(MainActivity.this, EditItemActivity.class);
                switchToEdit.putExtra("toDoText", todoUIManager.getItems().get(position).title);

                startActivityForResult(switchToEdit, REQUEST_CODE);
            }
        }

        );
    }

    /**
     * Removes ToDo Item
     * @param pos position of ToDo in ToDos list
     */
    private void removeItem(int pos) {
        if (todoUIManager.removeTodo(pos)) {
            itemsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Callback invoked after secondary activity completes
     * @param requestCode Constant to ensure appropriate activity result was returned
     * @param resultCode Outcome of activity result
     * @param data Extra data returned from activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            ToDo newTodo = new ToDo();
            newTodo.title = data.getStringExtra("toDoText");
            updateToDoText(currentlyEdittedItemPosition, newTodo);
        }

    }

    /**
     * Updates text of existing ToDo
     * @param listItemID position of ToDo in ToDos list
     * @param todo new value of ToDo
     */
    private void updateToDoText(Integer listItemID, ToDo todo) {
        if (todoUIManager.updateTodo(listItemID, todo)) {
            itemsAdapter.notifyDataSetChanged();
        }
        currentlyEdittedItemPosition = null;
    }

    /**
     * Callback for when new ToDo is added
     * @param v invoking view
     */
    public void onAddItem(View v) {
        showAddDialog();
    }

    private void showAddDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddTodoDialogFragment addToDoDialogFragment = AddTodoDialogFragment.newInstance();
        addToDoDialogFragment.show(fm, "fragment_add_todo");
    }

    public void onFinishAddTodoDialogFragment(ToDo toDo, View view) {

        if (todoUIManager.addTodo(toDo)) {
            itemsAdapter.notifyDataSetChanged();
        }


    }

}