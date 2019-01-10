import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class NoticeWindow extends JFrame
{
	
	public NoticeWindow(String message) //creates a new NoticeWindow displayed the notification message
	{
		getContentPane().setLayout(null);
		
		JLabel errorMsg = new JLabel(message); //JLabel with the notification message text
		errorMsg.setHorizontalAlignment(SwingConstants.CENTER);
		errorMsg.setBounds(10, 11, 264, 35);
		getContentPane().add(errorMsg);
		setTitle("Notice");

		JButton btnNewButton = new JButton("OK"); //JButton to close the window
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		btnNewButton.setBounds(105, 70, 70, 23);
		getContentPane().add(btnNewButton);

		setAlwaysOnTop(true);
		
		getContentPane().setBackground(Color.WHITE);
		setSize(300, 140);
		setVisible(true);
		setLocationRelativeTo(null);
	}

}
