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
		"What is the participation rate for QLD?"	Q341. //hangs
		"What is the employment rate for VIC?" //Q342. hangs
		"How many men aged 15-19 are in Sydney?" //Q343.//Does not find Sydney
  		Does not parse unmarried.
  		"How many men were born in Ghana?" /Q441. Does not parse Ghana.
  		
  		"How many builders are in Canberra?" //Q497. Does not parse Builders. Does not parse Canberra!!
  		
  		
 */
		
		
		
/*
		"How many women in Tasmania are unmarried?"	//Q433. 78276. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"how many women in Murrumbeena are single" // Q488. 1526. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.2.SA2.208021181.A/ABS?startTime=2011&endTime=2011

 */
		printQueryResult("How many people in Canberra?") ; 							// Q88. 
		
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
