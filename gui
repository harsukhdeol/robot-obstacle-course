package rocket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;












import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextPane;

public class GUI extends JFrame {


	
	public String[][] coordinate= new String[15][40]; //2d array to plot objects

	
	public static String setup[]= new String[5];//arraylist that holds IP, motor and sensor ports
	
	
	
	private JPanel contentPane;
	private JTextField txtIP;
	
	public RemoteDriver robot;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//fills 2d array with empty spaces
	public void fillMap(String[][] array){
		for (int i =0; i<array.length; i++){
			for (int j= 0 ; j< array[0].length; j++){
								coordinate[i][j]= " ";
			}
					}
		
	}
public int x;
public int y;
// gets x and y coordiate and plots it to scale in array
public void plot(float xint, float yint){
	 y= (int) Math.round((xint*40/2.5));
	 x = (int) Math.round( (yint *15/2.5));
	
	coordinate [x][y]= "X";
}
// once color has been determined, plots a different character in map to change color later
 public void yellow(float xint, float yint){
	 y= (int) Math.round((xint*40/2.5));
	 x = (int) Math.round( (yint *15/2.5));
	 coordinate[x][y]= "Y";
	 
	 
 }
	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 632);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConnect = new JLabel("Enter IP address");
		lblConnect.setBounds(36, 32, 116, 14);
		contentPane.add(lblConnect);
		
		JLabel lblsetup = new JLabel("Left");
		lblsetup.setBounds(36, 147, 46, 14);
		contentPane.add(lblsetup);
		
		JLabel lblOperation = new JLabel("Operation");
		lblOperation.setBounds(36, 263, 83, 14);
		contentPane.add(lblOperation);
		
		
		
		
		final JComboBox cmbLeft = new JComboBox();
		final JComboBox cmbRight = new JComboBox();
		final JComboBox cmbGyro = new JComboBox();
		final JComboBox cmbUltra = new JComboBox();
		final JComboBox cmbColor = new JComboBox();
		
		final JLabel lblConnection = new JLabel("");
		lblConnection.setBounds(74, 110, 78, 14);
		contentPane.add(lblConnection);
		
		
		
		txtIP = new JTextField();
		txtIP.setBounds(46, 57, 86, 20);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		IP= txtIP.getText();
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//gets IP, motor and sensor info from combo boxes
				setup[0]= cmbLeft.getSelectedItem().toString();
				setup[1]= cmbRight.getSelectedItem().toString();
				setup[2]= cmbGyro.getSelectedItem().toString();
				setup[3]= cmbUltra.getSelectedItem().toString();
				setup[4]= cmbColor.getSelectedItem().toString();
				System.out.print(Arrays.toString(setup));
				IP= txtIP.getText();		
				try {
					 robot = new RemoteDriver(IP,setup[0], setup[1], setup[2], setup[3], setup[4]);
					lblConnection.setText("Connected");
					lblConnection.setBackground(Color.GREEN);
				} catch (RemoteException | MalformedURLException
						| NotBoundException e) {
					//robot.close();
					lblConnection.setBackground(Color.RED);
					lblConnection.setText("Error");
					
					e.printStackTrace();
				
				}
			}
		});
		btnConnect.setBounds(154, 56, 89, 23);
		contentPane.add(btnConnect);
		
		JButton btnScan = new JButton("Scan");
		btnScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				try {
					//scans area once btnScan is clicked (method from RemoteDriver)
					robot.scan();
				} catch (RemoteException e) {
							
				
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnScan.setBounds(36, 288, 89, 23);
		contentPane.add(btnScan);
		
		JButton btnDepart = new JButton("Depart");
		btnDepart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				robot.depart();
			//TODO
				
			}
		});
		btnDepart.setBounds(161, 288, 89, 23);
		contentPane.add(btnDepart);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(36, 110, 46, 14);
		contentPane.add(lblStatus);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
					try {
						//diconnects robot when btnDisconnect is clicked
						robot.close();
						lblConnection.setText("Disconnected");
						lblConnection.setBackground(Color.BLUE);
					} catch (RemoteException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			
			
			}
		});
		btnDisconnect.setBounds(154, 106, 89, 23);
		contentPane.add(btnDisconnect);
		
	
		cmbLeft.setBounds(74, 144, 45, 20);
		cmbLeft.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D"}));
		contentPane.add(cmbLeft);
		setup[0]= cmbLeft.getSelectedItem().toString();
		
	
		cmbRight.setBounds(74, 189, 45, 20);
		cmbRight.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D"}));
		contentPane.add(cmbRight);
		
		setup[1]= cmbRight.getSelectedItem().toString();
		
		
		JLabel lblRight = new JLabel("Right:");
		lblRight.setBounds(36, 192, 46, 14);
		contentPane.add(lblRight);
		
		JLabel lblGyroSensor = new JLabel("Gyro Sensor:");
		lblGyroSensor.setBounds(135, 147, 83, 14);
		contentPane.add(lblGyroSensor);

		//combo boxes provide connection options for user to select
		cmbGyro.setModel(new DefaultComboBoxModel(new String[] {"S1", "S2", "S3", "S4"}));
		cmbGyro.setBounds(205, 144, 45, 20);
		contentPane.add(cmbGyro);
		setup[2]= cmbGyro.getSelectedItem().toString();
		
		;
		cmbUltra.setModel(new DefaultComboBoxModel(new String[] {"S1", "S2", "S3", "S4"}));
		cmbUltra.setBounds(205, 227, 45, 20);
		contentPane.add(cmbUltra);
		setup[3]= cmbUltra.getSelectedItem().toString();
		
		JLabel lblUltrasonicSensor = new JLabel("Ultrasonic Sensor:");
		lblUltrasonicSensor.setBounds(36, 230, 136, 14);
		contentPane.add(lblUltrasonicSensor);
		
	
		cmbColor.setModel(new DefaultComboBoxModel(new String[] {"S1", "S2", "S3", "S4"}));
		cmbColor.setBounds(205, 189, 45, 20);
		contentPane.add(cmbColor);
		setup[4]= cmbColor.getSelectedItem().toString();
		
		JLabel lblColorSensor = new JLabel("Color Sensor:");
		lblColorSensor.setBounds(129, 192, 89, 14);
		contentPane.add(lblColorSensor);
		
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					robot.close();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnExit.setBounds(154, 11, 89, 23);
		contentPane.add(btnExit);
		
