
public class Tile {

	int yCord;
	int xCord;

	// Constructor
	public Tile(int x, int y) {

		this.xCord = x;
		this.yCord = y;

	}
	// Getters and Setters

	public int getyCord() {
		return yCord;
	}

	public void setyCord(int yCord) {
		this.yCord = yCord;
	}

	public int getxCord() {
		return xCord;
	}

	public void setxCord(int xCord) {
		this.xCord = xCord;
	}

	public String getCord() {
									
		return (this.xCord + "," + this.getyCord());
	}

	@Override
	public boolean equals(Object o) {

		Tile tile = (Tile) o;

		if (xCord != tile.xCord)
			return false;
		if (yCord != tile.yCord)
			return false;

		return true;
	}

	@Override
	public int hashCode() {

		int result = 31 * yCord;
		result = 31 * result + xCord;
		return result;
	}

}
