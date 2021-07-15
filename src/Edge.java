/**
 * Class to define a custom type to represent edges in a weighted graph
 * @author Joey & Yusuf
 * @version 15.07..21
 */
public class Edge {
	
	public Vertex startVertex;
	public Vertex endVertex;
	public int weight;
	
	/**
	 * Constructor.
	 * @param startVertex	Starting Vertex.
	 * @param endvertex		Neighboring Vertex.
	 * @param weight		Weight between two.
	 */
	public Edge (Vertex startVertex, Vertex endVertex, int weight) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.weight = weight;
	}
}