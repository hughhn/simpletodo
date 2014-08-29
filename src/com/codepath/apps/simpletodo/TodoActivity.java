package com.codepath.apps.simpletodo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import com.codepath.apps.simpletodo.EditItemFragment.EditItemDialogListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

class TodoItemComparator implements Comparator<TodoItem> {
    @Override
    public int compare(TodoItem o1, TodoItem o2) {
        return o1.getPrio() - o2.getPrio();
    }
}

public class TodoActivity extends FragmentActivity implements EditItemDialogListener {
	private ArrayList<TodoItem> items;
	private ArrayAdapter<TodoItem> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	EditItemFragment frag;
	TodoItemComparator todoComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		itemsAdapter = new TodoItemAdapter(this, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
		todoComparator = new TodoItemComparator();
	}
	
	public void addTodoItem(View v) {
		TodoItem item = new TodoItem(etNewItem.getText().toString());
		items.add(item);
		Collections.sort(items, todoComparator);
		itemsAdapter.notifyDataSetChanged();
		etNewItem.setText("");
		saveItems();
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				showEditDialog(position, items.get(position));
			}
		});
	}
	
	
	private void showEditDialog(int pos, TodoItem item) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		frag = EditItemFragment.newInstance(pos, item);
		frag.show(ft, "fragment_edit_item");
	}
	
	@Override
	public void onFinishEditDialog(int pos, TodoItem item) {
		// TODO Auto-generated method stub
		items.set(pos, item);
		Collections.sort(items, todoComparator);
		itemsAdapter.notifyDataSetChanged();
		saveItems();
		frag.dismiss();
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = null;
		FileInputStream fis = null;
		ObjectInputStream is = null;
		
		todoFile = new File(filesDir, "todo.txt");
		
		try {
			fis = new FileInputStream(todoFile);
			is = new ObjectInputStream(fis);
			items = (ArrayList<TodoItem>) is.readObject();
			is.close();
		} catch (IOException e) {
			items = new ArrayList<TodoItem>();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			items = new ArrayList<TodoItem>();
			e.printStackTrace();
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = null;
		FileOutputStream fos = null;
		ObjectOutputStream os = null;
		
		try {
			todoFile = new File(filesDir, "todo.txt");
			if (!todoFile.exists()) {
				todoFile.createNewFile();
			}
			
			fos = new FileOutputStream(todoFile);
			os = new ObjectOutputStream(fos);

			os.writeObject(items);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
