package algraph.controller;

import algraph.view.BoolItem;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class BoolItemController {
	private BoolItem boolItem[]=new BoolItem[15];
	
	public BoolItemController() {
		for(int i=0; i<15; i++) {
			this.boolItem[i] = new BoolItem(i, false);
			this.boolItem[i].getHBox().setVisible(false);
		}
	}
	public void setBoolItem(int i, boolean v) {
		this.boolItem[i].setBool(v);
	}
	public void setNumberOfBool(int i, int n) {
		this.boolItem[i].setNumber(n);
	}
	public Text getBoolItem(int i, boolean v) {
		return this.boolItem[i].getBool();
	}
	public Text getNumberOfBool(int i, int n) {
		return this.boolItem[i].getNumber();
	}
	public HBox getHBox(int i) {
		return this.boolItem[i].getHBox();
	}
	
}
