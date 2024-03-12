package jframe;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageStudents extends JFrame  {
	private static final long serialVersionUID = 1L;
    String name, course,year;
    int studentid;
    DefaultTableModel model;

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageStudents window = new ManageStudents();
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
	public ManageStudents() {
				initialize();
				setstudentDetailsToTable();
	}
	public void setstudentDetailsToTable() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            con.setAutoCommit(true);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("studentid");
            model.addColumn("Name");
            model.addColumn("course");
            model.addColumn("year");
            model.setRowCount(0);

            while (rs.next()) {
                String studentid = rs.getString("studentid");
                String name = rs.getString("name");
                String course = rs.getString("course");
                String year = rs.getString("year");
                Object[] obj = { studentid, name, course, year };
                model.addRow(obj);
            }
            
            rs.close();
            st.close();
            con.close(); // Close the connection after use
        } catch (Exception e) {
            // Print stack trace for debugging
            e.printStackTrace();
        }
    }
	
	public boolean addstudent() {
        boolean isAdded = false;
        try {
            // Get text from text fields
            String studentidText = textField.getText();
            String name =textField_1.getText();
            String course = textField_2.getText();
            String year = textField_3.getText();
            
            // Validate input
            if (studentidText.isEmpty() || name.isEmpty() || course.isEmpty() || year.isEmpty()) {
                System.out.println("Please fill in all fields.");
                return false;
            }
            
            // Parse input
            int studentid = Integer.parseInt(studentidText);
            

            // Add book to database
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            String sql = "INSERT INTO students VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentid);
            pst.setString(2, name);
            pst.setString(3, course);
            pst.setString(4, year);
            int rowCount = pst.executeUpdate();
            
            // Check if book was successfully added
            if (rowCount > 0) {
                isAdded = true;
                JOptionPane.showMessageDialog(null, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Close resources
            pst.close();
            con.close();
        } catch (NumberFormatException e) {
            // Handle invalid input format
            System.out.println("Please enter valid numeric values for Book ID and Quantity.");
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
        return isAdded;
    }
	
	public boolean deleteStudent() {
        int studentid = Integer.parseInt(textField.getText()); // Use textField instead of txt_textField
        boolean isDeleted = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            String sql = "DELETE FROM students WHERE studentid=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentid);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;
                JOptionPane.showMessageDialog(null, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                isDeleted = false;
            }
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
	
	public boolean updateStudent() {
	    int studentid = Integer.parseInt(textField.getText()); 
	    String name = textField_1.getText(); 
	    String course = textField_2.getText();
	    String year = textField_3.getText(); 

	    boolean isUpdated = false;
	    try {
	        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
	        String sql = "UPDATE students SET name=?, course=?, year=? WHERE studentid=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, name);
	        pst.setString(2, course);
	        pst.setString(3, year);
	        pst.setInt(4, studentid);
	        int rowCount = pst.executeUpdate();
	        if (rowCount > 0) {
	            isUpdated = true;
	            JOptionPane.showMessageDialog(null, "Student updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            isUpdated = false;
	        }
	        pst.close();
	        con.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return isUpdated;
	}


	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 582, 392);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		panel.setBounds(0, 0, 199, 391);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 119, 64, 23);
		panel.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Name");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblName.setBounds(10, 159, 39, 23);
		panel.add(lblName);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setForeground(Color.WHITE);
		lblCourse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCourse.setBounds(8, 193, 51, 23);
		panel.add(lblCourse);
		
		JLabel lblYear = new JLabel("Year");
		lblYear.setForeground(Color.WHITE);
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblYear.setBounds(10, 235, 64, 23);
		panel.add(lblYear);
		
		textField = new JTextField();
		textField.setBounds(88, 121, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(88, 161, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addstudent();
			}
		});
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(64, 279, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DELETE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteStudent();
			}
		});
		btnNewButton_1.setBackground(Color.RED);
		btnNewButton_1.setBounds(64, 313, 89, 23);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\AddNewBookIcons\\icons8_Literature_100px_1.png"));
		lblNewLabel_1.setBounds(37, 0, 138, 109);
		panel.add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(88, 195, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(88, 237, 86, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStudent();
			}
		});
		btnUpdate.setBackground(Color.RED);
		btnUpdate.setBounds(64, 347, 89, 23);
		panel.add(btnUpdate);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(198, 0, 383, 391);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Manage Students");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(134, 46, 214, 35);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\AddNewBookIcons\\icons8_Student_Male_100px.png"));
		lblNewLabel_3.setBounds(27, 11, 96, 93);
		panel_1.add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 171, 310, 93);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Student ID", "Name", "Course", "Year"
			}
		));
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            int selectedRow = table.getSelectedRow();
	            if (selectedRow != -1) { // If a row is selected
	                // Retrieve data from the selected row
	                String studentid = table.getValueAt(selectedRow, 0).toString();
	                String name = table.getValueAt(selectedRow, 1).toString();
	                String course = table.getValueAt(selectedRow, 2).toString();
	                String year = table.getValueAt(selectedRow, 3).toString();

	                // Populate data into text fields for updating or deletion
	                textField.setText(studentid);
	                textField_1.setText(name);
	                textField_2.setText(course);
	                textField_3.setText(year);
	            }
	        }
	    });

		
		JLabel lblNewLabel_4 = new JLabel("x");
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		
		});
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel_4.setForeground(Color.BLUE);
		lblNewLabel_4.setBounds(331, 0, 46, 35);
		panel_1.add(lblNewLabel_4);
        frame.setVisible(true);
	}
}
