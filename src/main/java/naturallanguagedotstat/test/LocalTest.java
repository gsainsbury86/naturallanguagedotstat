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

	/* TODO: flag to switch between Census versions. Fix later. */
	public static String collectionGroupName;
	public static String regionTypeName;
	
	public static final String resourcesDir = "src/main/webapp/WEB-INF/resources/";


	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{

		log = true;
		localLoad = true;
		
		//collectionGroupName = "Census_2011";
		//regionTypeName = "Region Type";
		
		collectionGroupName = "Census_2016";
		regionTypeName = "Geography Level";
		
		
				
				
		
		

		// The following don't work for whatever reasons.
		// http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.7.0.AUS.0.A/ABS?startTime=2011&endTime=2011
		// "How many Italian people in Australia?" // Q1655. returns number of people who speak Italian.
		
/*
		// These work and need to be web checked and then integrated into unit tests.
		printQueryResult("How many people follow the jedi religion?");  // Q1235.
		
Questions 1100-1600 

1556 How many girls aged between 15 and 19 are in Sheffield? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.19+17+18+15+16.6.SA2.604021091.A/ABS?startTime=2011&endTime=2011
1391 How many women in the ACT are Irish? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2201.8.STE.8.A/ABS?startTime=2011&endTime=2011
1331 What is the childcare CPI in Brisbane? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/CPI/2.3.115498.10.Q/ABS?startTime=2014-Q1&endTime=2014-Q1
1283 how many men in QLD speak Cantonese? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7101.3.STE.3.A/ABS?startTime=2011&endTime=2011
1261 How many women in tasmania attend University? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B15/2.50.6.STE.6.A/ABS?startTime=2011&endTime=2011
1260 How many men work in the racing industry? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.O15.R.0.AUS.0.A/ABS?startTime=2011&endTime=2011
1225 How many women work in tourism?  http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.O15.H.0.AUS.0.A/ABS?startTime=2011&endTime=2011
1210 How many men under 25 live in Geelong? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.22+23+24+25+3+2+1+0+7+6+5+4+9+8+19+17+18+15+16+13+14+11+12+21+20+10.2.SA2.203021039.A/ABS?startTime=2011&endTime=2011
1183 Number of Iranians living in WA? http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.41.5.STE.5.A/ABS?startTime=2011&endTime=2011
1182 How many people in the ACT speak Japanese?	 http://stat.data.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.7201.8.STE.8.A/ABS?startTime=2011&endTime=2011



		
		
 */
		//printQueryResult("How many women in Australia are Irish?", collectionGroupName, regionTypeName);
		
		int random = (int)(Math.random() * 50 + 1);
		//int random = 1;
		printQueryResult(UnitTester.TEST_QUERIES[random], collectionGroupName, regionTypeName);
		
		
 
	}
	
	public static void printQueryResult( String str, String collectionGroupName, String regionTypeName) throws IOException, ClassNotFoundException, SQLException{
		Service service = new Service();
		
		Response res = service.query(str);
		

		JsonReader jsonReader = Json.createReader(new StringReader((String) res.getEntity()));
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		System.out.println(object);//.getInt("result")+ " <-- " + str +"\n");
	}

}
