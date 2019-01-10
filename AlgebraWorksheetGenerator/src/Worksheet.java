import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Worksheet
{
	private Student student;
	private int level;
	private ArrayList<String> problems;
	private Calendar cal;
	private int id;
	private int score;

	public Worksheet(Student s) //creates a new worksheet for Student s, intended to create new Worksheets
	{
		cal = Calendar.getInstance();
		student = s;
		level = s.getLevel();
		problems = new ArrayList<String>();
		id = generateId();
		score = -1;

		generateWorksheet();
		this.student.getWorksheets().add(this);
		saveAs(s.getLastName() + ", " + s.getFirstName() + " - " + level);
	}

	public Worksheet(Student s, int lvl, Calendar c, ArrayList<String> probs) //creates a new Worksheet for Student s, intended to instantiate a Worksheet from saved data
	{
		cal = Calendar.getInstance();
		cal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
		student = s;
		level = lvl;
		cal = c;
		problems = probs;
		id = generateId();

	}

	/**
	 * @param fileName
	 *            - exclude ".txt" from file name
	 */
	private void saveAs(String fileName)
	{
		try
		{
			String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator
					+ fileName + ".txt";
			File worksheetFile = new File(desktopPath);

			if (worksheetFile.exists())
			{
				worksheetFile = new File(getUniqueFileString(desktopPath, 2));
			}

			FileWriter fileWriter = new FileWriter(worksheetFile);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			String header = student.getFirstName() + " " + student.getLastName();
			printWriter.printf("%-42s", header);
			printWriter.printf("%42s", "Worksheet #" + id);
			printWriter.println();

			for (int i = 0; i < problems.size(); i++)
			{
				printWriter.printf("%-42s", problems.get(i));
				i++;
				printWriter.printf("%-42s", problems.get(i));
				for (int q = 0; q < 9; q++)
				{
					printWriter.println("");
				}

			}

			fileWriter.close();
			printWriter.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void printWorksheetToFile(PrintWriter printWriter)
	{
		printWriter.println(level);
		printWriter.println(cal.get(Calendar.MONTH) + 1);
		printWriter.println(cal.get(Calendar.DAY_OF_MONTH));
		printWriter.println(cal.get(Calendar.YEAR));
		for (String s : problems)
			printWriter.println(s);
	}

	private void generateWorksheet()
	{
		char[] operations = { '+', '-', 'x', '/' };
		char[] variables = { 'a', 'b', 'c', 'd', 'w', 'x', 'y', 'z' };
		for (int i = 1; i <= 6; i++)
		{
			String problem = null;
			char var = variables[(int) (Math.random() * 8)];
			int operation = (int) (Math.random() * 2);
			if (level <= 5)
			{
				int number = (int) (Math.random() * level * 30);
				int offset = (int) (Math.random() * number + 1);
				problem = var + " " + operations[operation] + " " + offset + " = " + number;

			} else if (level <= 10)
			{
				operation += 2;
				int factors[] = returnFactorPair();
				int factor = factors[(int) (Math.random() * 4) + 1];
				if (operation == 2)
					problem = factor + "" + var + " = " + factors[0];
				else
					problem = var + "/" + factor + " = " + factors[0] / factor;

			} else if (level <= 15)
			{
				int offset = (int) (Math.random() * (level - 10) * 30); // constant offset
				int operation2 = (int)(Math.random() * 2) + 2; // mult or divide
				int factors[] = returnFactorPair(); // factors
				int factor = factors[(int) (Math.random() * 4) + 1]; // factor using
				
				if(operation == 0 && operation2 == 2)
					problem = factor + "" + var + operations[operation] + offset + " = " + factors[0] + offset;
				else if(operation == 0 && operation2 == 3)
					problem = var + "/" + factor + operations[operation] + offset + " = " + factors[0] / factor + offset;
				else if(operation == 1 && operation2 == 2)
					problem = factor + "" + var + operations[operation] + offset + " = " + (factors[0] - offset);
				else
					problem = var + "/" + factor + operations[operation] + offset + " = " + (factors[0] / factor - offset);
			}
			problems.add(problem);
		}

	}

	private int[] returnFactorPair() // make not just 1-3 / 1-5
	{
		int[] factors = new int[5];
		factors[0] = 1;
		for(int i = 1; i <= 4; i++)
		{
			int factor = (int) (Math.random() * (level % 5)) + 2;
			factors[0] *= factor;
			factors[i] = factor;
		}
		return factors;
	}

	private String getUniqueFileString(String fileString, int num)
	{
		String tempFileString = fileString.replaceAll(".txt", " (" + num + ").txt");
		if (new File(tempFileString).exists())
		{
			return getUniqueFileString(fileString, num + 1);
		}
		return tempFileString;

	}

	public String toString()
	{
		if (score != -1)
			return getDateAssigned() + " - level " + level + ", " + score + " / 6";
		else
			return getDateAssigned() + " - level " + level + " - not done";
	}

	private int generateId()
	{
		Student s = student;
		String data = s.getFirstName() + s.getLastName() + level + cal.get(Calendar.MONTH)
				+ cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.YEAR);
		return Math.abs(data.hashCode() % 10000);
	}

	public int getId()
	{
		return id;
	}

	public String getDateAssigned()
	{
		return cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
	}
	
	public void setScore(int newScore)
	{
		score = newScore;
	}
}
