package gautam.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

import static android.support.v7.widget.AppCompatDrawableManager.get;
import static gautam.simpletodo.Priority.getAllPriorityLevels;

/**
 * Secondary activity allowing for editting of existing ToDos
 */
public class EditItemActivity extends AppCompatActivity {

    /**
     * Updates textfield with current text of ToDo
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        EditText titleText = (EditText) findViewById(R.id.editTodoTitle);
        EditText descriptionText = (EditText) findViewById(R.id.editToDoDescription);
        EditText size = (EditText) findViewById(R.id.editToDoSize);
        Spinner month = (Spinner) findViewById(R.id.editToDoMonthSpinner);
        NumberPicker day = (NumberPicker) findViewById(R.id.editToDoDatePickerDay);
        NumberPicker year = (NumberPicker) findViewById(R.id.editToDoDatePickerYear);
        Spinner priority = (Spinner) findViewById(R.id.editToDoPriority);

        String[] months = new String[] {
                "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, months);
        month.setAdapter(adapter);

        String[] priorityLevels = getAllPriorityLevels();
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, priorityLevels);
        priority.setAdapter(priorityAdapter);


        year.setMaxValue(2030);
        year.setMinValue(2017);
        day.setMinValue(1);
        day.setMaxValue(31);
        titleText.setText(getIntent().getStringExtra("toDoTitle"));
        descriptionText.setText(getIntent().getStringExtra("toDoDescription"));
        Date dueDate = new Date(getIntent().getStringExtra("toDoDate"));
        month.setSelection(dueDate.getMonth() - 1);
        day.setValue(dueDate.getDate());
        year.setValue(dueDate.getYear());
        size.setText(getIntent().getStringExtra("toDoSize"));

        priority.setSelection(gautam.simpletodo.Priority.determinePriorityFromString(getIntent().getStringExtra("toDoPriority")).getPriorityNumber() - 1);
    }

    /**
     * Returns back to main activity with new ToDo Text
     * @param view
     */
    public void onSubmit(View view) {

        EditText titleText = (EditText) findViewById(R.id.editTodoTitle);
        EditText descriptionText = (EditText) findViewById(R.id.editToDoDescription);
        EditText size = (EditText) findViewById(R.id.editToDoSize);
        Spinner month = (Spinner) findViewById(R.id.editToDoMonthSpinner);
        NumberPicker day = (NumberPicker) findViewById(R.id.editToDoDatePickerDay);
        NumberPicker year = (NumberPicker) findViewById(R.id.editToDoDatePickerYear);
        Date date = new Date(year.getValue(), gautam.simpletodo.Months.determineMonth(month.getSelectedItem().toString()).getMonthNumber(), day.getValue());
        Spinner priority = (Spinner) findViewById(R.id.editToDoPriority);

        Intent data = new Intent();
        data.putExtra("toDoTitle", titleText.getText().toString());
        data.putExtra("toDoDescription", descriptionText.getText().toString());
        data.putExtra("toDoDate", date.toString());
        data.putExtra("toDoSize", size.getText().toString());
        data.putExtra("toDoPriority", priority.getSelectedItem().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
