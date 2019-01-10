import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class Utils
{
	public static ImageIcon scaleImageIcon(ImageIcon icon, int width, int height)
	{
		Image old = icon.getImage();
		Image scaled = old.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}
	
	public static ImageIcon scaleImageIcon(ImageIcon icon, double scale)
	{
		Image old = icon.getImage();
		Image scaled = old.getScaledInstance((int)(old.getWidth(null) * scale), (int)(old.getHeight(null) * scale), Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}
	
	
	public static ImageIcon scaleImageIcon(ImageIcon icon, int square)
	{
		Image old = icon.getImage();
		if(old.getWidth(null) > old.getHeight(null))
			return scaleImageIcon(icon, (double)square/Math.max(old.getWidth(null), old.getHeight(null)));
		else
			return scaleImageIcon(icon, (double)square/old.getHeight(null));
	}
	
	//
	public static JButton getButtonWithIcon(JButton button, String file) //no .png, assumed
	{
		button.setIcon(Utils.scaleImageIcon(new ImageIcon(Utils.class.getResource("/res/" + file + ".png")), Math.min(button.getWidth(), button.getHeight() - 6)));
		return button;
	}
	
	public static ImageIcon getImageIcon(String fileName)
	{
		return new ImageIcon(Utils.class.getResource("/res/" + fileName + ".png"));
	}
	
	public static void makeBtnSimple(JButton b)
	{
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorder(new LineBorder(Color.BLACK));
	}
}
