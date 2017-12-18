
public class Setup {

	Tile[][] grid;
	int gridSize;
	Tile agent, a, b, c;

	public Tile[][] getGrid() {
		return grid;
	}

	public Setup(int xCordA, int yCordA, int xCordB, int yCordB, int xCordC, int yCordC, int xCordAgent, int yCordAgent,
			int size) {

		this.gridSize = size;
		grid = new Tile[gridSize][gridSize];
		populate();

		agent = new Tile(xCordAgent, yCordAgent);
		a = new Tile(xCordA, yCordA);
		b = new Tile(xCordB, yCordB);
		c = new Tile(xCordC, yCordC);

		buildTile(xCordA, yCordA, a);
		buildTile(xCordB, yCordB, b);
		buildTile(xCordC, yCordC, c);
		buildTile(xCordAgent, yCordAgent, agent);
	}

	public boolean moveAgent(Direction dir) {

		switch (dir) {
		case UP:
			if (agent.getyCord() == 0)
				return false;
			else {
				int newYPos = agent.getyCord() - 1;
				Tile old = grid[agent.getxCord()][newYPos];
				grid[agent.getxCord()][agent.getyCord()] = old;
				old.setyCord(agent.getyCord());
				grid[agent.getxCord()][newYPos] = agent;
				agent.setyCord(newYPos);

				return true;
			}
		case DOWN:
			if (agent.getyCord() == gridSize - 1)
				return false;
			else {
				int newYPos = agent.getyCord() + 1;
				Tile old = grid[agent.getxCord()][newYPos];
				grid[agent.getxCord()][agent.getyCord()] = old;
				old.setyCord(agent.getyCord());
				grid[agent.getxCord()][newYPos] = agent;
				agent.setyCord(newYPos);

				return true;
			}
		case LEFT:
			if (agent.getxCord() == 0)
				return false;
			else {
				int newXPos = agent.getxCord() - 1;
				Tile old = grid[newXPos][agent.getyCord()];
				grid[agent.getxCord()][agent.getyCord()] = old;
				old.setxCord(agent.getxCord());
				grid[newXPos][agent.getyCord()] = agent;
				agent.setxCord(newXPos);

				return true;
			}
		case RIGHT:
			if (agent.getxCord() == gridSize - 1)
				return false;
			else {
				int newXPos = agent.getxCord() + 1;
				Tile old = grid[newXPos][agent.getyCord()];
				grid[agent.getxCord()][agent.getyCord()] = old;
				old.setxCord(agent.getxCord());
				grid[newXPos][agent.getyCord()] = agent;
				agent.setxCord(newXPos);

				return true;
			}
		}
		return false;

	}

	
	public void populate(){
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				buildTile(i, j, new Tile(i, j));
			}
		}
	}
	public void buildTile(int x, int y, Tile tile) {
		grid[x][y] = tile;
	}

	public boolean compareTo(Setup setup) {
		return (this.a.getCord().equals(setup.a.getCord()) && this.b.getCord().equals(setup.b.getCord())
				&& this.c.getCord().equals(setup.c.getCord())
				&& this.agent.getCord().equals(setup.agent.getCord()));
	}

	public boolean equalToGoal(Setup endState) {
		return (this.a.getCord().equals(endState.a.getCord()) && this.b.getCord().equals(endState.b.getCord())
				&& this.c.getCord().equals(endState.c.getCord()));
	}

	// hashcode and equals
	int temp1 = gridSize - 1;

	@Override
	public int hashCode() {
		int result = gridSize;
		result = 31 * result + (b != null ? b.hashCode() : 0);
		result = 31 * result + (c != null ? c.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {

		Setup state = (Setup) o;

		if (gridSize != state.gridSize)
			return false;
		if (temp1 != state.temp1)
			return false;
		if (a != null ? !a.equals(state.a) : state.a != null)
			return false;
		if (agent != null ? !agent.equals(state.agent) : state.agent != null)
			return false;
		if (b != null ? !b.equals(state.b) : state.b != null)
			return false;
		if (c != null ? !c.equals(state.c) : state.c != null)
			return false;

		return true;
	}
	
	

}
