package algraph.controller;

import javafx.scene.control.TextArea;

public class PseudoCodeController {
	HomeController homeController;
	String pseudoCode;
	
	public PseudoCodeController(HomeController homeController) {
		this.homeController = homeController;
		this.pseudoCode = new String("");
	}
	
	public void addString(StringBuilder s) {
		this.pseudoCode = s.toString();
	}
	
	public void addString(String s) {
		this.pseudoCode = s;
	}
	
	public String getString() {
		return this.pseudoCode;
	}
	
	public void clearAll() {
		this.pseudoCode="";
	}
}
