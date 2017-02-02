package gautam.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gautam on 1/25/17.
 */

public class ToDosAdapter extends ArrayAdapter<gautam.simpletodo.ToDo> {
    public ToDosAdapter(Context context, ArrayList<gautam.simpletodo.ToDo> toDos) {
        super(context, 0, toDos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        gautam.simpletodo.ToDo todo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView dueDate = (TextView) convertView.findViewById(R.id.dueDate);
        TextView priority = (TextView) convertView.findViewById(R.id.priority);
        TextView size = (TextView) convertView.findViewById(R.id.size);
        // Populate the data into the template view using the data object
        title.setText(todo.title);
        // TODO: Format date properly
        Date date = todo.dueDate;
        dueDate.setText(date.getMonth() + "/" + date.getDate() + "/" + date.getYear());
        // TODO: Format priority correctly
        priority.setText(todo.priority.toString());

        size.setText(todo.size.toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
