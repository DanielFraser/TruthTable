import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Combinatorics 
{
	private static int threadsDone = 0;
	private static int totalThreads = 0;
	public static Date start;
	public Combinatorics(String s)
	{
		createThreads(s);
	}
	
	public static void reset()
	{
		threadsDone = 0;
	}
	public static void counter()
	{
		++threadsDone;
		if(threadsDone == totalThreads)
		{
			System.out.println(getDateDiff(start, new Date(), TimeUnit.MILLISECONDS));
		}
	}
	
	private void createThreads(String s)
	{
		ArrayList<Character> chars = numOfChars(s);
		totalThreads = 1;
		System.out.println(getDateDiff(start, new Date(), TimeUnit.MILLISECONDS));
		new CombinatoricsThreads(s, "").start();
	}
	
	private ArrayList<Character> numOfChars(String s)
	{
		ArrayList<Character> chars = new ArrayList<Character>();
		for(int i = 0; i < s.length(); i++)
		{
			if(!chars.contains(s.charAt(i)))
				chars.add(s.charAt(i));
		}
		return chars;
	}
	
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}
