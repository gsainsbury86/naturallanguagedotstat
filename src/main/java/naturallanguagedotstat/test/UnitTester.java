package naturallanguagedotstat.test;

import static org.junit.Assert.*;

import java.io.IOException;

import naturallanguagedotstat.Service;

import org.junit.Test;

public class UnitTester {

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		LocalTest.debug = true;

		testQueries();
	}

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

		"What is the female 20-24 population of Sandy Bay?",
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
		"Sandy Bay's female population aged 20-24 years?"
	};

	private static final int[] TEST_RESULTS = {		
		21507719, //"What is the population of Australia?"
		
		11156, //"What is the population of Sandy Bay?",
		11156, //"How many people live in Sandy Bay?",
		11156, //"Population of Sandy Bay?",
		11156, //"In Sandy Bay, how many people are there?",
		11156, //"What is Sandy Bay's population?",
		11156, //"Sandy Bay's population?",

		5695, //"What is the female population of Sandy Bay?",
		5695, //"What is the population of women in Sandy Bay?",
		5695, //"How many females live in Sandy Bay?",
		5695, //"How many women live in Sandy Bay?",
		5695, //"How many women in Sandy Bay are there?",
		5695, //"Female population of Sandy Bay?",
		5695, //"In Sandy Bay, how many women are there?",
		5695, //"What is Sandy Bay's female population?",
		5695, //"Sandy Bay's female population?",

		745, //"What is the female 20-24 population of Sandy Bay?",
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
	};

	@Test
	public static void testQueries() throws IOException, ClassNotFoundException {

		//TODO: Add queries for SA3s and SA2s.
		Service service = new Service();

		for(int i = 0; i < TEST_QUERIES.length; i++){
			try{
				assertEquals(TEST_QUERIES[i], TEST_RESULTS[i], Integer.parseInt(service.query(TEST_QUERIES[i])));
			}catch(Exception e){
				System.err.println("Test " + i + " :" + e);
			}
		}


	} 
}
