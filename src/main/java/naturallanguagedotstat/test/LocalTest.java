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

		printQueryResult("How many men are Baptist?") ;

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
