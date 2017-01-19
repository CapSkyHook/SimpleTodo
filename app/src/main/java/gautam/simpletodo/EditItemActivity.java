package gautam.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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
        EditText toDoText = (EditText) findViewById(R.id.toDoMessage);
        toDoText.setText(getIntent().getStringExtra("toDoText"));
    }

    /**
     * Returns back to main activity with new ToDo Text
     * @param v
     */
    public void onSubmit(View v) {
        EditText toDoText = (EditText) findViewById(R.id.toDoMessage);
        Intent data = new Intent();
        data.putExtra("toDoText", toDoText.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
