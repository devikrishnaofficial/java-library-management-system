package jframe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.border.MatteBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;

public class HomePage {

    private JFrame frame;
    private JTable table;
    private JTable table_1;
    private JLabel lblNewLabel_11;
    private JLabel lblNewLabel_11_1;
    private JLabel lblNewLabel_11_2;

    Color mouseEnterColor=new Color(51,51,51);
	Color mouseExitColor=new Color(0,0,0);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HomePage window = new HomePage();
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
    public HomePage() {
        initialize();
        setstudentDetailsToTable();
        setBookDetailsToTable(); 
        setDataToCards();
    }

    public void setBookDetailsToTable() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            con.setAutoCommit(true);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            
            DefaultTableModel model = new DefaultTableModel();
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
    public void setstudentDetailsToTable() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            con.setAutoCommit(true);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            DefaultTableModel model = new DefaultTableModel();
            table_1.setModel(model); // Use table_1 here instead of table

            model.addColumn("studentid");
            model.addColumn("Name");
            model.addColumn("course");
            model.addColumn("year");

            // Clear existing rows
            model.setRowCount(0);

            while (rs.next()) {
                String studentid = rs.getString("studentid");
                String name = rs.getString("name");
                String course = rs.getString("course");
                String year = rs.getString("year");
                Object[] obj = { studentid, name, course, year };
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

	
    public void setDataToCards() {
        Statement st = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "msc", "msc");
            st = con.createStatement();
            
            // Count the number of rows in the books table
            ResultSet rsBooks = st.executeQuery("SELECT COUNT(*) FROM books");
            rsBooks.next(); // Move the cursor to the first row
            int bookCount = rsBooks.getInt(1); // Get the value of the first column
            lblNewLabel_11.setText(Integer.toString(bookCount));

            // Count the number of rows in the students table
            ResultSet rsStudents = st.executeQuery("SELECT COUNT(*) FROM students");
            rsStudents.next(); // Move the cursor to the first row
            int studentCount = rsStudents.getInt(1); // Get the value of the first column
            lblNewLabel_11_1.setText(Integer.toString(studentCount));

            
            ResultSet rsIssueBooks = st.executeQuery("SELECT COUNT(*) FROM issuebook WHERE status='Pending'");
            rsIssueBooks.next(); // Move the cursor to the first row
            int issueBookCount = rsIssueBooks.getInt(1); // Get the value of the first column
            lblNewLabel_11_2.setText(Integer.toString(issueBookCount));

            // Close the ResultSets
            rsBooks.close();
            rsStudents.close();
            rsIssueBooks.close();
            
            // Close the statement and connection
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace when an exception occurs
        }
    }


	/**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 576, 361);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 255));
        panel.setBounds(0, 0, 576, 49);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_menu_48px_1.png"));
        lblNewLabel.setBounds(10, 11, 31, 27);
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Library Management System");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(51, 11, 202, 27);
        panel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\male_user_50px.png"));
        lblNewLabel_2.setBounds(373, 0, 50, 49);
        panel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel(" Welcome Admin");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_3.setBackground(new Color(240, 240, 240));
        lblNewLabel_3.setForeground(new Color(255, 255, 255));
        lblNewLabel_3.setBounds(418, 22, 117, 27);
        panel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("x");
        lblNewLabel_4.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.exit(0);
        	}
        });
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 21));
        lblNewLabel_4.setForeground(new Color(255, 255, 255));
        lblNewLabel_4.setBounds(545, 0, 31, 25);
        panel.add(lblNewLabel_4);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(0, 0, 0));
        panel_2.setBounds(0, 47, 147, 314);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.RED);
        panel_1.setForeground(new Color(255, 0, 0));
        panel_1.setBounds(0, 31, 162, 26);
        panel_2.add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setBounds(10, -7, 35, 32);
        panel_1.add(lblNewLabel_5);
        lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_Home_26px_2.png"));
        
        JLabel lblNewLabel_6 = new JLabel("Home");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_6.setForeground(new Color(255, 255, 255));
        lblNewLabel_6.setBounds(44, 0, 76, 25);
        panel_1.add(lblNewLabel_6);
        
        JPanel panel_1_1 = new JPanel();
        panel_1_1.setForeground(new Color(204, 0, 0));
        panel_1_1.setBackground(Color.BLACK);
        panel_1_1.setBounds(0, 57, 162, 32);
        panel_2.add(panel_1_1);
        panel_1_1.setLayout(null);
        
        JLabel lblNewLabel_5_1 = new JLabel("");
        lblNewLabel_5_1.setBounds(23, 0, 22, 32);
        panel_1_1.add(lblNewLabel_5_1);
        
        JLabel lblNewLabel_6_1 = new JLabel("LMS Dashboard");
        lblNewLabel_6_1.setBounds(42, 7, 93, 25);
        lblNewLabel_6_1.setForeground(Color.WHITE);
        lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        panel_1_1.add(lblNewLabel_6_1);
        
        JLabel lblNewLabel_7 = new JLabel("New label");
        lblNewLabel_7.setBounds(10, 0, 46, 32);
        lblNewLabel_7.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_Library_32px.png"));
        panel_1_1.add(lblNewLabel_7);
        
        JLabel lblNewLabel_8 = new JLabel("Features");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel_8.setForeground(Color.WHITE);
        lblNewLabel_8.setBounds(0, 84, 54, 21);
        panel_2.add(lblNewLabel_8);
        
        JPanel panel_1_1_1 = new JPanel();
        panel_1_1_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		panel_1_1_1.setBackground(mouseEnterColor);
        	}
        	@Override
        	public void mouseExited(MouseEvent e) {
        		panel_1_1_1.setBackground(mouseExitColor);
        	}
        });
        panel_1_1_1.setLayout(null);
        panel_1_1_1.setForeground(new Color(204, 0, 0));
        panel_1_1_1.setBackground(Color.BLACK);
        panel_1_1_1.setBounds(0, 107, 162, 32);
        panel_2.add(panel_1_1_1);
        
        JLabel lblNewLabel_6_1_1 = new JLabel("Manage Books");
        lblNewLabel_6_1_1.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ManageBooks books=new ManageBooks();
        		books.setVisible(true);
        		dispose();
        	}

			private void dispose() {
				// TODO Auto-generated method stub
				
			}
        });
        lblNewLabel_6_1_1.setForeground(Color.WHITE);
        lblNewLabel_6_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_6_1_1.setBounds(55, 0, 93, 25);
        panel_1_1_1.add(lblNewLabel_6_1_1);
        
        JPanel panel_1_1_1_1 = new JPanel();
        panel_1_1_1_1.setForeground(new Color(204, 0, 0));
        panel_1_1_1_1.setBackground(Color.BLACK);
        panel_1_1_1_1.setBounds(0, 138, 162, 32);
        panel_2.add(panel_1_1_1_1);
        panel_1_1_1_1.setLayout(null);
        
        JLabel lblNewLabel_6_1_1_1 = new JLabel("Manage Students");
        lblNewLabel_6_1_1_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ManageStudents Students=new ManageStudents();
        		Students.setVisible(true);
        		dispose();
        	}

			private void dispose() {
				// TODO Auto-generated method stub
				
			}
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		panel_1_1_1_1.setBackground(mouseEnterColor);
        	}
        	@Override
        	public void mouseExited(MouseEvent e) {
        		panel_1_1_1_1.setBackground(mouseExitColor);
        	}
        });
        lblNewLabel_6_1_1_1.setBounds(40, 0, 112, 25);
        lblNewLabel_6_1_1_1.setForeground(Color.WHITE);
        lblNewLabel_6_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        panel_1_1_1_1.add(lblNewLabel_6_1_1_1);
        
        JPanel panel_1_1_1_1_1 = new JPanel();
        panel_1_1_1_1_1.setLayout(null);
        panel_1_1_1_1_1.setForeground(new Color(204, 0, 0));
        panel_1_1_1_1_1.setBackground(Color.BLACK);
        panel_1_1_1_1_1.setBounds(0, 170, 162, 32);
        panel_2.add(panel_1_1_1_1_1);
        
        JLabel lblNewLabel_6_1_1_1_1 = new JLabel("Issue Book");
        lblNewLabel_6_1_1_1_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		IssuedBook ibook=new IssuedBook();
        		ibook.setVisible(true);
        		dispose();
        	}

			private void dispose() {
				// TODO Auto-generated method stub
				
			}
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		panel_1_1_1_1_1.setBackground(mouseEnterColor);
        	}
        	@Override
        	public void mouseExited(MouseEvent e) {
        		panel_1_1_1_1_1.setBackground(mouseExitColor);	
        	}
        });
        lblNewLabel_6_1_1_1_1.setForeground(Color.WHITE);
        lblNewLabel_6_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_6_1_1_1_1.setBounds(59, 0, 93, 25);
        panel_1_1_1_1_1.add(lblNewLabel_6_1_1_1_1);
        
        JPanel panel_1_1_1_1_2 = new JPanel();
        panel_1_1_1_1_2.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		panel_1_1_1_1_2.setBackground(mouseEnterColor);	
        	}
        	@Override
        	public void mouseExited(MouseEvent e) {
        		panel_1_1_1_1_2.setBackground(mouseExitColor);	
        	}
        	@Override
        	public void mouseClicked(MouseEvent e) {
        	    ReturnBook ret = new ReturnBook();
        	    ret.setVisible(true);
        	    dispose();
        	}
			private void dispose() {
				// TODO Auto-generated method stub
				
			}

        });
        panel_1_1_1_1_2.setLayout(null);
        panel_1_1_1_1_2.setForeground(new Color(204, 0, 0));
        panel_1_1_1_1_2.setBackground(Color.BLACK);
        panel_1_1_1_1_2.setBounds(0, 202, 162, 32);
        panel_2.add(panel_1_1_1_1_2);
        
        JLabel lblNewLabel_6_1_1_1_2 = new JLabel("Return Book");
        lblNewLabel_6_1_1_1_2.setForeground(Color.WHITE);
        lblNewLabel_6_1_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_6_1_1_1_2.setBounds(42, 0, 93, 25);
        panel_1_1_1_1_2.add(lblNewLabel_6_1_1_1_2);
        
        JPanel panel_1_1_1_1_6 = new JPanel();
        panel_1_1_1_1_6.setForeground(new Color(204, 0, 0));
        panel_1_1_1_1_6.setBackground(Color.BLACK);
        panel_1_1_1_1_6.setBounds(0, 271, 162, 32);
        panel_2.add(panel_1_1_1_1_6);
        panel_1_1_1_1_6.setLayout(null);
        
        JLabel lblNewLabel_6_1_1_1_6 = new JLabel("LOGOUT");
        lblNewLabel_6_1_1_1_6.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		Loginpage login=new Loginpage();
                login.setVisible(true);
                dispose();
        	}

			private void dispose() {
				// TODO Auto-generated method stub
				
			}
        	
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		panel_1_1_1_1_6.setBackground(mouseEnterColor);
        	}
        	@Override
        	public void mouseExited(MouseEvent e) {
        		panel_1_1_1_1_6.setBackground(mouseExitColor);
        	}
        });
        lblNewLabel_6_1_1_1_6.setBounds(59, 0, 93, 25);
        panel_1_1_1_1_6.add(lblNewLabel_6_1_1_1_6);
        lblNewLabel_6_1_1_1_6.setForeground(Color.WHITE);
        lblNewLabel_6_1_1_1_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        JLabel lblNewLabel_10 = new JLabel("");
        lblNewLabel_10.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_Exit_26px_2.png"));
        lblNewLabel_10.setBounds(10, 0, 46, 26);
        panel_1_1_1_1_6.add(lblNewLabel_10);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(Color.WHITE);
        panel_4.setBounds(145, 47, 431, 314);
        frame.getContentPane().add(panel_4);
        panel_4.setLayout(null);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new MatteBorder(10, 0, 0, 0, (Color) Color.RED));
        panel_3.setBounds(43, 34, 89, 45);
        panel_4.add(panel_3);
        panel_3.setLayout(null);
        
        lblNewLabel_11 = new JLabel("");

        lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_11.setForeground(new Color(0, 0, 0));
        lblNewLabel_11.setBounds(51, 11, 38, 23);
        panel_3.add(lblNewLabel_11);
        
        JLabel lblNewLabel_12 = new JLabel("");
        lblNewLabel_12.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_Book_Shelf_50px.png"));
        lblNewLabel_12.setBounds(0, 11, 44, 44);
        panel_3.add(lblNewLabel_12);
        
        JLabel lblNewLabel_9 = new JLabel("No of books");
        lblNewLabel_9.setBounds(43, 21, 71, 14);
        panel_4.add(lblNewLabel_9);
        
        JPanel panel_3_1 = new JPanel();
        panel_3_1.setLayout(null);
        panel_3_1.setBorder(new MatteBorder(10, 0, 0, 0, (Color) new Color(0, 0, 255)));
        panel_3_1.setBounds(155, 34, 89, 45);
        panel_4.add(panel_3_1);
        
         lblNewLabel_11_1 = new JLabel("");
        lblNewLabel_11_1.setForeground(Color.BLACK);
        lblNewLabel_11_1.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_11_1.setBounds(51, 11, 38, 23);
        panel_3_1.add(lblNewLabel_11_1);
        
        JLabel lblNewLabel_12_1 = new JLabel("");
        lblNewLabel_12_1.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_People_50px.png"));
        lblNewLabel_12_1.setBounds(0, 11, 50, 44);
        panel_3_1.add(lblNewLabel_12_1);
        
        JPanel panel_3_2 = new JPanel();
        panel_3_2.setLayout(null);
        panel_3_2.setBorder(new MatteBorder(10, 0, 0, 0, (Color) Color.RED));
        panel_3_2.setBounds(262, 34, 89, 45);
        panel_4.add(panel_3_2);
        
        lblNewLabel_11_2 = new JLabel("");
        lblNewLabel_11_2.setForeground(Color.BLACK);
        lblNewLabel_11_2.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_11_2.setBounds(45, 11, 38, 23);
        panel_3_2.add(lblNewLabel_11_2);
        
        JLabel lblNewLabel_12_2 = new JLabel("");
        lblNewLabel_12_2.setIcon(new ImageIcon("C:\\Users\\devik\\Downloads\\adminIcons\\adminIcons\\icons8_Sell_26px.png"));
        lblNewLabel_12_2.setBounds(10, 11, 35, 44);
        panel_3_2.add(lblNewLabel_12_2);
        
        JLabel lblNewLabel_13 = new JLabel("No of students");
        lblNewLabel_13.setBounds(155, 21, 89, 14);
        panel_4.add(lblNewLabel_13);
        
        JLabel lblNewLabel_14 = new JLabel("Issued Books");
        lblNewLabel_14.setBounds(262, 21, 89, 14);
        panel_4.add(lblNewLabel_14);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(43, 109, 320, 76);
        panel_4.add(scrollPane);
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Student ID", "Name", "Course", "Year"
        	}
        ));
        scrollPane.setViewportView(table);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(27, 221, 379, 93);
        panel_4.add(scrollPane_1);
        
        table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Student ID", "Name", "Course", "Year"
        	}
        ));
        scrollPane_1.setViewportView(table_1);
        
        JLabel lblNewLabel_15 = new JLabel("Book Details");
        lblNewLabel_15.setBounds(26, 90, 106, 18);
        panel_4.add(lblNewLabel_15);
        
        JLabel lblNewLabel_16 = new JLabel("Student Details");
        lblNewLabel_16.setBounds(24, 196, 76, 14);
        panel_4.add(lblNewLabel_16);
        frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, lblNewLabel, panel_2, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNewLabel_4, panel_1, lblNewLabel_5, lblNewLabel_6, panel_1_1, lblNewLabel_5_1, lblNewLabel_6_1, lblNewLabel_7, lblNewLabel_8, panel_1_1_1, lblNewLabel_6_1_1, panel_1_1_1_1, lblNewLabel_6_1_1_1, panel_1_1_1_1_1, lblNewLabel_6_1_1_1_1, panel_1_1_1_1_2, lblNewLabel_6_1_1_1_2, panel_1_1_1_1_6, lblNewLabel_6_1_1_1_6, lblNewLabel_10, panel_4, panel_3, lblNewLabel_9, lblNewLabel_11, lblNewLabel_12, panel_3_1, lblNewLabel_11_1, lblNewLabel_12_1, panel_3_2, lblNewLabel_11_2, lblNewLabel_12_2, lblNewLabel_13, lblNewLabel_14, table, scrollPane, table_1, scrollPane_1, lblNewLabel_15, lblNewLabel_16}));
        frame.setVisible(true);
    }
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}

