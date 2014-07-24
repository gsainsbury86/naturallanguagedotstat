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

		log = false;
		localLoad = true;
		unitTests = true;

/*
		// The following don't work for whatever reasons.
		printQueryResult("females in adelaide over 30") ; //Q11	"does not parse over 30"
  		
  		
 */
		
		
		
/*
		// These work and need to be web checked and then integrated into unit tests.
		 * 
  		"How many builders are in Canberra?" //Q497. 
		printQueryResult("How many men were born in Ghana?"); //Q441. 
		printQueryResult("How many women in Sandy Bay are not employed?"); 


 */
		 
		printQueryResult("How many people in Tasmania were born in Hungary?"); 

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
