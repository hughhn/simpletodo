package com.codepath.apps.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemFragment extends DialogFragment {
	private EditText etNewItem;
	private Button saveBtn;
	private int pos;
	private String item;
	private EditItemDialogListener listener;
	
	public EditItemFragment() {
		// Empty constructor required for DialogFragment
	}
	
	public static EditItemFragment newInstance(int pos, String item) {
		EditItemFragment frag = new EditItemFragment();
		Bundle args = new Bundle();
		args.putInt("pos", pos);
		args.putString("item", item);
		frag.setArguments(args);
		return frag;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
		pos = getArguments().getInt("pos", 0);
		item = getArguments().getString("item", "Enter Name");
		etNewItem = (EditText) view.findViewById(R.id.editExistingItem);
		etNewItem.requestFocus();
		etNewItem.setText(item);
		etNewItem.setSelection(item.length());
		saveBtn = (Button)view.findViewById(R.id.btnSave);
		saveBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	listener.onFinishEditDialog(pos, etNewItem.getText().toString());
            }
        });
		getDialog().setTitle("Edit item");
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}
	
	public interface EditItemDialogListener {
        void onFinishEditDialog(int pos, String item);
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