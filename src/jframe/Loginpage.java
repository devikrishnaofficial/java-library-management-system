package jframe;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Loginpage {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    
    /**
     * Launch the application.
     */
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Loginpage window = new Loginpage();
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
    public Loginpage() {
        initialize();
    }
    public boolean validateLogin() {
        String username = textField.getText();
        String password = textField_1.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter username");
            return false;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter password");
            return false;
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            PreparedStatement pst = con.prepareStatement("select * from users where username=? and password=?");
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(frame, "Login successful");
                HomePage home = new HomePage();
                home.setVisible(true);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password");
            }
            
            // Closing resources
            rs.close();
            pst.close();
            con.close();
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            // Handle ClassNotFoundException
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle SQLException
        }
        
        return true;
    }
     
    public void dispose() {
		
	}

	private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
        frame.getContentPane().setBackground(new Color(0, 102, 204));
        frame.setBounds(100, 100, 582, 369);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        JPanel panel = new JPanel(); // Declare and initialize panel
        panel.setBounds(0, 0, 340, 700);
        panel.setForeground(new Color(0, 0, 0));
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome to");
		lblNewLabel.setFont(new Font("Sitka Heading", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 0, 169, 36);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Read-Main Library ");
		lblNewLabel_1.setFont(new Font("Sitka Heading", Font.BOLD, 20));
		lblNewLabel_1.setBounds(123, 11, 185, 36);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\\");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\library-3.png (1).png"));
		lblNewLabel_2.setBounds(-76, -290, 979, 990);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Library");
		lblNewLabel_3.setFont(new Font("Sitka Heading", Font.BOLD, 20));
		lblNewLabel_3.setBounds(30, 151, 87, 26);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("LoginPage");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBackground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Sitka Heading", Font.BOLD, 30));
		lblNewLabel_4.setBounds(394, 38, 144, 38);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Login to your account");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Sitka Subheading", Font.ITALIC, 10));
		lblNewLabel_5.setBounds(394, 65, 117, 23);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Username");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(370, 121, 82, 23);
		frame.getContentPane().add(lblNewLabel_6);
		
		textField = new JTextField();
		textField.setForeground(new Color(0, 0, 0));
		textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(452, 123, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Password");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_7.setBounds(370, 155, 66, 14);
		frame.getContentPane().add(lblNewLabel_7);
		
		textField_1 = new JTextField();
		textField_1.setBounds(452, 153, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("New label");
		lblNewLabel_10.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\icons\\icons8_Account_50px.png"));
		lblNewLabel_10.setBounds(339, 32, 49, 50);
		frame.getContentPane().add(lblNewLabel_10);
        JButton btnNewButton_1 = new JButton("SIGN-UP");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signup sign1=new signup();
                sign1.setVisible(true);
                dispose();
                
            }
        });
        btnNewButton_1.setBounds(404, 254, 89, 23);
        frame.getContentPane().add(btnNewButton_1);
    
JButton btnNewButton_2 = new JButton("LOGIN");
btnNewButton_2.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		validateLogin();
	}
});
        
        JButton btnNewButton_21 = new JButton("LOGIN");
        btnNewButton_21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validateLogin();
            }
        });
        btnNewButton_21.setBounds(404, 220, 89, 23);
        frame.getContentPane().add(btnNewButton_21);
        
        JLabel lblNewLabel_12 = new JLabel("x");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblNewLabel_12.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel_12.setBounds(548, 11, 31, 28);
		frame.getContentPane().add(lblNewLabel_12);
        
        frame.setVisible(true);
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}