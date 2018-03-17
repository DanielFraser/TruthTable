import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("serial")
public class ClosuresPanel extends JPanel
{
	private JTextField txtCoordinates;
	private StyledDocument doc, doc1;
	/**
	 * Create the application.
	 */
	public ClosuresPanel() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 950, 740);
		this.setLayout(null);

		JCheckBox chckbxTransitive = new JCheckBox("Transitive closure");
		chckbxTransitive.setBounds(6, 7, 138, 23);
		add(chckbxTransitive);

		JCheckBox chckbxReflexive = new JCheckBox("Reflexive closure");
		chckbxReflexive.setBounds(6, 33, 138, 23);
		add(chckbxReflexive);

		JCheckBox chckbxSymmetric = new JCheckBox("Symmetric closure");
		chckbxSymmetric.setBounds(6, 59, 138, 23);
		add(chckbxSymmetric);

		JLabel lblNewLabel = new JLabel("T =");
		lblNewLabel.setBounds(6, 89, 33, 14);
		add(lblNewLabel);

		txtCoordinates = new JTextField();
		txtCoordinates.setBounds(33, 89, 262, 20);
		add(txtCoordinates);
		txtCoordinates.setColumns(10);

		JButton btnRandomNumbers = new JButton("Random numbers");
		btnRandomNumbers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtCoordinates.setText(randomNums());
			}
		});
		btnRandomNumbers.setBounds(10, 120, 134, 23);
		add(btnRandomNumbers);

		doc = new DefaultStyledDocument();
		JTextWrapPane txtAnswer = new JTextWrapPane(doc);
		txtAnswer.setEditable(false);
		JScrollPane jspFinal = new JScrollPane(txtAnswer);
		//jspFinal.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspFinal.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspFinal.setBounds(12, 200, 437, 395);
		add(jspFinal);

		doc1 = new DefaultStyledDocument();
		JTextWrapPane txtEqClass = new JTextWrapPane(doc1);
		txtEqClass.setEditable(false);
		txtEqClass.setLineWrap(false);
		JScrollPane jspEqClass = new JScrollPane(txtEqClass);
		jspEqClass.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspEqClass.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspEqClass.setBounds(472, 200, 437, 393);
		add(jspEqClass);

		JCheckBox chckbxEqClasses = new JCheckBox("Equivalence classes");
		chckbxEqClasses.setBounds(146, 7, 149, 23);
		add(chckbxEqClasses);

		JCheckBox chckbxEqClassesSimplify = new JCheckBox("Simplify Equivalence classes (merge all the same classes)");
		chckbxEqClassesSimplify.setBounds(156, 33, 370, 23);
		add(chckbxEqClassesSimplify);

		JButton btnNewButton = new JButton("Solve");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] s = Solve(txtCoordinates.getText(), chckbxTransitive.isSelected(),chckbxReflexive.isSelected(),chckbxSymmetric.isSelected(), chckbxEqClasses.isSelected(),chckbxEqClassesSimplify.isSelected());
				txtAnswer.setText(s[0]);
				txtEqClass.setText(s[1]);
			}
		});
		btnNewButton.setBounds(10, 154, 134, 23);
		add(btnNewButton);

		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				String[] examples = new String[3];
				examples[0] = "(0,0) (0,4) (1,1) (1,3) (2,2) (3,1) (3,3) (4,0) (4,4)";
				examples[1] = "(0,4) (1,1) (1,3) (8,9) (3,2) (3,3) (4,0) (10,10)";
				examples[2] = "(0,4) (1,1) (3,13) (2,2) (13,1) (3,3) (4,0) (9,13)";
				if (event.getStateChange() == ItemEvent.SELECTED) 
				{
					if(event.getItem().toString().equals("Test 2 example"))
					{
						txtCoordinates.setText(examples[0]);
					}
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "Test 2 example", "Example 1", "Example 2"}));
		comboBox.setBounds(181, 121, 114, 20);
		add(comboBox);



	}
	/**
	 * @param s, transitive, reflexive, symmetric, EQclasses
	 * main method that calls the necessary methods
	 * returns a string to display ArrayList and another for equivalent classes
	 * 
	 * @return String[]
	 */
	public String[] Solve(String s, boolean transitive, boolean reflexive, boolean symmetric, boolean EQclasses, boolean simplify)
	{
		ArrayList<Coordinate> relations = new ArrayList<Coordinate>();
		ArrayList<Coordinate> finalRel = new ArrayList<Coordinate>();


		toArray(relations, s);
		finalRel.addAll(relations); //adds all current coordinates
		s = "";
		if(transitive) //if user wants it to be transitive
		{
			transitive(relations, finalRel);
		}
		if(reflexive) //if user wants it to be reflexive
		{
			reflexive(relations);
			for(Coordinate co : relations)
				if(!finalRel.contains(co))
					finalRel.add(co);
		}
		if(symmetric) //if user wants it to be symmetric
		{
			symmetric(relations, finalRel);
		}
		if(EQclasses) //if user wants the equivalence classes (if possible)
		{
			transitive(finalRel);
			reflexive(finalRel);
			reflexive = reflexiveB(finalRel);
			symmetric(finalRel);
			symmetric = symmetricB(finalRel);
			transitive(finalRel);
			transitive = transitiveB(finalRel);
			if(transitive && reflexive && symmetric)
			{
				s = equivalenceClasses(finalRel, simplify);
			}
			else
			{
				s = "Cannot be done! The relation isn't equivalent.";
			}
		}
		finalRel.sort((Coordinate co0, Coordinate co1) -> co0.compareTo(co1));
		String[] textPanes = {arrayToString(finalRel),s};
		return textPanes;

	}
	/**
	 * @param al
	 * converts ArrayList of coordinate to booleanMatrix
	 * 
	 * @return boolMatrix[][]
	 */
	public boolean[][] boolMatrix(ArrayList<Coordinate> al) 
	{
		int[] range = minAndMax(al);
		boolean[][] boolMatrix = new boolean[range[1]-range[0]+1][range[1]-range[0]+1];
		for(int i = 0; i < al.size(); i++)
		{
			boolMatrix[al.get(i).x-range[0]][al.get(i).y-range[0]] = true;
		}
		return boolMatrix;
	}
	/**
	 * @param bm, coAl
	 * converts boolean matrix to arraylist of coordinates
	 * 
	 * @return void
	 */
	public void boolToCoord(boolean[][] bm, ArrayList<Coordinate> coAl) 
	{
		int[] range = minAndMax(coAl);
		int size = bm.length;
		Coordinate co;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(bm[i][j])
				{
					co = new Coordinate(i+range[0], j+range[0]);
					if(!coAl.contains(co))
						coAl.add(co);
				}
			}
		}
	}
	/**
	 * @param bm, coAl
	 * converts boolean matrix to arraylist of coordinates
	 * 
	 * @return void
	 */
	public void boolToCoord(boolean[][] bm, ArrayList<Coordinate> coAl, ArrayList<Coordinate> finalRel) 
	{
		int[] range = minAndMax(finalRel);
		int size = bm.length;
		Coordinate co;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(bm[i][j])
				{
					co = new Coordinate(i+range[0], j+range[0]);
					if(!coAl.contains(co))
						coAl.add(co);
				}
			}
		}
	}
	/**
	 * @param rel, finalRel
	 * adds coordinates to make rel transitive
	 * 
	 * @return void
	 */
	public void transitive(ArrayList<Coordinate> rel, ArrayList<Coordinate> finalRel)
	{
		boolean[][] boolMatrix = boolMatrix(rel);
		int size = boolMatrix.length;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					boolMatrix[j][k] = (boolMatrix[j][k]) || ((boolMatrix[j][i]) && (boolMatrix[i][k]))?true:false;
				}
			}
		}
		boolToCoord(boolMatrix, finalRel);
	}
	/**
	 * @param rel, finalRel
	 * adds coordinates to make finalRel transitive
	 * 
	 * @return void
	 */
	public void transitive(ArrayList<Coordinate> finalRel)
	{
		boolean[][] boolMatrix = boolMatrix(finalRel);
		int size = boolMatrix.length;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					boolMatrix[j][k] = (boolMatrix[j][k]) || ((boolMatrix[j][i]) && (boolMatrix[i][k]))?true:false;
				}
			}
		}
		boolToCoord(boolMatrix, finalRel);
	}
	/**
	 * @param rel, finalRel
	 * adds coordinates to make rel reflexive
	 * 
	 * @return void
	 */
	public void reflexive(ArrayList<Coordinate> rel)
	{
		int[] range = minAndMax(rel);
		Coordinate co = new Coordinate(range[0], range[0]);
		for(int i = range[0]; i <= range[1]; i++)
		{
			co = new Coordinate(i, i);
			if(!rel.contains(co))
			{
				rel.add(co);
			}
		}
	}
	/**
	 * Takes in a String and ArrayList
	 * adds coordinates from string to ArrayList
	 * 
	 * @return void
	 */
	public void symmetric(ArrayList<Coordinate> rel, ArrayList<Coordinate> finalRel)
	{
		Coordinate co;
		for(int i = 0; i < rel.size(); i++)
		{
			co = rel.get(i).getSymmetric();
			if(!rel.contains(co))
			{
				finalRel.add(co);
			}
		}

	}
	/**
	 * Takes in a String and ArrayList
	 * adds coordinates from string to ArrayList
	 * 
	 * @return void
	 */
	public void symmetric(ArrayList<Coordinate> finalRel)
	{
		Coordinate co;
		for(int i = 0; i < finalRel.size(); i++)
		{
			co = finalRel.get(i).getSymmetric();
			if(!finalRel.contains(co))
			{
				finalRel.add(co);
			}
		}

	}
	/**
	 * Takes in a String and ArrayList
	 * adds coordinates from string to ArrayList
	 * 
	 * @return void
	 */
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
	/**
	 * Takes in a String
	 * Checks whether or not string is a coordinate
	 * 
	 * @return whether or not string is coordinate
	 */
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
	/**
	 * Takes in a String
	 * Checks whether or not string is numeric
	 * 
	 * @return whether or not string is numeric
	 */
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
	/**
	 * gets rid of duplicates in Arraylist
	 * 
	 * @return Nothing
	 */
	private void ridOfDups(ArrayList<Coordinate> c)
	{
		Set<Coordinate> s = new LinkedHashSet<>(c);
		c = new ArrayList<Coordinate>(s);
	}

	/**
	 * Creates random string of coordinates
	 * 
	 * @return string of coordinates
	 */
	private String randomNums()
	{
		final int MIN = 0;
		final int MAX = 100;
		Random rand = new Random();
		String s = "", ns;

		int numOfCoords = rand.nextInt(MAX+1), num1, num2;
		for(int i = 0; i < numOfCoords; i++)
		{
			num1 = rand.nextInt(MAX+1)-MIN;
			num2 = rand.nextInt(MAX+1)-MIN;
			ns = "(" + num1 + "," + num2 + ")";
			if(s.indexOf(ns) == -1)
				s += " " + ns;
		}
		s = s.trim();
		return s;
	}
	/**
	 * @param al
	 * turns ArrayList into string
	 * 
	 * @return string of ArrayList
	 */
	private String arrayToString(ArrayList<Coordinate> al)
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
	/**
	 * @param in
	 * turns ArrayList into string
	 * 
	 * @return string of ArrayList
	 */
	private String intArrayToString(ArrayList<Integer> in)
	{
		String s = "";
		for(int i = 0; i < in.size(); i++)
		{
			s += in.get(i);
			if(i != in.size() - 1)
				s+= ", ";
		}
		return s;
	}
	public String stringArrToString(String[] s)
	{
		String newS = "";
		for(String b : s)
			newS += " " + b;
		newS = newS.trim();
		return newS;
	}
	/**
	 * @param hm
	 * turns HashMap into string
	 * 
	 * @return string of HashMap
	 */
	public String eqclassToString(HashMap<Integer, ArrayList<Integer>> hm, boolean simplify)
	{
		String s = "", k = "";
		if(hm.size() > 0)
		{
			Integer key;
			ArrayList<Integer> al;
			if(simplify)
			{
				ArrayList<String> keys = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();
				for (Entry<Integer, ArrayList<Integer>> entry : hm.entrySet()) 
				{
					key = entry.getKey();
					al = new ArrayList<Integer>(entry.getValue());
					k = intArrayToString(entry.getValue());
					if(!values.contains(k))
					{
						values.add(k);
						keys.add(String.valueOf(entry.getKey()));
					}
				}

				for(int i = 0; i < keys.size(); i++)
				{
					s += "["+keys.get(i)+"] = " + values.get(i) + "\n";
				}
			}
			else
			{
				for (Entry<Integer, ArrayList<Integer>> entry : hm.entrySet()) 
				{
					key = entry.getKey();
					al = new ArrayList<Integer>(entry.getValue());
					s +=  "[" + key+"] = " + intArrayToString(al) + "\n";
				}
			}

		}
		return s;
	}
	/**
	 * @param rel
	 * gets lowest and highest numbers in coordinate ArrayList
	 * 
	 * @return lowest and highest numbers
	 */
	public int[] minAndMax(ArrayList<Coordinate> rel)
	{
		int[] range = {rel.get(0).x,rel.get(0).x};
		for(int i = 0; i < rel.size(); i++)
		{
			if(rel.get(i).y < range[0])
				range[0] = rel.get(i).y;
			else if(rel.get(i).y > range[1])
				range[1] = rel.get(i).y;
			if(rel.get(i).x < range[0])
				range[0] = rel.get(i).x;
			else if(rel.get(i).x > range[1])
				range[1] = rel.get(i).x;
		}
		return range;
	}
	/**
	 * @param s, ta, Stdoc, color
	 * changes color of text
	 * (not currently used)
	 * @return void
	 */
	public void changeColor(String s, JTextWrapPane ta, StyledDocument Stdoc, Color color)
	{
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, color);
		Stdoc.setCharacterAttributes(0, s.length(), set, true);
	}
	/**
	 * @param rel
	 * checks if ArrayList is reflexive
	 * 
	 * @return boolean
	 */
	private boolean reflexiveB(ArrayList<Coordinate> rel)
	{
		int[] range = minAndMax(rel);
		Coordinate co = new Coordinate(range[0], range[0]);
		for(int i = range[0]; i < range[1]; i++)
		{
			co = new Coordinate(i, i);
			if(!rel.contains(co))
				return false;
		}
		return true;
	}
	/**
	 * @param rel
	 * checks if ArrayList is transitive
	 * 
	 * @return boolean
	 */
	private boolean transitiveB(ArrayList<Coordinate> finalRel)
	{
		ArrayList<Coordinate> co = new ArrayList<Coordinate>();
		boolean[][] boolMatrix = boolMatrix(finalRel);
		int size = boolMatrix.length;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				for(int k = 0; k < size; k++)
				{
					boolMatrix[j][k] = (boolMatrix[j][k]) || ((boolMatrix[j][i]) && (boolMatrix[i][k]))?true:false;
				}
			}
		}
		boolToCoord(boolMatrix, co, finalRel);
		return finalRel.containsAll(co);
	}
	/**
	 * @param rel
	 * checks if ArrayList is symmetric
	 * 
	 * @return boolean
	 */
	private boolean symmetricB(ArrayList<Coordinate> rel)
	{
		Coordinate co = new Coordinate(rel.get(0).y, rel.get(0).x);
		for(int i = 0; i < rel.size(); i++)
		{
			co = new Coordinate(rel.get(i).x,  rel.get(i).y);
			if(!rel.contains(co))
				return false;
		}
		return true;
	}
	/**
	 * @param rel
	 * gets all the equivalence classes of the arrayList
	 * (already checks if its equivalent before calling)
	 * @return
	 */
	private String equivalenceClasses(ArrayList<Coordinate> rel, boolean simplify)
	{
		HashMap<Integer, ArrayList<Integer>> hm = new HashMap<Integer, ArrayList<Integer>>();
		for(int i = 0; i < rel.size(); i++)
		{
			if (hm.containsKey(rel.get(i).x))
			{
				ArrayList<Integer> values = hm.get(rel.get(i).x);
				if(!values.contains(rel.get(i).y))
				{
					values.add(rel.get(i).y);
					Collections.sort(values);
				}

			}
			else
			{
				ArrayList<Integer> values = new ArrayList<Integer>();
				values.add(rel.get(i).y);
				hm.put(rel.get(i).x, values);
			}
		}

		return eqclassToString(hm, simplify);
	}
}
