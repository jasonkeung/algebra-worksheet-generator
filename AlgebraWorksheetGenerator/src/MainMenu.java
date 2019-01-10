import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenu extends JFrame
{
	private WindowHandler windowHandler;

	public MainMenu(WindowHandler handler) //creates a small window for the MainMenu
	{
		windowHandler = handler;
		initLabels();
		initButtons();
		initWindow(653, 243);
	}

	private void initWindow(int w, int h) //initializes JFrame
	{
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setSize(653, 293);
		setTitle("Algebra Worksheet Generator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void initLabels() //adds JLabel to the JFrame content pane
	{
		JLabel welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setForeground(Color.BLACK);
		welcomeLabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
		welcomeLabel.setBounds(10, 18, 170, 28);
		getContentPane().add(welcomeLabel);
	}

	private void initButtons() //adds JButtons to the JFrame content pane
	{

		JButton btnScores = new JButton(); //JButton to show the ScoreWindow
		btnScores.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (windowHandler.getScoreWindow() == null)
					windowHandler.setScoreWindow(new ScoreWindow(windowHandler));
				windowHandler.getScoreWindow().setLocationRelativeTo(null);
				setVisible(false);
				windowHandler.getScoreWindow().setVisible(true);
			}
		});
		Utils.makeBtnSimple(btnScores);
		btnScores.setBorder(null);
		btnScores.setBounds(0, 40, 215, 215);
		getContentPane().add(Utils.getButtonWithIcon(btnScores, "scores"));

		JButton btnStudents = new JButton(); //JButton to show the StudentWindow
		btnStudents.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (windowHandler.getStudentWindow() == null)
					windowHandler.setStudentWindow(new StudentWindow(windowHandler));
				windowHandler.getStudentWindow().setLocationRelativeTo(null);
				setVisible(false);
				windowHandler.getStudentWindow().getStudentList().clearSelection();
				windowHandler.getStudentWindow().setVisible(true);

			}
		});
		Utils.makeBtnSimple(btnStudents);
		btnStudents.setBorder(null);
		btnStudents.setBounds(215, 40, 215, 215);
		getContentPane().add(Utils.getButtonWithIcon(btnStudents, "students"));

		JButton btnGenerate = new JButton(); //JButton to show the GenerateWindow
		btnGenerate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				if (windowHandler.getGenerateWindow() == null)
					windowHandler.setGenerateWindow(new GenerateWindow(windowHandler));
				windowHandler.getGenerateWindow().setLocationRelativeTo(null);
				setVisible(false);
				windowHandler.getGenerateWindow().setVisible(true);
			}
		});
		Utils.makeBtnSimple(btnGenerate);
		btnGenerate.setBorder(null);
		btnGenerate.setBounds(430, 40, 215, 215);
		getContentPane().add(Utils.getButtonWithIcon(btnGenerate, "worksheet"));
	}

}
