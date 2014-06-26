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
		
		*/

		
		//B03
		printQueryResult("how many people were aged 15-20 were at home on the night of the Census in Eden");	//321

		//B05
		printQueryResult("How many women have never been married?");	//	2753427
		printQueryResult("How many women have never married in Karabar?");	// 1164

		//B06
		printQueryResult("How many men are not married in Karabar?");	// 1257

		//B07
		printQueryResult("How many indigenous men 35-40 years old are there?") ; // 16234
		printQueryResult("How many non indigenous men 35-40 years old are there?") ; // 691051
		printQueryResult("How many men 35-40 years old are not indigenous?") ; // 691051

		//B09
		printQueryResult("How many people in Victoria were born in Australia?") ; //3670934

		//B42
		printQueryResult("How many men in Goulburn were full time employed?") ; // 3726
		printQueryResult("How many men in Goulburn were employed full time?") ; // 3726
		printQueryResult("How many people aged 35-40 in Goulburn were employed in total?") ; //2047  

		
		

		/* 
		// The following queries are correct and now should be moved to UnitTester 
		 */




	}
	
	public static void printQueryResult( String str) throws IOException, ClassNotFoundException{
		Service service = new Service();

		JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(str).getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object.getInt("result")+ " <-- " + str +"\n");
	}

}
