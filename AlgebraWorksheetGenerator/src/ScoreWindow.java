import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ScoreWindow extends JFrame
{
	private WindowHandler windowHandler;
	private StudentScoresWindow studentScoresWindow;
	private DefaultListModel<Student> students;
	private JList<Student> studentList;

	public ScoreWindow(WindowHandler handler) // creates a window where the user
												// can select a student to add a
												// score for
	{
		windowHandler = handler;
		studentScoresWindow = null;
		students = handler.getStudentModel();
		initWindow(400, 740);
	}

	private void initWindow(int w, int h) // initializes the JFrame and adds all
											// the Java Swing components to the
											// content pane
	{
		setSize(w, h);
		getContentPane().setBackground(Color.WHITE);
		setTitle("Generate a Worksheet");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);

		JButton btnBack = new JButton(); // JButton to return to the MainMenu
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
		btnBack.setIcon(Utils.scaleImageIcon(new ImageIcon(GenerateWindow.class.getResource("/res/backButton.png")),
				btnBack.getWidth(), btnBack.getHeight()));
		getContentPane().add(btnBack);

		studentList = new JList<Student>(); // JList of students for the user to
											// choose from
		studentList.setModel(students);

		studentList.setBorder(null);
		studentList.setFont(new Font("Arial Black", Font.PLAIN, 20));
		studentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane scrollPane = new JScrollPane(studentList); // JScrollPane to
																// allow for
																// scrolling
																// through the
																// studentList
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPane.setViewportBorder(new LineBorder(Color.DARK_GRAY));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(11, 72, 373, 549);
		getContentPane().add(scrollPane);

		JLabel lblStudents = new JLabel("Select a student to add/change a score: "); // JLabel
																						// with
																						// instruction
																						// text
		lblStudents.setForeground(Color.BLACK);
		lblStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudents.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblStudents.setBounds(10, 41, 374, 20);
		getContentPane().add(lblStudents);

		JLabel lblSelectAStudent = new JLabel("*Select a student."); // JLabel
																		// with
																		// red
																		// warning
																		// text
																		// to be
																		// displayed
																		// when
																		// necessary
		lblSelectAStudent.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblSelectAStudent.setForeground(Color.RED);
		lblSelectAStudent.setBounds(135, 631, 137, 20);
		lblSelectAStudent.setVisible(false);
		getContentPane().add(lblSelectAStudent);

		JButton btnSelect = new JButton("Select Student"); // JButton confirming
															// the user's inputs
															// and adding a
															// score for the
															// student
		Utils.makeBtnSimple(btnSelect); // displays warning texts for any
										// unacceptable user inputs
		ScoreWindow thisWindow = this;
		btnSelect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (studentList.getSelectedValue() != null)
				{
					lblSelectAStudent.setVisible(false);
					studentScoresWindow = new StudentScoresWindow(studentList.getSelectedValue(), thisWindow);
					studentScoresWindow.setLocationRelativeTo(null);
					setVisible(false);
					studentScoresWindow.setVisible(true);
					lblSelectAStudent.setVisible(false);
				} else
					lblSelectAStudent.setVisible(true);
			}
		});
		btnSelect.setFont(new Font("Arial Black", Font.PLAIN, 14));
		btnSelect.setBounds(100, 661, 200, 40);
		getContentPane().add(btnSelect);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
