package naturallanguagedotstat.test;

import static org.hamcrest.CoreMatchers.is;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

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
	public void testQueries() throws FileNotFoundException, IOException, ClassNotFoundException {

		LocalTest.localLoad = true;
		LocalTest.debug = false;
		LocalTest.test = true;

		for(int i = 0; i < TEST_QUERIES.length; i++){
			//System.out.println("Testing: ("+(i+1)+ "/"+TEST_QUERIES.length +"): "+ TEST_QUERIES[i]);

			JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(TEST_QUERIES[i]).getEntity()));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();
			
			collector.checkThat(TEST_QUERIES[i], object.getString("url"), is(TEST_RESULTS[i]));
		}
		
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
		
		//B03
		"How many people aged 18-20 were at home on Census night in Goulburn?",	
		"How many people were at home on Census night in Goulburn?",	
		"how many men were at home on the night of the Census in Goulburn",

		
		//B04
		"What is the population of Australia?",
		"What is the population of Sandy Bay?",
		"How many people live in Sandy Bay?",
		"Population of Sandy Bay?",
		"In Sandy Bay, how many people are there?",
		"What is Sandy Bay's population?",
		"Sandy Bay's population?",
		"What is the female population of Sandy Bay?",
		"What is the population of women in Sandy Bay?",
		"How many females live in Sandy Bay?",
		"How many women live in Sandy Bay?",
		"How many women in Sandy Bay are there?",
		"Female population of Sandy Bay?",
		"In Sandy Bay, how many women are there?",
		"What is Sandy Bay's female population?",
		"Sandy Bay's female population?",
		"What is the female 15-19 population of Sandy Bay?",
		"What is the female population of Sandy Bay aged 20-24?",
		"What is the female population aged 20-24 of Sandy Bay?",
		"What is the population of women aged between 20 and 24 in Sandy Bay?",				
		"How many 20-24 year old females live in Sandy Bay?",
		"How many females  20-24 year old live in Sandy Bay?",
		"How many females aged 20-24 live in Sandy Bay?",
		"How many females aged 20 to 24 live in Sandy Bay?",
		"How many women aged between 20 and 24 live in Sandy Bay?",
		"How many women 20-24 in Sandy Bay are there?",
		"In Sandy Bay, how many women aged 20-24 are there?",
		"What is Sandy Bay's 20-24 female population?",
		"Sandy Bay's female population aged 20-24 years?",
		"What is the population of Sandy Bay women aged 20-24?",


		//B05		
		"How many married men aged 25-30 are in Braidwood?",
		"How many women aged 25-30 are separated?",
		"How many divorced men are there in Braidwood?",
		"How many widows aged 25-30 are in Braidwood?",
		//"How many women have never been married?", 
		"How many people aged 25-30 are married?",
		"How many men in Braidwood are separated?",
		"How many people are divorced?",
		
		"How many widowed women aged 25-30 are in Braidwood?",
		"How many men aged 25-30 have never married?",
		"How many married men are there in Braidwood?",
		"How many separated persons aged 25-30 are in Braidwood?",
		"How many divorced women are there?",
		"How many widows aged 25-30 are there?",
		//"How many women have never married in Braidwood?",
		"How many people are married?",

		"How many married 25-30 women are in Braidwood?",
		"How many females married in Braidwood are 25-30?",
		"How many married females in Braidwood are between 25 and 30 years old?",
		"How many married women are in Braidwood?",
		"How many women are separated in Braidwood?",
		"How many women are divorced in Braidwood?",
		"How many widowed women are in Braidwood?",
		"How many women in Braidwood are married?", 
		"How many women in Braidwood are divorced?", 
		"How many women in Braidwood aged 25-30 are divorced",


		//B06
		"How many men are living in a de facto relationship in Braidwood?", 
		"How many people are living in a de facto relationship?", 
		"How many females are in a de facto relationship in Braidwood?", 
		"How many people have a registered marriage in Braidwood?", 
		"How many registered marriages are there in Braidwood?", 

		//B07
		"What is the indigenous population of men aged 35-40 in Goulburn?", 
		"How many women aged 35-40 are indigenous?", 
		"How many men in Goulburn are indigenous?", 
		"How many people aged 35-40 in Goulburn are indigenous?", 
		"How many indigenous 35-40 year olds in Goulburn are female?", 
