import java.util.ArrayList;

public class Student implements Comparable
{
	private String firstName, lastName;
	private int level;
	private ArrayList<Worksheet> worksheets;

	public Student(String first, String last, int level) //creates a new Student with no worksheets
	{
		firstName = first;
		lastName = last;
		this.level = level;
		worksheets = new ArrayList<Worksheet>();
	}

	public void addScore(int score, int worksheetLevel) //alters the Student's level based on the score and level of the worksheet
	{
		if (score >= 6)
			level = Math.max(worksheetLevel + score - 5, level);
		else
			level = worksheetLevel + score - 5;
	}

	public String getFirstName() //getter
	{
		return firstName;
	}

	public String getLastName() //getter
	{
		return lastName;
	}

	public int getLevel() //getter
	{
		return level;
	}

	@Override
	public String toString() //toString Method to be used when saving
	{
		return lastName + ", " + firstName + " - " + level;
	}

	@Override
	public int compareTo(Object other) //overriding compareTo method to compare Student's based on name and level
	{
		return (lastName + firstName + level).compareTo(
				((Student) other).getLastName() + ((Student) other).getFirstName() + ((Student) other).getLevel());
	}

	public ArrayList<Worksheet> getWorksheets() //getter
	{
		return worksheets;
	}

}
