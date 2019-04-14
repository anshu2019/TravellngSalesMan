package tsp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * This a a Helper class , which contains commonly used methods
 * @author Anshu Anand
 *
 */
public class Helper {
	public static String sdf = "dd-MMM-yyyy";

	/**
	 * This method checks , if a date is within a valid range
	 * @Precondition all the three date should be present
	 * @Postcondition validity of date is given
	 * @param testDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isWithinRange(Date testDate, Date startDate, Date endDate) {

		return testDate.compareTo(startDate) >= 0 && testDate.compareTo(endDate) <= 0;

	}

	/**
	 * This method converts string value of date to date format
	 * @Precondition String format date exist
	 * @Postcondition Date is formated
	 * @param dt
	 * @return
	 * @throws ParseException
	 */
	public static Date getFormatedDate(String dt) throws ParseException {
		Date DF;
		DateFormat ToDF = new SimpleDateFormat("MM/dd/yyyy");
		ToDF.setLenient(false); // this is important!
		Date ToDate = ToDF.parse(dt);
		String dateStringTo = new SimpleDateFormat(sdf).format(ToDate);
		DateFormat ToDF1 = new SimpleDateFormat(sdf);
		DF = ToDF1.parse(dateStringTo);
		return DF;
	}

	/**
	 * This method finds the distance between two locations in feet
	 * @Precondition Coordinates for both location exists
	 * @Postcondition distance calculated in feet
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double getDistanceInFeet(String x1, String y1, String x2, String y2) {
		double dist = 0;
		double dx1 = Double.parseDouble(x1);
		double dx2 = Double.parseDouble(x2);
		double dy1 = Double.parseDouble(y1);
		double dy2 = Double.parseDouble(y2);
		dist = Math.sqrt((dx1 - dx2)*(dx1 - dx2)+ (dy1 - dy2)*(dy1 - dy2));
		return dist;
	}
	
	/**
	 * This method finds the distance between two locations in miles
	 * @Precondition Coordinates for both location exists
	 * @Postcondition distance calculated in miles
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double getDistanceInMile(String x1, String y1, String x2, String y2) {
		double dist = 0;		
		dist = getDistanceInFeet(x1,y1,x2,y2);
		dist = dist * 0.00018939;
		return dist;
	}
	
	/**
	 * This method reads file froma directory with date in a valid range
	 * @Precondition File path and date range are valid
	 * @Postcondition data is read and stored in a linkedlist
	 * @param path
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static LinkedList<String> readDataFromFile(String path,Date startDate,Date endDate) throws IOException, ParseException {
		// Open the file
		FileInputStream fstream = null;
		LinkedList<String> list = new LinkedList<String>();
		int iterCount = 0;
		try {
			fstream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			if (iterCount == 0) {
				iterCount++;
				continue;
			}
			String data = strLine;
			String[] d = data.split(",");
			Date crimeDate = getFormatedDate(d[5]);

			if (isWithinRange(crimeDate,startDate,endDate)) {
				list.add(strLine);
			}
			iterCount++;
		}

		// Close the input stream
		br.close();

		return list;
	}
	
	/**
	 * This method returns the factorial
	 * @Precondition number is provided
	 * @Postcondition Factorial is calculated
	 * @param n
	 * @return
	 */
	public static int getFactorial(int n) {
		if (n == 0) {
	           return 1;
	       } else {
	           return n * getFactorial(n - 1);
	       }
		
	}
}
