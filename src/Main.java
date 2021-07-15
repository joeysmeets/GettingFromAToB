/**
 * Main class to test if all works.
 * @author Joey & Yusuf
 * @version 15.07.21
 */
public class Main {
	
	public static void main(String[] args) throws NoPathException {
		WeightedGraph test = new WeightedGraph();
//		test.createRandomWeightedGraph(20,40);
//		matrix = createMatrix(20, 40);
//		test.randomDijkstra(WeightedGraph.Mode.CHEAPEST);
		test.dijkstra(1, 3, WeightedGraph.Mode.SHORTEST);
//		System.out.println(test);
	}
}
