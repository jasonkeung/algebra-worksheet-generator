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

public class GenerateWindow extends JFrame
{
	private WindowHandler windowHandler;
	private DefaultListModel<Student> students;
	private JList<Student> studentList;

	public GenerateWindow(WindowHandler handler) //creates a window where the user can select a student to generate a worksheet for
	{
		setResizable(false);
		windowHandler = handler;
		students = handler.getStudentListModel();
		initWindow(400, 740);
	}

	private void initWindow(int w, int h) //initializes the JFrame and adds all the Java Swing components to the content pane
	{
		setSize(417, 740);
		getContentPane().setBackground(Color.WHITE);
		setTitle("Generate a Worksheet");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JButton btnBack = new JButton(); //button that returns to the MainMenu
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

		studentList = new JList<Student>(); //JList of students for the user to choose from
		studentList.setModel(students);
		studentList.setBorder(null);
		studentList.setFont(new Font("Arial Black", Font.PLAIN, 20));
		studentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(studentList); //JScrollPane to allow for scrolling through the studentList
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPane.setViewportBorder(new LineBorder(Color.DARK_GRAY));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(11, 71, 390, 580);
		getContentPane().add(scrollPane);

		JLabel lblStudents = new JLabel("Select student(s) to generate worksheet(s) for: "); //JLabel text with instructions
		lblStudents.setForeground(Color.BLACK);
		lblStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudents.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblStudents.setBounds(10, 41, 390, 20);
		getContentPane().add(lblStudents);

		JButton btnGenerate = new JButton("Generate!"); //button to generate a new worksheet for the selected student
		Utils.makeBtnSimple(btnGenerate);
		btnGenerate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int[] indicesToGenerate = studentList.getSelectedIndices();
				for (int i = 0; i < indicesToGenerate.length; i++)
				{
					new Worksheet(studentList.getModel().getElementAt(indicesToGenerate[i]));
				}
				
				windowHandler.saveStudents();
				if(windowHandler.getNoticeWindow() == null)
					windowHandler.setNoticeWindow(new NoticeWindow("New worksheet(s) on desktop!"));
				else
					windowHandler.getNoticeWindow().setVisible(true);
			}
		});
		btnGenerate.setFont(new Font("Arial Black", Font.PLAIN, 14));
		btnGenerate.setBounds(106, 661, 200, 40);
		getContentPane().add(btnGenerate);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	
}
