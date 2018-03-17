import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class SequencesPanel extends JPanel
{
	private JTextField txtA1;
	private JTextField txtEquation;
	private JTextField txtMax;
	private JTextField txtFirstNum;
	private JTextField txtSecondNum;
	private JTextField txtSecondTerm;
	private JTextField txtFirstTerm;
	private JTextField txtTerm;

	/**
	 * Create the application.
	 */
	public SequencesPanel() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 627, 372);
		this.setLayout(null);

		//textfield for first number in sequence
		txtA1 = new JTextField();
		txtA1.setBounds(45, 71, 86, 20);
		add(txtA1);
		txtA1.setColumns(10);

		//textfield to get equation
		txtEquation = new JTextField();
		txtEquation.setText("An = Am");
		txtEquation.setBounds(66, 99, 170, 20);
		add(txtEquation);
		txtEquation.setColumns(10);

		//label that does nothing
		JLabel lblA1 = new JLabel("A");
		lblA1.setBounds(10, 74, 15, 14);
		add(lblA1);

		//label that does nothing
		JLabel lblNewLabel = new JLabel("1");
		lblNewLabel.setBounds(20, 77, 15, 14);
		add(lblNewLabel);

		//label to describe what this half of the panel does
		JLabel lblNewLabel_1 = new JLabel("Partial sum (use An and Am)");
		lblNewLabel_1.setBounds(10, 35, 156, 14);
		add(lblNewLabel_1);

		//label that describes textfield
		JLabel lblEquation = new JLabel("Equation");
		lblEquation.setBounds(10, 102, 121, 14);
		add(lblEquation);

		//where answer will be displayed
		JLabel lblAnsPS = new JLabel("Answer: ");
		lblAnsPS.setBounds(10, 202, 226, 14);
		add(lblAnsPS);

		//what term to go to
		txtMax = new JTextField();
		txtMax.setBounds(122, 124, 86, 20);
		add(txtMax);
		txtMax.setColumns(10);

		//describes txtMax textfield
		JLabel lblNewLabel_2 = new JLabel("Up to what number?");
		lblNewLabel_2.setBounds(10, 127, 121, 14);
		add(lblNewLabel_2);

		//calls the method and displays the answer
		JButton btnSolveSum = new JButton("Solve Sum");
		btnSolveSum.addActionListener((e) ->  {
			long total = partialSum(txtEquation.getText(), Integer.parseInt(txtA1.getText()), Integer.parseInt(txtMax.getText()));
			lblAnsPS.setText("Answer: " + total);
		});
		btnSolveSum.setBounds(10, 168, 89, 23);
		add(btnSolveSum);

		//first number (not in the sequence)
		txtFirstNum = new JTextField();
		txtFirstNum.setBounds(506, 71, 86, 20);
		add(txtFirstNum);
		txtFirstNum.setColumns(10);

		//second number (not in the sequence)
		txtSecondNum = new JTextField();
		txtSecondNum.setBounds(506, 112, 86, 20);
		add(txtSecondNum);
		txtSecondNum.setColumns(10);

		//label that does nothing
		JLabel label = new JLabel("A");
		label.setBounds(465, 74, 15, 14);
		add(label);

		//label that does nothing
		JLabel label_1 = new JLabel("A");
		label_1.setBounds(465, 115, 15, 14);
		add(label_1);

		//textfield for second term
		txtSecondTerm = new JTextField();
		txtSecondTerm.setColumns(10);
		txtSecondTerm.setBounds(481, 137, 15, 20);
		add(txtSecondTerm);

		//textfield for first term
		txtFirstTerm = new JTextField();
		txtFirstTerm.setColumns(10);
		txtFirstTerm.setBounds(481, 84, 15, 20);
		add(txtFirstTerm);

		//textfield for term they want to go to
		txtTerm = new JTextField();
		txtTerm.setBounds(506, 182, 86, 20);
		add(txtTerm);
		txtTerm.setColumns(10);

		//describes txtTerm textfield
		JLabel lblNewLabel_3 = new JLabel("What term do you want?");
		lblNewLabel_3.setBounds(367, 185, 170, 14);
		add(lblNewLabel_3);

		//describes 2nd half of panel
		JLabel lblSolveSequence = new JLabel("Solve Sequence");
		lblSolveSequence.setBounds(436, 35, 156, 14);
		add(lblSolveSequence);

		//tells program whether its Geometric or Arithmetic sequence
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Geometric", "Arithmetic"}));
		comboBox.setBounds(465, 213, 127, 20);
		add(comboBox);

		//answer for sequence term
		JLabel lblSeqAns = new JLabel("Answer: ");
		lblSeqAns.setBounds(380, 279, 247, 14);
		add(lblSeqAns);

		//solves the problem and displays the answer
		JButton btnSolveSeq = new JButton("Solve sequence");
		btnSolveSeq.addActionListener((e) -> {
			BigDecimal nthTerm = solveSequence(txtFirstNum.getText(), txtFirstTerm.getText(),txtSecondNum.getText(), txtSecondTerm.getText(),txtTerm.getText(),comboBox.getSelectedItem().toString());
			lblSeqAns.setText("Answer: " + nthTerm);
		});
		btnSolveSeq.setBounds(461, 244, 127, 23);
		add(btnSolveSeq);
	}

	/**
	 * gets the text from all 5 textfields and combobox
	 * finds the answer based on the combobox answer
	 * returns the term as a bigdecimal
	 */
	public BigDecimal solveSequence(String an1, String n1, String an2, String n2, String n3, String sequence)
	{
		//initializes variables
		int term = toInt(n3);
		int[] terms = {toInt(n1), toInt(n2)};
		double[] nums = {toDouble(an1), toDouble(an2)};

		if(sequence.equalsIgnoreCase("geometric"))
		{
			//finds which term is the highest term
			double r = 0;
			int lowest = 0, highest = 1;
			//switch terms if need be
			if(terms[0] > terms[1])
			{
				highest = 0;
				lowest = 1;
			}
			r = (nums[highest] / nums[lowest])/(terms[highest]-terms[lowest]);
			double diff = term - terms[highest];
			//if term is either below or above the highest term
			if(diff < 0)
			{
				return new BigDecimal(nums[highest]/(-diff*r));
			}
			else
				return new BigDecimal(nums[highest]*Math.pow(r, diff));
		}
		//if its arithmetic
		else
		{
			//establishes d (difference between terms)
			double d = 0;
			int lowest = 0, highest = 1;
			//switch terms if need be
			if(terms[0] > terms[1])
			{
				highest = 0;
				lowest = 1;
			}
			//gets the difference
			if(terms[highest] - terms[lowest] == 1)
			{
				d = nums[highest] - nums[lowest];
			}
			else
			{
				d = terms[highest]-terms[lowest];
				d = (nums[highest]-nums[lowest]) / d;
			}
			return new BigDecimal(nums[highest] + ((term - terms[highest])*d));
		}
	}
	/**
	 * gets the first number in the sequence, the equation and what term to go up to
	 * finds the answer based on the whether or not there is a number in front of Am
	 * returns the term as a long
	 */
	public long partialSum(String equation, int a1, int max)
	{
		long total = 0;
		int a = equation.indexOf("Am"), b = equation.indexOf("=");
		boolean isGeometric = false;
		//determines if equation is arithmetic
		if(!equation.substring(b+1,a).trim().equals(""))
		{
			isGeometric = true;
		}
		//if equation is geometric, get the Q(multiplier)
		if(isGeometric)
		{
			b = equation.indexOf("=");
			a = Integer.parseInt((equation.substring(b+1, a)).trim());
			total = (long) (a1 * ((1-Math.pow(a, max))/(1-a)));
		}
		//if equation is arithmetic
		else
		{
			total = a1;
			int d = equation.indexOf("+");
			if(d != -1)
			{
				d = Integer.parseInt((equation.substring(d+1)).trim());
				total = (max/2)*((2*a1)+((max-1)*d));
			}

		}

		return total;
	}
	//method for converting strings to ints
	public int toInt(String s)
	{
		return Integer.parseInt(s);
	}
	//method for converting strings to doubles
	public double toDouble(String s)
	{
		return Double.parseDouble(s);
	}
}
