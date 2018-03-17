import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ProofsPanel extends JPanel
{

	JTextArea txtProof, txtAnswer; //text areas for proof and answers
	private JTextField tfFirstChar; //textfield for first character
	private JTextField tfSecondChar;//textfield for second character
	String symbol = "->", b1 = " ", b2 = " "; //strings from comboboxes
	CopyOnWriteArrayList<Equation> lines = new CopyOnWriteArrayList<Equation>(); //arraylist to hope usable lines (2 iterators work on it at the same time)
	ArrayList<String> answer = new ArrayList<String>(); //arraylist to hold the answers
	
	/**
	 * Create the application.
	 */
	public ProofsPanel() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 752, 582);
		this.setLayout(null);

		//label for instructions
		JLabel lblEnterEquationHere = new JLabel("Enter line here: ");
		lblEnterEquationHere.setBounds(10, 11, 108, 14);
		this.add(lblEnterEquationHere);

		//tells the computer to create table and solve equation
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				txtAnswer.setText("");
				calculate();
			}

		});
		btnCalculate.setBounds(21, 113, 89, 23);
		this.add(btnCalculate);

		//textfield to hold proof
		txtProof = new JTextArea();
		txtProof.setEditable(false);
		JScrollPane spProof = new JScrollPane(txtProof);
		spProof.setBounds(10, 148, 140, 418);
		this.add(spProof);

		//textfield for the final answer
		txtAnswer = new JTextArea();
		txtAnswer.setEditable(false);
		JScrollPane spAnswer = new JScrollPane(txtAnswer);
		spAnswer.setBounds(236, 41, 490, 525);
		this.add(spAnswer);

		//label for telling the user where answer will be displayed
		JLabel lblAnswerWillBe = new JLabel("Answer will be shown here:");
		lblAnswerWillBe.setBounds(236, 11, 164, 14);
		this.add(lblAnswerWillBe);

		//displays help for the user
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = "Enter a statement on each line \n"
						+ "to compare answers have one line with dashes(---)\n"
						+ "then put in the answer and it will be compared against the actual";
				JOptionPane.showMessageDialog(null, s);
			}
		});
		btnHelp.setBounds(632, 7, 89, 23);
		this.add(btnHelp);

		//adds equation to textfield and arraylist
		JButton btnAddEquation = new JButton("Add");
		btnAddEquation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtAnswer.getText().isEmpty())
				{
					Equation line;
					if(symbol.equals("SI"))
					{
						line = new Equation(b1 + tfFirstChar.getText() + "      ");
						line.setSingle(true);
					}
						
					else
						line = new Equation(b1 + tfFirstChar.getText() + " " + symbol + " " + b2 + tfSecondChar.getText());

					if(txtProof.getText().isEmpty())
						txtProof.setText(line.toString());
					else
						txtProof.setText(txtProof.getText() + "\n" + line);
					
					if(!lines.contains(line))
					{
						lines.add(line);
						answer.add(line.toString());
					}
						
				}
			}
		});
		btnAddEquation.setBounds(21, 79, 89, 23);
		this.add(btnAddEquation);

		tfFirstChar = new JTextField();
		tfFirstChar.setDocument(new JTextFieldLimit(1));
		tfFirstChar.setBounds(50, 44, 19, 20);
		this.add(tfFirstChar);
		tfFirstChar.setColumns(1);

		tfSecondChar = new JTextField();
		tfSecondChar.setDocument(new JTextFieldLimit(1));
		tfSecondChar.setText(" ");
		tfSecondChar.setColumns(1);
		tfSecondChar.setBounds(177, 44, 19, 20);
		this.add(tfSecondChar);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"->", "||", "&&", "==", "SI"}));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				symbol = comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setBounds(79, 44, 50, 20);
		this.add(comboBox);

		//combobox for boolean of first character
		JComboBox firstBoolean = new JComboBox();
		firstBoolean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				b1 = firstBoolean.getSelectedItem().toString();
			}
		});
		firstBoolean.setModel(new DefaultComboBoxModel(new String[] {" ", "!"}));
		firstBoolean.setBounds(10, 44, 28, 20);
		this.add(firstBoolean);

		//combobox for boolean of second character
		JComboBox secondBoolean = new JComboBox();
		secondBoolean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b2 = secondBoolean.getSelectedItem().toString();
			}
		});
		secondBoolean.setModel(new DefaultComboBoxModel(new String[] {" ", "!"}));
		secondBoolean.setBounds(139, 44, 28, 20);
		this.add(secondBoolean);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtAnswer.setText("");
				txtProof.setText("");
				answer.clear();
				lines.clear();
			}
		});
		btnClear.setBounds(130, 79, 89, 23);
		this.add(btnClear);
		
		JButton btnRandom = new JButton("Random");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Random rndNum = new Random();
				int numOfLines = Math.abs(rndNum.nextInt(60));
				char[] alphabetL = "abcdefghijklmnopqrstuvwxyz".toCharArray();
				char[] alphabetU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
				char[] others = "@#$%^*()".toCharArray();
				
			}
		});
		btnRandom.setBounds(120, 113, 89, 23);
		this.add(btnRandom);
	}

	//where the program calculates all the necessary equations
	public void calculate()
	{
		ListIterator<Equation> mainIT; //main iterator
		ListIterator<Equation> subIT; //sub iterator
		String s = ""; //used for creating new equation
		Equation eq1, eq2; //used for accessing data from iterators
		int n, time = 0;
		boolean worked = false;
		while(lines.size() > 1 && time < 10)
		{
			mainIT = lines.listIterator();
			//main iterator
			while(mainIT.hasNext() && time < 10)
			{
				eq1 = mainIT.next();
				n = mainIT.nextIndex();
				//makes sure next index is within bounds
				if(n < lines.size())
				{
					subIT = lines.listIterator(n);
					//sub iterator for all other objects in the list
					while(subIT.hasNext())
					{
						eq2 = subIT.next();
						worked = hypotheticalSyllogism(eq1, eq2);
						if(!worked)
						{
							time++;
						}
						if(time == 10)
							System.out.println("looped 10 times");
					}
				}
			}

		}
		if(time == 10)
			txtAnswer.setText("Sorry, the program couldn't solve it.");
		else
			printArray(answer);
	}
	//prints arraylist
	private void printArray(ArrayList<String> stAL)
	{
		String ans = "";
		for(int i = 0; i < stAL.size(); i++)
		{
			ans = ans + i + ") " + stAL.get(i) + "\n";
		}
		txtAnswer.setText(ans);
	}
	//
	private void modusPonens(Equation eq1)
	{
		String s = "";
		boolean done = false;
		Equation tempEQ;
		char c1 = eq1.getC1(), c2 = eq1.getC2();
		if(eq1.getSymbol().equals("->"))
		{
			s = String.valueOf(c2);
			answer.add(String.valueOf(c1));
			answer.add(s + "	M.P");
			
			for(int i = 0; i < lines.size() && !done; i++)
			{
				tempEQ = lines.get(i);
				if(!tempEQ.equals(eq1))
				{
					if(c1 == tempEQ.getC1() && eq1.isB1() == tempEQ.isB1())
						tempEQ.setC1(c2);
					else if(c1 == tempEQ.getC2() && eq1.isB2() == tempEQ.isB2())
						tempEQ.setC2(c2);
				}
					
			}
		}
		lines.remove(lines.indexOf(eq1));
	}
	//
	private void modusTollens(Equation eq1)
	{
		String s = "";
		if(eq1.getSymbol().equals("->"))
		{
			s = String.valueOf(eq1.getC1());
			answer.add(boolToStringR(!eq1.isB2()) + eq1.getC2());
			answer.add(boolToStringR(!eq1.isB1())+s + "	M.T");
			
		}
	}
	//if one equation implies a variable and that variable implies another
	//then the 1st variable of the 1st equation implies 2nd variable in 2nd equation
	private boolean hypotheticalSyllogism(Equation eq1, Equation eq2)
	{
		boolean b = false;
		String s = "";
		//if eq1 can convert to implication (if not already one)
		if(!eq1.getSymbol().equals("->"))
		{
			eq1.convert();
			if(eq1.getSymbol().equals("->"))
				answer.add(eq1.toString() + "	conversion");
		}
			
		//if eq2 can convert to implication (if not already one)
		if(!eq2.getSymbol().equals("->"))
		{
			eq2.convert();
			if(eq2.getSymbol().equals("->"))
				answer.add(eq2.toString() + "	conversion");
		}
			
		//if both equations are implication
		if(eq1.getSymbol().equals("->") && eq2.getSymbol().equals("->"))
		{
			if(eq1.getC2() == eq2.getC1() && eq1.isB2() == eq2.isB1())
			{
				eq1.setC2(eq2.getC2());
				eq1.setB2(eq2.isB2());
				answer.add(eq1.toString() + "	H.S");
				lines.remove(eq2);
				b = true;
			}
			else if(eq2.getC2() == eq1.getC1() && eq2.isB2() == eq1.isB1())
			{
				eq2.setC2(eq1.getC2());
				eq2.setB2(eq1.isB2());
				answer.add(eq2.toString() + "	H.S");
				lines.remove(eq1);
				b = true;
			}
		}
		return b;
	}
	//
	private String disjunctiveSyllogism(Equation eq1, Equation eq2)
	{
		String s = "";
		return s;
	}
	//
	private String invalidFallacies(Equation eq1, Equation eq2)
	{
		String s = "";
		return s;
	}
	//like M.P but other variable
	private String inverser(Equation eq1, Equation eq2)
	{
		String s = "";
		return s;
	}
	//replaces one equation with another and deletes the other
	private void replace(Equation eq1)
	{
		Equation tempEQ;
		char c1 = eq1.getC1(), c2 = eq1.getC2();
		boolean done = false;
		for(int i = 0; i < lines.size() && !done; i++)
		{
			tempEQ = lines.get(i);
			if(!tempEQ.equals(eq1))
			{
				if(c1 == tempEQ.getC1())
					tempEQ.setC1(c2);
				else if(c1 == tempEQ.getC2())
					tempEQ.setC2(c2);
				done = true;
			}	
		}
	}
	//converts boolean to string
	private String boolToStringR(boolean b)
	{
		if(b)
			return " ";
		return "!";
	}
}

class JTextFieldLimit extends PlainDocument {
  private int limit;
  JTextFieldLimit(int limit) {
    super();
    this.limit = limit;
  }

  JTextFieldLimit(int limit, boolean upper) {
    super();
    this.limit = limit;
  }

  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
    if (str == null)
      return;

    if ((getLength() + str.length()) <= limit) {
      super.insertString(offset, str, attr);
    }
  }
}
