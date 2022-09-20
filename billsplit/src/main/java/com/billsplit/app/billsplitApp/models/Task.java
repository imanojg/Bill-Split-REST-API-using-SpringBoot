package com.billsplit.app.billsplitApp.models;

public interface Task<T> extends Comparable<T> {

	@Override
	int compareTo(T o);

}