//		"How many indigenous 35-40 year olds are there in Australia?",
		"How many men aged 35-40 in Goulburn are indigenous?", 
		"How many indigenous males are there in Braidwood?", 

		//B09
		"What is the population of Tasmania born in vietnam",
		"How many women in Australia were born in Germany?",
		"How many people in Goulburn were born in China?",
		"How many men born in the US are in Goulburn?",
		"How many men in Goulburn are from India?",  


		//B13
		"How many people speak Iranian in Goulburn?",
		"In Goulburn, how many men speak Cantonese?",
		"How many women in Goulburn only speak English?",
		"How many women speak Arabic in Australia?",
		"How many women speak Arabic?",
		"How many women speak Arabic in Goulburn-Yass?",


		//B14
		"How many men in Goulburn are muslims?",
		"How many Pentecostal men are there in Goulburn?",
		"How many mormons in Goulburn?",
		"How many women in Goulburn have no religion?",
		"How many women follow Buddhism in Goulburn?",

		//B18
		"How many people aged 20-25 need assistance in Australia?",

		//B19
		"How many women aged 45-50 in New South Wales did volunteer work?",
		"How many men aged 20-25 in Goulburn did volunteer work?",

		//B21
		"How many women aged 15-19 provided unpaid assistance in Australia?",
		"How many women provided unpaid assistance in Australia?",

		//B40
		"How many women in Capital Region have a graduate diploma?",
		"How many women have a bachelor degree in Capital Region?",
		"How many people 15-24 have a diploma in Goulburn",
		"How many women 25-34 in Goulburn have a postgraduate degree",
		"How many 35-44 year old men in Goulburn have a bachelors degree ",

		//B41
		"How many women aged 25-30 in Goulburn have studied science?",
		"How many women in Goulburn have an engineering degree?",
		"How many men aged 65-80 in Goulburn have studied architecture?",
		"How many men in Goulburn have a commerce degree?",
		
		//B42
		"How many people aged 35-40 in Goulburn were employed?",
		"How many people aged 35-40 in Goulburn were in employment?",
		"How many men in Goulburn were in full time employment?",
		"How many women in Goulburn were in the labour force?",
		"How many people in Goulburn were in employment?",
		"How many people in Goulburn were in part time employment?",
		"How many women were in Goulburn in the labour force?",
		"How many people in Goulburn were part time employment?", 
		"How many people in Goulburn were employed part time?", 
		"How many men in Goulburn were full-time employed?",


		//B43
		"How many men aged 35-40 in Queanbeyan work in agriculture?",
		"How many men aged 35-40 in Queanbeyan work in mining?",
		"How many men aged 35-40 in Queanbeyan work in manufacturing?",
		"How many men aged 35-40 in Queanbeyan work in utilities?",
		"How many men aged 35-40 in Queanbeyan work in construction?",

		"How many men aged 35-40 in Queanbeyan are in the agricultural sector?", 
		"How many men aged 35-40 in Queanbeyan are in the mining sector?", 
		"How many men from the manufacturing industry in Queanbeyan are aged 35-40?", 
		"How many men from the utilities industry in Queanbeyan are aged 35-40?", 
		"How many men from the construction industry in Queanbeyan are aged 35-40?", 
		"How many women aged 18-19 in Tasmania are community workers?",
		
		//B45
		"How many men aged 35-40 in Braidwood are managers?",
		"How many females aged 20 to 25 in Tasmania are Managers?", 
		"How many 20-25 year old females in Tasmania are professionals?", 
		"How many 20-25 year old women in Tasmania are technicians?", 
		"How many women aged 20-25 in Tasmania are community workers?", 
		"How many women aged 20-25 in Tasmania are labourers?"
	};

	private static final String[] TEST_RESULTS = {	
		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/T15.1.1.SA2.101011001.A/ABS", //How many people aged 18-20 were at home on Census night in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B03/TT.1.1.SA2.101011001.A/ABS", //How many people were at home on Census night in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/1.TT.1.SA2.101011001.A/ABS", //how many men were at home on the night of the Census in Goulburn
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.0.AUS.0.A/ABS", //What is the population of Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS", //What is the population of Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS", //How many people live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS", //Population of Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS", //In Sandy Bay, how many people are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS", //What is Sandy Bay's population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/3.TT.6.SA2.601051031.A/ABS", //Sandy Bay's population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //What is the female population of Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //What is the population of women in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //How many females live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //How many women live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //How many women in Sandy Bay are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //Female population of Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //In Sandy Bay, how many women are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //What is Sandy Bay's female population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.TT.6.SA2.601051031.A/ABS", //Sandy Bay's female population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.19+17+18+15+16.6.SA2.601051031.A/ABS", //What is the female 15-19 population of Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //What is the female population of Sandy Bay aged 20-24?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //What is the female population aged 20-24 of Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //What is the population of women aged between 20 and 24 in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //How many 20-24 year old females live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //How many females  20-24 year old live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //How many females aged 20-24 live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //How many females aged 20 to 24 live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //How many women aged between 20 and 24 live in Sandy Bay?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //How many women 20-24 in Sandy Bay are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //In Sandy Bay, how many women aged 20-24 are there?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //What is Sandy Bay's 20-24 female population?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //Sandy Bay's female population aged 20-24 years?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B04/2.22+23+24+21+20.6.SA2.601051031.A/ABS", //What is the population of Sandy Bay women aged 20-24?

		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.5.1.SA2.101021007.A/ABS",	//		"How many married men aged 25-30 are in Braidwood?",	46
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.4.0.AUS.0.A/ABS",		//"How many women aged 25-30 are separated?", 38274
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.3.1.SA2.101021007.A/ABS",		//"How many divorced men are there in Braidwood?",137
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.2.1.SA2.101021007.A/ABS",		//"How many widows aged 25-30 are in Braidwood?",0
		//"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.0.AUS.0.A/ABS",		//"How many women have never been married?",	2753427
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.5.0.AUS.0.A/ABS",		//"How many people aged 25-30 are married?",1198562
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.4.1.SA2.101021007.A/ABS",		//"How many men in Braidwood are separated?",50
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.3.0.AUS.0.A/ABS",		//"How many people are divorced?",1460900
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.2.1.SA2.101021007.A/ABS",		//"How many widowed women aged 25-30 are in Braidwood?",0
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.T25.1.0.AUS.0.A/ABS",		//"How many men aged 25-30 have never married?",895608
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/1.TT.5.1.SA2.101021007.A/ABS",		//"How many married men are there in Braidwood?",	723
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.4.1.SA2.101021007.A/ABS",		//"How many separated persons aged 25-30 are in Braidwood?",7
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.0.AUS.0.A/ABS",		//"How many divorced women are there?",835077
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.T25.2.0.AUS.0.A/ABS",		//"How many widows aged 25-30 are there?",4616
		//"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.1.1.SA2.101021007.A/ABS",		//"How many women have never married in Braidwood?", xx
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.0.AUS.0.A/ABS",		//"How many people are married?",8461114
		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101021007.A/ABS", //How many married 25-30 women are in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101021007.A/ABS", //How many females married in Braidwood are 25-30?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.5.1.SA2.101021007.A/ABS", //How many married females in Braidwood are between 25 and 30 years old?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.1.SA2.101021007.A/ABS", //How many married women are in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.4.1.SA2.101021007.A/ABS", //How many women are separated in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.1.SA2.101021007.A/ABS", //How many women are divorced in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.2.1.SA2.101021007.A/ABS", //How many widowed women are in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.5.1.SA2.101021007.A/ABS", //How many women in Braidwood are married?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.TT.3.1.SA2.101021007.A/ABS", //How many women in Braidwood are divorced?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/2.T25.3.1.SA2.101021007.A/ABS", //How many women in Braidwood aged 25-30 are divorced
		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/1.TT.2.1.SA2.101021007.A/ABS", 	// "How many men are living in a de facto relationship in Braidwood?",125 
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.2.0.AUS.0.A/ABS",			// "How many people are living in a de facto relationship?", 1476370
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/2.TT.2.1.SA2.101021007.A/ABS", 	// How many females are in a de facto relationship in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B06/3.TT.1.1.SA2.101021007.A/ABS", 	// How many people have a registered marriage in Braidwood?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B05/3.TT.5.1.SA2.101021007.A/ABS",	// "How many registered marriages are there in Braidwood?", 1427

		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.1.SA2.101011001.A/ABS",	// "What is the indigenous population of men aged 35-40 in Goulburn?", 27
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.0.AUS.0.A/ABS",			// "How many women aged 35-40 are indigenous?", 17838
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101011001.A/ABS",	// "How many men in Goulburn are indigenous?", 350
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.A35.4.1.SA2.101011001.A/ABS",	// "How many people aged 35-40 in Goulburn are indigenous?", 45
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/2.A35.4.1.SA2.101011001.A/ABS",	// "How many indigenous 35-40 year olds in Goulburn are female?", 18
//		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/3.A35.4.0.AUS.0.A/ABS",			// How many indigenous 35-40 year olds are there?", 34072"
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.A35.4.1.SA2.101011001.A/ABS", 	// How many men aged 35-40 in Goulburn are indigenous?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B07/1.TT.4.1.SA2.101021007.A/ABS", 	// How many indigenous males are there in Braidwood?
		
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.5105.6.STE.6.A/ABS", //What is the population of Tasmania born in vietnam
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/2.2304.0.AUS.0.A/ABS", //How many women in Australia were born in Germany?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/3.6101.1.SA2.101011001.A/ABS", //How many people in Goulburn were born in China?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.8104.1.SA2.101011001.A/ABS", //How many men born in the US are in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B09/1.7103.1.SA2.101011001.A/ABS", //How many men in Goulburn are from India?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/3.41.1.SA2.101011001.A/ABS", //How many people speak Iranian in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/1.7101.1.SA2.101011001.A/ABS", //In Goulburn, how many men speak Cantonese?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.1.1.SA2.101011001.A/ABS", //How many women in Goulburn only speak English?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.0.AUS.0.A/ABS", //How many women speak Arabic in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.0.AUS.0.A/ABS", //How many women speak Arabic?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B13/2.4202.1.SA3.10101.A/ABS", //How many women speak Arabic in Goulburn-Yass?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.4.1.SA2.101011001.A/ABS", //How many men in Goulburn are muslims?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/1.24.1.SA2.101011001.A/ABS", //How many Pentecostal men are there in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/3.215.1.SA2.101011001.A/ABS", //How many mormons in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.7.1.SA2.101011001.A/ABS", //How many women in Goulburn have no religion?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B14/2.101.1.SA2.101011001.A/ABS", //How many women follow Buddhism in Goulburn?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B18/3.A20.1.0.AUS.0.A/ABS", //How many people aged 20-25 need assistance in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/2.T45.2.1.STE.1.A/ABS", //How many women aged 45-50 in New South Wales did volunteer work?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B19/1.A20.2.1.SA2.101011001.A/ABS", //How many men aged 20-25 in Goulburn did volunteer work?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.O15+A15.2.0.AUS.0.A/ABS", //How many women aged 15-19 provided unpaid assistance in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B21/2.O15.2.0.AUS.0.A/ABS", //How many women provided unpaid assistance in Australia?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/2.O15.2.1.SA4.101.A/ABS", //How many women in Capital Region have a graduate diploma?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/2.O15.3.1.SA4.101.A/ABS", //How many women have a bachelor degree in Capital Region?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/3.O15+T15.2.1.SA2.101011001.A/ABS", //How many people 15-24 have a diploma in Goulburn
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/2.T25.1.1.SA2.101011001.A/ABS", //How many women 25-34 in Goulburn have a postgraduate degree
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B40/1.T35.3.1.SA2.101011001.A/ABS", //How many 35-44 year old men in Goulburn have a bachelors degree 
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/01.2.T25.1.SA2.101011001.A/ABS", //How many women aged 25-30 in Goulburn have studied science?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/03.2.O15.1.SA2.101011001.A/ABS", //How many women in Goulburn have an engineering degree?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/04.1.T65.1.SA2.101011001.A/ABS", //How many men aged 65-80 in Goulburn have studied architecture?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B41/08.1.O15.1.SA2.101011001.A/ABS", //How many men in Goulburn have a commerce degree?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.1.SA2.101011001.A/ABS", //How many people aged 35-40 in Goulburn were employed?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.T35.EMP.1.SA2.101011001.A/ABS", //How many people aged 35-40 in Goulburn were in employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.1.1.SA2.101011001.A/ABS", //How many men in Goulburn were in full time employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/2.O15.LF.1.SA2.101011001.A/ABS", //How many women in Goulburn were in the labour force?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.EMP.1.SA2.101011001.A/ABS", //How many people in Goulburn were in employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS", //How many people in Goulburn were in part time employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/2.O15.LF.1.SA2.101011001.A/ABS", //How many women were in Goulburn in the labour force?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS", //How many people in Goulburn were part time employment?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/3.O15.2.1.SA2.101011001.A/ABS", //How many people in Goulburn were employed part time?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B42/1.O15.EMP.1.SA2.101011001.A/ABS", //How many men in Goulburn were full-time employed?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.A.1.SA3.10102.A/ABS", //How many men aged 35-40 in Queanbeyan work in agriculture?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.B.1.SA3.10102.A/ABS", //How many men aged 35-40 in Queanbeyan work in mining?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.C.1.SA3.10102.A/ABS", //How many men aged 35-40 in Queanbeyan work in manufacturing?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.D.1.SA3.10102.A/ABS", //How many men aged 35-40 in Queanbeyan work in utilities?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.E.1.SA3.10102.A/ABS", //How many men aged 35-40 in Queanbeyan work in construction?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.A.1.SA3.10102.A/ABS", //How many men aged 35-40 in Queanbeyan are in the agricultural sector?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.B.1.SA3.10102.A/ABS", //How many men aged 35-40 in Queanbeyan are in the mining sector?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.C.1.SA3.10102.A/ABS", //How many men from the manufacturing industry in Queanbeyan are aged 35-40?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.D.1.SA3.10102.A/ABS", //How many men from the utilities industry in Queanbeyan are aged 35-40?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B43/1.T35.E.1.SA3.10102.A/ABS", //How many men from the construction industry in Queanbeyan are aged 35-40?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A15.2.4.6.STE.6.A/ABS", //How many women aged 18-19 in Tasmania are community workers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/T35.1.1.1.SA2.101021007.A/ABS", //How many men aged 35-40 in Braidwood are managers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.1.6.STE.6.A/ABS", //How many females aged 20 to 25 in Tasmania are Managers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.2.6.STE.6.A/ABS", //How many 20-25 year old females in Tasmania are professionals?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.3.6.STE.6.A/ABS", //How many 20-25 year old women in Tasmania are technicians?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.4.6.STE.6.A/ABS", //How many women aged 20-25 in Tasmania are community workers?
		"http://stat.abs.gov.au/restsdmx/sdmx.ashx/GetData/ABS_CENSUS2011_B45/A20.2.8.6.STE.6.A/ABS", //How many women aged 20-25 in Tasmania are labourers?


	};
}
