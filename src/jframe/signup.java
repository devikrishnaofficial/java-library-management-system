package jframe;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class signup {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_4;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    signup window = new signup();
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
    public signup() {
        initialize();
    }

    

    public void insertSignupDetails() {
        String userid = textField_4.getText();
        String username = textField.getText();
        String password = textField_1.getText();
        String email = textField_2.getText();
        
        if (userid.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() ) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.");
            return; // Stop further processing
        }
        
        // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid email address.");
            return; // Stop further processing
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            String sql = "INSERT INTO users(userid,username, password, email) VALUES (?, ?, ?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userid);
            pst.setString(2, username);
            pst.setString(3, password);
            pst.setString(4, email);
           

            int updatedRowCount = pst.executeUpdate();

            if (updatedRowCount > 0) {
                JOptionPane.showMessageDialog(frame, "Record Inserted Successfully");
                Loginpage login=new Loginpage();
                login.setVisible(true);
                dispose();
                
            } else {
                JOptionPane.showMessageDialog(frame, "Record Insertion Failed");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception for debugging
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }
    private boolean isValidEmail(String email) {
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    

	private void dispose() {
		// TODO Auto-generated method stub
		
	}

	/**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
        frame.getContentPane().setBackground(new Color(0, 102, 204));
        frame.setBounds(100, 100, 582, 369);
        frame.setUndecorated(true); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

       JPanel panel = new JPanel();
		panel.setBounds(0, 0, 340,700);
		panel.setForeground(new Color(0, 0, 0));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to");
		lblNewLabel.setFont(new Font("Sitka Heading", Font.BOLD, 20));
		lblNewLabel.setBounds(68, 51, 169, 36);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Read-Main Library ");
		lblNewLabel_1.setFont(new Font("Sitka Heading", Font.BOLD, 20));
		lblNewLabel_1.setBounds(52, 74, 185, 36);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\\");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\icons\\signup-library-icon.png"));
		lblNewLabel_2.setBounds(-253, -301, 979, 990);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Library");
		lblNewLabel_3.setFont(new Font("Sitka Heading", Font.BOLD, 20));
		lblNewLabel_3.setBounds(30, 151, 87, 26);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Sign-up");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Sitka Heading", Font.BOLD, 30));
		lblNewLabel_4.setBounds(394, 38, 144, 38);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Create new account here");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Sitka Subheading", Font.ITALIC, 10));
		lblNewLabel_5.setBounds(394, 65, 117, 23);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Username");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(368, 134, 82, 23);
		frame.getContentPane().add(lblNewLabel_6);
		
		textField = new JTextField();
		textField.setForeground(new Color(0, 0, 0));
		textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(452, 137, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Password");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(368, 169, 66, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		textField_1 = new JTextField();
		textField_1.setBounds(452, 168, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Email");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(368, 197, 66, 20);
		frame.getContentPane().add(lblNewLabel_8);
		
		textField_2 = new JTextField();
		textField_2.setBounds(452, 199, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("New label");
		lblNewLabel_10.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\icons\\icons8_Account_50px.png"));
		lblNewLabel_10.setBounds(339, 32, 49, 50);
		frame.getContentPane().add(lblNewLabel_10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\icons\\icons8_Secure_50px.png"));
		btnNewButton.setBounds(350, 260, 66, 59);
		frame.getContentPane().add(btnNewButton);
		

        JButton btnNewButton_1 = new JButton("SIGN-UP");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertSignupDetails();
                
            }
        });
        btnNewButton_1.setBounds(436, 262, 89, 23);
        frame.getContentPane().add(btnNewButton_1);
JButton btnNewButton_2 = new JButton("LOGIN");
btnNewButton_2.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		Loginpage login=new Loginpage();
		login.setVisible(true);
		dispose();
	}
});
		btnNewButton_2.setBounds(436, 296, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		textField_4 = new JTextField();
		textField_4.setBounds(452, 103, 86, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_11 = new JLabel("UserId");
		lblNewLabel_11.setForeground(new Color(255, 255, 255));
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_11.setBounds(368, 109, 66, 14);
		frame.getContentPane().add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("x");
		lblNewLabel_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_12.setBounds(546, 11, 36, 27);
		frame.getContentPane().add(lblNewLabel_12);
	    frame.setVisible(true);
    }



	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}