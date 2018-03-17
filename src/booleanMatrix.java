import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class booleanMatrix extends JPanel
{
	private JTextField txtRange;
	private JTextField txtA;
	private JTextField txtB;
	private JTextWrapPane txtAreaFirst;
	private JTextWrapPane txtAreaSecond;
	private JTextWrapPane txtAreaFinal;
	private StyledDocument doc;
	private StyledDocument doc1;
	private StyledDocument doc2;
	/**
	 * Create the application.
	 */
	public booleanMatrix() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 950, 740);
		this.setLayout(null);

		JLabel lblTypeInRange = new JLabel("Type in range like 0-50 (min cannot be lower than 0)");
		lblTypeInRange.setBounds(10, 11, 425, 14);
		add(lblTypeInRange);

		txtRange = new JTextField();
		txtRange.setBounds(54, 36, 563, 20);
		add(txtRange);
		txtRange.setColumns(10);

		JLabel lblRange = new JLabel("Range: ");
		lblRange.setBounds(10, 39, 112, 14);
		add(lblRange);

		JLabel lblTypeInFirst = new JLabel("Type in first subset");
		lblTypeInFirst.setBounds(10, 82, 103, 14);
		add(lblTypeInFirst);

		JLabel lblA = new JLabel("A: ");
		lblA.setBounds(10, 110, 112, 14);
		add(lblA);

		txtA = new JTextField();
		txtA.setColumns(10);
		txtA.setBounds(37, 107, 580, 20);
		add(txtA);

		JLabel lblTypeInSecond = new JLabel("Type in second subset");
		lblTypeInSecond.setBounds(10, 182, 124, 14);
		add(lblTypeInSecond);

		JLabel lblB = new JLabel("B:");
		lblB.setBounds(10, 207, 112, 14);
		add(lblB);

		txtB = new JTextField();
		txtB.setColumns(10);
		txtB.setBounds(37, 204, 580, 20);
		add(txtB);

		JLabel lblAnswer = new JLabel("Answer: ");
		lblAnswer.setBounds(10, 269, 563, 14);
		add(lblAnswer);

		JLabel lblSymbol = new JLabel("U");
		lblSymbol.setBounds(259, 447, 20, 14);
		add(lblSymbol);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) 
				{
					String s = e.getItem().toString();
					if(s.charAt(0) != 'A' && s.charAt(0) != 'B')
						lblSymbol.setText(""+s.charAt(0));
					else
						lblSymbol.setText("o");
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"U (union)", "\u2229 (intersection)", "A o B (Compositon)", "B o A (Compositon)"}));
		comboBox.setBounds(10, 151, 154, 20);
		add(comboBox);

		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = solve(txtRange.getText(), txtA.getText(), txtB.getText(), comboBox.getSelectedItem().toString());
				lblAnswer.setText("Answer: " + s);
			}
		});
		btnSolve.setBounds(10, 235, 89, 23);
		add(btnSolve);
		
		doc = (StyledDocument) new DefaultStyledDocument();
		txtAreaFirst = new JTextWrapPane(doc);
		txtAreaFirst.setBackground(Color.BLACK);
		txtAreaFirst.setEditable(false);
		JScrollPane jspFirst = new JScrollPane(txtAreaFirst);
		jspFirst.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspFirst.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspFirst.setBounds(10, 294, 237, 419);
		add(jspFirst);
		
		
		
		doc1 = (StyledDocument) new DefaultStyledDocument();
		txtAreaSecond = new JTextWrapPane(doc1);
		txtAreaSecond.setBackground(Color.BLACK);
		txtAreaSecond.setEditable(false);
		JScrollPane jspSecond = new JScrollPane(txtAreaSecond);
		jspSecond.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspSecond.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspSecond.setBounds(289, 294, 237, 419);
		add(jspSecond);
		
		doc2 = (StyledDocument) new DefaultStyledDocument();
		txtAreaFinal = new JTextWrapPane(doc2);
		txtAreaFinal.setBackground(Color.BLACK);
		txtAreaFinal.setEditable(false);
		JScrollPane jspFinal = new JScrollPane(txtAreaFinal);
		jspFinal.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspFinal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspFinal.setBounds(607, 294, 230, 419);
		add(jspFinal);
		
		JLabel lblNewLabel = new JLabel("=");
		lblNewLabel.setBounds(564, 447, 20, 14);
		add(lblNewLabel);
		
		JButton btnRandA = new JButton("Add random numbers to A");
		btnRandA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtA.setText(randomNums());
			}
		});
		btnRandA.setBounds(146, 78, 196, 23);
		add(btnRandA);
		
		JButton btnRandB = new JButton("Add random numbers to B");
		btnRandB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtB.setText(randomNums());
			}
		});
		btnRandB.setBounds(146, 178, 196, 23);
		add(btnRandB);
	}

	public String solve(String range, String A, String B, String action)
	{
		ArrayList<Coordinate> relA = new ArrayList<Coordinate>();
		ArrayList<Coordinate> relB = new ArrayList<Coordinate>();
		toArray(relA, A);
		toArray(relB, B);

		String s = "";
		if(action.equals("U (union)"))
		{
			String boolS = displayBoolMatrix(boolMatrix(relA));
			txtAreaFirst.setText(boolS);
			changeColor(boolS, txtAreaFirst, doc);
			for(int i = 0; i < relB.size(); i++)
			{
				if(!relA.contains(relB.get(i)))
					relA.add(relB.get(i));
			}
			boolS = displayBoolMatrix(boolMatrix(relB));
			txtAreaSecond.setText(boolS);
			changeColor(boolS, txtAreaSecond, doc1);
			boolS = displayBoolMatrix(boolMatrix(relA));
			txtAreaFinal.setText(boolS);
			changeColor(boolS, txtAreaFinal, doc2);
			s = arrayToString(relA);
		}
		else if(action.contains(" o "))
		{
			String boolS;
			ArrayList<Coordinate> finalArr = new ArrayList<Coordinate>();
			Iterator<Coordinate> it;
			if(action.contains("A o B"))
			{
				for(int i = 0; i < relA.size(); i++)
				{
					it = relB.listIterator();
					while(it.hasNext())
					{
						Coordinate c = it.next();
						if(c.y == relA.get(i).x)
							finalArr.add(new Coordinate(c.x, relA.get(i).y));
					}
				}
			}
			else
			{
				for(int i = 0; i < relB.size(); i++)
				{
					it = relA.listIterator();
					while(it.hasNext())
					{
						Coordinate c = it.next();
						if(c.y == relB.get(i).x)
							finalArr.add(new Coordinate(c.x, relB.get(i).y));
					}
				}
			}
			
			
			boolS = displayBoolMatrix(boolMatrix(relA));
			txtAreaFirst.setText(boolS);
			changeColor(boolS, txtAreaFirst, doc);
			boolS = displayBoolMatrix(boolMatrix(relB));
			txtAreaSecond.setText(boolS);
			changeColor(boolS, txtAreaSecond, doc1);
			boolS = displayBoolMatrix(boolMatrix(finalArr));
			txtAreaFinal.setText(boolS);
			changeColor(boolS, txtAreaFinal, doc2);
			s = arrayToString(finalArr);
		}
		else
		{
			String boolS;
			ArrayList<Coordinate> finalArr = new ArrayList<Coordinate>();
			for(int i = 0; i < relB.size(); i++)
			{
				if(relA.contains(relB.get(i)))
					finalArr.add(relB.get(i));
			}
			if(finalArr.size() > 0)
				s = arrayToString(finalArr);
			else
				s = "Nothing intersects";
			boolS = displayBoolMatrix(boolMatrix(relA));
			txtAreaFirst.setText(boolS);
			changeColor(boolS, txtAreaFirst, doc);
			boolS = displayBoolMatrix(boolMatrix(relB));
			txtAreaSecond.setText(boolS);
			changeColor(boolS, txtAreaSecond, doc1);
			boolS = displayBoolMatrix(boolMatrix(finalArr));
			txtAreaFinal.setText(boolS);
			changeColor(boolS, txtAreaFinal, doc2);
		}
		
		
		return s;
	}

	public String arrayToString(ArrayList<Coordinate> al)
	{
		String s = "";
		if(al.size() > 0)
		{
			s = al.get(0).toString();
			for(int i = 1; i < al.size(); i++)
			{
				s += " " + al.get(i).toString();
			}
		}
		return s;
	}

	public void ridOfDups(ArrayList<Coordinate> c)
	{
		Set<Coordinate> s = new LinkedHashSet<>(c);
		c = new ArrayList<Coordinate>(s);
	}

	public void toArray(ArrayList<Coordinate> p, String s)
	{
		String[] array = s.split(" ");
		int x, y;
		int co;
		for(int i = 0; i < array.length; i++)
		{
			if(isCoord(array[i]))
			{
				co = array[i].indexOf(",");
				x = Integer.parseInt(array[i].substring(1, co));
				y = Integer.parseInt(array[i].substring(co+1,array[i].length()-1));
				Coordinate c = new Coordinate(x, y);
				p.add(c);
			}

		}
		ridOfDups(p);
	}

	public boolean[][] boolMatrix(ArrayList<Coordinate> al) 
	{
		int min, max;
		int[] range = maxAndMin();
		max = range[1];
		min = range[0];
		
		boolean[][] boolMatrix = new boolean[max-min+1][max-min+1];
		for(int i = 0; i < al.size(); i++)
		{
			boolMatrix[al.get(i).x][al.get(i).y] = true;
		}
		return boolMatrix;
	}

	public String boolToString(boolean b)
	{
		if(b)
			return "1";
		else 
			return "0";
	}


	public String displayBoolMatrix(boolean[][] matrix) 
	{
		String s = "";
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				s += boolToString(matrix[i][j]);
				if(j != matrix[i].length - 1)
					s += " ";

			}
			s += "\n";
		}
		return s;
	}
	
	private int[] maxAndMin()
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
	}
	
	public String randomNums()
	{
		String s, ns = "";
		int low, max;
		int[] range = maxAndMin();
		low = range[0];
		max = range[1];
		s = "";
		Random rand = new Random();
		int numOfCoords = rand.nextInt(max*2+1), num1, num2;
		for(int i = 0; i < numOfCoords; i++)
		{
			num1 = rand.nextInt(max+1)-low;
			num2 = rand.nextInt(max+1)-low;
			ns = "(" + num1 + "," + num2 + ")";
			if(s.indexOf(ns) == -1)
				s += " " + ns;
		}
		s = s.trim();
		return s;
	}
	
	public void changeColor(String s, JTextWrapPane ta, StyledDocument doc3)
	{
		SimpleAttributeSet set;
		ta.setLineWrap(false);
		for(int i = 0; i < s.length(); i++)
		{
			if(s.charAt(i) == '0')
			{
				set = new SimpleAttributeSet();
				StyleConstants.setForeground(set, Color.RED);
				doc3.setCharacterAttributes(i, 1, set, true);
			}
				
			else if(s.charAt(i) == '1')
			{
				set = new SimpleAttributeSet();
				StyleConstants.setForeground(set, Color.GREEN);
				doc3.setCharacterAttributes(i, 1, set, true);
			}
				
		}
		
	}
	public boolean isCoord(String s)
	{
		if(s.indexOf("(") == 0 && s.indexOf(")") == s.length()-1)
		{
			if(s.indexOf(",") != -1)
			{
				if(isNumeric(s.substring(1,s.indexOf(",")).trim()) && isNumeric(s.substring(s.indexOf(",")+1,s.length()-1).trim()))
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isNumeric(String str)
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
	
	/*public void update(String s)
	{
		String boolS;
		boolS = displayBoolMatrix(boolMatrix(relB));
		txtAreaSecond.setText(boolS);
		changeColor(boolS, txtAreaSecond, doc1);
		boolS = displayBoolMatrix(boolMatrix(relA));
		txtAreaFinal.setText(boolS);
		changeColor(boolS, txtAreaFinal, doc2);
		s = arrayToString(relA);
	}*/
}
