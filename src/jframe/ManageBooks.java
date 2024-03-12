package jframe;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManageBooks extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    String name, author;
    int bookid, quantity;
    DefaultTableModel model;

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTable table;
	
	

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ManageBooks window = new ManageBooks();
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
    public ManageBooks() {
    	
        initialize();
        setBookDetailsToTable();
    }

    public void setBookDetailsToTable() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            con.setAutoCommit(true);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");

            
            model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("Book ID");
            model.addColumn("Name");
            model.addColumn("Author");
            model.addColumn("Quantity");

            // Clear existing rows
            model.setRowCount(0);

            while (rs.next()) {
                String bookid = rs.getString("bookid");
                String name = rs.getString("name");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                Object[] obj = { bookid, name, author, quantity };
                model.addRow(obj);
            }
            
            // Close resources
            rs.close();
            st.close();
            con.close(); // Close the connection after use
        } catch (Exception e) {
            // Print stack trace for debugging
            e.printStackTrace();
        }
    }
    public boolean addBook() {
        boolean isAdded = false;
        try {
            // Get text from text fields
            String bookIdText = textField.getText();
            String name =textField_1.getText();
            String author = textField_2.getText();
            String quantityText = textField_3.getText();
            
            // Validate input
            if (bookIdText.isEmpty() || name.isEmpty() || author.isEmpty() || quantityText.isEmpty()) {
                System.out.println("Please fill in all fields.");
                return false;
            }
            
            // Parse input
            int bookid = Integer.parseInt(bookIdText);
            int quantity = Integer.parseInt(quantityText);

            // Add book to database
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            String sql = "INSERT INTO books VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookid);
            pst.setString(2, name);
            pst.setString(3, author);
            pst.setInt(4, quantity);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isAdded = true;
                JOptionPane.showMessageDialog(null, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
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
  //clear table
    public boolean deleteBook() {
        int bookid = Integer.parseInt(textField.getText()); // Use textField instead of txt_textField
        boolean isDeleted = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            String sql = "DELETE FROM books WHERE bookid=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, bookid);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;
                JOptionPane.showMessageDialog(null, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    public boolean updateBook() {
        int bookid = Integer.parseInt(textField.getText()); 
        String name = textField_1.getText(); 
        String author = textField_2.getText();
        int quantity = Integer.parseInt(textField_3.getText());
        boolean isUpdated = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            String sql = "UPDATE books SET name=?, author=?, quantity=? WHERE bookid=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, author);
            pst.setInt(3, quantity);
            pst.setInt(4, bookid);
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
                JOptionPane.showMessageDialog(null, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        frame.setBounds(100, 100, 618, 416);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 255));
        panel.setBounds(0, 0, 175, 416);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Book ID");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setForeground(new Color(255, 250, 250));
        lblNewLabel.setBounds(5, 75, 49, 20);
        panel.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(64, 76, 86, 20);
        panel.add(textField);
        textField.setColumns(10);

        JLabel lblBookName = new JLabel("Name");
        lblBookName.setForeground(new Color(255, 250, 250));
        lblBookName.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblBookName.setBounds(5, 106, 49, 20);
        panel.add(lblBookName);

        textField_1 = new JTextField();
        textField_1.setBounds(64, 107, 86, 20);
        panel.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Author");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_1.setForeground(new Color(255, 250, 250));
        lblNewLabel_1.setBounds(5, 140, 46, 14);
        panel.add(lblNewLabel_1);

        textField_2 = new JTextField();
        textField_2.setBounds(64, 138, 86, 20);
        panel.add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Quantity");
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_2.setBounds(5, 176, 49, 14);
        panel.add(lblNewLabel_2);

        textField_3 = new JTextField();
        textField_3.setBounds(64, 174, 86, 20);
        panel.add(textField_3);
        textField_3.setColumns(10);

        JButton btnNewButton = new JButton("ADD");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		addBook();
        	}
        });
        btnNewButton.setBackground(new Color(255, 69, 0));
        btnNewButton.setBounds(64, 218, 89, 23);
        panel.add(btnNewButton);

        JButton btnNewButton_2 = new JButton("DELETE");
        btnNewButton_2.setBackground(new Color(255, 69, 0));
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	deleteBook();
            }
            
        });
        btnNewButton_2.setBounds(64, 252, 89, 20);
        panel.add(btnNewButton_2);

        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\devik\\Documents\\icons\\icons8_Account_50px.png"));
        lblNewLabel_4.setBounds(5, 209, 59, 89);
        panel.add(lblNewLabel_4);
        
        JLabel lblNewLabel_7 = new JLabel("");
        lblNewLabel_7.setBounds(69, 0, 38, 79);
        panel.add(lblNewLabel_7);
        lblNewLabel_7.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_Library_32px.png"));
        
        JButton btnNewButton_1 = new JButton("LOGOUT");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		Loginpage login=new Loginpage();
                login.setVisible(true);
                dispose();
        	}
        	
        	
        });
        btnNewButton_1.setBounds(46, 319, 89, 34);
        panel.add(btnNewButton_1);
        
        JButton btnNewButton_3 = new JButton("UPDATE");
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		updateBook();
        	}
        });
        btnNewButton_3.setBackground(Color.RED);
        btnNewButton_3.setBounds(64, 283, 89, 23);
        panel.add(btnNewButton_3);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBounds(174, 0, 444, 416);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(53, 176, 318, 83);
        panel_1.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
     // Add this code inside your ManageBooks class constructor after setBookDetailsToTable() method call.
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected
                    // Retrieve data from the selected row
                    String bookid = table.getValueAt(selectedRow, 0).toString();
                    String name = table.getValueAt(selectedRow, 1).toString();
                    String author = table.getValueAt(selectedRow, 2).toString();
                    String quantity = table.getValueAt(selectedRow, 3).toString();

                    // Populate data into text fields for updating
                    textField.setText(bookid);
                    textField_1.setText(name);
                    textField_2.setText(author);
                    textField_3.setText(quantity);
                }
            }
        });


        JLabel lblNewLabel_3 = new JLabel("MANAGE BOOKS");
        lblNewLabel_3.setBounds(142, 48, 145, 28);
        panel_1.add(lblNewLabel_3);
        lblNewLabel_3.setForeground(Color.RED);
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 17));

        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\stack-of-books (1).png"));
        lblNewLabel_5.setBounds(64, 32, 68, 65);
        panel_1.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("x");
        lblNewLabel_6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.exit(0);
            }
        });
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 28));
        lblNewLabel_6.setForeground(Color.BLUE);
        lblNewLabel_6.setBounds(416, 11, 18, 28);
        panel_1.add(lblNewLabel_6);
        frame.setVisible(true);
    }

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub
    }
}

