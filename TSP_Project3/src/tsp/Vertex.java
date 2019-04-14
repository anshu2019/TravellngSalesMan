package tsp;

/**
 * This class is vertex of the graph
 * @author Anshu Anand
 *
 */
public class Vertex {

	public String lat;
	public String lng;
	public String date;
	public String street;
	public String offence;
	public String xCord;
	public String yCord;
	public String tract;

	/**
	 * constructor of the vertex
	 * @param data
	 */
	public Vertex(String data) {
		String[] d = data.split(",");

		xCord = d[0];
		yCord = d[1];
		street = d[3];
		offence = d[4];
		date = d[5];
		tract = d[6];
		lat = d[7];
		lng = d[8];
	}

	/**
	 * This is overridden toString method
	 */
	@Override
	public String toString() {
		String delim = ",";
		String rs = xCord + delim + yCord + delim + street + delim + offence + delim + date + delim+tract+delim + lat + delim + lng;

		return rs;

	}
}
