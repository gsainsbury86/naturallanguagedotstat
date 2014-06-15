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

		// printQueryResult("How many people aged 35-40 in Goulburn were employed in total?") ; //2047  //can't have the word total in it.
		*/


		/* 
		// The following queries are correct and now should be moved to UnitTester 
		
		//B03


		// B42
		 */
		printQueryResult("How many women in Sandy Bay work in agriculture?") ; //35

		

	}
	
	public static void printQueryResult( String str) throws IOException, ClassNotFoundException{
		Service service = new Service();

		JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(str).getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object.getString("result")+ " <-- " + str);
	}

}
