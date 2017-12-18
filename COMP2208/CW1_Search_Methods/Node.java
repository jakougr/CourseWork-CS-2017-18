
import java.util.ArrayList;

public class Node implements Comparable {

	Setup grid;
	Node parent;
	int depth;

	int distanceToGoal;

	public Node(Setup blocks, Node par) {
		this.parent = par;
		this.grid = blocks;
		this.depth = par.depth + 1;
	}

	public Node(Setup blocks) {
		this.grid = blocks;
		this.depth = 0;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDistanceToGoal() {
		return distanceToGoal;
	}

	public void setDistanceToGoal(int distanceToGoal) {
		this.distanceToGoal = distanceToGoal;
	}

	public Setup getGrid() {
		return grid;
	}

	public void setGrid(Setup grid) {
		this.grid = grid;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Setup getConf() {
		return grid;
	}

	public ArrayList<Node> calcMoves() {

		ArrayList<Node> moves = new ArrayList<Node>();

		
		if (grid.agent.getyCord() < grid.gridSize - 1) {
			Setup state = new Setup(this.grid.a.getxCord(), this.grid.a.getyCord(), this.grid.b.getxCord(),
					this.grid.b.getyCord(), this.grid.c.getxCord(), this.grid.c.getyCord(), this.grid.agent.getxCord(),
					this.grid.agent.getyCord(), this.grid.gridSize);
			Node nodeT = new Node(state, this);
			nodeT.grid.moveAgent(Direction.DOWN);
			moves.add(nodeT);
		}
		if (grid.agent.getxCord() < grid.gridSize - 1) {
			Setup state = new Setup(this.grid.a.getxCord(), this.grid.a.getyCord(), this.grid.b.getxCord(),
					this.grid.b.getyCord(), this.grid.c.getxCord(), this.grid.c.getyCord(), this.grid.agent.getxCord(),
					this.grid.agent.getyCord(), this.grid.gridSize);
			Node nodeT = new Node(state, this);
			nodeT.grid.moveAgent(Direction.RIGHT);
			moves.add(nodeT);
		}
		if (grid.agent.getxCord() > 0) {
			Setup state = new Setup(this.grid.a.getxCord(), this.grid.a.getyCord(), this.grid.b.getxCord(),
					this.grid.b.getyCord(), this.grid.c.getxCord(), this.grid.c.getyCord(), this.grid.agent.getxCord(),
					this.grid.agent.getyCord(), this.grid.gridSize);
			Node nodeT = new Node(state, this);
			nodeT.grid.moveAgent(Direction.LEFT);
			moves.add(nodeT);
		}
		if (grid.agent.getyCord() > 0) {
			Setup state = new Setup(this.grid.a.getxCord(), this.grid.a.getyCord(), this.grid.b.getxCord(),
					this.grid.b.getyCord(), this.grid.c.getxCord(), this.grid.c.getyCord(), this.grid.agent.getxCord(),
					this.grid.agent.getyCord(), this.grid.gridSize);
			Node nodeT = new Node(state, this);
			nodeT.grid.moveAgent(Direction.UP);
			moves.add(nodeT);
		}
		

		return moves;
	}

	public void manhattanValue(Node end) {
		int aCostX = Math.abs(end.getConf().a.getxCord() - getConf().a.getxCord());
		int aCostY = Math.abs(end.getConf().a.getyCord() - getConf().a.getyCord());
		int bCostX = Math.abs(end.getConf().b.getxCord() - getConf().b.getxCord());
		int bCostY = Math.abs(end.getConf().b.getyCord() - getConf().b.getyCord());
		int cCostX = Math.abs(end.getConf().c.getxCord() - getConf().c.getxCord());
		int cCostY = Math.abs(end.getConf().c.getyCord() - getConf().c.getyCord());
		this.distanceToGoal = getDepth() + aCostX + aCostY + bCostX + bCostY + cCostX + cCostY;
	}

	@Override
	public int compareTo(Object node) {
		if (distanceToGoal == ((Node) node).distanceToGoal)
			return 0;
		else if (distanceToGoal < ((Node) node).distanceToGoal)
			return -1;
		else
			return 1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Node node = (Node) o;

		if (grid != null ? !grid.equals(node.grid) : node.grid != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() { // generated automatically
		int result = (grid != null ? grid.hashCode() : 0);
		return result;
	}

}
