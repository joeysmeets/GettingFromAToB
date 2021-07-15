import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Class that puts all classes for the weighted Graph together. 
 * @author Joey & Yusuf
 * @version 15.07.21
 */
public class WeightedGraph {
	
	private  String[] vertexNames;
	private  int[][] matrix;
	private  Random r;
	private  int edges;
	private  int vertices;
	private  ArrayList<Vertex> weightedGraph = new ArrayList<Vertex>();
	
	/**
	 * Constructor.
	 * Sets up the Random object and fills the vertexNames with the names. 
	 */
	public WeightedGraph() {
		r = new Random();
		fillVertexNames();
	}
	
	/**
	 * Creates a Matrix with a given amount of vertices and puts a given amount of random weighted edges 
	 * between them. It then puts the vertices in an ArrayList to store the vertices and it further constructs 
	 * the vertices by adding the neighbors to their individual neighbor HashMap.  
	 * @param vertices number of vertices wanted
	 * @param edges    number of vertices wanted
	 */
	public void createRandomWeightedGraph(int vertices, int edges) {
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
	 * @param verticess number of vertices wanted
	 * @param edgess    number of vertices wanted
	 * @return matrix   Complete matrix of the given number of vertices and edges.
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
				matrix[i][j] = temp;
				matrix[j][i] = temp;
				edges--;
			}
		}
		return matrix;
	}
	
	
	/**
	 * Overrides the default toString method and makes sure that the two-dimensional Array is shown better.
	 * @return connections String of each vertex' connections.
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
	 * Determines the shortest of cheapest way between two vertices. 
	 * @param  startVertex 		Starting point of the path.
	 * @param  endVertex   		End point of the path.
	 * @param  mode             Shortest or Cheapest.
	 * @return pathString 		Through which vertices does the path go and what is the final distance value.
	 * @throws NoPathException 	Exception for when there is no connection between the start- and end vertices.
	 */
	public String dijkstra(int startVertex, int endVertex, Mode mode) throws NoPathException {
		if (weightedGraph.get(startVertex).getNeighbors().isEmpty() == true || 
			weightedGraph.get(endVertex).getNeighbors().isEmpty() == true) {
			throw new NoPathException("There is no path from " + weightedGraph.get(startVertex).getName() + " to " + weightedGraph.get(endVertex).getName());
		}
		
		Vertex currentVertex = weightedGraph.get(startVertex);
		Vertex destinationVertex = weightedGraph.get(endVertex);
		
		Vertex smallestDistanceVertex = new Vertex(1000, "smallestDistance");
		smallestDistanceVertex.setDistance(Integer.MAX_VALUE);
		
		while (currentVertex != destinationVertex) {
			if (currentVertex.getNeighbors().isEmpty() == false) {
				for (Vertex neighbor : currentVertex.neighbors.keySet()) {
					if (neighbor.isVisited() == false) {
						if (neighbor.getDistance() == 0 || 
							neighbor.getDistance() > matrix[currentVertex.getIndex()][neighbor.getIndex()] + currentVertex.getDistance()) {
							
							if (mode == Mode.CHEAPEST) {
								neighbor.setDistance(currentVertex.getDistance() + matrix[currentVertex.getIndex()][neighbor.getIndex()] + currentVertex.getDistance());	
							}
							
							if (mode == Mode.SHORTEST) {
								neighbor.setDistance(currentVertex.getDistance() + 1);
								}
							
							if (neighbor.getDistance() < smallestDistanceVertex.getDistance()) {
								smallestDistanceVertex = neighbor;
							}
						}
					}
					neighbor.setPrevious(currentVertex);	
				}
				currentVertex.setVisited(true);
				currentVertex = smallestDistanceVertex;
				smallestDistanceVertex.setDistance(Integer.MAX_VALUE);
			} 
		}	
		if (currentVertex == destinationVertex) {
			Stack<String> path = new Stack<>();
			String pathString = "";
			
			while (currentVertex.getIndex() != startVertex) {
				path.push(currentVertex.getPrevious().getName());
				currentVertex = currentVertex.getPrevious();
			}
			
			for (int i = 0; i < path.size(); i++) {
				pathString = pathString + path.pop() + ", ";
			}
			
			if (mode == Mode.CHEAPEST) {
				return "The cheapest way to get from " + weightedGraph.get(startVertex) + " to " + weightedGraph.get(endVertex) + "is through: " + "\n" + pathString + "\n" +
						"This way has an end total distance of " + destinationVertex.getDistance(); 	
			}
			
			if (mode == Mode.SHORTEST) {
				return "The shortest way to get from " + weightedGraph.get(startVertex) + " to " + weightedGraph.get(endVertex) + "is through: " + "\n" + pathString + "\n" +
						"This way has an end total distance of " + destinationVertex.getDistance();	 
			}
		}
		return "";
	}	
	
	/**
	 * Takes two random vertices in a weighted graph and determines the shortest- or cheapest path.
	 * @param  mode  			Shortest or Cheapest.
	 * @return pathString   	Through which vertices does the path go and what is the final distance value.
	 * @throws NoPathException	Exception for when there is no connection between the start- and end vertices.
	 */
	public String randomDijkstra(Mode mode) throws NoPathException {
		int start = r.nextInt(weightedGraph.size());
		int end =   r.nextInt(weightedGraph.size());
		while (end == start) {
			end =   r.nextInt(weightedGraph.size());
		}
		return dijkstra(start, end, mode);
	}
	
	/**
	 * Enum for two modes of Dijkstra.
	 * @author Joey & Yusuf
	 * @version 15.07.21
	 */
	public enum Mode {
		CHEAPEST, SHORTEST;
	}
}