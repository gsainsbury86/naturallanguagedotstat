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
  
 */

/*

 */
		printQueryResult("How many women follow Buddhism") ; 							// Q88. 

		
		printQueryResult("@ How many men aged 40-45 in Tasmania have a graduate diploma?") ; 							// Q100. 514  http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/1.T35.2.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women studied engineering?") ; 							// Q99. 102766. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women in Australia studied engineering?") ; 							// Q98. 102776. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women aged 20-50 work in the Agricultural industry") ; 							// Q97. 3652. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.A20.A.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many people in Tasmania were born in New Zealand?") ; 							// Q96.4927. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1201.6.STE.6.A/ABS?startTime=2011&endTime=2011 
		printQueryResult("How many men in Sandy Bay speak Mandarin?") ; 							// Q95.294.  http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7104.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many men in Sandy Bay speak Chinese?") ; 							// Q93.436.  http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.71.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many people aged 30-40 speak a language other than English?") ; 							// Q91. 3912939. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.LOTE.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women in Adelaide follow Buddhism") ; 							// Q90. 513. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women follow Buddhism") ; 							// Q88. 288792. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the average rental payment for South Australia") ; 	// Q87. 220. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.4.STE.4.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the average household income for Sandy Bay") ; 		// Q86. 1278. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many men in Sandy Bay are Baptist") ; 					// Q85. 42. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many men are Baptist") ; 									// Q84. 166187. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many men aged 40-50 are buddhists") ; 					// Q78. 240185. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.101.0.AUS.0.A/ABS?startTime=2011&endTime=2011 
		printQueryResult("How many men aged 40-50 are Baptist") ; 						// Q77.166187. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many men aged 40-50 are Catholics") ; 					// Q76. 2607251. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.207.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("how many 15-20 year olds in Australia are widowed") ; 		// Q75. 722. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.A15.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many female 30-40 year olds in Adelaide have never been married") ; 			// Q73. 262. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T35.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many female 30-40 year olds in Adelaide are in de facto relationships") ; 	// Q72. 75. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.T35.2.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many female 30-40 year olds in Adelaide are not married") ; 					// Q71. 239. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.T35.3.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women in Sandy Bay are unemployed") ; 				// Q64 1583267 //http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many persons in Victoria are employed full time") ; 		// Q64 1583267	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.1.2.STE.2.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many 0-15 year old females live in Tasmania") ; 			// Q62 48994	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.3+2+1+0+7+6+5+4+9+8+15+13+14+11+12+10.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many 40-43 year olds live in Tasmania") ; 				// Q62 27492	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.43+42+41+40.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many 40-41 year olds live in Adelaide") ; 				// Q60	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.41+40.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women in Sandy Bay") ; 								// Q54.5696	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women in Sandy Bay are married") ; 					// Q51.2246	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women are there in Tasmania") ; 						// Q49.262679	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the population of Sandy Bay") ; 						// Q43. 11157	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the population of Tasmania") ; 						// Q41.495354	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the population of Australia") ; 						// Q40.21507719	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the current CPI") ; 									// Q39.0.6	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		printQueryResult("How many women aged 15-18 live in Lindisfarne") ; 			// Q35.136	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.17+18+15+16.6.SA2.601021008.A/ABS?startTime=2011&endTime=2011
		printQueryResult("What is the CPI of Tasmania?") ; 								// Q32. 0.5	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		printQueryResult("How many men live in Sandy Bay") ; 							// Q32. 5461 http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women in Tasmania are there") ; 						// Q30. 252679	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many people live in Tasmania?") ; 						// Q27. 495354	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("what is the CPI for australia") ; 							// Q24. 0.6		http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		printQueryResult("how many men aged 35-40 years in Moonah work in agriculture") ; 	// Q18. 8	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.A.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011
		printQueryResult("cpi") ; 														// Q17. 0.6	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		printQueryResult("how many women in Tasmania are divorced?") ; 					//Q16. 22334. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("females in adelaide 30 who are from Korea") ; 				// Q15	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.6203.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("females in adelaide 30 who are managers") ; 					// Q14	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T25.2.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("females in adelaide 30") ; 									// Q13	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.30.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("females in adelaide 30 to 80") ; 								// Q12	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.35+36+33+34+39+37+38+43+42+41+40+30+32+31+79+78+77+67+66+69+68+70+71+72+73+74+75+76+59+58+57+56+55+64+65+62+63+60+61+49+48+45+44+47+46+51+52+53+54+50.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("females in adelaide") ; 										// Q10. http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		printQueryResult("females") ; 													// Q8. 10873705	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women aged 20-40 in Tasmania are living in a de facto relationship?") ; 	// Q5. 3164	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.A20.2.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("How many women aged 20-40 in Tasmania are not married?") ; 	// Q3. 8758	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.A20.3.6.STE.6.A/ABS?startTime=2011&endTime=2011
		printQueryResult("Number of women in Sandy Bay who are managers?") ; 			// Q2. 265	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/O15.2.1.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		printQueryResult("Number of men in Sandy Bay?") ; 								// Q1. 5461	http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011

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
