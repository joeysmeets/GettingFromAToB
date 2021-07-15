import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Joey & Yusuf
 * @version 15.07..21
 */
public class WeightedGraph {
	
	private  String[] vertexNames;
	private  int[][] matrix;
	private  Random r;
	private  int edges;
	private  int vertices;
	private  ArrayList<Vertex> weightedGraph = new ArrayList<Vertex>();
	
	
	public WeightedGraph() {
		r = new Random();
		fillVertexNames();
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param weight
	 * @return
	 */
	public Edge createEdge(Vertex from, Vertex to, int weight) {
		return new Edge(from, to, weight);
	}
	
	/**
	 * 
	 * @param index
	 * @param name
	 * @param previous
	 * @param distance
	 * @param visited
	 * @return
	 */
	private Vertex createVertex(int index, String name) {
		return new Vertex(index, name);
	}
	
	/**
	 * 
	 * @param index
	 * @param name
	 * @return
	 */
	private Vertex createVertex(int index, String name) {
		return new Vertex(index, name);
	}
	
//	/**
//	 * 
//	 * @return
//	 */
//	private int getVertices() {
//		return vertices;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 */
//	private int getEdges( ){
//		return edges;
//	}
	
	/**
	 * 
	 */
	private void randomGraph() {
		
	}

	
	/**
	 * Creates a Matrix with a given amount of vertices and puts a given amount of random weighted edges 
	 * between them. If then puts the vertices in an Arraylist to store the vertices and it further contructs 
	 * the vertices by adding the neighbors to their neighbor HashMap.  
	 * @param vertices
	 * @param edges
	 */
	public void createWeightedGraph(int vertices, int edges) {
		matrix = createMatrix(vertices, edges);
		
		for (int i = 0; i < matrix.length; i++) {
			Vertex vertex = new Vertex(i, vertexNames[i]);
			weightedGraph.add(vertex);
		}
		
		for (int i = 0; i < vertices; i++) {
			Vertex vertex = weightedGraph.get(i);
			for (int j = 0; j < vertices; j++) {
				if (matrix[i][j] != 0) {
					vertex.addNeighbor(new Vertex(j, vertexNames[j]), matrix[i][j]);
				}
			}
		}
	}
	
	
	/**
	 * Creates a matrix with the given amount of edges and vertices. 
	 * It also randomly puts the weight between vertices.
	 * @param vertices
	 * @param edges
	 * @return
	 */
	private int[][] createMatrix(int verticess, int edgess) {
		vertices = verticess;
		edges = edgess;
		matrix = new int[vertices][vertices];
		
		// Setting edges between points to the max value.
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				matrix[i][j] = 0;
			}
		}
		
		while(edges != 0) {
			int i = r.nextInt(vertices);
			int j = r.nextInt(vertices);
			if(matrix[i][j] == 0 && i != j && matrix[j][i] == 0){
				int temp = r.nextInt(100) + 1;
				System.out.println(matrix[i][j] = temp);
				matrix[j][i] = temp;
				edges--;
			}
		}
		return matrix;
	}
	
	
	/**
	 * 
	 */
	public String toString() {
//		for (int i = 0; i < vertices; i++) {
//			for (int j = 0; j < vertices; j++) {
//				System.out.println(matrix[i][j] + "  ");
//			}
//			System.out.println();
//		}
		String connections = "";
		
		for (int i = 0; i < vertices; i++) {
			connections += "Vertex " + vertexNames[i] + " is connected to:" + "\n";
			for (int j = 0; j < vertices; j++) {
				if(matrix[i][j] != 0) {
					connections += vertexNames[j] + "\n";
				}
			}
			connections += "\n" + "\n";
		}
		return connections;
	}
	
	/**
	 * Fills the vertexNames class field with the 40 biggest cities on the world.
	 */
	private void fillVertexNames() {
		vertexNames = new String[]{"Tokyo", "Delhi", "Shanghai", "Sao Paulo", "Mexico City", "Cairo", "Mumbai", 
		                         "Beijing", "Dhaka", "Osaka", "New York", "Karachi", "Buenos Aires", "Chongqing", 
		                         "Istanbul", "Kolkata", "Manila", "Lagos", "Rio de Janeiro", "Tianjinv", "Kinshasa",
		                         "Guangzhou", "Los Angeles", "Moscow", "Shenzhen", "Lahore", "Bangalore", "Paris",
		                         "Bogota", "Jakarta", "Chennai", "Lima", "Bangkok", "Seoul", "Nagoya", "Hyderadad",
		                         "London", "Tehran", "Chicago", "Chengdu"};
	}
	
	/**
	 * 
	 * @param startVertex
	 * @param endVertex
	 * @return
	 * @throws NoPathException 
	 */
	public String dijksta(int startVertex, int endVertex, Mode mode) throws NoPathException {
//		if (weightedGraph.get(startVertex).getNeighbors().isEmpty() || weightedGraph.get(endVertex).getNeighbors().isEmpty()) {
//			throw new NoPathException("There is no path from " + weightedGraph.get(startVertex).getName() + " to " + weightedGraph.get(endVertex).getName());
//		}
		
		Vertex vertex = weightedGraph.get(startVertex);
		vertex.setVisited(true);
		for (Vertex v : vertex.neighbors.keySet()) {
			if(v.isVisited() == false && v.getDistance() > v.neighbors.get(v) + v.getDistance()) {
//				v.
			}
		}
	}
	
	public enum Mode {
		CHEAPEST, SHORTEST;
	}
}