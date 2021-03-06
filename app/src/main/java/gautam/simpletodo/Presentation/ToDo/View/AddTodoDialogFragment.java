package gautam.simpletodo;

/**
 * Created by Gautam on 1/26/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import  android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

import static gautam.simpletodo.Priority.getAllPriorityLevels;
import static gautam.simpletodo.R.id.priority;
import static gautam.simpletodo.R.id.size;

public class AddTodoDialogFragment extends DialogFragment {

    private EditText mEditText;

    public interface AddTodoDialogFragmentListener {
        void onFinishAddTodoDialogFragment(gautam.simpletodo.ToDo newToDo, View view);
    }


    public AddTodoDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddTodoDialogFragment newInstance() {
        AddTodoDialogFragment frag = new AddTodoDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "New Task");
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_todo, container);
        String[] months = new String[] {
                "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"
        };
        Spinner monthSpinner = (Spinner) view.findViewById(R.id.addToDoMonthSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(adapter);

        String[] priorityLevels = getAllPriorityLevels();
        Spinner prioritySpinner = (Spinner) view.findViewById(R.id.addToDoPriority);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, priorityLevels);
        prioritySpinner.setAdapter(priorityAdapter);

        NumberPicker yearNumberPicker = (NumberPicker) view.findViewById(R.id.addToDoDatePickerYear);
        yearNumberPicker.setMaxValue(2030);
        yearNumberPicker.setMinValue(2017);
        NumberPicker dayNumberPicker = (NumberPicker) view.findViewById(R.id.addToDoDatePickerDay);
        dayNumberPicker.setMinValue(1);
        dayNumberPicker.setMaxValue(31);


        Button submit = (Button) view.findViewById(R.id.addToDoSubmitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View submitButton) {

                gautam.simpletodo.ToDo newToDo = new gautam.simpletodo.ToDo();
                EditText titleText = (EditText) view.findViewById(R.id.addTodoTitle);
                newToDo.title = titleText.getText().toString();
                EditText descriptionText = (EditText) view.findViewById(R.id.addToDoDescription);
                newToDo.description = descriptionText.getText().toString() ;
                EditText size = (EditText) view.findViewById(R.id.addToDoSize);
                newToDo.size = size.getText().toString().equals("") ? 0 : Integer.parseInt(size.getText().toString());
                Spinner month = (Spinner) view.findViewById(R.id.addToDoMonthSpinner);
                NumberPicker day = (NumberPicker) view.findViewById(R.id.addToDoDatePickerDay);
                NumberPicker year = (NumberPicker) view.findViewById(R.id.addToDoDatePickerYear);
                newToDo.dueDate = new Date(year.getValue(), gautam.simpletodo.Months.determineMonth(month.getSelectedItem().toString()).getMonthNumber(), day.getValue());
                Spinner priority = (Spinner) view.findViewById(R.id.addToDoPriority);
                newToDo.priority = gautam.simpletodo.Priority.determinePriorityFromString(priority.getSelectedItem().toString());

                AddTodoDialogFragmentListener listener = (AddTodoDialogFragmentListener) getActivity();
                listener.onFinishAddTodoDialogFragment(newToDo, view);
                // Close the dialog and return back to the parent activity
                dismiss();

            }
        });
        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
