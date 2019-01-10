import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.TreeSet;

import javax.swing.DefaultListModel;

public class WindowHandler
{
	private MainMenu menu;
	private StudentWindow studentWindow;
	private GenerateWindow generateWindow;
	private ScoreWindow scoreWindow;
	private NoticeWindow noticeWindow;
	private DefaultListModel<Student> students;
	private HashSet<Worksheet> worksheets;

	public WindowHandler()
	{
		menu = new MainMenu(this);
		studentWindow = null;
		generateWindow = null;
		noticeWindow = null;
		students = new DefaultListModel<Student>();
		worksheets = new HashSet<Worksheet>(10000);
		loadStudents();
	}

	private void loadStudents() // loads students from student data file and
	// adds into studentList sorted
	{
		TreeSet<Student> studentList = new TreeSet<Student>();
		try
		{
			File dataFolder = new File("data");
			if (!dataFolder.exists())
				dataFolder.mkdir();
			File studentsFile = new File("data/students.txt");
			if (!studentsFile.exists())
				studentsFile.createNewFile();
			FileReader fileReader = new FileReader("data/students.txt");
			BufferedReader reader = new BufferedReader(fileReader);
			String tempLine = reader.readLine();
			Student tempStudent = null;
			while (tempLine != null)
			{

				if (tempLine.startsWith("***", 0))
				{
					String lastName = tempLine.substring(3, tempLine.indexOf(", "));
					String firstName = tempLine.substring(tempLine.indexOf(", ") + 2, tempLine.indexOf(" - "));
					int level = Integer.parseInt(tempLine.substring(tempLine.indexOf(" - ") + 3));
					tempStudent = new Student(firstName, lastName, level);
					studentList.add(tempStudent);
					tempLine = reader.readLine();
				} else
				{
					int worksheetLevel = Integer.parseInt(tempLine);
					int month = Integer.parseInt(reader.readLine());
					int day = Integer.parseInt(reader.readLine());
					int year = Integer.parseInt(reader.readLine());
					Calendar calendar = Calendar.getInstance();
					calendar.set(year, month - 1, day);
					ArrayList<String> tempProblems = new ArrayList<String>();
					tempLine = reader.readLine();
					for (int i = 0; i < 6; i++)
					{
						tempProblems.add(tempLine);
						tempLine = reader.readLine();
					}
					tempStudent.getWorksheets().add(new Worksheet(tempStudent, worksheetLevel, calendar, tempProblems));

				}
			}
			for (Student s : studentList)
				students.addElement(s);
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("ERROR");
		}
	}

	/**
	 * Saves with new student line starting with "***" Next line -
	 * numWorksheetsDone Next line(s) - worksheet ID's
	 */
	public void saveStudents()
	{
		try
		{
			File studentFile = new File("data/students.txt");
			if (!studentFile.exists())
			{
				studentFile.createNewFile();
			}
			PrintWriter writer = new PrintWriter("data/students.txt", "UTF-8");
			for (int i = 0; i < students.size(); i++)
			{
				Student tempStudent = students.get(i);
				writer.println("***" + tempStudent);
				for (int j = 0; j < students.get(i).getWorksheets().size(); j++)
				{
					students.get(i).getWorksheets().get(j).printWorksheetToFile(writer);
				}
			}
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public MainMenu getMenu()
	{
		return menu;
	}

	public ScoreWindow getScoreWindow()
	{
		return scoreWindow;
	}

	public void setScoreWindow(ScoreWindow scoreWindow)
	{
		this.scoreWindow = scoreWindow;
	}

	public StudentWindow getStudentWindow()
	{
		return studentWindow;
	}

	public void setStudentWindow(StudentWindow window)
	{
		studentWindow = window;
	}

	public GenerateWindow getGenerateWindow()
	{
		return generateWindow;
	}

	public void setGenerateWindow(GenerateWindow generateWindow)
	{
		this.generateWindow = generateWindow;
	}

	public DefaultListModel<Student> getStudentModel()
	{
		return students;
	}
	
	public ArrayList<Student> getStudents()
	{
		ArrayList<Student> result = new ArrayList<Student>();
		for (Object s : students.toArray())
			result.add((Student) s);
		return result;
	}

	public void setStudents(DefaultListModel<Student> newList)
	{
		students = newList;
	}

	public NoticeWindow getNoticeWindow()
	{
		return noticeWindow;
	}

	public void setNoticeWindow(NoticeWindow noticeWindow)
	{
		this.noticeWindow = noticeWindow;
	}

	public HashSet<Worksheet> getWorksheets()
	{
		return worksheets;
	}

	public void setWorksheets(HashSet<Worksheet> worksheets)
	{
		this.worksheets = worksheets;
	}
}
