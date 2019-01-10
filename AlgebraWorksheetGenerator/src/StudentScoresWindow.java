import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class StudentScoresWindow extends JFrame
{
	private Student student;
	private ScoreWindow scoreWindow;
	private JList<Worksheet> worksheetList;

	public StudentScoresWindow(Student s, ScoreWindow sw)
	{
		student = s;
		scoreWindow = sw;
		initWindow(400, 650);
	}

	private void initWindow(int w, int h)
	{
		JButton btnBack = new JButton(); // JButton to return to the MainMenu
		btnBack.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent arg0)
			{
				scoreWindow.setLocationRelativeTo(null);
				setVisible(false);
				scoreWindow.setVisible(true);
			}
		});
		Utils.makeBtnSimple(btnBack);
		btnBack.setBorder(null);
		btnBack.setBounds(10, 6, 83, 30);
		btnBack.setIcon(Utils.scaleImageIcon(new ImageIcon(GenerateWindow.class.getResource("/res/backButton.png")),
				btnBack.getWidth(), btnBack.getHeight()));
		getContentPane().add(btnBack);

		worksheetList = new JList<Worksheet>();
		worksheetList.setModel(new DefaultListModel<Worksheet>());
		DefaultListModel<Worksheet> worksheetListModel = (DefaultListModel<Worksheet>) worksheetList.getModel();
		for (Worksheet ws : student.getWorksheets())
			worksheetListModel.addElement(ws);
		worksheetList.setBorder(null);
		worksheetList.setFont(new Font("Arial Black", Font.PLAIN, 16));
		worksheetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(worksheetList); // JScrollPane
																	// to allow
																	// for
																	// scrolling
																	// through
																	// the
																	// scoreList
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPane.setViewportBorder(new LineBorder(Color.DARK_GRAY));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 76, 374, 496);
		getContentPane().add(scrollPane, BorderLayout.NORTH);

		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		JLabel label = new JLabel("Select a worksheet to add/change a score: ");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial Black", Font.PLAIN, 14));
		label.setBounds(10, 46, 374, 20);
		getContentPane().add(label);

		JLabel lblSelectAWorksheet = new JLabel("*Select a worksheet."); // JLabel with red warning text to be displayed when necessary
		lblSelectAWorksheet.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblSelectAWorksheet.setForeground(Color.RED);
		lblSelectAWorksheet.setBounds(136, 631, 137, 20);
		lblSelectAWorksheet.setVisible(false);
		getContentPane().add(lblSelectAWorksheet);

		JButton btnSelect = new JButton("Select Worksheet");
		StudentScoresWindow thisWindow = this;
		btnSelect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (worksheetList.getSelectedValue() != null)
				{
					ScoreChangeWindow scoreChange = new ScoreChangeWindow(thisWindow);
					lblSelectAWorksheet.setVisible(false);
				} else
					lblSelectAWorksheet.setVisible(true);
			}
		});
		btnSelect.setFont(new Font("Arial Black", Font.PLAIN, 14));
		btnSelect.setBounds(100, 580, 200, 40);
		getContentPane().add(btnSelect);

		setTitle(student.getLastName() + ", " + student.getFirstName() + " - Assigned Worksheets");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(w, h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		if (worksheetListModel.getSize() == 0)
		{
			NoticeWindow notice = new NoticeWindow("Student has no assigned worksheets.");
		}
	}
	
	public Worksheet getSelectedWorksheet()
	{
		return worksheetList.getSelectedValue();
	}
}
