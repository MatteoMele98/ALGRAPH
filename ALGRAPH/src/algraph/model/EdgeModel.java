package algraph.model;

public class EdgeModel {
	private NodeModel startNode;
	private NodeModel endNode;
	private Integer weight;
	
	public EdgeModel(NodeModel u, NodeModel v, int w){
		this.startNode = u;
		this.endNode = v;
		this.weight = w;
	}
	
	public EdgeModel(NodeModel u, NodeModel v){
		this.startNode = u;
		this.endNode = v;
		this.weight = null;
	}

	public NodeModel getStartNode() {
		return startNode;
	}

	public void setStartNode(NodeModel startNode) {
		this.startNode = startNode;
	}

	public NodeModel getEndNode() {
		return endNode;
	}

	public void setEndNode(NodeModel endNode) {
		this.endNode = endNode;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}