// map created on text pane
		JTextPane txtMap = new JTextPane();
		txtMap.setEditable(false);
		txtMap.setBounds(20, 322, 256, 250);
		contentPane.add(txtMap);
		
		fillMap(coordinate);
		//get coordinate 
		plot(1,2);
		yellow (0,0);

		String lineSeparator = System.lineSeparator();
		StringBuilder sb = new StringBuilder();
		
		//prints out 2d array in correct format on text pane
		for (String[] row : coordinate) {
						
		
		    sb.append(Arrays.toString(row))
		      .append(lineSeparator);
		}

		String result = sb.toString();
		String format =result.replace("[", "")
				.replace("]", "")
				.replace(",", "")
				;
		
	
		 txtMap.setText(format);
		
		//The following code changes the color of item at specific index 

	        Style style = txtMap.addStyle("I'm a Style", null);
	        StyleConstants.setForeground(style, Color.red);

	        try { doc1.insertString(0, "*",style); }
	        catch (BadLocationException e){}

	        StyleConstants.setForeground(style, Color.blue);

	        try { doc1.insertString(1004, "blue",style); }
	        catch (BadLocationException e){}

	     	        MutableAttributeSet attrs = txtMap.getInputAttributes();
	        StyledDocument doc = txtMap.getStyledDocument();      
	         
	  
    int length = txtMap.getText().length();
 for (int i = 0; i < length; i++) {
            switch (txtMap.getText().charAt(i)) {
	            case('Y'):
			// if Y found, color will change to yellow
	                StyleConstants.setForeground(attrs, Color.yellow);
	          
	            break;
		// if X found,color will change to red
	            case('X'):
	            	StyleConstants.setForeground(attrs, Color.red);
	                
	            
	            break;
	            }
	            doc.setCharacterAttributes(i, 1, attrs, false);
	        
	        }
	    
	}
}



