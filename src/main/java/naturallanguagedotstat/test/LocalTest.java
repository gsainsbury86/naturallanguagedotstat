package naturallanguagedotstat.test;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;

import naturallanguagedotstat.Service;

public class LocalTest {

	/* print logging statements */
	public static boolean log = false;
	/* running UnitTests so do not perform ABS.Stat restful call */
	public static boolean unitTests = false;
	/* running locally so load data irrespective of web context */
	public static boolean localLoad = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{

		log = true;
		localLoad = true;
		unitTests = true;


		// The following don't work for whatever reasons.
		
		
		
/*
		// These work and need to be web checked and then integrated into unit tests.
		printQueryResult("Number of atheists?");  	// Q637
		printQueryResult("Average wage?");  		// Q875. 
		printQueryResult("coffee cpi?");  		// Q877. 
		printQueryResult("foreign language speakers in howrah?"); 
		printQueryResult("How many people follow the jedi religion?");  //Q1235.
		printQueryResult("How many females are engineers?"); 
		"How many Irish women live in Adelaide?",			//Q1390



 */
		 

	}
	
	public static void printQueryResult( String str) throws IOException, ClassNotFoundException, SQLException{
		Service service = new Service();
		
		Response res = service.query(str);


		JsonReader jsonReader = Json.createReader(new StringReader((String) res.getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object);//.getInt("result")+ " <-- " + str +"\n");
	}

}
