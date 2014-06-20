package naturallanguagedotstat.test;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import naturallanguagedotstat.Service;

public class LocalTest {

	public static boolean debug = false;
	public static boolean test = false;
	public static boolean localLoad = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		debug = true;
		localLoad = true;


		/* The following queries fail for various as it does not successfully identify the region....

		// printQueryResult("How many people aged 35-40 in Goulburn were employed in total?") ; //2047  //can't have the word total in it.

		// "http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.2.STE.2.A/ABS",			// printQueryResult("How many people in Victoria were born in Victoria?") ; 
		
		printQueryResult("How many men in Tasmania were born in Vietnam?") ; //129
		*/


		/* 
		// The following queries are correct and now should be moved to UnitTester 
		

		 */
		



	}
	
	public static void printQueryResult( String str) throws IOException, ClassNotFoundException{
		Service service = new Service();

		JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(str).getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object.getInt("result")+ " <-- " + str);
	}

}
