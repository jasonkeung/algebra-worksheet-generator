import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class ScoreChangeWindow extends JFrame
{
	private JTextField textField;
	private StudentScoresWindow studentScoresWin;

	public ScoreChangeWindow(StudentScoresWindow win)
	{
		studentScoresWin = win;
		getContentPane().setLayout(null);

		JLabel lblScore = new JLabel("/ 6");
		lblScore.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(140, 41, 30, 20);
		getContentPane().add(lblScore);
		
		JLabel scoreWarning = new JLabel("*Enter a score from 1 to 6.");
		scoreWarning.setFont(new Font("Arial Black", Font.PLAIN, 12));
		scoreWarning.setForeground(Color.RED);
		scoreWarning.setVisible(false);
		scoreWarning.setBounds(50, 66, 180, 20);
		getContentPane().add(scoreWarning);

		JButton btnChange = new JButton("Change");
		btnChange.setFont(new Font("Arial Black", Font.PLAIN, 12));
		btnChange.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int score = Integer.parseInt(textField.getText());
					if(score >= 0 && score <= 6)
					{
						studentScoresWin.getSelectedWorksheet().setScore(score);
						dispose();
					}
					else
						throw new NumberFormatException();
				} catch (NumberFormatException exception)
				{
					scoreWarning.setVisible(true);
				}
			}
		});
		btnChange.setBounds(95, 90, 90, 20);
		getContentPane().add(btnChange);

		setAlwaysOnTop(true);

		getContentPane().setBackground(Color.WHITE);

		textField = new JTextField();
		textField.setFont(new Font("Arial Black", Font.PLAIN, 14));
		textField.setBounds(110, 41, 30, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblWorksheet = new JLabel();
		lblWorksheet.setText(studentScoresWin.getSelectedWorksheet().toString());
		lblWorksheet.setBounds(10, 11, 264, 20);
		

		setTitle("Worksheet Score Editor");
		getContentPane().add(lblWorksheet);
		setSize(300, 160);
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
