import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.concurrent.Semaphore;

public class UsingJDBC {

	public static void main (String[] args) {
		printTableOne();
		System.out.println();
		printTableTwo();
	}
	
	//print two column table of className and number of students	
	public static void printTableOne()
	{
		//Hashtable<String, Integer> map = new Hashtable<String, Integer>();
		
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//String[] classes = {"ART123", "REL100", "ECO966", "BUS456"};
			conn = DriverManager.getConnection("jdbc:mysql://localhost/StudentGrades2?","root","OceanHawks3#");
			st = conn.createStatement();
			System.out.printf("%-10s  %-20s  %n", "ClassName", "Number of Students");
			
			rs = st.executeQuery("SELECT ClassName, COUNT(*) as Count FROM Grades \n"
					+ "group by ClassName \n"
					+ "ORDER BY Count");
			
			//int size = 0;
			
			while (rs.next())
			{
				System.out.printf("%-10s  %-20s  %n", rs.getString(1), rs.getInt(2));
			}
			//map.put(classes[i], size);	
			
			//System.out.printf("%-15s  %-20s  %n", classes[i], size);
			
			/*for (int i = 0; i < classes.length; i++)
			{
				ps = conn.prepareStatement("SELECT ClassName, COUNT (ClassName) FROM Grade", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, classes[i]); // set first variable in prepared statement
				rs = ps.executeQuery();
				int size = 0;
				if (rs != null) 
				{
					rs.last();    // moves cursor to the last row
					size = rs.getRow(); // get row id 
				}
				//map.put(classes[i], size);	
				
				System.out.printf("%-15s  %-20s  %n", classes[i], size);
			}*/
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		//return map;
	}
	
	//print three column table of classname, name, and grade
	public static void printTableTwo()
	{
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/StudentGrades2?","root","OceanHawks3#");
			st = conn.createStatement();
			System.out.printf("%-10s  %-20s  %-20s %n", "ClassName", "Name" , "Grade");
			
			rs = st.executeQuery("SELECT g.ClassName, s.Name, g.Grade FROM Grades g, StudentInfo s "
					+ "WHERE s.SID = g.SID");
			
			while (rs.next())
			{
				System.out.printf("%-10s  %-20s  %-20s %n", rs.getString(1) , rs.getString(2), rs.getString(3));
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	
}