package com.duolaguanjia.bean;

public class Item {
	public static final int ITEM = 0;
	public static final int SECTION = 1;

	public final int type;
	public final String text;
	public   String   id;

	public int sectionPosition;
	public int listPosition;
	public Item(int type, String text) {
		this.type = type;
		this.text = text;
	}
	public Item(int type, String text, String id) {
		this.type = type;
		this.text = text;
		this.id=id;
	}

	@Override
	public String toString() {
		return text;
	}




}
