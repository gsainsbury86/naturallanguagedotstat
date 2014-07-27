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
		// http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.7.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		
		
/*
		// These work and need to be web checked and then integrated into unit tests.
		printQueryResult("How many people follow the jedi religion?");  // Q1235.

 */
		printQueryResult("How many people less than 40 have a diploma in Goulburn?"); 

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
