package gautam.simpletodo;

import android.content.Context;
import android.content.Intent;
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.media.CamcorderProfile.get;
/**
    Main Activity class for Todo Application allowing for CRUD functionality for ToDos
 */
public class MainActivity extends AppCompatActivity {

    /**
     * List of ToDo items
     */
    ArrayList<String> items;
    /**
     * Adapter for list view
     */
    ArrayAdapter<String> itemsAdapter;
    /**
     * List View of ToDo items
     */
    ListView lvItems;
    /**
     * Position of ToDo in original list
     */
    private Integer currentlyEdittedItemPosition = null;
    /**
     * Constant for intent tracking
     */
    private final int REQUEST_CODE = 20;

    /**
     * Entry point for MainActivity, sets up ToDo app list view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        if (items.size() == 0) {
            items.add("Fly to the moon");
            items.add("Tuck and Roll");
        }

        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
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
                switchToEdit.putExtra("toDoText", items.get(position));

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
        items.remove(pos);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
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
            updateToDoText(currentlyEdittedItemPosition, data.getStringExtra("toDoText"));
        }

    }

    /**
     * Updates text of existing ToDo
     * @param listItemID position of ToDo in ToDos list
     * @param toDoText new value of ToDo text
     */
    private void updateToDoText(Integer listItemID, String toDoText) {
        items.set(listItemID, toDoText);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
        currentlyEdittedItemPosition = null;
    }

    /**
     * Gets ToDos list from persistent storage on disk
     */
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    /**
     * Writes ToDos list to persistent storage on disk
     */
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback for when new ToDo is added
     * @param v invoking view
     */
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }
}
