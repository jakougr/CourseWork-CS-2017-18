
/**
 * Main Class
 * @author ik5g15
 *
 */

public class Main {

	public static void main(String[] args) throws Exception{
		
			for (int i = 4; i <= 25; i++) {
				
				Grid grid = new Grid(0, i - 1, 1, i - 1, 2, i - 1, i - 1, i - 1, 1, i - 3, 1, i - 2, 1,
						i - 1, i - 1, i - 1, i);
				System.out.println("\n\n********GRID " + i + "X" + i + " TESTING********");
				
				try {
					System.out.println("BFS " + i + ", is starting.");
					grid.breadthFirst(grid.currPos, grid.finPos);
					System.out.println("BFS " + i + ", has ended.\n");
					
					if (i<=6){
					
					System.out.println("DFS " + i + ", is starting.");
					grid.depthFirst(grid.currPos, grid.finPos);
					System.out.println("DFS " + i + ", has ended.\n");
					}
					
					
					System.out.println("A* " + i + ", is starting.");
					grid.astar(grid.currPos, grid.finPos);
					System.out.println("A* " + i + ", has ended.\n");
					
					if(i<11){
						System.out.println("IDS " + i + ", is starting.");
						grid.idsMethod(grid.currPos, grid.finPos, null, 0);
						System.out.println("IDS " + i + ", has ended.\n");
					}
				} catch (OutOfMemoryError oome) {
					System.out.println("I ran out of memory :(");
				} catch (Exception e) {
					System.out.println("Error");
				}
			}


	}
		

	
}
