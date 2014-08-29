package com.codepath.apps.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class EditItemFragment extends DialogFragment {
	private EditText etNewItem;
	private Button saveBtn;
	private Button highPrioBtn;
	private Button mediumPrioBtn;
	private Button lowPrioBtn;
	private int pos;
	private int prio;
	private String item;
	private String dueDate;
	private EditItemDialogListener listener;
	
	public EditItemFragment() {
		// Empty constructor required for DialogFragment
	}
	
	public static EditItemFragment newInstance(int pos, TodoItem item) {
		EditItemFragment frag = new EditItemFragment();
		Bundle args = new Bundle();
		args.putInt("pos", pos);
		args.putInt("prio", item.prio);
		args.putString("item", item.item);
		args.putString("dueDate", item.dueDate);
		frag.setArguments(args);
		return frag;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
		pos = getArguments().getInt("pos", 0);
		prio = getArguments().getInt("prio", 0);
		item = getArguments().getString("item", "Enter Name");
		dueDate = getArguments().getString("dueDate", "");
		
		etNewItem = (EditText) view.findViewById(R.id.editExistingItem);
		etNewItem.requestFocus();
		etNewItem.setText(item);
		etNewItem.setSelection(item.length());
		
		saveBtn = (Button)view.findViewById(R.id.btnSave);
		saveBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	TodoItem changedItem = new TodoItem(etNewItem.getText().toString(), prio);
            	listener.onFinishEditDialog(pos, changedItem);
            }
        });
		
		highPrioBtn = (Button)view.findViewById(R.id.btnHighPrio);
		highPrioBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	prio = TodoItem.HIGH_PRIORITY;
            	highPrioBtn.setAlpha(1f);
            	mediumPrioBtn.setAlpha(0.2f);
            	lowPrioBtn.setAlpha(0.2f);
            }
        });
		
		mediumPrioBtn = (Button)view.findViewById(R.id.btnMediumPrio);
		mediumPrioBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	prio = TodoItem.MEDIUM_PRIORITY;
            	highPrioBtn.setAlpha(0.2f);
            	mediumPrioBtn.setAlpha(1f);
            	lowPrioBtn.setAlpha(0.2f);
            }
        });
		
		lowPrioBtn = (Button)view.findViewById(R.id.btnLowPrio);
		lowPrioBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	prio = TodoItem.LOW_PRIORITY;
            	highPrioBtn.setAlpha(0.2f);
            	mediumPrioBtn.setAlpha(0.2f);
            	lowPrioBtn.setAlpha(1f);
            }
        });
		
		getDialog().setTitle("Edit Item");
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}
	
	public interface EditItemDialogListener {
        void onFinishEditDialog(int pos, TodoItem item);
    }
	
	// Store the listener (activity) that will have events fired once the fragment is attached
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof EditItemDialogListener) {
			listener = (EditItemDialogListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
	            + " must implement EditItemFragment.EditItemDialogListener");
		}
	 }

}