import java.util.HashMap;

/**
 * Class to define a custom type to represent Vertices in a weighted graph
 * @author Joey & Yusuf
 * @version 15.07..21
 */
public class Vertex {
	
	private int index;
	private String name;
	private Double distance;
	HashMap<Vertex, Double> neighbors = new HashMap<>();
	private Vertex previous;
	private boolean visited;
	/**
	 * Constructor with only name and index.
	 * @param index index in the Matrix.
	 * @param name	Contains the name of the Vertex.
	 */
	public Vertex (int index, String name) {
		this.index = index;
		this.name = name;
		distance = Double.POSITIVE_INFINITY;
	}
	
	// Setters and Getters
	
	/**
	 * Sets the vertex' index.
	 * @param index.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * Returns the vertex' index
	 * @return index.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Sets the name of this vertex.
	 * @param name	String with the new name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the vertex' name.
	 * @return name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the shortest distance to from the starting vertex to this vertex.
	 * @param distance	Integer value of shortest distance.
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	/**
	 * Returns shortest distance to the starting vertex.
	 * @return distance.
	 */
	public Double getDistance() {
		return distance;
	}
	
	/**
	 * Adds a neighbor to the HashMap and sets its distance.
	 * @param neighbor 
	 * @param distance weight between this vertex and its neighbor
	 */
	public void addNeighbor(Vertex neighbor, Double distance) {
		neighbors.put(neighbor, distance);
	}
	
	
	/**
	 * Returns all adjacent neighbors with the distance to them.
	 * @return neighbors HashMap of all neighbors of the vertex with their distance.
	 */
	public HashMap<Vertex, Double> getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Sets the previous checked vertex
	 * @param previous
	 */
	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}
	
	/**
	 * Returns the vertex that came before in the shorts- or cheapest path.
	 * @return previous The Vertex that came before in the path.
	 */
	public Vertex getPrevious() {
		return previous;
	}
	
	/**
	 * Sets the visited status of this vertex.
	 * @param visited	Was this Vertex visited.
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	/**
	 * Returns if the vertex was already visited.
	 * @return Visited status of the Vertex.
	 */
	public boolean isVisited() {
		return visited;
	}
}