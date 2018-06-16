package algraph.model;

public class NodeModel implements Comparable<NodeModel> {
	private int index;
	private String label;
	
	private String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	public NodeModel(int number){
		this.index = number;
		this.label = getCharForNumber(++number);
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

	@Override
	public int compareTo(NodeModel node2) {
		return this.index - node2.getIndex();
	}
}
