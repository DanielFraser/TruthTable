import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TruthTable
{
	//private ArrayList<Character> variables = new ArrayList<Character>();
	private HashSet<Character> variables = new HashSet<>();
	private String expr;
	//implicit (!A) || B
	//equivalence a == b
	public String calculateVariables(String s1) throws ScriptException
	{
		//initializes the script engine
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		StringBuilder vars = new StringBuilder(variables.size()*5);
		
		//sets a value for each variable
		Iterator<Character> it = variables.iterator();
		
		for(int i = 0; i < s1.length(); i++)
		{
			vars.append(it.next().toString() + " = " + s1.charAt(i) + "\n");
		}
		
		//adds in each variable to the engine
		engine.eval(vars.toString());

		//evaluates the equation with given values
		String e = engine.eval(expr).toString();
		if(e.equals("false"))
			e = "0";
		else if(e.equals("true"))
			e = "1";
		return e;
	}
	
	//initializes the variables
	public void setVariables(String equation)
	{
		variables.clear();
		expr = equation;
		for(char c : equation.toCharArray())
		{
			if(Character.isAlphabetic(c))
				variables.add(c);
		}
	}
	//returns the variable arrayList
	public HashSet<Character> getVariables()
	{
		return variables;
	}
}

