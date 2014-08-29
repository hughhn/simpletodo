package com.codepath.apps.simpletodo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {
    // View lookup cache
    private static class ViewHolder {
    	ImageView prio;
        TextView item;
        TextView dueDate;
    }

    public TodoItemAdapter(Context context, ArrayList<TodoItem> items) {
       super(context, R.layout.list_item_view, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
    	TodoItem item = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.list_item_view, parent, false);
          viewHolder.prio = (ImageView) convertView.findViewById(R.id.item_priority);
          viewHolder.item = (TextView) convertView.findViewById(R.id.item_text);
          viewHolder.dueDate = (TextView) convertView.findViewById(R.id.item_due_date);
          convertView.setTag(viewHolder);
       } else {
           viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       switch (item.prio) {
	       case TodoItem.HIGH_PRIORITY:
	    	   viewHolder.prio.setBackgroundColor(Color.parseColor("#ffff4444"));
	    	   break;
	       case TodoItem.MEDIUM_PRIORITY:
	    	   viewHolder.prio.setBackgroundColor(Color.parseColor("#ffffbb33"));
	    	   break;
	       case TodoItem.LOW_PRIORITY:
	    	   viewHolder.prio.setBackgroundColor(Color.parseColor("#ff99cc00"));
	    	   break;
       }
       
       viewHolder.item.setText(item.item);
       viewHolder.dueDate.setText(item.dueDate);
       // Return the completed view to render on screen
       return convertView;
   }
}