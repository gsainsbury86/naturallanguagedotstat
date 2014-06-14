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
	public void testQueries()
			throws FileNotFoundException, IOException, ClassNotFoundException {

		LocalTest.localLoad = true;
		LocalTest.debug = false;

		for(int i = 0; i < TEST_QUERIES.length; i++){
			System.out.println("Testing: ("+(i+1)+ "/"+TEST_QUERIES.length +"): "+ TEST_QUERIES[i]);

			JsonReader jsonReader = Json.createReader(new StringReader((String) service.query(TEST_QUERIES[i]).getEntity()));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();

			collector.checkThat(TEST_QUERIES[i], Integer.parseInt(
					(object.getString("result"))), is(TEST_RESULTS[i]));
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

		//B05
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
		"How many females have a de facto relationship in Braidwood?", 
		"How many people have a registered marriage in Braidwood?", 

		//B07
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

	private static final int[] TEST_RESULTS = {	
		
		//B03
		2497, 	// "How many people aged 18-20 were at home on Census night in Goulburn?",	
		20430, 	//	"How many people were at home on Census night in Goulburn?",	
		10760, 	//	"how many men were at home on the night of the Census in Goulburn",

		//B04
		21507719, //"What is the population of Australia?"

		11157, //"What is the population of Sandy Bay?",
		11157, //"How many people live in Sandy Bay?",
		11157, //"Population of Sandy Bay?",
		11157, //"In Sandy Bay, how many people are there?",
		11157, //"What is Sandy Bay's population?",
		11157, //"Sandy Bay's population?",

		5696, //"What is the female population of Sandy Bay?",
		5696, //"What is the population of women in Sandy Bay?",
		5696, //"How many females live in Sandy Bay?",
		5696, //"How many women live in Sandy Bay?",
		5696, //"How many women in Sandy Bay are there?",
		5696, //"Female population of Sandy Bay?",
		5696, //"In Sandy Bay, how many women are there?",
		5696, //"What is Sandy Bay's female population?",
		5696, //"Sandy Bay's female population?",

		355, //"What is the female 15-19 population of Sandy Bay?",  // this agegroup metadata has an extra space at the end!
		745, // "What is the female population of Sandy Bay aged 20-24?") ); //745
		745, //"What is the female population aged 20-24 of Sandy Bay?",
		745, //"What is the population of women aged between 20 and 24 in Sandy Bay?",				
		745, //"How many 20-24 year old females live in Sandy Bay?",
		745, //"How many females  20-24 year old live in Sandy Bay?",

		745, //"How many females aged 20-24 live in Sandy Bay?",
		745, //"How many females aged 20 to 24 live in Sandy Bay?",
		745, //"How many women aged between 20 and 24 live in Sandy Bay?",
		745, //"How many women 20-24 in Sandy Bay are there?",
		745, //"In Sandy Bay, how many women aged 20-24 are there?",
		745, //"What is Sandy Bay's 20-24 female population?",
		745, //"Sandy Bay's female population aged 20-24 years?"

		//B05
		60, //"How many married 25-30 women are in Braidwood?", 
		60, //"How many females married in Braidwood are 25-30?", 
		60, //"How many married females in Braidwood are between 25 and 30 years old?", 
		704, //"How many married women are in Braidwood?", 
		51, //"How many women are separated in Braidwood?", 
		150, //"How many women are divorced in Braidwood?", 
		145, //"How many widowed women are in Braidwood?", 
		704, //"How many women in Braidwood are married?", 
		150, //"How many women in Braidwood are divorced?", 
		3,	// "How many women in Braidwood aged 25-30 are divorced"


		//B06
		133, 	//"How many females have a de facto relationship in Braidwood?", 
		1235, 	//"How many people have a registered marriage in Braidwood?", 

		//B07
		27, 	//"How many men aged 35-40 in Goulburn are indigenous?", 
		22, 	//"How many indigenous males are there in Braidwood?", 

		//B09
		267, 	// "What is the population of Tasmania born in vietnam") ); //267
		56674, 	//	"How many women in Australia were born in Germany?"); // 
		45, 	// "How many people in Goulburn were born in China?"); // 45
		14,		// "How many men born in the US are in Goulburn?"); // 14
		30, 	//	"How many men in Goulburn are from India?"); // 30  

		//B13
		9,		//	"How many people speak Iranian in Goulburn?"); //9
		16,		// 	"In Goulburn, how many men speak Cantonese?");
		9979,	//	"How many women in Goulburn only speak English?"); //9979
		139945,	// 	"How many women speak Arabic in Australia?"); //139945
		139945,	// 	"How many women speak Arabic?"); //139945

		//B14
		20,		//	"How many men in Goulburn are muslims?"); // 20
		61,		// 	"How many Pentecostal men are there in Goulburn?"); //61
		26,		// 	"How many mormons in Goulburn?"); //26
		1216,	// 	"How many women in Goulburn have no religion?"); //1216
		84,		// 	"How many women follow Buddhism in Goulburn?"); //84

		//B18
		20107,	// 	"How many people aged 20-25 need assistance in Australia?"); //20107


		//B19
		102622,	// 	"How many women aged 45-50 in New South Wales did volunteer work?"); //102622
		73, 	// 	"How many men aged 20-25 in Goulburn did volunteer work?"); 

		//B21
		32277,		//	"How many women aged 15-19 provided unpaid assistance in Australia?"); //32277
		1160621,	// "How many women provided unpaid assistance in Australia?"); //1160621

		//B40
		1963,	// 	"How many women in Capital Region have a graduate diploma?"); // 1963
		9773,	// 	"How many women have a bachelor degree in Capital Region?"); // 9773
		5, 		//	"How many people 15-24 have a diploma in Goulburn");
		14,		// 	"How many women 25-34 in Goulburn have a postgraduate degree");
		90, 	// 	"How many 35-44 year old men in Goulburn have a bachelors degree ");		

		//B41
		6, 		// "How many women aged 25-30 in Goulburn have studied science ?")); //6
		53,		// "How many women in Goulburn have an engineering degree?")); // 53
		50,		// "How many men aged 65-80 in Goulburn have studied architecture?")); // 50
		404,	// "How many men in Goulburn have a commerce degree?")); // 404

		//B42
		2047, 	// "How many people aged 35-40 in Goulburn were employed?")); //2047  
		2047,	// "How many people aged 35-40 in Goulburn were in employment?")); //2047
		3726,	// "How many men in Goulburn were in full time employment?"));	//3726
		4722,	// "How many women in Goulburn were in the labour force?")); //4722
		9362,	// "How many people in Goulburn were in employment?")); //9362
		2821,	// "How many people in Goulburn were in part time employment?")); //2821
		4722,	//	"How many women were in Goulburn in the labour force?")); //4722
		2821,		// "How many people in Goulburn were part time employment?") ); 
		2821, 		// "How many people in Goulburn were employed part time?") ); 

		//B43
		43,		//	"How many men aged 35-40 in Queanbeyan work in agriculture?") ); //43
		11,		//	"How many men aged 35-40 in Queanbeyan work in mining?") ); //11
		275,	//	"How many men aged 35-40 in Queanbeyan work in manufacturing?") );	//275
		79,		//	"How many men aged 35-40 in Queanbeyan work in utilities?") );	//79
		567,	//	"How many men aged 35-40 in Queanbeyan work in construction?") );	//567

		43, 	//	"How many men aged 35-40 in Queanbeyan are in the agricultural sector?", 
		11, 	//	"How many men aged 35-40 in Queanbeyan are in the mining sector?", 
		275, 	//	"How many men from the manufacturing industry in Queanbeyan are aged 35-40?", 
		79, 	//	"How many men from the utilities industry in Queanbeyan are aged 35-40?", 
		567, 	//	"How many men from the construction industry in Queanbeyan are aged 35-40?", 
		1552, 	//	"How many women aged 18-19 in Tasmania are community workers?"); 
		
		//B45
		39, 	//	"How many men aged 35-40 in Braidwood are managers?", 
		402, 	//	"How many females aged 20 to 25 in Tasmania are Managers?", 
		1190, 	//	"How many 20-25 year old females in Tasmania are professionals?", 
		705, 	//	"How many 20-25 year old women in Tasmania are technicians?", 
		2166, 	//	"How many women aged 20-25 in Tasmania are community workers?", 
		711 	//	"How many women aged 20-25 in Tasmania are labourers?" 

	};
}
