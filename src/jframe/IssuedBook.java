package jframe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IssuedBook {
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	private JLabel lblNewLabel_7;
    private JLabel lblNewLabel_7_1;
    private JLabel lblNewLabel_7_2;
    private JLabel lblNewLabel_7_3;
    
    private JLabel lblNewLabel_7_7;
    private JLabel lblNewLabel_7_4;
    private JLabel lblNewLabel_7_5;
    private JLabel lblNewLabel_7_6;
    private JTextField textField_4;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IssuedBook window = new IssuedBook();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IssuedBook() {
		initialize();
	}

	public void getBookDetails() {
	    try {
	        int bookid = Integer.parseInt(textField.getText());
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        PreparedStatement pst = con.prepareStatement("SELECT * FROM books WHERE bookid=?");
	        pst.setInt(1, bookid);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            lblNewLabel_7.setText(String.valueOf(rs.getInt("bookid")));
	            lblNewLabel_7_1.setText(rs.getString("name"));
	            lblNewLabel_7_2.setText(rs.getString("author"));
	            lblNewLabel_7_3.setText(rs.getString("quantity"));
	        } else {
	            
	            JOptionPane.showMessageDialog(frame, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        con.close(); // Close connection after use
	    } catch (Exception e) {
	        // Handle exceptions properly
	        e.printStackTrace();
	        
	        JOptionPane.showMessageDialog(frame, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	public void getStudentDetails() {
	    try {
	        int studentid = Integer.parseInt(textField_1.getText());
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        PreparedStatement pst = con.prepareStatement("SELECT * FROM students WHERE studentid=?");
	        pst.setInt(1, studentid);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            lblNewLabel_7_5.setText(String.valueOf(rs.getInt("studentid")));
	            lblNewLabel_7_6.setText(rs.getString("name"));
	            lblNewLabel_7_4.setText(rs.getString("course"));
	            lblNewLabel_7_7.setText(rs.getString("year"));
	        } else {
	            
	            JOptionPane.showMessageDialog(frame, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        con.close(); // Close connection after use
	    } catch (Exception e) {
	        // Handle exceptions properly
	        e.printStackTrace();
	        
	        JOptionPane.showMessageDialog(frame, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	public boolean issueBook() {
		int issueid = Integer.parseInt(textField_4.getText());
		int bookid = Integer.parseInt(textField.getText());
	    int studentid = Integer.parseInt(textField_1.getText());
	    Date issueDate = Date.valueOf(textField_2.getText());
	    Date returnDate = Date.valueOf(textField_3.getText());
	    boolean isissued = false; 

	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        String sql = "INSERT INTO issuebook (issueid,bookid, studentid, IssueDate, ReturnDate,status) VALUES (?,?, ?, ?, ?,'Pending')";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, issueid);
	        pst.setInt(2, bookid);
	        pst.setInt(3, studentid);
	        pst.setDate(4, issueDate);
	        pst.setDate(5, returnDate);
	        int rowCount = pst.executeUpdate(); // Use executeUpdate() instead of execute.Update()
	        if (rowCount > 0) {
	            isissued = true;
	        } else {
	            isissued = false;
	        }
	        con.close(); // Close connection after use
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return isissued;
	}
	public void updateBookCount() {
	    int bookid = Integer.parseInt(textField.getText());
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        String sql = "update books set quantity=quantity-1 where bookid=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, bookid);

	        int rowCount = pst.executeUpdate();
	        if (rowCount > 0) {
	            JOptionPane.showMessageDialog(frame, this, "Book count updated", rowCount);
	            int initialCount = Integer.parseInt(lblNewLabel_7_3.getText());
	            lblNewLabel_7_3.setText(String.valueOf(initialCount - 1)); // Corrected line
	        } else {
	            JOptionPane.showMessageDialog(frame, this, "Can't update book count", rowCount); // Corrected typo
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 662, 443);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 205, 443);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\AddNewBookIcons\\icons8_Literature_100px_1.png"));
		lblNewLabel.setBounds(10, 11, 100, 94);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Book");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(120, 31, 46, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Details");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_1.setBounds(120, 51, 75, 28);
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_6 = new JLabel(" Book ID :");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6.setBounds(25, 156, 56, 14);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("Name :");
		lblNewLabel_6_1.setForeground(Color.WHITE);
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6_1.setBounds(25, 194, 56, 14);
		panel.add(lblNewLabel_6_1);
		
		JLabel lblNewLabel_6_2 = new JLabel("Author :");
		lblNewLabel_6_2.setForeground(Color.WHITE);
		lblNewLabel_6_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6_2.setBounds(25, 234, 56, 14);
		panel.add(lblNewLabel_6_2);
		
		JLabel lblNewLabel_6_3 = new JLabel("Quantity :");
		lblNewLabel_6_3.setForeground(Color.WHITE);
		lblNewLabel_6_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6_3.setBounds(25, 276, 56, 14);
		panel.add(lblNewLabel_6_3);
		
		lblNewLabel_7 = new JLabel(""); // Initialize the class-level field
		lblNewLabel_7.setBounds(97, 156, 69, 14);
		panel.add(lblNewLabel_7);

		lblNewLabel_7_1 = new JLabel(""); // Initialize the class-level field
		lblNewLabel_7_1.setBounds(97, 195, 69, 14);
		panel.add(lblNewLabel_7_1);

		lblNewLabel_7_2 = new JLabel(""); // Initialize the class-level field
		lblNewLabel_7_2.setBounds(97, 235, 98, 14);
		panel.add(lblNewLabel_7_2);

		lblNewLabel_7_3 = new JLabel(""); // Initialize the class-level field
		lblNewLabel_7_3.setBounds(91, 277, 69, 14);
		panel.add(lblNewLabel_7_3);

		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLUE);
		panel_1.setBounds(203, 0, 225, 501);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\AddNewBookIcons\\icons8_Student_Registration_100px_2.png"));
		lblNewLabel_1.setBounds(10, 11, 108, 100);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Student");
		lblNewLabel_3_2.setForeground(Color.WHITE);
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_2.setBounds(128, 30, 87, 29);
		panel_1.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Details");
		lblNewLabel_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_1_1.setBounds(128, 57, 75, 28);
		panel_1.add(lblNewLabel_3_1_1);
		
		JLabel lblNewLabel_6_4 = new JLabel("Student ID :");
		lblNewLabel_6_4.setForeground(Color.WHITE);
		lblNewLabel_6_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6_4.setBounds(18, 153, 75, 14);
		panel_1.add(lblNewLabel_6_4);
		
		JLabel lblNewLabel_6_5 = new JLabel("Name :");
		lblNewLabel_6_5.setForeground(Color.WHITE);
		lblNewLabel_6_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6_5.setBounds(25, 190, 56, 14);
		panel_1.add(lblNewLabel_6_5);
		
		JLabel lblNewLabel_6_6 = new JLabel("Course :");
		lblNewLabel_6_6.setForeground(Color.WHITE);
		lblNewLabel_6_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6_6.setBounds(25, 231, 56, 14);
		panel_1.add(lblNewLabel_6_6);
		
		JLabel lblNewLabel_6_7 = new JLabel("Year :");
		lblNewLabel_6_7.setForeground(Color.WHITE);
		lblNewLabel_6_7.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6_7.setBounds(25, 270, 56, 14);
		panel_1.add(lblNewLabel_6_7);
		
		lblNewLabel_7_5 = new JLabel("");
	    lblNewLabel_7_5.setBounds(103, 154, 69, 14);
	    panel_1.add(lblNewLabel_7_5);

	    lblNewLabel_7_6 = new JLabel("");
	    lblNewLabel_7_6.setBounds(91, 190, 69, 14);
	    panel_1.add(lblNewLabel_7_6);

	    lblNewLabel_7_4 = new JLabel("");
	    lblNewLabel_7_4.setBounds(91, 232, 69, 14);
	    panel_1.add(lblNewLabel_7_4);

	    lblNewLabel_7_7 = new JLabel("");
	    lblNewLabel_7_7.setBounds(80, 271, 69, 14);
	    panel_1.add(lblNewLabel_7_7);

		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(427, 0, 235, 443);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\AddNewBookIcons\\icons8_Books_52px_1.png"));
		lblNewLabel_2.setBounds(25, 26, 74, 68);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("Issue");
		lblNewLabel_3_3.setForeground(Color.RED);
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_3.setBounds(91, 36, 46, 22);
		panel_2.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_3_3_1 = new JLabel("Books");
		lblNewLabel_3_3_1.setForeground(Color.RED);
		lblNewLabel_3_3_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_3_1.setBounds(91, 57, 67, 22);
		panel_2.add(lblNewLabel_3_3_1);
		
		JLabel lblNewLabel_4 = new JLabel("x");
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
        		System.exit(0);
			}

			
		});
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setBackground(Color.GRAY);
		lblNewLabel_4.setBounds(210, 0, 25, 29);
		panel_2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Book ID");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(35, 171, 46, 14);
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Student ID");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5_1.setBounds(25, 202, 74, 14);
		panel_2.add(lblNewLabel_5_1);
		
		JButton btnNewButton = new JButton("Issue");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (issueBook()) {
		            JOptionPane.showMessageDialog(frame, "Book issued Successfully");
		            updateBookCount();
		        } else {
		            JOptionPane.showMessageDialog(frame, "Book not issued");
		        }
		    }
		});

		btnNewButton.setBackground(Color.RED);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(69, 325, 89, 23);
		panel_2.add(btnNewButton);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("Issue Date");
		lblNewLabel_5_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5_1_1.setBounds(25, 227, 74, 14);
		panel_2.add(lblNewLabel_5_1_1);
		
		JLabel lblNewLabel_5_1_2 = new JLabel("Return Date");
		lblNewLabel_5_1_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5_1_2.setBounds(25, 264, 74, 14);
		panel_2.add(lblNewLabel_5_1_2);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusLost(FocusEvent e) {
		        getBookDetails();
		    }
		});
		textField.setBounds(112, 169, 86, 20);
		panel_2.add(textField);
		textField.setColumns(10);

		
		textField_1 = new JTextField();
		textField_1.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusLost(FocusEvent e) {
		        getStudentDetails(); 
		    }
		});

		textField_1.setBounds(112, 200, 86, 20);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(112, 231, 86, 20);
		panel_2.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(112, 262, 86, 20);
		panel_2.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(112, 138, 86, 20);
		panel_2.add(textField_4);
		
		JLabel lblNewLabel_5_2 = new JLabel("Issue ID");
		lblNewLabel_5_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5_2.setBounds(35, 141, 46, 14);
		panel_2.add(lblNewLabel_5_2);
		frame.setVisible(true);
	}
	
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
