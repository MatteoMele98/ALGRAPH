package algraph.controller;

import java.util.ArrayList;

import algraph.view.BoolItem;


public class BoolItemController {
	//private BoolItem boolItem[]=new BoolItem[15];
	private ArrayList<BoolItem> boolItem;
	
	public BoolItemController() {
		this.boolItem = new ArrayList<BoolItem>();
	}
	
	/*
	 * @param newItem 
	 * adds newItem(default false) at the end of boolItem array
	 */
	public void add(BoolItem newItem) {
		this.boolItem.add(newItem);
	}
	
	/*
	 * @param index
	 * @return the BoolItem in the index position
	 */
	public BoolItem getBoolItem(int index) {
		return this.boolItem.get(index);
	}
}
