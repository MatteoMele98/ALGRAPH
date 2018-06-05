package algraph.view;

import algraph.utils.*;

public class GraphView {
	private static final int  MAX_NODES = 15;
	private static final int  MIN_NODES = 3;
	
	public Create_Matrice matrice; //matrice di adiacenza NOTA: bisogna combinarla con la funzione in Homeontroller.java
	private NodeView nodes[]; //array dei nodi
	private EdgeView edge[][]; //matrice archi
	
	/*
	 * n = number nodes
	 */
	public GraphView (int n) {
		this.nodes = new NodeView[MAX_NODES];
		
		//crea grafo
		for(int i = 0; i<MAX_NODES; i++) {
			if(i < n) {
				Point coordinates = new Point(550+250*Math.cos(Math.PI*2*i/n), 300-250*Math.sin(Math.PI*2*i/n));
				this.nodes[i] = new NodeView(i, coordinates);
			}
			else
				//this.nodes[i] = new NodeView();
				
		}
		
		this.matrice=new Create_Matrice();
		this.edge = new Arrow[MAX_NODES][MAX_NODES];
		for(int i=0; i<MAX_NODES; i++) {
			int p;
			for(int z=0; z<MAX_NODES; z++) {
				if(i<n && z<n) {
					if(i==z) {
						this.matrice.insertVal(i, z, 0);
						this.edge[i][z] = new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
						this.edge[i][z].setPeso(0);
					}else {
						if(Math.random() >= 0.5) p =(int)(Math.random() * ( 50-0 ));
						else p =(int)(Math.random() * ( 0-50 ));
						double x=Math.random();
						if(x<0.4 || x>0.7) p=0;
						if(p==0) {
							this.matrice.insertVal(i, z, null);
						}else {
							this.matrice.insertVal(i, z, p);
						}
						this.edge[i][z]=new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
						if(p==0) {
							this.edge[i][z].setPeso(null);
						}else {
							this.edge[i][z].setPeso(p);
						}
						
					}
				}else {
					this.edge[i][z]=new Arrow();
				}
			}	
		}
	}
		public Grafo(int l, Create_Matrice m,boolean d) {
		
		//inserimento nodo
		this.number_of_node=l;
		this.g = new Nodo[20];
		//crea grafo
		for(int i=0; i<20; i++) {
			if(m.getVal(i, i)!=null) 
				this.g[i]=new Nodo(i,i, 550+250*Math.cos(Math.PI*2*i/this.number_of_node), 300-250*Math.sin(Math.PI*2*i/this.number_of_node));
			else 
				this.g[i]=new Nodo();
		}
	
		this.matrice=new Create_Matrice();
		this.edge = new Arrow[20][20];
		for(int i=0; i<20; i++) {
			for(int z=0; z<20; z++) {
				if(m.getVal(i, z)!=null) {
					this.matrice.insertVal(i, z, m.getVal(i, z));
					this.edge[i][z]=new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
					this.edge[i][z].setPeso(m.getVal(i,z));
				}else {
					this.matrice.insertVal(i, z, m.getVal(i, z));
					this.edge[i][z]=new Arrow();
				}
				//se d � false inserisci altrimenti elimina	
				//if(!d) {
					/*if(this.matrice.getVal(i, i)==null && !insert) {
						this.matrice.insertVal(i, i, 0);
						this.edge[i][i]=new Arrow();
						insert=true;
					}else if(this.matrice.getVal(i, z)==null){
						this.matrice.insertVal(i, z, m.getVal(i, z));
						this.edge[i][z]=new Arrow();
					}else{
						this.matrice.insertVal(i, z, m.getVal(i, z));
						this.edge[i][z]=new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
						this.edge[i][z].setPeso(m.getVal(i,z));
					}*/
						//inserisce nodo
						/*if(i<this.number_of_node && z<this.number_of_node) {
							//if(this.matrice.getVal(i, i)==null)
							if(z<this.number_of_node-1 && i<this.number_of_node-1) {
								if(this.matrice.getVal(i, i)==null && !insert) {
									this.matrice.insertVal(i, z, null);
									this.edge[i][z]=new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
									this.edge[i][z]=new Arrow();
									insert=true;
								}
								else {
									this.matrice.insertVal(i, z, m.getVal(i,z));
									this.edge[i][z]=new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
									this.edge[i][z].setPeso(m.getVal(i,z));
								}
							}else {
								this.matrice.insertVal(i, z, null);
								this.edge[i][z]=new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
								this.edge[i][z]=new Arrow();
							}
						}else {
							this.edge[i][z]=new Arrow();
						}*/
				/*	}else {
					if(i<this.number_of_node && z<this.number_of_node) {
							this.matrice.insertVal(i, z, m.getVal(i,z));
							this.edge[i][z]=new Arrow(this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),this.g[i].getPosXCircle(),this.g[i].getPosYCircle(),this.g[z].getPosXCircle(),this.g[z].getPosYCircle(),25);
							this.edge[i][z].setPeso(m.getVal(i,z));	
						}else {
							this.edge[i][z]=new Arrow();
						}
					}*/
			}	
		}
	}
	@Override
	public int getNumberOfNode() {
		//System.out.println("N nodi "+this.matrice.getDimensione());
		return this.number_of_node;
	}
	@Override
	public void insertNode() {
		
		boolean insert=false;
		for(int i=0; i<20; i++) {
			for(int z=0; z<20; z++) {
				if(this.matrice.getVal(i, i)==null && !insert) {
					if(i>this.number_of_node-1) {
						this.number_of_node=this.number_of_node+1;
						this.matrice.insertVal(i, i, 0);
						this.edge[i][z]=new Arrow();
						insert=true;
					}else {
						this.matrice.insertVal(i, i, 0);
						this.edge[i][z]=new Arrow();
						insert=true;
					}
				}
			}
		}
	}
	@Override
	public void insertEdge(int uno, int due,int val) {
		if(uno==due) {
			this.matrice.insertVal(uno, due, null);
		}else {
			this.matrice.insertVal(uno, due, val);
			this.edge[uno][due]=new Arrow(this.g[uno].getPosXCircle(),this.g[uno].getPosYCircle(),this.g[due].getPosXCircle(),this.g[due].getPosYCircle(),this.g[uno].getPosXCircle(),this.g[uno].getPosYCircle(),this.g[due].getPosXCircle(),this.g[due].getPosYCircle(),25);
			this.edge[uno][due].setPeso(val);
	}
			
	}
	@Override
	public void deleteNode(int r) {
		for(int i=0; i<20; i++) {
			this.matrice.insertVal(r, i, null);
			this.matrice.insertVal(i, r, null);
			this.edge[r][i]=new Arrow();
			this.edge[i][r]=new Arrow();
		}
	}
	@Override
	public Nodo getNode(int n) {
		return this.g[n];
	}
	@Override
	public Arrow getArrow(int n,int m) {
		return this.edge[n][m];
	}
}
