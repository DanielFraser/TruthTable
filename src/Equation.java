
public class Equation 
{
	private char c1, c2; 		//the 2 variables
	private boolean b1, b2, single; 	//the 2 booleans (1 for each variable)
	private String symbol, line;//symbol - like || or &&, line - equation in string form

	//constructor
	public Equation(String s)
	{
		line = s;
		c1 = s.charAt(1);
		c2 = s.charAt(7);
		symbol = s.substring(3, 5);
		b1 = setBoolean(s.charAt(0));
		b2 = setBoolean(s.charAt(6));
	}
	//converts string to boolean
	private boolean setBoolean(char c)
	{
		if(c == '!')
			return false;
		return true;
	}
	//returns first char
	public char getC1() {
		return c1;
	}
	//returns second char
	public char getC2() {
		return c2;
	}
	//changes first character
	public void setC1(char c) {
		this.c1 = c;
	}
	//changes second character
	public void setC2(char c) {
		this.c2 = c;
	}
	//returns first boolean
	public boolean isB1() {
		return b1;
	}
	//returns second boolean
	public boolean isB2() {
		return b2;
	}
	//changes first boolean
	public void setB1(boolean b) {
		this.b1 = b;
	}
	//changes second boolean
	public void setB2(boolean b) {
		this.b2 = b;
	}
	//returns symbol
	public String getSymbol() {
		return symbol;
	}

	//line in string form
	public String toString()
	{
		return boolToStringR(b1) + c1 + " " + symbol + " " +  boolToStringR(b2) + c2;
	}
	//changes implication to or and vice versa
	public void convert()
	{
		if(symbol.equalsIgnoreCase("->"))
		{
			this.symbol = "||";
			this.b1 = !this.b1;
		}
		else if(symbol.equalsIgnoreCase("||"))
		{
			this.symbol = "->";
			this.b1 = !this.b1;
		}
	}

	@Override
	//checks if 2 lines are the same
	public boolean equals(Object obj) {
		if(obj instanceof Equation) 
		{
			Equation temp = (Equation) obj;
			if(temp.isB1() == this.b1 && temp.isB2() == this.b2 && temp.getC1() == this.c1 && temp.getC2() == this.c2 && this.symbol.equals(temp.getSymbol()))
				return true;
			else 
				return false;
		}
		else {
			return false;
		}
	}
	//converts boolean to is string form
	private String boolToStringR(boolean b)
	{
		if(b)
			return " ";
		return "!";
	}
	public boolean isSingle() {
		return single;
	}
	public void setSingle(boolean single) {
		this.single = single;
	}
	//negates current equation
	public void negate()
	{
		if(this.symbol.equalsIgnoreCase("->"))
			convert();
		this.b1 = !this.b1;
		this.b2 = !this.b2;
		if(this.symbol.equalsIgnoreCase("||"))
			this.symbol = "&&";
		else if(this.symbol.equalsIgnoreCase("&&"))
			this.symbol = "||";
	}
}
