
public class BoolMatrixThread extends Thread
{
	
	@Override
	public void run()
	{
		
	}
	
	public String txt1()
	{
		return "";
	}
	//private 
	/*private int[] maxAndMin()
	{
		int[] range = new int[2];
		String s = txtRange.getText().toString();
		if(s.indexOf("-") > 0)
		{
			
			String nums[] = s.split("-");
			for(int i = 0; i < 2; i++)
			{
				if(nums[i].equals("") || isNumeric(nums[i]))
					if(i == 0)
						range[i] = 0;
					else
						range[i] = 20;
				else
					range[i] = Integer.parseInt(nums[i]);
			}
			
		}
		else
		{
			if(s.indexOf("-") == 0)
			{
				s =s.substring(1);
			}
			s = s.trim();
			range[0] = 0;
			if(isNumeric(s))
				range[1] = Integer.parseInt(s);
			else
				range[1] = 20;
			range[0] = 0;
		}
		return range;
	}*/
	
	private boolean isNumeric(String str)
	{
		if(str.length() == 0)
			return false;
		for(int i = 0; i < str.length(); i++)
		{
			if(!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}
}
