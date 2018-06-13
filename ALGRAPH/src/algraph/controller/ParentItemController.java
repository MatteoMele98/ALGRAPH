package algraph.controller;

import java.util.ArrayList;

import algraph.view.ParentItem;

public class ParentItemController {
	//private BoolItem boolItem[]=new BoolItem[15];
	private ArrayList<ParentItem> parentItem;
	
	public ParentItemController() {
		this.parentItem = new ArrayList<ParentItem>();
	}
	
	/*
	 * @param newItem 
	 * adds newItem(default false) at the end of boolItem array
	 */
	public void add(ParentItem newItem) {
		this.parentItem.add(newItem);
	}
	
	/*
	 * @param index
	 * @return the BoolItem in the index position
	 */
	public ParentItem getBoolItem(int index) {
		return this.parentItem.get(index);
	}
}
