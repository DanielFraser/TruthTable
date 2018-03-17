import java.util.ArrayList;

public class CombinatoricsThreads extends Thread
{
	String s = "";
	String chars;
	public CombinatoricsThreads(String initS, String initC) 
	{
		this.s = initS;
		this.chars = initC;
	}

	@Override
	public void run() 
	{
		Combinatorics.reset();
		choicesR();
	}

	private void choicesR()
	{
		String combo = "";
		char[] c = chars.toCharArray();
		for(char a : c)
		{
			s = s.replaceFirst(String.valueOf(a), "");
		}
		
		permutation("", s);
		Combinatorics.counter();
	}

	public void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 1)
	    	System.out.println(chars + prefix + str);
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
}
