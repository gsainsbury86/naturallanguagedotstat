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

		
		printQueryResult("How many men in Tasmania were born in Vietnam?") ; //129
		*/


		/* 
		// The following queries are correct and now should be moved to UnitTester 
		printQueryResult("How many people whose country of birth is Vietnam live in Tasmania?") ; //267
		printQueryResult("Number of women in Australia born in Australia?") ; //7605247
		printQueryResult("How many people in Tasmania were born in Vietnam?") ; //267
		printQueryResult("Number of women in Australia whose country of birth is also Australia?") ; //7605247
		printQueryResult("How many people in Tasmania were born in Tasmania?") ; 
		

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
