package com.codepath.apps.simpletodo;

public class TodoItem {
	public static final int HIGH_PRIORITY = 0;
	public static final int MEDIUM_PRIORITY = 1;
	public static final int LOW_PRIORITY = 2;
	
	public String item;
	public int prio;
	public String dueDate;

	public TodoItem(String item, int prio, String dueDate) {
		this.item = item;
		if (prio >= HIGH_PRIORITY && prio <= LOW_PRIORITY) {
			this.prio = prio;
		} else {
			this.prio = HIGH_PRIORITY;
		}
		this.dueDate = dueDate;
	}
	
	public TodoItem(String item, int prio) {
		this(item, prio, "");
	}
	
	public TodoItem(String item) {
		this(item, HIGH_PRIORITY, "");
	}
	
	public int getPrio() {
		return this.prio;
	}
}