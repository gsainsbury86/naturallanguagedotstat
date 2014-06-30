package naturallanguagedotstat.test;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import naturallanguagedotstat.Service;

public class LocalTest {

	public static boolean debug = false;
	public static boolean test = false;
	public static boolean localLoad = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{

		debug = true;
		localLoad = true;


		/* The following queries fail for various as it does not successfully identify the region....
		
		*/

		// The following queries are correct and now should be moved to UnitTester 
		/*
		 */

		printQueryResult("What is the median income for families in Braidwood?") ;



	}
	
	public static void printQueryResult( String str) throws IOException, ClassNotFoundException, SQLException{
		Service service = new Service();

		JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(str).getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object.getInt("result")+ " <-- " + str +"\n");
	}

}
