package naturallanguagedotstat.test;

import static org.hamcrest.CoreMatchers.is;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import naturallanguagedotstat.Service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;



public class UnitTester {

	@Rule
	public ErrorCollector collector;

	public Service service;

	public UnitTester() throws IOException, ClassNotFoundException{
		LocalTest.localLoad = true;
		LocalTest.log = true;
		LocalTest.unitTests = true;
		
		collector = new ErrorCollector();
		service = new Service();
	}

	@Test
	public void testQueries() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {


		System.out.println("Starting "+TEST_QUERIES.length +" functional tests.");
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < TEST_QUERIES.length; i++){
			 // System.out.println("Testing: ("+(i+1)+ "/"+TEST_QUERIES.length +"): "+ TEST_QUERIES[i]);
				
			JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(TEST_QUERIES[i]).getEntity()));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();
						
			collector.checkThat(TEST_QUERIES[i], object.getString("url"), is(TEST_RESULTS[i]));
		}
		
		long elapsedTime = System.currentTimeMillis() - startTime;

		System.out.println("Functional tests were completed in "+elapsedTime +" milliseconds." );
		
	}

	//	@Test
	//	public void testQueries()
	//			throws FileNotFoundException, IOException, ClassNotFoundException {
	//
	//		LocalTest.localLoad = true;
	//		LocalTest.debug = false;
	//
	//		for(int i = 0; i < TEST_QUERIES.length; i++){
	//			assertEquals(TEST_QUERIES[i],Integer.parseInt(service.query(TEST_QUERIES[i])), TEST_RESULTS[i]);
	//		}
	//	}

	public static final String[] TEST_QUERIES = {
		
		// Extra 1100-1600
		"How many girls aged between 15 and 19 are in Sheffield?",		// Q1556. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.19+17+18+15+16.6.SA2.604021091.A/ABS?startTime=2011&endTime=2011
		"How many women in the ACT are Irish?",							// Q1391. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2201.8.STE.8.A/ABS?startTime=2011&endTime=2011
		"What is the childcare CPI in Brisbane?",						// Q1331. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.3.115498.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many men in QLD speak Cantonese?",							// Q1283. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7101.3.STE.3.A/ABS?startTime=2011&endTime=2011
		"How many women in tasmania attend University?",				// Q1261. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B15/2.50.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many men work in the racing industry?",					// Q1260. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.R.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women work in tourism?",								// Q1225. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.H.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many men under 25 live in Geelong?",						// Q1210. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.22+23+24+25+3+2+1+0+7+6+5+4+9+8+19+17+18+15+16+13+14+11+12+21+20+10.2.SA2.203021039.A/ABS?startTime=2011&endTime=2011
		"Number of Iranians living in WA?",								// Q1183. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.41.5.STE.5.A/ABS?startTime=2011&endTime=2011
		"How many people in the ACT speak Japanese?",					// Q1182. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.7201.8.STE.8.A/ABS?startTime=2011&endTime=2011

		// Extra 1650-1844
		"What is the female unemployment rate in Canberra?", 			// Q1844. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.2.8.SA3.80105.A/ABS?startTime=2011&endTime=2011
		"Number of indigenous persons in Tasmania?", 					// Q1841. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.TT.4.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"Average age of females in ACT?",  								// Q1835. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.8.STE.8.A/ABS?startTime=2011&endTime=2011
		"Number of people in Sydney who are Christian?",				// Q1817. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.2.1.SA4.116.A/ABS?startTime=2011&endTime=2011
		"Number of people in Sydney who have no religion?", 			// Q1813. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.7.1.SA4.116.A/ABS?startTime=2011&endTime=2011
		"How many people in Melbourne were born overseas?",				// Q1788. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/3.BPELSE.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011
		"How many Australians have university degrees?",				// Q1763. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/NSQ_3.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many people in Australia are employed?", 					// Q1738. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.EMP.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the median rental payment in Mitcham?", 				// Q1719. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.4.SA3.40303.A/ABS?startTime=2011&endTime=2011
		"How many men born in Italy?", 									// Q1711. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.3104.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the average age of Tasmanians?", 						// Q1707. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many unemployed in Panania?",								// Q1680. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.UEMP.1.SA2.119011359.A/ABS?startTime=2011&endTime=2011
		"How many people in Queensland work as pilots?", 				// Q1675. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.I.3.STE.3.A/ABS?startTime=2011&endTime=2011
		"How many people are retired in Tocumwal?", 					// Q1664. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/LF_6.3.1.SA2.109031185.A/ABS?startTime=2011&endTime=2011
		"How many people live in Ballarat?",							// Q1654. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.2.SA3.20101.A/ABS?startTime=2011&endTime=2011
		
		
		"Number of atheists?", 										// Q637 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.7.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"Average wage?",  											// Q875. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"foreign language speakers in howrah?",  					//	 	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.LOTE.6.SA2.601021007.A/ABS?startTime=2011&endTime=2011
		"How many females are engineers?",							// 		http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011

		"Number of females in adelaide aged 30 who are managers?",	// Q1380. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T25.2.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many people whose country of birth is New Zealand live in Tasmania?", //Q1297. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1201.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"median rent in VIC?", 										// Q1282 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.2.STE.2.A/ABS?startTime=2011&endTime=2011
		"How many teachers are there in WA?", 						// Q1274 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.P.5.STE.5.A/ABS?startTime=2011&endTime=2011
		"What is Australia's median age?",							// Q1273 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the unemployment rate?", 							// Q1265 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the median family income for Adelaide?",			// Q1247 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"What is the median family income for Darwin?",				// Q1246 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.7.SA4.701.A/ABS?startTime=2011&endTime=2011
		"How many women in Deloraine?",								// Q1226 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.602021054.A/ABS?startTime=2011&endTime=2011
		"How many people speak Dutch in Adelaide?",					// Q1200 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.1401.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many aboriginal males live in Howrah?",				// Q1191 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.6.SA2.601021007.A/ABS?startTime=2011&endTime=2011
		"How many men in Geelong are teachers?",					// Q1155 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.2.SA2.203021039.A/ABS?startTime=2011&endTime=2011
		"how many women speak Spanish?",							// Q1146 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.2303.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"Number of people from Korea?",								// Q908 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6203.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the average age of women in Melbourne?",			// Q894 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011
		"average age of males in Hobart?",							// Q893 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011
		"Number of people in Belconnen?",							// Q883 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.8.SA2.801011002.A/ABS?startTime=2011&endTime=2011
		"Average income?",											// Q876 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many people in Sydney?",								// Q865 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA4.116.A/ABS?startTime=2011&endTime=2011
  		"How many builders are in Canberra?", 						// Q830 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B44/TOT.3.8.SA3.80105.A/ABS?startTime=2011&endTime=2011 
		"Number of males, aged 18?", 								// Q642 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.18.0.AUS.0.A/ABS?startTime=2011&endTime=2011
  		"Hindus?" , 												// Q636 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011
  		"Number of people born in South Korea living in Adelaide", 	// Q623 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6203.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"CPI?", 													// Q611 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many men were born in Ghana?",							// Q441 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/1.BPELSE.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women in Sandy Bay are not employed?", 			// 		 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/LF_6.2.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011

		// Queries 501-600
 		"How many people are from Korea?",							// Q599 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6203.0.AUS.0.A/ABS?startTime=2011&endTime=2011
 		"How many aboriginal people in Tasmania?", 					// Q588 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.TT.4.6.STE.6.A/ABS?startTime=2011&endTime=2011
 		"How many single women in Unley?",							// Q577 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.4.SA3.40107.A/ABS?startTime=2011&endTime=2011
 		"How many single women in South Australia?",				// Q576 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.4.STE.4.A/ABS?startTime=2011&endTime=2011
		"How many  divorced men in South Australia?",				// Q573 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.4.STE.4.A/ABS?startTime=2011&endTime=2011
		"How many  divorced men in Adelaide?",						// Q572 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many people in Hobart work as dentists?",				// Q565 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.Q.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011
		"How many people in Lenah Valley work in Agriculture?",		// Q562 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.A.6.SA2.601051028.A/ABS?startTime=2011&endTime=2011
		"How many people in Tasmania were born in Malaysia?",		// Q560 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.5203.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many people in Tasmania were born in Sri Lanka?",		// Q559 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.7107.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many people in Tasmania were born in Germany?",		// Q558 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.2304.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many people in Tasmania were born in uk?",				// Q557 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.2100.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many people in Tasmania were born in Hungary?",		// Q556 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/3.BPELSE.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many in Tasmania are married?",						// Q554 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"What is the cpi for food?", 								// Q553 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the cpi for tas?",									// Q552 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the unemployment rate for Tasmania?", 				// Q548 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.3.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"how many people 0 to 15 in Canberra?", 					// Q545 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.3+2+1+0+7+6+5+4+9+8+15+13+14+11+12+10.8.SA3.80105.A/ABS?startTime=2011&endTime=2011
		"how many IT people in Tasmania?",							// Q539 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.J.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"how many doctors in Tasmania?",							// Q538 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.Q.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"cpi?",														// Q535 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many women in Adelaide are married?",					// Q528 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many people live in Canberra?",						// Q516 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.8.SA3.80105.A/ABS?startTime=2011&endTime=2011
		"How many people live in Adelaide?",						// Q514 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many men with IT degrees in Adelaide?",				// Q513 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.J.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many single women in Burnside council?",				// Q512 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.4.SA3.40103.A/ABS?startTime=2011&endTime=2011
		"How many men in Frankston?",								// Q509 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.2.SA2.214011371.A/ABS?startTime=2011&endTime=2011
		"How many men in melbourne?",								// Q504 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011
		"What is the unemployment rate in NSW?",					// Q503 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.3.1.STE.1.A/ABS?startTime=2011&endTime=2011
		"What is the current CPI in NSW?",							// Q500 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1

		// Queries 401-500
		"How many women in Murrumbeena are single?", 				// Q488. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.2.SA2.208021181.A/ABS?startTime=2011&endTime=2011
		"How many women in Tasmania are unmarried?"	,				// Q433. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"What is the employment rate in Victoria?",					// Q342 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/EPR.3.2.STE.2.A/ABS?startTime=2011&endTime=2011
		"What is the participation rate for QLD?",					// Q341 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/LFPR.3.3.STE.3.A/ABS?startTime=2011&endTime=2011
		"How many Jewish people live in Caufield?", 				// Q495. 97335. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.5.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"how many women in Murrumbeena are single?",					// Q488. 1526. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.2.SA2.208021181.A/ABS?startTime=2011&endTime=2011
		"how many women in Australia are aged 65-99", 				// Q456. 1121632. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.79+78+77+67+66+69+68+70+71+72+73+74+75+76+65.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"how many women in Australia work in engineering?",			 // Q455. 102776. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"Inflation rate for Tasmania?",								// Q444. 0.6. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many men were born in Korea?"	,						// Q442. 34383. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.6203.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women in Tasmania are not married?", 				// Q434. 78276.  http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many women in Tasmania are unmarried?",				// Q433. 78276. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011

		// Queries 301-400
		"What is the average family income for Darwin?", 			//	Q377. 2040. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.7.SA4.701.A/ABS?startTime=2011&endTime=2011
		"What is the median family income for Darwin?", 			//	Q377. 2040. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.7.SA4.701.A/ABS?startTime=2011&endTime=2011
		"How many men in Belconnen aged 30-40 are divorced?", 		// Q374. 25. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T35.3.8.SA2.801011002.A/ABS?startTime=2011&endTime=2011
		"How many men in Collingwood aged 15-20 are widowed?",		// Q370. 0. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.A15.2.2.SA2.206071141.A/ABS?startTime=2011&endTime=2011
		"What is the current CPI for housing in NSW?", 				// Q369. 0.5. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.20003.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the current CPI for food?", 						// Q367. 0.6. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the current inflation rate for food?", 			// Q367. 0.6. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the current inflation rate for housing?", 			// Q366. 0.6. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the current CPI for housing?", 					// Q365. 0.6. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.20003.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many teachers are there in WA?", 						// Q364. 88075. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.P.5.STE.5.A/ABS?startTime=2011&endTime=2011
		"How many men in Queensland aged 15-20 are widowed", 		// Q363. 64. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.A15.2.3.STE.3.A/ABS?startTime=2011&endTime=2011
		"How many men aged 15-20 are widowed?", 					// Q361. 352. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.A15.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the housing CPI for Canberra?", 					// Q340. -0.2, http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.8.20003.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the inflation rate for TAS?", 						// Q337. 0.5. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the CPI for New South Wales?", 					// Q335. 0.6 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the CPI for NSW?", 								// Q335. 0.6 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"Number of women in Queensland who were born in Vietnam?", 	// Q327. 8830. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.5105.3.STE.3.A/ABS?startTime=2011&endTime=2011
		"Number of women who speak Chinese in Tasmania?", 			// Q325. 1701. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.71.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"Number of indigenous in Hobart?", 							// Q318. 91. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.TT.4.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011
		"Median family income for Sandy Bay?", 						// Q314. 1892. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many women were born in Italy?", 						// Q309. 90591. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.3104.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women aged 20-30 in Bendigo were born in Germany?", // Q305. 17.http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.2.SA2.202011018.A/ABS?startTime=2011&endTime=2011
		"How many women in Bendigo were born in Germany?", 			// Q305. 17.http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.2.SA2.202011018.A/ABS?startTime=2011&endTime=2011
		"How many women speak Chinese?", 							//. Q304. 348957 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.71.0.AUS.0.A/ABS?startTime=2011&endTime=2011

		// Queries 201-300
 		"How many Anglicans in Aust?", 								// Q299, 3679907. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.201.0.AUS.0.A/ABS?startTime=2011&endTime=2011
 		"How many women in Bendigo were born in Germany?", 			// Q286.17. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.2.SA2.202011018.A/ABS?startTime=2011&endTime=2011	
 		"How many women in Mildura are Buddhists?",					 // Q285. 167. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.2.SA3.21502.A/ABS?startTime=2011&endTime=2011
 		"How many women in Tasmania are Buddhists?", 				// Q284. 2009, http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.6.STE.6.A/ABS?startTime=2011&endTime=2011
 		"How many women in Tasmania are Catholic?", 				// Q281. 46784. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.207.6.STE.6.A/ABS?startTime=2011&endTime=2011
 		"How many women in Tasmania are Catholics?", 				// Q281. 46784. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.207.6.STE.6.A/ABS?startTime=2011&endTime=2011
 		"How many men in Mildura are teachers?", 					// Q279. 508. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.2.SA3.21502.A/ABS?startTime=2011&endTime=2011
 		"How many men in Geelong are teachers?", 					// Q278. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.2.SA2.203021039.A/ABS?startTime=2011&endTime=2011
 		"How many men in Richmond are teachers?", 					// Q277. 2784. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.1.SA4.112.A/ABS?startTime=2011&endTime=2011
 		"Pop of redfern?", 											// Q276. 16559. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.117031335.A/ABS?startTime=2011&endTime=2011
 		"how many women in Tasmania are teachers?", 				// Q275. 13549. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.P.6.STE.6.A/ABS?startTime=2011&endTime=2011
 		"How many women aged 30-40 in Tasmania were born in Vietnam?", // Q269. 138. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.5105.6.STE.6.A/ABS?startTime=2011&endTime=2011
 		"How many women in Tasmania were born in Vietnam?", 		// Q268. 138. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.5105.6.STE.6.A/ABS?startTime=2011&endTime=2011
 		"Number of women aged 30-40 in IT in Sandy Bay?", 			// Q267. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.J.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
 		"Women in IT in Taroona?", 									// Q265. 9. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.J.6.SA2.601041026.A/ABS?startTime=2011&endTime=2011
 		"Women in Taroona?", 										// Q263. 1822. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601041026.A/ABS?startTime=2011&endTime=2011
 		"What is the food cpi in Sydney?", 							// Q261. 0.7. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"What is the tobacco cpi in Hobart?", 						// Q258. 3.3. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20006.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"What is the alcohol cpi in Hobart?", 						// Q257. 3.3. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20006.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"What is the food cpi in Hobart?", 							// Q256. -0.3. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"What is the cpi in Hobart?", 								// Q255, 0.5. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"What is the CPI for transport in Hobart?", 				// Q253. 0.6. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20005.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"What is the CPI for transport in Tasmania?", 				// Q253. 0.6. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20005.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"What is the food CPI for Tasmania?", 						// Q252. -0.3. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
 		"Number of muslim men?", 									// Q237. 249022. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.0.AUS.0.A/ABS?startTime=2011&endTime=2011
 		"Number of muslim men in Belconnen?", 						// Q236. 130. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.8.SA2.801011002.A/ABS?startTime=2011&endTime=2011
 		"Number of muslim men in Melbourne", 						// Q232. 481. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011
 		"Number of muslim men in Hobart",							// Q231. 47. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011
 		"Number of muslim men in ACT", 								// Q228. 3973. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.8.STE.8.A/ABS?startTime=2011&endTime=2011
 		"Number of muslim men in Sandy Bay", 						// Q227. 194. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
 		"Number of muslim men in Tasmania", 						// Q225. 931. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many women in Lindisfarne are divorced?", 				// Q218. 344. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.6.SA2.601021008.A/ABS?startTime=2011&endTime=2011
		"How many females in Victoria?", 							// Q206. 2721423. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.2.STE.2.A/ABS?startTime=2011&endTime=2011
		"how many men aged 20-30 work in finance?", 				// Q203. 11722. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.A20.K.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		
		// Queries 101-200
		"how many men in sandy bay are married?", 					// Q153. 2221. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.5.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many women in Tasmania work in IT?", 					// Q141. 1554. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.J.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"What is the transport CPI for Tasmania?", 					// Q139. 0.6 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20005.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"What is the number of people married in Tasmania", 		// Q123. 190898. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"What is the average age of people in Victoria?", 			// Q104. 37. 	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.2.STE.2.A/ABS?startTime=2011&endTime=2011
		"What is the average age of people in Braidwood?",			// Q103. 46. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011
		"What is the average age of people in Tasmania?",  			// Q102. 40. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.STE.6.A/ABS?startTime=2011&endTime=2011
		
		// Queries 51-100
		"How many men aged 40-45 in Tasmania have a graduate diploma?", 	// Q100. 514  http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/1.T35.2.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many women studied engineering?", 								// Q99. 102766. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women in Australia studied engineering?", 				// Q98. 102776. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women aged 20-50 work in the Agricultural industry", 		// Q97. 3652. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.A20+T25+T35+T45.A.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many people in Tasmania were born in New Zealand?", 			// Q96.4927. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1201.6.STE.6.A/ABS?startTime=2011&endTime=2011 
		"How many men in Sandy Bay speak Mandarin?", 						// Q95.294.  http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7104.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many men in Sandy Bay speak Chinese?", 						// Q93.436.  http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.71.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many people aged 30-40 speak a language other than English?", 	// Q91. 3912939. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.LOTE.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women in Adelaide follow Buddhism?", 						// Q90. 513. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many women follow Buddhism?", 									// Q88. 288792. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the average rental payment for South Australia?", 			// Q87. 220. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.4.STE.4.A/ABS?startTime=2011&endTime=2011
		"What is the average household income for Sandy Bay?", 				// Q86. 1278. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many men in Sandy Bay are Baptist?", 							// Q85. 42. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many men are Baptist?", 										// Q84. 166187. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many men aged 40-50 are buddhists?", 							// Q78. 240185. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.101.0.AUS.0.A/ABS?startTime=2011&endTime=2011 
		"How many men aged 40-50 are Baptist?", 							// Q77.166187. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many men aged 40-50 are Catholics?", 							// Q76. 2607251. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.207.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"how many 15-20 year olds in Australia are widowed?", 				// Q75. 722. 	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.A15.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many female 30-40 year olds in Adelaide have never been married?", 			// Q73. 262. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T35.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many female 30-40 year olds in Adelaide are in de facto relationships?", 	// Q72. 75.  http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.T35.2.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many female 30-40 year olds in Adelaide are not married?", 					// Q71. 239. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.T35.3.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many women in Sandy Bay are unemployed?", 						// Q64 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/2.O15.UEMP.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many persons in Victoria are employed full time?", 			// Q64 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.1.2.STE.2.A/ABS?startTime=2011&endTime=2011
		"How many 0-15 year old females live in Tasmania?", 				// Q62 48994	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.3+2+1+0+7+6+5+4+9+8+15+13+14+11+12+10.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many 40-43 year olds live in Tasmania?", 						// Q62 27492	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.43+42+41+40.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many 40-41 year olds live in Adelaide?", 						// Q60	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.41+40.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"How many women in Sandy Bay?", 									// Q54.5696	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many women in Sandy Bay are married?", 						// Q51.2246	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		
		// Queries 01-50
		"How many women are there in Tasmania?", 						// Q49.262679	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"What is the population of Sandy Bay?", 						// Q43. 11157	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"What is the population of Tasmania?", 							// Q41.495354	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"What is the population of Australia?", 						// Q40.21507719	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"What is the current CPI?", 									// Q39.0.6	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many women aged 15-18 live in Lindisfarne?", 				// Q35.136	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.17+18+15+16.6.SA2.601021008.A/ABS?startTime=2011&endTime=2011
		"What is the CPI of Tasmania?", 								// Q32. 0.5	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many men live in Sandy Bay", 								// Q32. 5461 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"How many women in Tasmania are there?", 						// Q30. 252679	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"How many people live in Tasmania?", 							// Q27. 495354	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"What is the CPI for Australia?", 								// Q24. 0.6		http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many men aged 35-40 years in Moonah work in agriculture?", // Q18. 8	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.A.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011
		"cpi?", 														// Q17. 0.6	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
		"How many women in Tasmania are divorced?", 					//Q16. 22334. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011
		"Females in adelaide 30 who are from Korea?", 					// Q15	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.6203.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"Females in adelaide 30 who are managers?", 					// Q14	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T25.2.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"Females in adelaide 30?", 										// Q13	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.30.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"Females in adelaide 30 to 80?", 								// Q12	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.35+36+33+34+39+37+38+43+42+41+40+30+32+31+79+78+77+67+66+69+68+70+71+72+73+74+75+76+59+58+57+56+55+64+65+62+63+60+61+49+48+45+44+47+46+51+52+53+54+50.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"Females in adelaide?", 										// Q10. http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011
		"Females?", 													// Q8. 10873705	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		"How many women aged 20-40 in Tasmania are living in a de facto relationship?", 	// Q5. 3164	"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.A20+T25+T35.2.6.STE.6.A/ABS?startTime=2011&endTime=2011"
		"How many women aged 20-40 in Tasmania are not married?", 		// Q3. 8758	
		"Number of women in Sandy Bay who are managers?", 				// Q2. 265	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/O15.2.1.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011
		"Number of men in Sandy Bay?", 									// Q1. 5461	http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011

		
		//B02
		"What's median age for people in Australia?",	 // 37
		"What is Australia's median age?",		//  37
		"What is the average age of people in Tasmania?",	// 40 
		"What is the average income for people in Tasmania?", // 499
		"What's the median personal income for people in Queanbeyan?", // 833
		"What is the median personal income in Queanbeyan?", // 833
		"What is the average family income in Braidwood?", // 1259
		"What is the median income for families in Braidwood?", // 1259
		"What's the median family income in Moonah?", // 1158
		"What's the average household income in Moonah?", //  870
		"What is the median income for households in Karabar?", //   1560
		"What is the median household income in Karabar?", //  1560
		"What is the average mortgage repayment in Eden?", // 1430
		"What's the median repayment for mortgages in Eden?", // 1430
		"What is the median mortgage repayment in Cooma?", // 1200
		"What is the median cost of rent in Moonah?", // 230
		"What is the median rent in Goulburn?", // 185
		"What's the median rental payment in Goulburn?", // 185 

		//B03
		"How many people aged 18-20 were at home on Census night in Goulburn?",	
		"How many people were at home on Census night in Goulburn?",	
		"how many people were aged 15-20 were at home on the night of the Census in Eden?",

		//B04
		"What is the population of Australia?",
		"What is the population of Goulburn?",
		"How many people live in Goulburn?",
		"Population of Goulburn?",
		"In Goulburn, how many people are there?",
		"What is Goulburn's population?",
		"Goulburn's population?",
		"What is the population of men in Goulburn?",
		"How many males live in Karabar?",
		"How many men live in Karabar?",
		"Male population of Queanbeyan?",
		"In Queanbeyan, how many men are there?",
		"What is Queanbeyan's male population?",
		"What is the female population aged 20-24 of Cooma?",
		"What is the population of women aged between 20 and 24 in Cooma?",				
		"How many 20-24 year old females live in Cooma?",
		"How many females aged 20-24 live in Moonah?",
		"How many females aged 20 to 24 live in Moonah?",
		"How many women aged between 20 and 24 live in Moonah?",
		"How many women 20-24 in Moonah are there?",
		"In Sandy Bay, how many women aged 20-24 are there?",
		"What is Sandy Bay's 20-24 female population?",
		"Sandy Bay's female population aged 20-24 years?",
		"What is the population of Sandy Bay women aged 20-24?",
		"In Braidwood, how many women aged 20-24 are there?",
		"What is Braidwood's 20-24 female population?",
		"Goulburn's female population aged 20-24 years?",
		"What is the population of Goulburn women aged 20-24?",

		//B05		
		"How many married men aged between 25 and 30 are in Karabar?",
		"How many female 25-30 year olds in Tasmania are separated?",
		"How many divorced men are there in Cooma?",
		"How many 25-30 year old widows are in Cooma?",
		"How many  people aged 25-30 are married?",
		"How many men in Goulburn are separated?",
		"How many people are divorced?",
		"How many widowed women aged 25-30 are in Goulburn?",
		"How many men aged 25-30 have never married?",
		"How many married men are there in Goulburn?",
		"How many separated persons aged 25-30 are in Goulburn?",
		"How many divorced women are there?",
		"How many widows aged 25-30 are there?",
		"How many people are married?",
		"How many married 25-30 women are in Eden?",
		"How many females married in Eden are 25-30?",
		"How many married females in Eden are between 25 and 30 years old?",
		"How many married women are in Eden?",
		"How many men are separated in Eden?",
		"How many men are divorced in Braidwood?",
		"How many widowed men are in Braidwood?",
		"How many men in Braidwood are married?", 
		"How many men in Braidwood are divorced?", 
		"How many men in Braidwood aged 25-30 are divorced",
		"What is the male population in Queanbeyan that are separated?",
		"What is the male population in Capital Region that are divorced?",
		"What is the male population in Sandy Bay that are widows?",
		"How many women have never been married?",
		"How many women have never married in Karabar?",

		//B06
		"How many men are living in a de facto relationship in Karabar?", 
		"How many people are living in a de facto relationship?", 
		"How many females are in a de facto relationship in Eden?", 
		"How many people have a registered marriage in Braidwood?", 
		"How many registered marriages are there in Braidwood?", 
		"What is Sandy Bay's married population?",
		"What is Goulburn's married population?",
		"How many people have a registered marriage in Sandy Bay?",
		"How many men in Sandy Bay are in a de facto relationship?",
		"How many men are not married in Karabar?",
		
		//B07
		"What is the indigenous population of men aged between 35 and 40 in Cooma?", 
		"How many women aged 35-40 are indigenous?", 
		"How many men in Cooma are indigenous?", 
		"How many people aged 35-40 in Goulburn are indigenous?", 
		"How many indigenous 35-40 year olds in Goulburn are female?", 
		"How many men aged 35-40 in Goulburn are indigenous?", 
		"How many indigenous males are there in Braidwood?", 
		"What is Goulburn's indigenous female 35-40 year old population?", 
		"What is Tasmania's male 35 to 40 year old indigenous population?", 
		"What is Braidwood's indigenous male population?", 
		"How many indigenous men 35-40 years old are there?",
		"How many non indigenous men 35-40 years old are there?",
		"How many men 35-40 years old are not indigenous?",

		//B09
		"What is the population of Tasmania born in Vietnam",
		"How many women in Australia were born in Germany?",
		"How many people in Cooma were born in China?",
		"How many men born in the US are in Cooma?",
		"How many men in Goulburn are from India?",  
		"How many people whose country of birth is New Zealand live in Tasmania?", 
		"Number of women in Australia born in Australia?",
		"How many people in Tasmania were born in the UK?",
		"Number of women in Australia whose country of birth is also Australia?",
		"How many men in Tasmania were born in Vietnam?",
		"Sandy Bay's population of men who were born in Greece?",
		"How many people in Victoria were born in Australia?",

		//B13
		"How many people speak Iranian in Goulburn?",
		"In Goulburn, how many men speak Cantonese?",
		"How many women in Goulburn only speak English?",
		"How many women speak Arabic in Australia?",
		"How many women speak Arabic?",
		"How many women speak Arabic in Tasmania?",
		"How many people in Tasmania speak Arabic?",
		"How many women in Sandy Bay speak Cantonese at home?",
		"How many men in Australia can speak German?",
		"How many women in Tasmania speak Greek at home?",
		"How many persons in Capital Region  speak Hindi?",
		"How many men in Sandy Bay speak Korean?",
		"How many people in Australia speak Vietnamese at home?",
		"How many women speak Spanish?",
		"How many men only speak English?",
		"How many females in Australia speak a language other than English",
		"How many Mandarin speakers are there?",
		"How many men in Capital Region are Chinese speakers?",

		//B14
		"How many men in Goulburn are muslims?",
		"How many Pentecostal men are there in Goulburn?",
		"How many mormons in Goulburn?",
		"How many women in Goulburn have no religion?",
		"How many women follow Buddhism in Goulburn?",
		"What is Australia's Christian population?",
		"What is Australia's female Buddhist population?",

		//B18
		"How many men aged 20-25 need assistance in Australia?",
		"How many female 20-25 year olds need assistance in Sandy Bay?",
		"How many people need assistance in Braidwood?",
		"How many people need assistance in Queanbeyan?",

		//B19
		"How many women aged 45-50 in Tasmania did volunteer work?",
		"How many men aged 20-25 in Goulburn did volunteer work?",
		"How many men aged 20-25 in Queanbeyan did volunteer work?",

		//B21
		"How many women aged 15-19 provided unpaid assistance in Australia?",
		"How many women provided unpaid assistance in Australia?",
		"How many women provided unpaid assistance in Queanbeyan?",

		//B40
		"How many women in Capital Region have a graduate diploma?",
		"How many women have a bachelor degree in Capital Region?",
		"How many people 15-24 have a diploma in Goulburn",
		"How many women 25-34 in Goulburn have a postgraduate degree",
		"How many 35-44 year old men in Goulburn have a bachelors degree ",

		//B41
		"How many women aged 25-30 in Moonah have studied science?",
		"How many women in Goulburn have an engineering degree?",
		"How many men aged 65-80 in Goulburn have studied architecture?",
		"How many men in Goulburn have a commerce degree?",
		
		"How many men in Braidwood have a commerce degree?",

		//B42
		"How many people aged 35-40 in Moonah were employed?",
		"How many people aged 35-40 in Moonah were in employment?",
		"How many men in Goulburn were in full time employment?",
		"How many women in Goulburn were in the labour force?",
		"How many people in Goulburn were in employment?",
		"How many people in Goulburn were in part time employment?",
		"How many people in Goulburn were part time employment?", 
		"How many people in Goulburn were employed part time?", 
		"How many men in Goulburn were full time employed?",
		"How many men in Goulburn were employed full time?",
		"How many people aged 35-40 in Goulburn were employed in total?",

		//B43
		"How many men aged 35-40 years in Moonah work in agriculture?",
		"How many men aged 35-40 in Sandy Bay work in mining?",
		"How many men aged 35-40 in Sandy Bay work in manufacturing?",
		"How many men aged 35-40 in Eden work in utilities?",
		"How many men aged 35-40 in Eden in construction?",
		"How many women aged 35-40 in Cooma are in the agricultural sector?", 
		"How many women aged 35-40 in Cooma are in the mining sector?", 
		"How many women from the manufacturing industry in Moonah are aged 35-40?", 
		"How many women from the utilities industry in Queanbeyan are aged 35-40?", 
		"How many women from the construction industry in Queanbeyan are aged 35-40?", 
		
		//B45
		"What is the female population women aged 18-19 years in Tasmania are community workers?",
		"What is the male population aged between 35 and 40 in Braidwood that are managers?",
		"What is the female population aged 20 to 25 in Tasmania are that Managers?", 
		"How many 20-25 year old females in Tasmania are professionals?", 
		"How many 20-25 year old women in Sandy Bay are technicians?", 
		"How many women aged 20-25 in Cooma are community workers?", 
		"How many women aged 20-25 in Karabar are labourers?",
		
		//CPI
		"What is the current CPI?", //0.6
		"What is the CPI for New South Wales?", // 0.6
		"What is the inflation rate for Tasmania?", //0.5
		"What is the current transport CPI?", //1.1
		"What is the food CPI for Hobart?", //-0.3
		"What is the housing CPI in Canberra?" //-0.2
		

		
	};

	private static final String[] TEST_RESULTS = {
		
		// Extra 1100-1600
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.19+17+18+15+16.6.SA2.604021091.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2201.8.STE.8.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.3.115498.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7101.3.STE.3.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B15/2.50.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.R.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.H.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.22+23+24+25+3+2+1+0+7+6+5+4+9+8+19+17+18+15+16+13+14+11+12+21+20+10.2.SA2.203021039.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.41.5.STE.5.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.7201.8.STE.8.A/ABS?startTime=2011&endTime=2011",

		//Q1654-1844
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.2.8.SA3.80105.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.TT.4.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.8.STE.8.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.2.1.SA4.116.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.7.1.SA4.116.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/3.BPELSE.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/NSQ_3.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.EMP.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.4.SA3.40303.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.3104.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.UEMP.1.SA2.119011359.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.I.3.STE.3.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/LF_6.3.1.SA2.109031185.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.2.SA3.20101.A/ABS?startTime=2011&endTime=2011",
		
		//
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.7.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.LOTE.6.SA2.601021007.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T25.2.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1201.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.2.STE.2.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.P.5.STE.5.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.7.SA4.701.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.602021054.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.1401.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.6.SA2.601021007.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.2.SA2.203021039.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.2303.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6203.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.8.SA2.801011002.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA4.116.A/ABS?startTime=2011&endTime=2011",
  		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B44/TOT.3.8.SA3.80105.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.18.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
  		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
  		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6203.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/1.BPELSE.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/LF_6.2.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",

		// Queries 501-600
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6203.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.TT.4.6.STE.6.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.4.SA3.40107.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.4.STE.4.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.4.STE.4.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.Q.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.A.6.SA2.601051028.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.5203.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.7107.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.2304.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.2100.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B01/3.BPELSE.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.3.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.3+2+1+0+7+6+5+4+9+8+15+13+14+11+12+10.8.SA3.80105.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.J.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.Q.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.8.SA3.80105.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.J.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.4.SA3.40103.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.2.SA2.214011371.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/UER.3.1.STE.1.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		
		// Queries 401-500
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.2.SA2.208021181.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/EPR.3.2.STE.2.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/LFPR.3.3.STE.3.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.5.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.2.SA2.208021181.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.79+78+77+67+66+69+68+70+71+72+73+74+75+76+65.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.6203.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011",

		// Queries 301-400
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.7.SA4.701.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.7.SA4.701.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T35.3.8.SA2.801011002.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.A15.2.2.SA2.206071141.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.20003.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.20003.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/3.O15.P.5.STE.5.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.A15.2.3.STE.3.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.A15.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.8.20003.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.5105.3.STE.3.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.71.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.TT.4.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.3104.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.2.SA2.202011018.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.2.SA2.202011018.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.71.0.AUS.0.A/ABS?startTime=2011&endTime=2011",

		// Queries 201-300
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.201.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.2.SA2.202011018.A/ABS?startTime=2011&endTime=2011",	
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.2.SA3.21502.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.6.STE.6.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.207.6.STE.6.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.207.6.STE.6.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.2.SA3.21502.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.2.SA2.203021039.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.P.1.SA4.112.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.117031335.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.P.6.STE.6.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.5105.6.STE.6.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.5105.6.STE.6.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.J.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.J.6.SA2.601041026.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601041026.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20006.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20006.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20005.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20005.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.8.SA2.801011002.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.2.SA2.206041122.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.6.SA2.601051027.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.8.STE.8.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
 		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.6.SA2.601021008.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.2.STE.2.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.A20+T25.K.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		
		// Queries 101-200
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.5.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.J.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20005.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.2.STE.2.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.STE.6.A/ABS?startTime=2011&endTime=2011",

		//Queries 50-100
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/1.T35.2.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.A20+T25+T35+T45.A.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1201.6.STE.6.A/ABS?startTime=2011&endTime=2011", 
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7104.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.71.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.LOTE.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.4.STE.4.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.101.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.203.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.207.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.A15.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T35.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.T35.2.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.T35.3.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/2.O15.UEMP.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.1.2.STE.2.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.3+2+1+0+7+6+5+4+9+8+15+13+14+11+12+10.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.43+42+41+40.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.41+40.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		
		//Queries 01-50
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.17+18+15+16.6.SA2.601021008.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.A.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.6203.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T25.2.1.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.30.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.35+36+33+34+39+37+38+43+42+41+40+30+32+31+79+78+77+67+66+69+68+70+71+72+73+74+75+76+59+58+57+56+55+64+65+62+63+60+61+49+48+45+44+47+46+51+52+53+54+50.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.4.SA2.401011001.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.0.AUS.0.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.A20+T25+T35.2.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.A20+T25+T35.3.6.STE.6.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/O15.2.1.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",

		//B02
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("What's median age for people in Australia?") ; // 37
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("What is Australia's median age?") ; //  37
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.STE.6.A/ABS?startTime=2011&endTime=2011",			// 	printQueryResult("What is the average age of people in Tasmania?") ; // 40 
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.6.STE.6.A/ABS?startTime=2011&endTime=2011",			// 	printQueryResult("What is the average income for people in Tasmania?") ; // 499
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.1.SA3.10102.A/ABS?startTime=2011&endTime=2011",		//	printQueryResult("What's the median personal income for people in Queanbeyan?") ; // 833
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.1.SA3.10102.A/ABS?startTime=2011&endTime=2011",		//	printQueryResult("What is the median personal income in Queanbeyan?") ; // 833
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What is the average family income in Braidwood?") ; // 1259
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	//  printQueryResult("What is the median income for families in Braidwood?") ; // 1259
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What's the median family income in Moonah?") ; // 1158
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What's the avereage household income in Moonah?") ; //  870
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What is the median income for households in Karabar?") ; //   1560
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What is the median household income in Karabar?") ; //  1560
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MMORT.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What is the average mortgage repayment in Eden?") ; // 1430
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MMORT.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What's the median repayment for mortgages in Eden?") ; // 1430
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MMORT.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What is the median mortgage repayment in Cooma?") ; // 1200
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What is the median cost of rent in Moonah?") ; // 230
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What is the median rent in Goulburn?") ; // 185
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("What's the median rental payment in Goulburn?") ; // 185 

		//B03
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/T15.1.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", // How many people aged 18-20 were at home on Census night in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/TT.1.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many people were at home on Census night in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/T15.1.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("how many people were aged 15-20 were at home on the night of the Census in Eden");	//321

		//B04
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.0.AUS.0.A/ABS?startTime=2011&endTime=2011",					//What is the population of Australia?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 			//What is the population of Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 			//How many people live in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 			//Population of Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 			//In Goulburn, how many people are there?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 			//What is Goulburn's population?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 			//Goulburn's population?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 			//What is the population of men in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011", 				//How many males live in Karabar?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011", 				//How many men live in Karabar?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA3.10102.A/ABS?startTime=2011&endTime=2011",				 //Male population of Queanbeyan?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA3.10102.A/ABS?startTime=2011&endTime=2011",				 //In Queanbeyan, how many men are there?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA3.10102.A/ABS?startTime=2011&endTime=2011", 				//What is Queanbeyan's male population?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", //What is the female population aged 20-24 of Cooma?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", //What is the population of women aged between 20 and 24 in Cooma?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", //How many 20-24 year old females live in Cooma?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", //How many females aged 20-24 live in moonah?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", //How many females aged 20 to 24 live in Moonah?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", //How many women aged between 20 and 24 live in Moonah?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", //How many women 20-24 in Moonah are there?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011", //In Sandy Bay, how many women aged 20-24 are there?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011", //What is Sandy Bay's 20-24 female population?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011", //Sandy Bay's female population aged 20-24 years?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011", //What is the population of Sandy Bay women aged 20-24?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("In Braidwood, how many women aged 20-24 are there?") ; // 38
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("What is Braidwood's 20-24 female population?") ; // 38
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("Goulburn's female population aged 20-24 years?") ; // 626
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("What is the population of Goulburn women aged 20-24?") ; // 626

		//B05
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.5.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011",	// "How many married men are aged between 25 and 30 are in Karabar?",	46
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.4.6.STE.6.A/ABS?startTime=2011&endTime=2011",			// "How many female 25-30 year olds are separated?", 38274
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011",	// "How many divorced men are there in Cooma?",137
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.2.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011",	// "How many 25-30 year old widows are in Cooma?",0
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.5.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many people aged 25-30 are married?",1198562
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.4.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	// "How many men in Goulburn are separated?",50
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many people are divorced?",1460900
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.2.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	// "How many widowed women aged 25-30 are in Goulburn?",0
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.1.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many men aged 25-30 have never married?",895608
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.5.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	// "How many married men are there in Goulburn?",	723
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.4.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	// "How many separated persons aged 25-30 are in Goulburn?",7
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many divorced women are there?",835077
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many widows aged 25-30 are there?",4616
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many people are married?",8461114
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", 	// How many married 25-30 women are in Eden?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", 	// How many females married in Eden are 25-30?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", 	// How many married females in Eden are between 25 and 30 years old?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", 	// How many married women are in Eden?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.4.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", 	// How many men are separated in Eden?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	// How many men are divorced in Braidwood?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.2.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	// How many widowed men are in Braidwood?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.5.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	// How many men in Braidwood are married?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	// How many men in Braidwood are divorced?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.3.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	// How many men in Braidwood aged 25-30 are divorced		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.4.1.SA3.10102.A/ABS?startTime=2011&endTime=2011",		// printQueryResult("What is the male population in Queanbeyan that are separated?") ; //710
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA4.101.A/ABS?startTime=2011&endTime=2011",			// printQueryResult("What is the male population in Capital Region that are divorced?") ; //7444
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.2.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",	// printQueryResult("What is the male population in Sandy Bay that are widows?") ; //545
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.0.AUS.0.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many women have never been married?");	//	2753427
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many women have never married in Karabar?");	// 1164

		//B06
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/1.TT.2.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011", 	// "How many men are living in a de facto relationship in Karabar?",125 
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many people are living in a de facto relationship?", 1476370
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.2.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", 	// How many females are in a de facto relationship in Eden?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.1.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	// How many people have a registered marriage in Braidwood?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	// "How many registered marriages are there in Braidwood?", 1427
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("What is Sandy Bay's married population?") ; // 4467
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("What is Goulburn's married population?") ; // 7723
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.1.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("How many people have a registered marriage in Sandy Bay?") ; // 3858
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/1.TT.2.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("How many men in Sandy Bay are in a de facto relationship?") ; // 381
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/1.TT.3.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many men are not married in Karabar?");	// 1257

		//B07
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011",	// "What is the indigenous population of men aged between 35 and 40 in Goulburn?", 27
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// "How many women aged 35-40 are indigenous?", 17838
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011",	// "How many men in Goulburn are indigenous?", 350
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.A35.4.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	// "How many people aged 35-40 in Goulburn are indigenous?", 45
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	// "How many indigenous 35-40 year olds in Goulburn are female?", 18
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many men aged 35-40 in Goulburn are indigenous?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	// How many indigenous males are there in Braidwood?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//		"What is Goulburn's indigenous female 35-40 year old population?", 18
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.6.STE.6.A/ABS?startTime=2011&endTime=2011",			//		"What is Tasmania's male 35 to 40 year old indigenous population?", 492
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	//		"What is Braidwood's indigenous male population?", 
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("How many indigenous men 35-40 years old are there?") ; // 16234
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.1.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("How many non indigenous men 35-40 years old are there?") ; // 691051
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.1.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// printQueryResult("How many men 35-40 years old are not indigenous?") ; // 691051

		//B09
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.5105.6.STE.6.A/ABS?startTime=2011&endTime=2011", 			// What is the population of Tasmania born in vietnam
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.0.AUS.0.A/ABS?startTime=2011&endTime=2011", 			// How many women in Australia were born in Germany?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6101.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", 	// How many people in Cooma were born in China?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.8104.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", 	// How many men born in the US are in Cooma?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.7103.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many men in Goulburn are from India?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1201.6.STE.6.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("How many people whose country of birth is New Zealand live in Tasmania?") ; //4927
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.1101.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("Number of women in Australia born in Australia?") ; //7605247
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.2100.6.STE.6.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("How many people in Tasmania were born in the UK?") ; //267
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.1101.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("Number of women in Australia whose country of birth is also Australia?") ; //7605247
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.5105.6.STE.6.A/ABS?startTime=2011&endTime=2011", 			//		printQueryResult("How many men in Tasmania were born in Vietnam?") ; //267
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.3207.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",	//		printQueryResult("Sandy Bay's population of men who were born in Greece?") ; // 36
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1101.2.STE.2.A/ABS?startTime=2011&endTime=2011",			//	printQueryResult("How many people in Victoria were born in Australia?") ; //3670934

		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.41.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	 //How many people speak Iranian in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7101.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //In Goulburn, how many men speak Cantonese?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.1.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	//How many women in Goulburn only speak English?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.0.AUS.0.A/ABS?startTime=2011&endTime=2011", 		//How many women speak Arabic in Australia?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.0.AUS.0.A/ABS?startTime=2011&endTime=2011", 		//How many women speak Arabic?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.6.STE.6.A/ABS?startTime=2011&endTime=2011", 		//How many women speak Arabic in Tasmania?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.4202.6.STE.6.A/ABS?startTime=2011&endTime=2011",		// 		printQueryResult("How many people in Tasmania speak Arabic?") ; // 910
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.7101.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",	// 		printQueryResult("How many women in Sandy Bay speak Cantonese at home?") ; //71
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.1301.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			//		printQueryResult("How many men in Australia can speak German?") ; // 36882
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.2201.6.STE.6.A/ABS?startTime=2011&endTime=2011",		// 		printQueryResult("How many women in Tasmania speak Greek at home?") ; // 571
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.5203.1.SA4.101.A/ABS?startTime=2011&endTime=2011",		//		printQueryResult("How many persons in Capital Region  speak Hindi?") ; //247
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7301.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",		//		printQueryResult("How many men in Sandy Bay speak Korean?") ; // 65	
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.6302.0.AUS.0.A/ABS?startTime=2011&endTime=2011", 		//		printQueryResult("How many people in Australia speak Vietnamese at home?") ; //233388
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.2303.0.AUS.0.A/ABS?startTime=2011&endTime=2011",		// printQueryResult("How many women speak Spanish?") ; //61659
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.1.0.AUS.0.A/ABS?startTime=2011&endTime=2011",			// printQueryResult("How many men only speak English?") ; //8147580
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.LOTE.0.AUS.0.A/ABS?startTime=2011&endTime=2011",		// printQueryResult("How many females in Australia speak a language other than English") ; //2019744
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.7104.0.AUS.0.A/ABS?startTime=2011&endTime=2011",		// printQueryResult("How many Mandarin speakers are there?") ; //336410
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.71.1.SA4.101.A/ABS?startTime=2011&endTime=2011",		// printQueryResult("How many men in Capital Region are Chinese speakers?") ; // 285
	
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 		//How many men in Goulburn are muslims?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.24.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 		//How many Pentecostal men are there in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.215.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",		 //How many mormons in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.7.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 		//How many women in Goulburn have no religion?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	//How many women follow Buddhism in Goulburn?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011",				//		printQueryResult("What is Australia's Christian population?") ; // 13150673
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.0.AUS.0.A/ABS?startTime=2011&endTime=2011",				//		printQueryResult("What is Australia's female Buddhist population?") ; //288792
		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/1.A20.1.0.AUS.0.A/ABS?startTime=2011&endTime=2011", //How many men 20-25 need assistance in Australia?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/2.A20.1.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many female 20-25 year olds need assistance in Sandy Bay?") ; // 7
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/3.TT.1.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many people need assistance in Braidwood?") ; // 121
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/3.TT.1.1.SA3.10102.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many people need assistance in Braidwood?") ; // 121
		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/2.T45.2.6.STE.6.A/ABS?startTime=2011&endTime=2011", //How many women aged 45-50 in Tasmania did volunteer work?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/1.A20.2.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //How many men aged 20-25 in Goulburn did volunteer work?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/1.A20.2.1.SA3.10102.A/ABS?startTime=2011&endTime=2011", //How many men aged 20-25 in Goulburn did volunteer work?

		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.A15.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011", //How many women aged 15-19 provided unpaid assistance in Australia?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.O15.2.0.AUS.0.A/ABS?startTime=2011&endTime=2011", //How many women provided unpaid assistance in Australia?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.O15.2.1.SA3.10102.A/ABS?startTime=2011&endTime=2011", //How many women provided unpaid assistance in Queanbeyan?

		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/NSQ_2.2.1.SA4.101.A/ABS?startTime=2011&endTime=2011", //How many women in Capital Region have a graduate diploma?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/NSQ_3.2.1.SA4.101.A/ABS?startTime=2011&endTime=2011", //How many women have a bachelor degree in Capital Region?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/3.T15.2.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //How many people 15-24 have a diploma in Goulburn
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/2.T25.1.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //How many women 25-34 in Goulburn have a postgraduate degree
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/1.T35.3.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //How many 35-44 year old men in Goulburn have a bachelors degree 

		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/01.2.T25.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", //How many women aged 25-30 in Moonah have studied science?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //How many women in Goulburn have an engineering degree?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/04.1.T75+T65.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //How many men aged 65-80 in Goulburn have studied architecture?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/08.1.O15.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", //How many men in Goulburn have a commerce degree?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/08.1.O15.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many men in Braidwood have a commerce degree?") ; // 7
		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", // How many people aged between 35 and 40 in Moonah were employed?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", // How many people aged 35-40 in Moonah were in employment?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.1.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many men in Goulburn were in full time employment?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B37/LFTOT.2.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many women in Goulburn were in the labour force?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.EMP.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", // How many people in Goulburn were in employment?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many people in Goulburn were in part time employment?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many people in Goulburn were part time employment?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011", 	// How many people in Goulburn were employed part time?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.1.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many men in Goulburn were full time employed?") ; // 3726
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.1.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many men in Goulburn were employed full time?") ; // 3726
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.1.SA2.101011001.A/ABS?startTime=2011&endTime=2011",	//	printQueryResult("How many people aged 35-40 in Goulburn were employed in total?") ; //2047  
		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.A.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", //How many men aged 35-40 in Moonah work in agriculture?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.B.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011", //How many men aged 35-40 in Sandy Bay work in mining?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.C.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011", //How many men aged 35-40 in Sandy Bay work in manufacturing?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.D.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", //How many men aged 35-40 in Eden work in utilities?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.E.1.SA2.101041023.A/ABS?startTime=2011&endTime=2011", //How many men aged 35-40 in Eden work in construction?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.A.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", //How many men aged 35-40 in Cooma are in the agricultural sector?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.B.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", //How many men aged 35-40 in Cooma are in the mining sector?
		
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.C.6.SA2.601031019.A/ABS?startTime=2011&endTime=2011", //How many men from the manufacturing industry in Moonah are aged 35-40?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.D.1.SA3.10102.A/ABS?startTime=2011&endTime=2011", //How many men from the utilities industry in Queanbeyan are aged 35-40?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.E.1.SA3.10102.A/ABS?startTime=2011&endTime=2011", //How many men from the construction industry in Queanbeyan are aged 35-40?

		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A15.2.4.6.STE.6.A/ABS?startTime=2011&endTime=2011", 			// What is the female population aged 18-19 in Tasmania that are community workers?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T35.1.1.1.SA2.101021007.A/ABS?startTime=2011&endTime=2011", 	//What is the male population aged 35-40 in Braidwood are managers?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.1.6.STE.6.A/ABS?startTime=2011&endTime=2011", 			// What is the female population aged 20 to 25 in Tasmania that are Managers?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.2.6.STE.6.A/ABS?startTime=2011&endTime=2011", 			//How many 20-25 year old females in Tasmania are professionals?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.3.6.SA2.601051031.A/ABS?startTime=2011&endTime=2011", 	//How many 20-25 year old women in Sandy Bay are technicians?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.4.1.SA2.101031014.A/ABS?startTime=2011&endTime=2011", 	//How many women aged 20-25 in Cooma are community workers?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.8.1.SA2.101021008.A/ABS?startTime=2011&endTime=2011", 	//How many women aged 20-25 in Karabar are labourers?

		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1", 		// "What is the current CPI?
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.1.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1", 		// printQueryResult("What is the CPI for New South Wales?") ; //
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.10001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1" ,		// printQueryResult("What is the inflation rate for Tasmania?") ; //
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.50.20005.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1",	// printQueryResult("What is the current transport CPI?") ; 
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.6.20001.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1", 	//	printQueryResult("What is the food CPI for Hobart?") ; 
		"http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.8.20003.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1"	// printQueryResult("What is the housing CPI in Canberra?") ; 


	};
}
