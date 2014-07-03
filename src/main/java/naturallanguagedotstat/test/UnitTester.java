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
		collector = new ErrorCollector();
		service = new Service();
	}

	@Test
	public void testQueries() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {

		LocalTest.localLoad = true;
		LocalTest.debug = true;
		LocalTest.test = true;
		System.out.println("Starting "+TEST_QUERIES.length +" functional tests.");

		for(int i = 0; i < TEST_QUERIES.length; i++){
			 System.out.println("Testing: ("+(i+1)+ "/"+TEST_QUERIES.length +"): "+ TEST_QUERIES[i]);

			JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(TEST_QUERIES[i]).getEntity()));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();
			
			collector.checkThat(TEST_QUERIES[i], object.getString("url"), is(TEST_RESULTS[i]));
		}
		System.out.println("Functional tests are now complete.");
		
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

	private static final String[] TEST_QUERIES = {
		
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
		"how many people were aged 15-20 were at home on the night of the Census in Eden",

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
		"What is the population of Tasmania born in vietnam",
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
		"How many women were in Goulburn in the labour force?",
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
		"How many women aged 20-25 in Karabar are labourers?"
	};

	private static final String[] TEST_RESULTS = {	
		
		//B02
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.0.AUS.0.A/ABS",			//	printQueryResult("What's median age for people in Australia?") ; // 37
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.0.AUS.0.A/ABS",			//	printQueryResult("What is Australia's median age?") ; //  37
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MAGE.6.STE.6.A/ABS",			// 	printQueryResult("What is the average age of people in Tasmania?") ; // 40 
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.6.STE.6.A/ABS",			// 	printQueryResult("What is the average income for people in Tasmania?") ; // 499
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.1.SA3.10102.A/ABS",		//	printQueryResult("What's the median personal income for people in Queanbeyan?") ; // 833
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTPI.1.SA3.10102.A/ABS",		//	printQueryResult("What is the median personal income in Queanbeyan?") ; // 833
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.1.SA2.101021007.A/ABS",	//	printQueryResult("What is the average family income in Braidwood?") ; // 1259
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.1.SA2.101021007.A/ABS",	//  printQueryResult("What is the median income for families in Braidwood?") ; // 1259
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTFI.6.SA2.601031019.A/ABS",	//	printQueryResult("What's the median family income in Moonah?") ; // 1158
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.6.SA2.601031019.A/ABS",	//	printQueryResult("What's the avereage household income in Moonah?") ; //  870
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.1.SA2.101021008.A/ABS",	//	printQueryResult("What is the median income for households in Karabar?") ; //   1560
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MTHI.1.SA2.101021008.A/ABS",	//	printQueryResult("What is the median household income in Karabar?") ; //  1560
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MMORT.1.SA2.101041023.A/ABS",	//	printQueryResult("What is the average mortgage repayment in Eden?") ; // 1430
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MMORT.1.SA2.101041023.A/ABS",	//	printQueryResult("What's the median repayment for mortgages in Eden?") ; // 1430
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MMORT.1.SA2.101031014.A/ABS",	//	printQueryResult("What is the median mortgage repayment in Cooma?") ; // 1200
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.6.SA2.601031019.A/ABS",	//	printQueryResult("What is the median cost of rent in Moonah?") ; // 230
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.1.SA2.101011001.A/ABS",	//	printQueryResult("What is the median rent in Goulburn?") ; // 185
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B02/MRENT.1.SA2.101011001.A/ABS",	//	printQueryResult("What's the median rental payment in Goulburn?") ; // 185 

		//B03
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/T15.1.1.SA2.101011001.A/ABS", // How many people aged 18-20 were at home on Census night in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/TT.1.1.SA2.101011001.A/ABS", 	// How many people were at home on Census night in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/T15.1.1.SA2.101041023.A/ABS",	//	printQueryResult("how many people were aged 15-20 were at home on the night of the Census in Eden");	//321

		//B04
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.0.AUS.0.A/ABS",					//What is the population of Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS", 			//What is the population of Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS", 			//How many people live in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS", 			//Population of Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS", 			//In Goulburn, how many people are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS", 			//What is Goulburn's population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.1.SA2.101011001.A/ABS", 			//Goulburn's population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA2.101011001.A/ABS", 			//What is the population of men in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA2.101021008.A/ABS", 				//How many males live in Karabar?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA2.101021008.A/ABS", 				//How many men live in Karabar?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA3.10102.A/ABS",				 //Male population of Queanbeyan?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA3.10102.A/ABS",				 //In Queanbeyan, how many men are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA3.10102.A/ABS", 				//What is Queanbeyan's male population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101031014.A/ABS", //What is the female population aged 20-24 of Cooma?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101031014.A/ABS", //What is the population of women aged between 20 and 24 in Cooma?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101031014.A/ABS", //How many 20-24 year old females live in Cooma?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS", //How many females aged 20-24 live in moonah?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS", //How many females aged 20 to 24 live in Moonah?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS", //How many women aged between 20 and 24 live in Moonah?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601031019.A/ABS", //How many women 20-24 in Moonah are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //In Sandy Bay, how many women aged 20-24 are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //What is Sandy Bay's 20-24 female population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //Sandy Bay's female population aged 20-24 years?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //What is the population of Sandy Bay women aged 20-24?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101021007.A/ABS",	//		printQueryResult("In Braidwood, how many women aged 20-24 are there?") ; // 38
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101021007.A/ABS",	//		printQueryResult("What is Braidwood's 20-24 female population?") ; // 38
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101011001.A/ABS",	//		printQueryResult("Goulburn's female population aged 20-24 years?") ; // 626
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.1.SA2.101011001.A/ABS",	//		printQueryResult("What is the population of Goulburn women aged 20-24?") ; // 626

		//B05
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.5.1.SA2.101021008.A/ABS",	// "How many married men are aged between 25 and 30 are in Karabar?",	46
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.4.6.STE.6.A/ABS",			// "How many female 25-30 year olds are separated?", 38274
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA2.101031014.A/ABS",	// "How many divorced men are there in Cooma?",137
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.2.1.SA2.101031014.A/ABS",	// "How many 25-30 year old widows are in Cooma?",0
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.5.0.AUS.0.A/ABS",			// "How many people aged 25-30 are married?",1198562
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.4.1.SA2.101011001.A/ABS",	// "How many men in Goulburn are separated?",50
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.3.0.AUS.0.A/ABS",			// "How many people are divorced?",1460900
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.2.1.SA2.101011001.A/ABS",	// "How many widowed women aged 25-30 are in Goulburn?",0
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.1.0.AUS.0.A/ABS",			// "How many men aged 25-30 have never married?",895608
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.5.1.SA2.101011001.A/ABS",	// "How many married men are there in Goulburn?",	723
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.4.1.SA2.101011001.A/ABS",	// "How many separated persons aged 25-30 are in Goulburn?",7
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.0.AUS.0.A/ABS",			// "How many divorced women are there?",835077
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.2.0.AUS.0.A/ABS",			// "How many widows aged 25-30 are there?",4616
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.0.AUS.0.A/ABS",			// "How many people are married?",8461114
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101041023.A/ABS", 	// How many married 25-30 women are in Eden?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101041023.A/ABS", 	// How many females married in Eden are 25-30?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101041023.A/ABS", 	// How many married females in Eden are between 25 and 30 years old?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.1.SA2.101041023.A/ABS", 	// How many married women are in Eden?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.4.1.SA2.101041023.A/ABS", 	// How many men are separated in Eden?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA2.101021007.A/ABS", 	// How many men are divorced in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.2.1.SA2.101021007.A/ABS", 	// How many widowed men are in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.5.1.SA2.101021007.A/ABS", 	// How many men in Braidwood are married?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA2.101021007.A/ABS", 	// How many men in Braidwood are divorced?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.3.1.SA2.101021007.A/ABS", 	// How many men in Braidwood aged 25-30 are divorced		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.4.1.SA3.10102.A/ABS",		// printQueryResult("What is the male population in Queanbeyan that are separated?") ; //710
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA4.101.A/ABS",			// printQueryResult("What is the male population in Capital Region that are divorced?") ; //7444
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.2.6.SA2.601051031.A/ABS",	// printQueryResult("What is the male population in Sandy Bay that are widows?") ; //545
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.0.AUS.0.A/ABS",	//	printQueryResult("How many women have never been married?");	//	2753427
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.1.SA2.101021008.A/ABS",	//	printQueryResult("How many women have never married in Karabar?");	// 1164

		//B06
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/1.TT.2.1.SA2.101021008.A/ABS", 	// "How many men are living in a de facto relationship in Karabar?",125 
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.2.0.AUS.0.A/ABS",			// "How many people are living in a de facto relationship?", 1476370
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.2.1.SA2.101041023.A/ABS", 	// How many females are in a de facto relationship in Eden?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.1.1.SA2.101021007.A/ABS", 	// How many people have a registered marriage in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.1.SA2.101021007.A/ABS",	// "How many registered marriages are there in Braidwood?", 1427
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.6.SA2.601051031.A/ABS",	//		printQueryResult("What is Sandy Bay's married population?") ; // 4467
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.1.SA2.101011001.A/ABS",	//		printQueryResult("What is Goulburn's married population?") ; // 7723
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.1.6.SA2.601051031.A/ABS",	//		printQueryResult("How many people have a registered marriage in Sandy Bay?") ; // 3858
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/1.TT.2.6.SA2.601051031.A/ABS",	//		printQueryResult("How many men in Sandy Bay are in a de facto relationship?") ; // 381
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/1.TT.3.1.SA2.101021008.A/ABS",	//	printQueryResult("How many men are not married in Karabar?");	// 1257

		//B07
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.1.SA2.101031014.A/ABS",	// "What is the indigenous population of men aged between 35 and 40 in Goulburn?", 27
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.0.AUS.0.A/ABS",			// "How many women aged 35-40 are indigenous?", 17838
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101031014.A/ABS",	// "How many men in Goulburn are indigenous?", 350
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.A35.4.1.SA2.101011001.A/ABS",	// "How many people aged 35-40 in Goulburn are indigenous?", 45
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.1.SA2.101011001.A/ABS",	// "How many indigenous 35-40 year olds in Goulburn are female?", 18
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.1.SA2.101011001.A/ABS", 	// How many men aged 35-40 in Goulburn are indigenous?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101021007.A/ABS", 	// How many indigenous males are there in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.1.SA2.101011001.A/ABS",	//		"What is Goulburn's indigenous female 35-40 year old population?", 18
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.6.STE.6.A/ABS",			//		"What is Tasmania's male 35 to 40 year old indigenous population?", 492
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101021007.A/ABS",	//		"What is Braidwood's indigenous male population?", 
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.0.AUS.0.A/ABS",			//	printQueryResult("How many indigenous men 35-40 years old are there?") ; // 16234
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.1.0.AUS.0.A/ABS",			//	printQueryResult("How many non indigenous men 35-40 years old are there?") ; // 691051
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.1.0.AUS.0.A/ABS",			// printQueryResult("How many men 35-40 years old are not indigenous?") ; // 691051

		//B09
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.5105.6.STE.6.A/ABS", 			// What is the population of Tasmania born in vietnam
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.0.AUS.0.A/ABS", 			// How many women in Australia were born in Germany?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6101.1.SA2.101031014.A/ABS", 	// How many people in Cooma were born in China?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.8104.1.SA2.101031014.A/ABS", 	// How many men born in the US are in Cooma?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.7103.1.SA2.101011001.A/ABS", 	// How many men in Goulburn are from India?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1201.6.STE.6.A/ABS",			//	printQueryResult("How many people whose country of birth is New Zealand live in Tasmania?") ; //4927
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.1101.0.AUS.0.A/ABS",			//	printQueryResult("Number of women in Australia born in Australia?") ; //7605247
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.2100.6.STE.6.A/ABS",			//	printQueryResult("How many people in Tasmania were born in the UK?") ; //267
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.1101.0.AUS.0.A/ABS",			//	printQueryResult("Number of women in Australia whose country of birth is also Australia?") ; //7605247
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.5105.6.STE.6.A/ABS", 			//		printQueryResult("How many men in Tasmania were born in Vietnam?") ; //267
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.3207.6.SA2.601051031.A/ABS",	//		printQueryResult("Sandy Bay's population of men who were born in Greece?") ; // 36
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.1101.2.STE.2.A/ABS",			//	printQueryResult("How many people in Victoria were born in Australia?") ; //3670934

		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.41.1.SA2.101011001.A/ABS",	 //How many people speak Iranian in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7101.1.SA2.101011001.A/ABS", //In Goulburn, how many men speak Cantonese?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.1.1.SA2.101011001.A/ABS", 	//How many women in Goulburn only speak English?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.0.AUS.0.A/ABS", 		//How many women speak Arabic in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.0.AUS.0.A/ABS", 		//How many women speak Arabic?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.6.STE.6.A/ABS", 		//How many women speak Arabic in Tasmania?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.4202.6.STE.6.A/ABS",		// 		printQueryResult("How many people in Tasmania speak Arabic?") ; // 910
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.7101.6.SA2.601051031.A/ABS",	// 		printQueryResult("How many women in Sandy Bay speak Cantonese at home?") ; //71
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.1301.0.AUS.0.A/ABS",			//		printQueryResult("How many men in Australia can speak German?") ; // 36882
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.2201.6.STE.6.A/ABS",		// 		printQueryResult("How many women in Tasmania speak Greek at home?") ; // 571
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.5203.1.SA4.101.A/ABS",		//		printQueryResult("How many persons in Capital Region  speak Hindi?") ; //247
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7301.6.SA2.601051031.A/ABS",		//		printQueryResult("How many men in Sandy Bay speak Korean?") ; // 65	
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.6302.0.AUS.0.A/ABS", 		//		printQueryResult("How many people in Australia speak Vietnamese at home?") ; //233388
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.2303.0.AUS.0.A/ABS",		// printQueryResult("How many women speak Spanish?") ; //61659
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.1.0.AUS.0.A/ABS",			// printQueryResult("How many men only speak English?") ; //8147580
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.LOTE.0.AUS.0.A/ABS",		// printQueryResult("How many females in Australia speak a language other than English") ; //2019744
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.7104.0.AUS.0.A/ABS",		// printQueryResult("How many Mandarin speakers are there?") ; //336410
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.71.1.SA4.101.A/ABS",		// printQueryResult("How many men in Capital Region are Chinese speakers?") ; // 285
	
		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.1.SA2.101011001.A/ABS", 		//How many men in Goulburn are muslims?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.24.1.SA2.101011001.A/ABS", 		//How many Pentecostal men are there in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.215.1.SA2.101011001.A/ABS",		 //How many mormons in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.7.1.SA2.101011001.A/ABS", 		//How many women in Goulburn have no religion?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.1.SA2.101011001.A/ABS", 	//How many women follow Buddhism in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.2.0.AUS.0.A/ABS",				//		printQueryResult("What is Australia's Christian population?") ; // 13150673
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.0.AUS.0.A/ABS",				//		printQueryResult("What is Australia's female Buddhist population?") ; //288792
		
		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/1.A20.1.0.AUS.0.A/ABS", //How many men 20-25 need assistance in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/2.A20.1.6.SA2.601051031.A/ABS",	//	printQueryResult("How many female 20-25 year olds need assistance in Sandy Bay?") ; // 7
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/3.TT.1.1.SA2.101021007.A/ABS",	//	printQueryResult("How many people need assistance in Braidwood?") ; // 121
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/3.TT.1.1.SA3.10102.A/ABS",	//	printQueryResult("How many people need assistance in Braidwood?") ; // 121

		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/2.T45.2.6.STE.6.A/ABS", //How many women aged 45-50 in Tasmania did volunteer work?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/1.A20.2.1.SA2.101011001.A/ABS", //How many men aged 20-25 in Goulburn did volunteer work?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/1.A20.2.1.SA3.10102.A/ABS", //How many men aged 20-25 in Goulburn did volunteer work?

		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.O15+A15.2.0.AUS.0.A/ABS", //How many women aged 15-19 provided unpaid assistance in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.O15.2.0.AUS.0.A/ABS", //How many women provided unpaid assistance in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.O15.2.1.SA3.10102.A/ABS", //How many women provided unpaid assistance in Queanbeyan?


		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/2.O15.2.1.SA4.101.A/ABS", //How many women in Capital Region have a graduate diploma?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/2.O15.3.1.SA4.101.A/ABS", //How many women have a bachelor degree in Capital Region?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/3.O15+T15.2.1.SA2.101011001.A/ABS", //How many people 15-24 have a diploma in Goulburn
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/2.T25.1.1.SA2.101011001.A/ABS", //How many women 25-34 in Goulburn have a postgraduate degree
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/1.T35.3.1.SA2.101011001.A/ABS", //How many 35-44 year old men in Goulburn have a bachelors degree 

		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/01.2.T25.6.SA2.601031019.A/ABS", //How many women aged 25-30 in Moonah have studied science?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.1.SA2.101011001.A/ABS", //How many women in Goulburn have an engineering degree?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/04.1.T65.1.SA2.101011001.A/ABS", //How many men aged 65-80 in Goulburn have studied architecture?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/08.1.O15.1.SA2.101011001.A/ABS", //How many men in Goulburn have a commerce degree?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/08.1.O15.1.SA2.101021007.A/ABS",	//	printQueryResult("How many men in Braidwood have a commerce degree?") ; // 7

		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.6.SA2.601031019.A/ABS", // How many people aged between 35 and 40 in Moonah were employed?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.6.SA2.601031019.A/ABS", // How many people aged 35-40 in Moonah were in employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.1.1.SA2.101011001.A/ABS", 	// How many men in Goulburn were in full time employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/2.O15.LF.1.SA2.101011001.A/ABS", 	// How many women in Goulburn were in the labour force?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.EMP.1.SA2.101011001.A/ABS", // How many people in Goulburn were in employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS", 	// How many people in Goulburn were in part time employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/2.O15.LF.1.SA2.101011001.A/ABS", 	// How many women were in Goulburn in the labour force?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS", 	// How many people in Goulburn were part time employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS", 	// How many people in Goulburn were employed part time?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.1.1.SA2.101011001.A/ABS",	//	printQueryResult("How many men in Goulburn were full time employed?") ; // 3726
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.1.1.SA2.101011001.A/ABS",	//	printQueryResult("How many men in Goulburn were employed full time?") ; // 3726
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.1.SA2.101011001.A/ABS",	//	printQueryResult("How many people aged 35-40 in Goulburn were employed in total?") ; //2047  

		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.A.6.SA2.601031019.A/ABS", //How many men aged 35-40 in Moonah work in agriculture?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.B.6.SA2.601051031.A/ABS", //How many men aged 35-40 in Sandy Bay work in mining?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.C.6.SA2.601051031.A/ABS", //How many men aged 35-40 in Sandy Bay work in manufacturing?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.D.1.SA2.101041023.A/ABS", //How many men aged 35-40 in Eden work in utilities?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.E.1.SA2.101041023.A/ABS", //How many men aged 35-40 in Eden work in construction?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.A.1.SA2.101031014.A/ABS", //How many men aged 35-40 in Cooma are in the agricultural sector?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.B.1.SA2.101031014.A/ABS", //How many men aged 35-40 in Cooma are in the mining sector?
		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.C.6.SA2.601031019.A/ABS", //How many men from the manufacturing industry in Moonah are aged 35-40?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.D.1.SA3.10102.A/ABS", //How many men from the utilities industry in Queanbeyan are aged 35-40?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/2.T35.E.1.SA3.10102.A/ABS", //How many men from the construction industry in Queanbeyan are aged 35-40?

		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A15.2.4.6.STE.6.A/ABS", 			// What is the female population aged 18-19 in Tasmania that are community workers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T35.1.1.1.SA2.101021007.A/ABS", 	//What is the male population aged 35-40 in Braidwood are managers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.1.6.STE.6.A/ABS", 			// What is the female population aged 20 to 25 in Tasmania that are Managers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.2.6.STE.6.A/ABS", 			//How many 20-25 year old females in Tasmania are professionals?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.3.6.SA2.601051031.A/ABS", 	//How many 20-25 year old women in Sandy Bay are technicians?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.4.1.SA2.101031014.A/ABS", 	//How many women aged 20-25 in Cooma are community workers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.8.1.SA2.101021008.A/ABS", 	//How many women aged 20-25 in Karabar are labourers?


	};
}
