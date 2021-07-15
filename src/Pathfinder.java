/**
 * 
 * @author joey
 *
 */
public class Pathfinder {
	
	public static void main(String[] args) throws NoPathException {
		WeightedGraph test = new WeightedGraph();
//		matrix = createMatrix(20, 40);
//		printMatrix();
		test.dijksta(1, 3, WeightedGraph.Mode.SHORTEST);
		test.createWeightedGraph(20,40);
		System.out.println(test);
	}
}
