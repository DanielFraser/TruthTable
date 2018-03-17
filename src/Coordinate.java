import java.awt.Point;

public class Coordinate extends Point implements Comparable<Coordinate>
{
	public Coordinate(int x, int y)
	{
		setLocation(x, y);
	}

	@Override
	public String toString() {

		return "(" + x + "," +  y + ")";
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public Coordinate getSymmetric() {
		return new Coordinate(this.y, this.x);
	}

	@Override
	public int compareTo(Coordinate arg0) {
		if(arg0 instanceof Coordinate)
		{
			Coordinate co = (Coordinate) arg0;
			return (this.x == co.x) ? this.y-co.y : this.x-co.x;
		}
		else
			return -1;
	}
}
