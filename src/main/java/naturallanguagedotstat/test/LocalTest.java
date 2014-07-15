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
		unitTests = false;
/*
		// Don't quite work yet, due to multiple months appearing in Query result
		printQueryResult("What is the participation rate for QLD?") ; // http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/LF/3.10.3.1599.10.M/ABS?startTime=2014-Q2&endTime=2014-Q2
		printQueryResult("What is the employment rate for Vic?") ; // http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.EMP.2.STE.2.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the unemployment rate for Tasmania?") ;	// http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/LF/6.14.3.1599.10.M/ABS?startTime=2014&endTime=2014

 * 		
 */

//		printQueryResult("What is the employment rate for Vic?") ; // http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/LF/2.5.3.1599.10.M/ABS?startTime=2014-Q2&endTime=2014-Q2
/*
		// The following don't work for whatever reasons.
		printQueryResult("females in adelaide over 30") ; //Q11	"does not parse over 30"
		printQueryResult("How many women in Sandy Bay are not employed") ; 		// Does not parse "not"	
		printQueryResult("How many men in Canberra?") ; 		// Does not parse "Canberra" in ASGS	
		
  
 */

/*

 */
		printQueryResult("How many women follow Buddhism") ; 							// Q88. 
/*

 		
		

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
