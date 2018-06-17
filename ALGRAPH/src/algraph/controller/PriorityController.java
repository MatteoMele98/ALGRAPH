package algraph.controller;

import java.util.ArrayList;
import java.util.TreeMap;

import algraph.model.NodeModel;
import algraph.view.PriorityItem;

public class PriorityController {
	private HomeController homeController;
	public TreeMap<NodeModel,PriorityItem> priorityItemMap;
	
	
	public PriorityController(HomeController homeController) {
		this.homeController = homeController;
		
		this.priorityItemMap = new TreeMap<NodeModel,PriorityItem>();
	}
	
	/*
	 * @param newItem 
	 * adds newItem(default +INF) at the end of priority array
	 */
	public void add(NodeModel node,PriorityItem newItem) {
		this.priorityItemMap.put(node, newItem);
	}
	
	/*
	 * @param index = position in the array
	 * @return the priorityItem in the index position
	 */
	public PriorityItem getPriorityItem(NodeModel node) {
		return this.priorityItemMap.get(node);
	}
}
