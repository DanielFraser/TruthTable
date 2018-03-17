import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class TruthTablePanel extends JPanel
{
	private JTextField equation;
	private JTable table;
	private TruthTable truthTable;
	private JScrollPane tablePane;
	private String[] a = {"Black & Yellow", "Rainbow", "None", "Last Row", "Light", "Easy"};
	private String currentDesign;
	private int totalTrue = 0, totalFalse = 0;
	private JLabel lblTotal,lblTrue,lblFalse;

	/**
	 * Create the application.
	 */
	public TruthTablePanel() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 950, 740);
		//this.setBounds(100, 100, 777, 627);
		this.setLayout(null);
		//this.
		
		//label for instrucitons
		JLabel lblEnterEquationHere = new JLabel("Enter Equation here: ");
		lblEnterEquationHere.setBounds(10, 11, 164, 14);
		this.add(lblEnterEquationHere);

		//textfield for reading in equation
		equation = new JTextField("");
		equation.setBounds(150, 8, 470, 20);
		this.add(equation);
		equation.setColumns(10);

		truthTable = new TruthTable();

		//tells the computer to create table and solve equation
		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				truthTable.setVariables(equation.getText());

				createTable();
				//truthTable.calculateVariables();
			}

		});
		btnCalculate.setBounds(803, 7, 121, 23);
		this.add(btnCalculate);

		//String for help dialog
		String s = "&& - and \n"
				+ "|| - or \n"
				+ "! - not \n"
				+ "^ - xor \n";

		//displays help message
		JButton Help = new JButton("Help");
		Help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, s, "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		Help.setBounds(803, 41, 121, 23);
		this.add(Help);

		//button that auto fills text field with an equation
		JButton btnEquation = new JButton("Equation 2");
		btnEquation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				equation.setText("(a && ((c ^ d) || b)) && !e || f && !g || !h && i ^ (j^k)");
			}
		});
		btnEquation.setBounds(803, 103, 121, 23);
		this.add(btnEquation);

		//label that tells what combobox does
		JLabel lblDesign = new JLabel("Design");
		lblDesign.setBounds(681+173, 137, 46, 14);
		this.add(lblDesign);

		//combobox for designs
		JComboBox comboBox = new JComboBox(a);
		comboBox.setSelectedIndex(5);
		currentDesign = comboBox.getSelectedItem().toString();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDesign = comboBox.getSelectedItem().toString();
			}
		});

		comboBox.setBounds(638+173, 162, 113, 20);
		this.add(comboBox);
		
		JButton btnEquation_1 = new JButton("Equation 1");
		btnEquation_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				equation.setText("a ^ b");
			}
		});
		btnEquation_1.setBounds(803, 75, 121, 23);
		this.add(btnEquation_1);
		
		JLabel lblTotalAnswers = new JLabel("Totals");
		lblTotalAnswers.setBounds(803, 197, 79, 14);
		this.add(lblTotalAnswers);
		
		lblTotal = new JLabel();
		lblTotal.setBounds(803, 222, 121, 14);
		this.add(lblTotal);
		
		lblTrue = new JLabel();
		lblTrue.setBounds(803, 247, 121, 14);
		this.add(lblTrue);
		
		lblFalse = new JLabel();
		lblFalse.setBounds(803, 272, 121, 14);
		this.add(lblFalse);
	}

	//Creates the table for the truth table and results
	public void createTable()
	{
		totalTrue = 0;
		totalFalse = 0;
		//Gets the variables from the TruthTable object to create the binary values
		HashSet<Character> var = truthTable.getVariables();
		Iterator<Character> it = var.iterator();
		int numColumns = var.size()+1;
		String[] columnNames = new String[numColumns];
		for(int i = 0; i < numColumns-1; i++)
			columnNames[i] = String.valueOf(it.next());

		//Just tells the table to make the last column to display the answer
		columnNames[columnNames.length-1] = "Ans";

		//n tells how many rows we need for the binary values
		int n = (int) Math.pow(2, var.size());
		Object[][] data = new Object[n][numColumns];
		String s;
		
		//Generates the data for the table
		for (int i = 0; i < n ; i++) 
		{
			s = Integer.toBinaryString(i);
			while (s.length() < numColumns-1) 
			{
				s = "0"+s;
			}

			for(int j = 0; j < s.length(); j++)
			{
				data[i][j] = (Character.getNumericValue(s.charAt(j)));
			}
			try {
				data[i][numColumns-1] = truthTable.calculateVariables(s);
				if(data[i][numColumns-1].toString().equals("0"))
					totalFalse++;
				else
					totalTrue++;
			} 
			catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Equation was not valid");
				data[i][numColumns-1] = "N/A";
			}
		}
		//tries to remove old table from frame
		try {
			this.remove(tablePane);
		} catch (Exception e) {
			// TODO: handle exception
		}
		table = new JTable(data, columnNames);
		//changes design of table
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus, int row, int col) {

				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
				
				//if user selects Black & Yellow design
				if(currentDesign.equals("Black & Yellow"))
				{
					if(row % 2 == 0)
					{
						setBackground(Color.yellow);
						setForeground(Color.BLACK);
					}
						
					else
					{
						setBackground(Color.BLACK);
						setForeground(Color.yellow);
					}
				}
				//if user selects Rainbow design
				else if(currentDesign.equals("Rainbow"))
				{
					int i = row % 6;
					Color[] c = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.MAGENTA};
					
					setBackground(c[i]);
					if(i == 4)
						setForeground(Color.white);
					else
						setForeground(Color.BLACK);
				}
				//if user selects Last Row design
				else if(currentDesign.equals("Last Row"))
				{
					if(col == numColumns - 1)
					{
						setBackground(Color.BLACK);
						setForeground(Color.white);
					}
					else
					{
						setForeground(Color.BLACK);
						setBackground(Color.white);
					}
						
				}
				//if user selects light design
				else if(currentDesign.equals("Light"))
				{
					if(row % 2 == 0)
						setBackground(Color.gray);
						
					else
						setBackground(Color.white);
					
				}
				//if user selects light design
				else if(currentDesign.equals("Easy"))
				{
					String d = table.getModel().getValueAt(row, col).toString();
					if(d.equals("1"))
						setBackground(Color.GREEN);
					else
						setBackground(Color.red);
				}
				return this;
			}   
		});
		//shows the totals in labels
		lblTotal.setText("Both: " + Integer.toString(n));
		lblTrue.setText("True: " + Integer.toString(totalTrue));
		lblFalse.setText("False: " + Integer.toString(totalFalse));
		int width = numColumns*40;
		if(width > 1000)
			width = 1000;
		int height = n * 25;
		if (height > 500)
			height = 500;
		tablePane = new JScrollPane(table);
		tablePane.setBounds(20, 36, 518, 80);
		tablePane.setSize(width, height);
		this.add(tablePane);
		this.repaint();
	}
}
