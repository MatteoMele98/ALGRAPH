package algraph.controller;

import java.util.ArrayList;
import java.util.TreeMap;

import algraph.model.NodeModel;
import algraph.view.BoolItem;


public class VisitedController {
	public TreeMap<NodeModel,BoolItem> boolItemMap;
	private HomeController homeController;
	
	
	
	public VisitedController(HomeController homeController) {
		this.boolItemMap = new TreeMap<NodeModel,BoolItem>();
		
		this.homeController = homeController;
	}
	
	/*
	 * @param newItem 
	 * adds newItem(default false) at the end of boolItem array
	 */
	public void add(NodeModel node,BoolItem newItem) {
		this.boolItemMap.put(node, newItem);
	}
	
	/*
	 * @param index
	 * @return the BoolItem in the index position
	 */
	public BoolItem getBoolItem(NodeModel node) {
		return this.boolItemMap.get(node);
	}
}
