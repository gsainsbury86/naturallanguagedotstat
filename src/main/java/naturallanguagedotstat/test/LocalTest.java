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

 		printQueryResult("How many people are from South Korea?") ; 							// Q599
 		printQueryResult("How many aboriginal people in Tasmania?") ; 							// Q588
 		printQueryResult("How many single women in Unley?") ; 							// Q577
 		printQueryResult("How many single women in South Australia?") ; 							// Q576
		printQueryResult("How many people divorced men in South Australia?") ; 							// Q573
		printQueryResult("How many people divorced men in Adelaide?") ; 							// 572
		printQueryResult("How many people in Hobart work as dentists?") ; 							// Q565
		printQueryResult("How many people in Lenah Valley work in Agriculture?") ; 							// Q562
		printQueryResult("How many people in Tasmania born in Malaysia?") ; 							// Q560
		printQueryResult("How many people in Tasmania born in Sri Lanka?") ; 							// Q559
		printQueryResult("How many people in Tasmania born in Germany?") ; 							// Q558
		printQueryResult("How many people in Tasmania born in uk?") ; 							// Q557
		printQueryResult("How many people in Tasmania born in Hungary?") ; 							// Q556
		printQueryResult("How many in Tasmania are married?") ; 							// Q554
		printQueryResult("What is the cpi for food?") ; 							// Q553
		printQueryResult("What is the cpi for tas?") ; 							// Q552
		printQueryResult("What is the unemployment rate for Tasmania?") ; 							// Q548
		printQueryResult("how many people 0 to 15 in Canberra?") ; 							// Q545
		printQueryResult("how many IT in Tasmania?") ; 							// Q539
		printQueryResult("how many doctors in Tasmania?") ; 							// Q538
		printQueryResult("cpi?") ; 							// Q535
		printQueryResult("How many women in Adelaide are married?") ; 							// Q528
		printQueryResult("How many people live in Canberra?") ; 							// Q516
		printQueryResult("How many people live in Adelaide?") ; 							// Q514
		printQueryResult("How many men with IT degrees in Adelaide?") ; 							// Q513
		printQueryResult("How many single women in Burnside council?") ; 							// Q512
		printQueryResult("How many men in Frankston?") ; 							// Q509
		printQueryResult("How many men in Eliza?") ; 							// Q505
		printQueryResult("How many men in melbourne?") ; 							// Q504
		printQueryResult("What is the unemployment rate in NSW?") ; 							// Q503
		printQueryResult("What is the current CPI in NSW?") ; 							// Q500
		"How many women in Tasmania are unmarried?"	//Q433. 78276. 
		"how many women in Murrumbeena are single" // Q488. 1526. 
		printQueryResult("What is the unemployment rate in Tasmania?") ; 		
		printQueryResult("What is the employment rate in Victoria?") ; 							// Q342
		printQueryResult("What is the participation rate for QLD?") ; 							// Q341

 */
		
		printQueryResult("How many people are from Korea?") ; 							// Q588

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
