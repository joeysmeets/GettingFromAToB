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
	private  Double[][] matrix; // weightMatrix
	private  Random r; // randomizer
	private  int vertices; // verticesCount, verticesAmount
	private  ArrayList<Vertex> weightedGraph = new ArrayList<Vertex>(); // vertices
	
	/**
	 * Constructor.
	 * Sets up the Random object and fills the vertexNames with the names. 
	 */
	public WeightedGraph() {
		r = new Random();
		fillVertexNames();
		createRandomWeightedGraph(20,40);
	}
	
	/**
	 * Creates a Matrix with a given amount of vertices and puts a given amount of random weighted edges 
	 * between them. It then puts the vertices in an ArrayList to store the vertices and it further constructs 
	 * the vertices by adding the neighbors to their individual neighbor HashMap.  
	 * @param vertices number of vertices wanted
	 * @param edges    number of vertices wanted
	 */
	public void createRandomWeightedGraph(int vertices, int edges) {
		// generate edges and weights
		matrix = createMatrix(vertices, edges);
		
		// instantiate vertices
		for (int i = 0; i < matrix.length; i++) {
			Vertex vertex = new Vertex(i, vertexNames[i]);
			weightedGraph.add(vertex);
		}
		
		// set neighbors
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
	private Double[][] createMatrix(int vertices, int edges) {
		this.vertices = vertices;
		matrix = new Double[vertices][vertices];
		
		// Setting edges between points to POSITIVE INFINITY.
		// matrix creation
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				matrix[i][j] = 0.0;
			}
		}
		
		// Puts a random weight between two vertices in the matrix to represent the edge. 
		while(edges != 0) {
			int i = r.nextInt(vertices);
			int j = r.nextInt(vertices); 
			if(matrix[i][j] == 0.0 && i != j){
				int tempInt = r.nextInt(100) + 1;
				Double temp = (double) tempInt;
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
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				if(matrix[i][j] == 0.0) {
					System.out.print("X");
					System.out.print("\t");
				} else {
					System.out.print(matrix[i][j]);
					System.out.print("\t");
				}
			}
			System.out.println();
		}
		System.out.println();
		
		String connections = "";
		
		for (int i = 0; i < vertices; i++) {
			connections += vertexNames[i] + " is connected to:" + "\n";
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
		Vertex currentVertex = weightedGraph.get(startVertex);
		Vertex destinationVertex = weightedGraph.get(endVertex);
		
		if (currentVertex.getNeighbors().isEmpty() == true || 
			destinationVertex.getNeighbors().isEmpty() == true) {
			throw new NoPathException("There is no path from " + currentVertex.getName() + " to " + destinationVertex.getName());
		}
		
		// local Vertex object to keep track of the shortest path neighbor for each Vertex
		Vertex smallestDistanceVertex = new Vertex(1000, "smallestDistance");
		// set distance to a Positive infinity double, so that the neighbor's distance will always be smaller if there is an Edge between
		smallestDistanceVertex.setDistance(Double.POSITIVE_INFINITY);
		
		// Make a starting value for the path distance. 
		currentVertex.setDistance((double) 0);
		
		// as long as the current Vertex is not visited, we keep going
		while (destinationVertex.isVisited() == false) {
			// if the current vertex has neighbors, continue
			if (currentVertex.getNeighbors().isEmpty() == false) {
				// looping through all neighbors of the current vertex
				for (Vertex neighbor : currentVertex.neighbors.keySet()) {
					// if the neighbor has not been visited yet, continue
					if (neighbor.isVisited() == false) {
						// if the neighbor has a bigger distance than the path from start to him through the current vertex
						// check if we found a cheaper route
						if (neighbor.getDistance() > currentVertex.getDistance() + currentVertex.neighbors.get(neighbor)) {
							// if the cheapest path is asked for, set the distance of the neighbor distance to the current distance + the weight of the edge between
							if (mode == Mode.CHEAPEST) {
								neighbor.setDistance(currentVertex.getDistance() + currentVertex.neighbors.get(neighbor));
//								System.out.println(currentVertex.getDistance() + currentVertex.neighbors.get(neighbor)); 
							}
							// if the shortest path is asked for, set the distance of the neighbor distance to the current distance + 1 ( 1 more edge)
							if (mode == Mode.SHORTEST) {
								neighbor.setDistance(currentVertex.getDistance() + 1);
								}
							// set the changed neighbor, so that we know where the path comes from
							neighbor.setPrevious(currentVertex);
//							System.out.println(currentVertex.getName());
//							System.out.println(neighbor.getName());
						}
						// if the distance of the checked neighbor is smaller than the current smallest neighbor, change the smallestDistanceVertex value to that neighbor
						if (neighbor.getDistance() < smallestDistanceVertex.getDistance()) {
							smallestDistanceVertex = neighbor;
//							System.out.println(smallestDistanceVertex.getName());
						}
					}	
				}
				// the current vertex has been checked
				currentVertex.setVisited(true);
				// the new current vertex is the neighbor with the shortest or cheapest path
				currentVertex = smallestDistanceVertex;
//				System.out.println(currentVertex.getName());
//				System.out.println(currentVertex.getDistance());
				// reset the smallestDistanceVertex to keep track of the new neighbors
				smallestDistanceVertex.setDistance(Double.POSITIVE_INFINITY);
			} else {
				return "There is no path between the two vertices.";
			}
		}
		// if the destination has been reached, print the path
		if (destinationVertex.isVisited() == true) {
			Stack<String> path = new Stack<>();
			String pathString = "";
			
			// adding all names of the path onto a stack
			while (currentVertex.getIndex() != startVertex) {
				path.push(currentVertex.getPrevious().getName());
				currentVertex = currentVertex.getPrevious();
			}
			
			// popping all items to get them in the right order to put them in a String
			for (int i = 0; i < path.size(); i++) {
				pathString = pathString + path.pop() + ", ";
			}
			
			// returning an appropriate message depending on the mode
			if (mode == Mode.CHEAPEST) {
				return "The cheapest way to get from " + weightedGraph.get(startVertex).getName() + " to " + weightedGraph.get(endVertex).getName()  + "is through: " + "\n" + pathString + "\n" +
						"This way has an end total distance of " + destinationVertex.getDistance(); 	
			}
			
			if (mode == Mode.SHORTEST) {
				return "The shortest way to get from " + weightedGraph.get(startVertex).getName()  + " to " + weightedGraph.get(endVertex).getName()  + "is through: " + "\n" + pathString + "\n" +
						"This way has an end total distance of " + destinationVertex.getDistance();	 
			}
		}
		return "There is no path between the two vertices.";
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