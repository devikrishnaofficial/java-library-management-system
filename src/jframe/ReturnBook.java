package jframe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class ReturnBook {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	
	
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_5_1;
	private JLabel lblNewLabel_5_2;
	private JLabel lblNewLabel_5_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnBook window = new ReturnBook();
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
	public ReturnBook() {
		initialize();
	}

	public void updateBookCount() {
	    int bookid = Integer.parseInt(textField.getText());
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        String sql = "update books set quantity=quantity+1 where bookid=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, bookid);

	        int rowCount = pst.executeUpdate();
	        if (rowCount > 0) {
	            JOptionPane.showMessageDialog(frame, this, "Book count updated", rowCount);
	            
	        } else {
	            JOptionPane.showMessageDialog(frame, this, "Can't update book count", rowCount); // Corrected typo
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	

	public boolean getIssuedBookDetails() {
		
	    int bookid = Integer.parseInt(textField.getText());
	    int studentid = Integer.parseInt(textField_1.getText());
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        String sql = "SELECT * FROM issuebook WHERE bookid=? AND studentid=? AND status=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, bookid);
	        pst.setInt(2, studentid);
	        pst.setString(3, "Pending");
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	        	lblNewLabel_5.setText(rs.getString("Issueid"));
	        	lblNewLabel_5_1.setText(rs.getString("issuedate"));
	        	lblNewLabel_5_2.setText(rs.getString("returndate"));
	        	lblNewLabel_5_3.setText(rs.getString("status"));
	            return true;
	        } else {
	            JOptionPane.showMessageDialog(null, "Record not found", "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public boolean returnBook() {
	    boolean isReturned = false;
	    int bookid = Integer.parseInt(textField.getText());
	    int studentid = Integer.parseInt(textField_1.getText());
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        String sql = "update issuebook set status=? where studentid=? and bookid=? and status=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, "Returned");
	        pst.setInt(2, studentid);
	        pst.setInt(3, bookid);
	        pst.setString(4, "Pending");

	        int rowCount = pst.executeUpdate();
	        if (rowCount > 0) {
	            isReturned = true;
	        } else {
	            isReturned = false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return isReturned;
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    frame = new JFrame();
	    frame.setBounds(100, 100, 662, 433);
	    frame.setUndecorated(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(null);
	    
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.RED);
	    panel.setBounds(0, 0, 307, 433);
	    frame.getContentPane().add(panel);
	    panel.setLayout(null);
	    
	    JLabel lblNewLabel = new JLabel("Book Details");
	    lblNewLabel.setForeground(Color.WHITE);
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
	    lblNewLabel.setBounds(126, 53, 156, 27);
	    panel.add(lblNewLabel);
	    
	    JLabel lblNewLabel_1 = new JLabel("");
	    lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\AddNewBookIcons\\icons8_Literature_100px_1.png"));
	    lblNewLabel_1.setBounds(10, 0, 106, 111);
	    panel.add(lblNewLabel_1);
	    
	    JLabel lblNewLabel_4_2 = new JLabel("Issue ID:");
	    lblNewLabel_4_2.setForeground(Color.WHITE);
	    lblNewLabel_4_2.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblNewLabel_4_2.setBounds(10, 171, 87, 22);
	    panel.add(lblNewLabel_4_2);
	    
	    lblNewLabel_5 = new JLabel(""); // Corrected
	    lblNewLabel_5.setForeground(Color.WHITE);
	    lblNewLabel_5.setBounds(91, 171, 69, 22);
	    panel.add(lblNewLabel_5);
	    
	    JLabel lblNewLabel_4_2_1 = new JLabel("Issue Date :");
	    lblNewLabel_4_2_1.setForeground(Color.WHITE);
	    lblNewLabel_4_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblNewLabel_4_2_1.setBounds(10, 219, 87, 22);
	    panel.add(lblNewLabel_4_2_1);
	    
	    lblNewLabel_5_1 = new JLabel(""); // Corrected
	    lblNewLabel_5_1.setForeground(Color.WHITE);
	    lblNewLabel_5_1.setBounds(91, 219, 75, 22);
	    panel.add(lblNewLabel_5_1);
	    
	    JLabel lblNewLabel_4_2_2 = new JLabel("Due Date :");
	    lblNewLabel_4_2_2.setBackground(new Color(240, 240, 240));
	    lblNewLabel_4_2_2.setForeground(Color.WHITE);
	    lblNewLabel_4_2_2.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblNewLabel_4_2_2.setBounds(10, 264, 87, 22);
	    panel.add(lblNewLabel_4_2_2);
	    
	    lblNewLabel_5_2 = new JLabel(""); // Corrected
	    lblNewLabel_5_2.setForeground(Color.WHITE);
	    lblNewLabel_5_2.setBounds(91, 264, 75, 22);
	    panel.add(lblNewLabel_5_2);
	    
	    JLabel lblNewLabel_4_2_2_1 = new JLabel("Status :");
	    lblNewLabel_4_2_2_1.setForeground(Color.WHITE);
	    lblNewLabel_4_2_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
	    lblNewLabel_4_2_2_1.setBackground(UIManager.getColor("Button.background"));
	    lblNewLabel_4_2_2_1.setBounds(10, 309, 87, 22);
	    panel.add(lblNewLabel_4_2_2_1);
	    
	    lblNewLabel_5_3 = new JLabel("");
	    lblNewLabel_5_3.setForeground(Color.WHITE);
	    lblNewLabel_5_3.setBounds(73, 309, 69, 22);
	    panel.add(lblNewLabel_5_3);
	    
	    JPanel panel_1 = new JPanel();
	    panel_1.setBackground(Color.WHITE);
	    panel_1.setBounds(307, 0, 355, 433);
	    frame.getContentPane().add(panel_1);
	    panel_1.setLayout(null);
	    
	    JLabel lblNewLabel_2 = new JLabel("Return Book");
	    lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
	    lblNewLabel_2.setForeground(Color.RED);
	    lblNewLabel_2.setBounds(154, 40, 175, 46);
	    panel_1.add(lblNewLabel_2);
	    
	    JLabel lblNewLabel_3 = new JLabel("");
	    lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\AddNewBookIcons\\icons8_Books_52px_1.png"));
	    lblNewLabel_3.setBounds(75, 28, 69, 58);
	    panel_1.add(lblNewLabel_3);
	    
	    JLabel lblNewLabel_4 = new JLabel("Student ID");
	    lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblNewLabel_4.setBounds(52, 179, 87, 22);
	    panel_1.add(lblNewLabel_4);
	    
	    JLabel lblNewLabel_4_1 = new JLabel("Book ID");
	    lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	    lblNewLabel_4_1.setBounds(52, 139, 87, 22);
	    panel_1.add(lblNewLabel_4_1);
	    
	    textField = new JTextField();
	    textField.setBounds(147, 142, 104, 20);
	    panel_1.add(textField);
	    textField.setColumns(10);
	    
	    textField_1 = new JTextField();
	    textField_1.setColumns(10);
	    textField_1.setBounds(147, 182, 104, 19);
	    panel_1.add(textField_1);
	    
	    JButton btnNewButton = new JButton("Find");
	    btnNewButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            getIssuedBookDetails();
	        }
	    });
	    btnNewButton.setForeground(Color.WHITE);
	    btnNewButton.setBackground(Color.RED);
	    btnNewButton.setBounds(100, 246, 104, 30);
	    panel_1.add(btnNewButton);
	    
	    JButton btnReturnBook = new JButton("Return Book");
	    btnReturnBook.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (returnBook()) {
	                JOptionPane.showMessageDialog(frame, "Book Returned Successfully");
	                updateBookCount();
	            } else {
	                JOptionPane.showMessageDialog(frame, "Book Return Failed");
	            }
	        }
	    });

	    btnReturnBook.setForeground(Color.WHITE);
	    btnReturnBook.setBackground(Color.BLUE);
	    btnReturnBook.setBounds(100, 287, 104, 30);
	    panel_1.add(btnReturnBook);
	    
	    JLabel lblNewLabel_6 = new JLabel("x");
	    lblNewLabel_6.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            System.exit(0);
	        }
	    });
	    lblNewLabel_6.setForeground(Color.RED);
	    lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 33));
	    lblNewLabel_6.setBounds(313, 0, 26, 29);
	    panel_1.add(lblNewLabel_6);
	    frame.setVisible(true);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
