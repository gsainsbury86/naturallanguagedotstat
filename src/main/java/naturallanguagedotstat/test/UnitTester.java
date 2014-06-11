package naturallanguagedotstat.test;

import static org.hamcrest.CoreMatchers.is;

import java.io.FileNotFoundException;
import java.io.IOException;

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
			System.out.println("Testing: ("+i+ "/"+TEST_QUERIES.length +"): "+ TEST_QUERIES[i]);
			collector.checkThat(TEST_QUERIES[i], Integer.parseInt(service.query(TEST_QUERIES[i])), is(TEST_RESULTS[i]));
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

		"How many married 25-30 women are in Braidwood?",
		"How many females married in Braidwood are 25-30?",
		"How many married females in Braidwood are between 25 and 30 years old?",

		"How many married women are in Braidwood?",
		"How many women are separated in Braidwood?",
		"How many women are divorced in Braidwood?",
		"How many widowed women are in Braidwood?",
		"How many women in Braidwood are married?", 
		"How many women in Braidwood are divorced?", 

		"How many females have a de facto relationship in Braidwood?", 
		"How many people have a registered marriage in Braidwood?", 

		"How many indigenous males are there in Braidwood?", 

		"How many men aged 35-40 in Braidwood are managers?", 

		"How many men aged 35-40 in Goulburn are indigenous?", 

		"How many men aged 35-40 in Queanbeyan are in the agricultural sector?", 
		"How many men aged 35-40 in Queanbeyan are in the mining sector?", 
		"How many men from the manufacturing industry in Queanbeyan are aged 35-40?", 
		"How many men from the utilities industry in Queanbeyan are aged 35-40?", 
		"How many men from the construction industry in Queanbeyan are aged 35-40?", 

		"How many females aged 20 to 25 in Tasmania are Managers?", 
		"How many 20-25 year old females in Tasmania are professionals?", 
		"How many 20-25 year old women in Tasmania are technicians?", 
		"How many women aged 20-25 in Tasmania are community workers?", 
		"How many women aged 20-25 in Tasmania are labourers?", 
		
		"How many women are aged 15-20 in Tasmania?",
		"How many women aged 18-19 in Tasmania are community workers?",
		"How many men aged 20-25 did volunteer work in Goulburn?",
		"How many people 15-24 have a diploma in Goulburn",
		"How many women 25-34 in Goulburn have a postgraduate degree",
		"How many 35-44 year old men in Goulburn have a bachelors degree ",

		//B03
		"How many persons aged 18-20 were at home on Census night in Goulburn?",	

		//B09
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
		"How many women aged 45-50 did volunteer work in New South Wales?",
		"How many women aged 45-50 did volunteer work in New South Wales?",
		
		//B21
		"How many women aged 15-19 provided unpaid assistance in Australia?",
		"How many women provided unpaid assistance in Australia?",
		
		//B40
		"How many women 25-34 in Goulburn have a postgraduate degree",
		"How many women in Capital Region have a graduate diploma?",
		"How many women have a bachelor degree in Capital Region?"

		
	};

	private static final int[] TEST_RESULTS = {		
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

		60, //"How many married 25-30 women are in Braidwood?", 
		60, //"How many females married in Braidwood are 25-30?", 
		60, //"How many married females in Braidwood are between 25 and 30 years old?", 

		704, //"How many married women are in Braidwood?", 
		51, //"How many women are separated in Braidwood?", 
		150, //"How many women are divorced in Braidwood?", 
		145, //"How many widowed women are in Braidwood?", 
		704, //"How many women in Braidwood are married?", 
		150, //"How many women in Braidwood are divorced?", 

		133, //"How many females have a de facto relationship in Braidwood?", 
		1235, //"How many people have a registered marriage in Braidwood?", 

		22, //"How many indigenous males are there in Braidwood?", 

		39, //"How many men aged 35-40 in Braidwood are managers?", 

		27, //"How many men aged 35-40 in Goulburn are indigenous?", 

		43, //"How many men aged 35-40 in Queanbeyan are in the agricultural sector?", 
		11, //"How many men aged 35-40 in Queanbeyan are in the mining sector?", 
		275, //"How many men from the manufacturing industry in Queanbeyan are aged 35-40?", 
		79, //"How many men from the utilities industry in Queanbeyan are aged 35-40?", 
		567, //"How many men from the construction industry in Queanbeyan are aged 35-40?", 

		402, //"How many females aged 20 to 25 in Tasmania are Managers?", 
		1190, //"How many 20-25 year old females in Tasmania are professionals?", 
		705, //"How many 20-25 year old women in Tasmania are technicians?", 
		2166, //"How many women aged 20-25 in Tasmania are community workers?", 
		711, //"How many women aged 20-25 in Tasmania are labourers?" 
		
		15846,  // service.query("How many women are aged 15-20 in Tasmania?");
		1552, // service.query("How many women aged 18-19 in Tasmania are community workers?"); 
		73, // service.query("How many men aged 20-25 did volunteer work in Goulburn?"); 
		5, //service.query("How many people 15-24 have a diploma in Goulburn");
		14,// service.query("How many women 25-34 in Goulburn have a postgraduate degree");
		90, // service.query("How many 35-44 year old men in Goulburn have a bachelors degree ");

		//B03
		2497,	//service.query("How many persons aged 18-20 were at home on Census night in Goulburn?" ); //		

		//B09
		56674, 	//service.query("How many women in Australia were born in Germany?"); // 
		45, // service.query("How many people in Goulburn were born in China?"); // 45
		14,	// service.query("How many men born in the US are in Goulburn?"); // 14
		30, 	//service.query("How many men in Goulburn are from India?"); // 30  

		//B13
		9,		//service.query("How many people speak Iranian in Goulburn?"); //9
		16,		// service.query("In Goulburn, how many men speak Cantonese?");
		9979,	//service.query("How many women in Goulburn only speak English?"); //9979
		139945,	// service.query("How many women speak Arabic in Australia?"); //139945
		139945,	// service.query("How many women speak Arabic?"); //139945

		//B14
		20,		//service.query("How many men in Goulburn are muslims?"); // 20
		61,		// service.query("How many Pentecostal men are there in Goulburn?"); //61
		26,		// service.query("How many mormons in Goulburn?"); //26
		1216,	// service.query("How many women in Goulburn have no religion?"); //1216
		84,		// service.query("How many women follow Buddhism in Goulburn?"); //84

		//B18
		20107,	// service.query("How many people aged 20-25 need assistance in Australia?"); //20107

		//B19
		102622,	// service.query("How many women aged 45-50 did volunteer work in New South Wales?"); //102622
		102622,	// service.query("How many women aged 45-50 did volunteer work in New South Wales?"); //102622
		
		//B21
		32277,	// service.query("How many women aged 15-19 provided unpaid assistance in Australia?"); //32277
		1160621,	// service.query("How many women provided unpaid assistance in Australia?"); //1160621
		
		//B40
		14,	//	service.query("How many women 25-34 in Goulburn have a postgraduate degree");
		1963,	// service.query("How many women in Capital Region have a graduate diploma?"); // 1963
		9773	// service.query("How many women have a bachelor degree in Capital Region?"); // 9773

		
	};
}
