package naturallanguagedotstat.test;

import static org.hamcrest.CoreMatchers.is;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;
import org.junit.rules.ErrorCollector;

import naturallanguagedotstat.Service;

public class LocalTest {

	public static boolean debug = false;
	public static boolean localLoad = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		debug = true;
		localLoad = true;


		/* The following queries fail for various as it does not successfully identify the region....
		service.query("How many women in Braidwood aged 25-30 are divorced"); //150
		service.query("What is the female population of Sandy Bay aged 20-24?");
		service.query("What is the population of Sandy Bay women aged 20-24?");

		service.query("How many people aged 20-25 do not have need assistance in Australia?");
		service.query("How many women aged 45-50 did voluntary work in New South Wales?"); //102622

		service.query("How many people aged 35-40 in Goulburn were employed in total?"); //2047  //can't have the word total in it.
		service.query("How many people in Goulburn were employed part time?"); 

		 */


		/* The following queries are correct and now should be moved to UnitTester 

		 */

		System.out.println( getQueryResult("How many women aged 20-25 in Tasmania are labourers?"));	// 711


	}
	
	public static String getQueryResult( String str) throws IOException, ClassNotFoundException{
		Service service = new Service();

		JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(str).getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		
		return  object.getString("result");
	}

}
