import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class StudentWindow extends JFrame
{
	private WindowHandler windowHandler;
	private ArrayList<Student> filteredStudents, allStudents;
	private JList<Student> studentList;
	private ArrayList<Worksheet> studentWorksheets;
	private JPanel rightPanel;
	private JLabel lblStudentName, lblStudentLevel;
	private JTextField searchStudentField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField levelField;

	public StudentWindow(WindowHandler handler) //creates a window where the user can manage students and view student information
	{
		windowHandler = handler;
		filteredStudents = handler.getStudents();
		allStudents = handler.getStudents();
		initWindow(800, 800);
		searchStudentField.transferFocus();
	}

	private void initWindow(int w, int h) //initializes the JFrame and adds all the Java Swing components to the content pane
	{
		setSize(w, h);
		getContentPane().setBackground(Color.WHITE);
		setTitle("Student Manager");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JButton btnBack = new JButton(); //JButton to return to the MainMenu
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				windowHandler.getMenu().setLocationRelativeTo(null);
				setVisible(false);
				windowHandler.getMenu().setVisible(true);
			}
		});
		Utils.makeBtnSimple(btnBack);
		btnBack.setBorder(null);
		btnBack.setBounds(10, 6, 83, 30);
		btnBack.setIcon(Utils.scaleImageIcon(new ImageIcon(StudentWindow.class.getResource("/res/backButton.png")),
				btnBack.getWidth(), btnBack.getHeight()));
		getContentPane().add(btnBack);

		JPanel leftPanel = new JPanel(); //JPanel containing components on the left half of the JFrame
		leftPanel.setBorder(new LineBorder(Color.DARK_GRAY));
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setBounds(10, 41, 370, 710);
		leftPanel.setLayout(null);
		getContentPane().add(leftPanel);

		studentList = new JList<Student>(); //JList containing all the Student objects loaded from the data file
		studentList.setModel(new DefaultListModel<Student>());
		DefaultListModel<Student> studentListModel = (DefaultListModel<Student>)studentList.getModel();
		for(Student s : filteredStudents)
			studentListModel.addElement(s);
		studentList.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent arg0)
			{
				loadStudentInfoView(studentList.getSelectedValue());
			}
		});
		studentList.setBorder(null);
		studentList.setFont(new Font("Arial Black", Font.PLAIN, 20));
		studentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane scrollPane = new JScrollPane(studentList); //JScrollPane to allow for scrolling through the studentList
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPane.setViewportBorder(new LineBorder(Color.DARK_GRAY));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, 21, 370, 689);
		leftPanel.add(scrollPane);

		searchStudentField = new JTextField("Search by Student Name"); //JTextField for user search-by-string input
		searchStudentField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				filteredStudents.clear();
				for (int i = 0; i < allStudents.size(); i++)
				{
					Student tempStudent = allStudents.get(i);
					String searchString =  tempStudent.getLastName() + ", " + tempStudent.getFirstName();
					if(searchString.contains(searchStudentField.getText()))
						filteredStudents.add(tempStudent);
				}
			}
		});
		searchStudentField.setBorder(new LineBorder(Color.DARK_GRAY));
		searchStudentField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent arg0)
			{
				if (searchStudentField.getText().equals("Search by Student Name"))
				{
					searchStudentField.setText("");
					searchStudentField.setForeground(Color.BLACK);
				} else
					searchStudentField.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (searchStudentField.getText().equals(""))
				{
					searchStudentField.setText("Search by Student Name");
					searchStudentField.setForeground(Color.GRAY);
					filteredStudents.clear();
					for (int i = 0; i < allStudents.size(); i++)
					{
						Student tempStudent = allStudents.get(i);
						filteredStudents.add(tempStudent);
					}
				}
				
			}
		});
		searchStudentField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		searchStudentField.setForeground(Color.GRAY);
		searchStudentField.setBounds(21, 1, 348, 20);
		searchStudentField.setBorder(BorderFactory.createEmptyBorder());
		searchStudentField.transferFocus();
		leftPanel.add(searchStudentField);

		JLabel lblSearchPic = new JLabel(); //JLabel containing the search icon picture
		lblSearchPic.setBackground(Color.WHITE);
		lblSearchPic.setIcon(
				Utils.scaleImageIcon(new ImageIcon(StudentWindow.class.getResource("/res/searchIcon.png")), 20));
		lblSearchPic.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearchPic.setBounds(1, 1, 20, 20);
		leftPanel.add(lblSearchPic);

		rightPanel = new JPanel(); //JPanel containing components on the right half of the JFrame 
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setBorder(new LineBorder(Color.DARK_GRAY));
		rightPanel.setBounds(390, 41, 394, 710);
		rightPanel.setLayout(null);
		getContentPane().add(rightPanel);

		JLabel lblAddAStudent = new JLabel("Add a Student: "); //JLabel containing contextual informational text
		lblAddAStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAStudent.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblAddAStudent.setBounds(11, 11, 180, 40);
		rightPanel.add(lblAddAStudent);

		JButton btnDeleteStudent = new JButton("Delete Student(s)");
		btnDeleteStudent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int[] indicesToDelete = studentList.getSelectedIndices();
				
				
				for (int i = indicesToDelete.length - 1; i >= 0; i--)
				{
					Student student = filteredStudents.remove(indicesToDelete[i]); // delete from both lists, make sure right indices bc will be different
					allStudents.remove(student);
				}
				windowHandler.saveStudents();
				loadStudentInfoView(null);
				searchStudentField.setText("");
				searchStudentField.requestFocus(); // to trigger searchStudentField's loseFocus event
				btnDeleteStudent.requestFocus();
				
			}
		});
		btnDeleteStudent.setFont(new Font("Arial Black", Font.PLAIN, 15));
		btnDeleteStudent.setBorder(new LineBorder(Color.DARK_GRAY));
		btnDeleteStudent.setBackground(Color.RED);
		btnDeleteStudent.setForeground(Color.WHITE);
		btnDeleteStudent.setBounds(201, 11, 180, 40);
		rightPanel.add(btnDeleteStudent);

		JPanel studentInfoPanel = new JPanel();
		studentInfoPanel.setBackground(Color.WHITE);
		studentInfoPanel.setBorder(new LineBorder(Color.DARK_GRAY));
		studentInfoPanel.setBounds(0, 151, 394, 570);
		rightPanel.add(studentInfoPanel);
		studentInfoPanel.setLayout(null);

		lblStudentName = new JLabel();
		lblStudentName.setFont(new Font("Arial Black", Font.PLAIN, 24));
		lblStudentName.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentName.setBounds(11, 11, 372, 50);
		studentInfoPanel.add(lblStudentName);

		lblStudentLevel = new JLabel();
		lblStudentLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentLevel.setFont(new Font("Arial Black", Font.PLAIN, 16));
		lblStudentLevel.setBounds(11, 71, 373, 30);
		studentInfoPanel.add(lblStudentLevel);

		studentWorksheets = new ArrayList<Worksheet>();

		JList<Worksheet> worksheetList = new JList<Worksheet>();
		worksheetList.setModel(new DefaultListModel<Worksheet>());
		DefaultListModel<Worksheet> worksheetListModel = (DefaultListModel<Worksheet>)worksheetList.getModel();
		for(Worksheet ws : studentWorksheets)
			worksheetListModel.addElement(ws);
		worksheetList.setFont(new Font("Arial Black", Font.PLAIN, 16));
		worksheetList.setBorder(null);
		worksheetList.setBounds(11, 111, 372, 448);

		JScrollPane listScrollPane = new JScrollPane(worksheetList);
		listScrollPane.setBounds(0, 111, 394, 448);
		listScrollPane.setBorder(new LineBorder(Color.DARK_GRAY));
		studentInfoPanel.add(listScrollPane);

		loadAddStudentView();

		
		
		JLabel lblCtrlInfo = new JLabel("Hold Ctrl to select multiple students");
		lblCtrlInfo.setForeground(Color.GRAY);
		lblCtrlInfo.setFont(new Font("Arial", Font.ITALIC, 12));
		lblCtrlInfo.setBounds(178, 752, 199, 14);
		getContentPane().add(lblCtrlInfo);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

	}
	

	private void loadStudentInfoView(Student s)
	{
		if (s != null)
		{
			lblStudentName.setText(s.getLastName() + ", " + s.getFirstName());
			lblStudentLevel.setText("Difficulty: " + s.getLevel());
			studentWorksheets.clear();
			for (Worksheet sheet : s.getWorksheets())
			{
				studentWorksheets.add(sheet);
			}
		} else
		{
			lblStudentName.setText("");
			lblStudentLevel.setText("");
			studentWorksheets.clear();
		}

	}

	private void loadAddStudentView()
	{
		JLabel firstWarning = new JLabel("*Enter a first name.");
		firstWarning.setFont(new Font("Arial Black", Font.PLAIN, 12));
		firstWarning.setForeground(Color.RED);
		firstWarning.setBounds(198, 61, 180, 20); // ****REMOVE
													// firstWarning.setBounds(levelField.getX()
													// + 90, 120, 199, 20);
		firstWarning.setVisible(false);
		rightPanel.add(firstWarning);

		JLabel lastWarning = new JLabel("*Enter a last name.");
		lastWarning.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lastWarning.setForeground(Color.RED);
		lastWarning.setBounds(198, 91, 180, 20); // ****REMOVE
		lastWarning.setVisible(false);
		rightPanel.add(lastWarning);

		JLabel diffWarning = new JLabel("*Enter a max 3-digit level.");
		diffWarning.setFont(new Font("Arial Black", Font.PLAIN, 12));
		diffWarning.setForeground(Color.RED);
		diffWarning.setBounds(198, 121, 180, 20); // ****REMOVE
		diffWarning.setVisible(false);
		rightPanel.add(diffWarning);

		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstName.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblFirstName.setBounds(7, 61, 100, 20);
		rightPanel.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblLastName.setBounds(7, 91, 100, 20);
		rightPanel.add(lblLastName);

		JLabel lblLevel = new JLabel("Level: ");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblLevel.setBounds(7, 121, 100, 20);// 151
		rightPanel.add(lblLevel);

		firstNameField = new JTextField();
		firstNameField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				lastNameField.requestFocus();

			}
		});
		firstNameField.setBounds(108, 61, 86, 20);
		rightPanel.add(firstNameField);
		firstNameField.setColumns(10);

		lastNameField = new JTextField();
		lastNameField.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				levelField.requestFocus();
			}
		});
		lastNameField.setBounds(108, 91, 86, 20);
		rightPanel.add(lastNameField);
		lastNameField.setColumns(3);

		levelField = new JTextField();
		levelField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (levelField.getText().length() > 3)
				{
					levelField.setText(levelField.getText().substring(0, 3));
					diffWarning.setVisible(true);
				} else if (e.getKeyCode() != KeyEvent.VK_ENTER)
					diffWarning.setVisible(false);
			}
		});
		levelField.setBounds(108, 121, 86, 20);
		levelField.addActionListener(new ActionListener() // make capitalization
															// constant, aa zz
															// AA ZZ
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean valid = true;
				String newStudentFirst = firstNameField.getText();
				String newStudentLast = lastNameField.getText();
				int newStudentDiff = -1;

				firstWarning.setVisible(false);
				lastWarning.setVisible(false);
				diffWarning.setVisible(false);
				if (newStudentFirst.equals(""))
				{
					valid = false;
					firstWarning.setVisible(true);
				}
				if (newStudentLast.equals(""))
				{
					valid = false;
					lastWarning.setVisible(true);
				}
				try
				{
					newStudentDiff = Integer.parseInt(levelField.getText());
				} catch (NumberFormatException exception)
				{
					diffWarning.setVisible(true);
					valid = false;
				}

				if (valid)
				{
					Student newStudent = new Student(newStudentFirst, newStudentLast, newStudentDiff);

					int indexToInsert = 0;
					while (indexToInsert < allStudents.size()
							&& allStudents.get(indexToInsert).compareTo(newStudent) <= 0)
						indexToInsert++;
					allStudents.add(indexToInsert, newStudent);
					
					searchStudentField.setText("");
					searchStudentField.requestFocus(); // to trigger searchStudentField's loseFocus event
					firstNameField.requestFocus();     // to trigger searchStudentField's loseFocus event
					windowHandler.saveStudents();
					firstNameField.setText("");
					lastNameField.setText("");
					levelField.setText("");
					
					firstWarning.setVisible(false);
					lastWarning.setVisible(false);
					diffWarning.setVisible(false);
				} else
				{
					if (firstWarning.isVisible())
					{
						firstNameField.requestFocus();
						firstNameField.selectAll();
					} else if (lastWarning.isVisible())
					{
						lastNameField.requestFocus();
						lastNameField.selectAll();
					} else
						levelField.selectAll();
				}
			}
		});
		rightPanel.add(levelField);

		firstNameField.requestFocus();

	}

	public JList<Student> getStudentList()
	{
		return studentList;
	}
}
