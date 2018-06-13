package algraph.model;

public class NodeModel {
	private int index;
	private String label;
	
	private String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	private Integer getNumberForChar(String c) {
		return (Integer.parseInt(c) - 65);
	}
	
	public NodeModel(int number){
		this.index = number;
		this.label = getCharForNumber(++number);
	}
	
	public NodeModel(String c) {
		this.index = getNumberForChar(c);
		this.label = c;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int number) {
		this.index = number;
	}

}
