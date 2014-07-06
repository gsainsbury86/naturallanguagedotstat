package naturallanguagedotstat.test;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;

import naturallanguagedotstat.QueryBuilder;
import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;

public class LocalTest {

	public static boolean debug = false;
	public static boolean test = false;
	public static boolean localLoad = false;

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{

		debug = true;
		localLoad = true;
		test = true;

		printQueryResult("What is the CPI for transport for Tasmania?") ;
//		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/1.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1" // "What is the current CPI?

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
