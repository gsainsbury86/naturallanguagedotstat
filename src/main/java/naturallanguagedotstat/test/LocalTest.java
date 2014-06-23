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
		
		//"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.0.AUS.0.A/ABS",		//"How many women have never been married?",	2753427
		//"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.1.SA2.101021007.A/ABS",		//"How many women have never married in Goulburn?", xx
//		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.A35.4.0.AUS.0.A/ABS",			// How many indigenous 35-40 year olds are there?", 34072"
// "how many men were at home on the night of the Census in Goulburn",
 * 
		*/


		/* 
		// The following queries are correct and now should be moved to UnitTester 

		printQueryResult("How many indigenous men 35-40 years old are there?") ; // 7


		 * 
		 */


		printQueryResult("What is the population of Lindisfarne?") ; // 7


	}
	
	public static void printQueryResult( String str) throws IOException, ClassNotFoundException{
		Service service = new Service();

		JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(str).getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object.getInt("result")+ " <-- " + str);
	}

}
