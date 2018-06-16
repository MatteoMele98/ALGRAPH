package algraph.controller;

import java.util.ArrayList;

import algraph.view.PriorityItem;

public class PriorityController {
	public ArrayList<PriorityItem> priorityItem;
	
	public PriorityController() {
		this.priorityItem = new ArrayList<PriorityItem>();
	}
	
	/*
	 * @param newItem 
	 * adds newItem(default +INF) at the end of priority array
	 */
	public void add(PriorityItem newItem) {
		this.priorityItem.add(newItem);
	}
	
	/*
	 * @param index = position in the array
	 * @return the priorityItem in the index position
	 */
	public PriorityItem getPriorityItem(int index) {
		return this.priorityItem.get(index);
	}
}
