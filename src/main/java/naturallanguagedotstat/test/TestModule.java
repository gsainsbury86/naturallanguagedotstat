package naturallanguagedotstat.test;

import java.io.IOException;
import java.util.ArrayList;
import naturallanguagedotstat.parser.*;

public class TestModule {
	// fields
	boolean testResult;

	// constructor
	public TestModule( ){
		testResult = false;
	}
	
	// Runs all automated tests.
	public boolean runAllTests() throws IOException, ClassNotFoundException{
		boolean b = true;
		b = b && testGrammarParser01(); 
		b = b && testGrammarParser02();
		b = b && testNumericParser01(); 
		b = b && testSemanticParser01(); 
		return b;
	};
	
	
	// For all these queries the grammar parser must correctly identify the NP.obj.
	boolean testGrammarParser01(){
		boolean testResult = true; //Assumes true until a test fails.
		String testQueryValue; 
		
		String[][] testQueries = {		
			{"What is the population of Sandy Bay?","Sandy Bay"},
			{"How many people live in Sandy Bay?","Sandy Bay"},
			{"Population of Sandy Bay?","Sandy Bay"},
			{"In Sandy Bay, how many people are there?","Sandy Bay"},
			{"What is Sandy Bay's population?","Sandy Bay"},
			{"Sandy Bay's population?","Sandy Bay"},

			{"What is the female population of Sandy Bay?","Sandy Bay"},
			{"What is the population of women in Sandy Bay?","Sandy Bay"},
			{"How many females live in Sandy Bay?","Sandy Bay"},
			{"How many women live in Sandy Bay?","Sandy Bay"},
			{"How many women in Sandy Bay are there?","Sandy Bay"},
			{"Female population of Sandy Bay?","Sandy Bay"},
			{"In Sandy Bay, how many women are there?","Sandy Bay"},
			{"What is Sandy Bay's female population?","Sandy Bay"},
			{"Sandy Bay's female population?","Sandy Bay"},

			{"What is the female 20-24 population of Sandy Bay?","Sandy Bay"},
			{"What is the female population aged 20-24 of Sandy Bay?","Sandy Bay"},
			{"What is the population of women aged between 20 and 24 in Sandy Bay?","Sandy Bay"},				
			{"How many 20-24 year old females live in Sandy Bay?","Sandy Bay"},
			{"How many females  20-24 year old live in Sandy Bay?","Sandy Bay"},
			
			{"How many females aged 20-24 live in Sandy Bay?","Sandy Bay"},
			{"How many females aged 20 to 24 live in Sandy Bay?","Sandy Bay"},
			{"How many women aged between 20 and 24 live in Sandy Bay?","Sandy Bay"},
			{"How many women 20-24 in Sandy Bay are there?","Sandy Bay"},
			{"In Sandy Bay, how many women aged 20-24 are there?","Sandy Bay"},
			{"What is Sandy Bay's 20-24 female population?","Sandy Bay"},
			{"Sandy Bay's female population aged 20-24 years?","Sandy Bay"}
		};

		// The following queries fail.. ;(
		// "What is the female population of Sandy Bay aged 20-24?"
		// "What is the population of Sandy Bay women aged 20-24?"
		
		for (int i=0; i< testQueries.length; i++) {
			GrammarParser grammarParser = new GrammarParser(testQueries[i][0]);
			grammarParser.parseText();
			testQueryValue = grammarParser.convertArrayListToString(grammarParser.npObjects);
			testResult = testResult && (testQueryValue.equals(testQueries[i][1]) );
			if(!testResult) {
				grammarParser.printOutput();
				System.out.println("testGM001.UnitTest #"+i+":failed!\n Input:"+ testQueries[i][1]+"\n TestResult: "+ testQueryValue+"\n\n\n"); 
			};
			grammarParser = null;			
		};
		
		return testResult;
	}

	// For all these queries the grammar parser must correctly identify the NP.subj.
	boolean testGrammarParser02(){
		boolean testResult = true; //Assumes true until a test fails.
		String testQueryValue;
		
		String[][] testQueries = {		
			{"What is the population of Sandy Bay?","population"},
			{"How many people live in Sandy Bay?","people"},
			{"Population of Sandy Bay?","Population"},
			{"In Sandy Bay, how many people are there?","people"},
			{"What is Sandy Bay's population?","population"},
			{"Sandy Bay's population?","population"},
			
			{"What is the female population of Sandy Bay?","female population"},
			{"How many males live in Sandy Bay?","males"},
			{"How many men live in Sandy Bay?","men"},
			{"How many women in Sandy Bay are there?","women"},
			{"Female population of Sandy Bay?","Female population"},
			{"In Sandy Bay, how many men are there?","men"},
			{"What is Sandy Bay's female population?","female population"},
			{"Sandy Bay's male population?","male population"},

			{"What is the population of women in Sandy Bay?","women\npopulation"},
		};
		
		for (int i=0; i< testQueries.length; i++) {
			GrammarParser grammarParser = new GrammarParser(testQueries[i][0]);
			grammarParser.parseText();
			testQueryValue = grammarParser.convertArrayListToString(grammarParser.npSubjects);
			testResult = testResult && (testQueryValue.equals(testQueries[i][1]) );
			if(!testResult) {				
				grammarParser.printOutput();
				System.out.println("testGM002.UnitTest #"+i+":failed!\n Input:"+ testQueries[i][1]+"\n TestResult: "+ testQueryValue+"\n\n\n"); 
			};
			grammarParser = null;			
		};
		
		return testResult;
	}
	
	// For all these queries the grammar parser must correctly identify the NP.subj.
	boolean testNumericParser01(){
		boolean testResult = true; //Assumes true until a test fails.
		String testQueryValue;
		
		String[][] testQueries = {		
				{"What is Sandy Bay's 24 year old female population ?","24"},
				{"In Sandy Bay, how many females are 24?","24"},				

				{"What is the female 20-24 population of Sandy Bay?","20~24"},
				{"What is the female population aged 20 - 24 of Sandy Bay?","20~24"},
				{"What is the population of women aged between 20 and 24 in Sandy Bay?","20~24"},				
				{"How many 20 to 24 year old females live in Sandy Bay?","20~24"},
				{"How many females  20 to 24 year old live in Sandy Bay?","20~24"},
				
				{"What is the female population aged over 20 in Sandy Bay?","20+"},
				{"What is the population of women aged more than 20 in Sandy Bay?","20+"},				
				{"What is the population of women aged older than 20 in Sandy Bay?","20+"},				

				{"What is Sandy Bay's over 20 female population ?","20+"},
				{"In Sandy Bay, how many females are older than 20?","20+"},				
				{"In Sandy Bay, how many females are more than 20?","20+"},				
				

				{"What is Sandy Bay's under 24 female population ?","0~24"},
				{"In Sandy Bay, how many females are younger than 24?","0~24"},				
				{"In Sandy Bay, how many females are less than 24?","0~24"}				

		};

		ArrayList<String> items; 
		for (int i=0; i< testQueries.length; i++) {
			GrammarParser grammarParser = new GrammarParser(testQueries[i][0]);
			grammarParser.parseText();
			items = grammarParser.npSubjects;
			
			String [] words;
			testQueryValue ="";
			for (int j=0; j< items.size(); j++) {
				words = items.get(j).split("[\\s]+");
				for (int k=0; k< words.length; k++) {
					if(words[k].contains("~") || words[k].contains("+") || isaNumber(words[k] )){
						testQueryValue = words[k];
					};
				};
			};
			
			testResult = testResult && (testQueryValue.equals(testQueries[i][1]) );
			if(!testResult) {				
				grammarParser.printOutput();
				System.out.println("testNM001.UnitTest #"+i+":failed!\n Input:"+ testQueries[i][0]+"\n TestResult: "+ testQueryValue+"\n\n\n"); 
			};
			grammarParser = null;			
		};
		
		return testResult;
	}
	
	boolean testSemanticParser01() throws IOException, ClassNotFoundException{
		String[][] testQueries = {		
				{"What is Sandy Bay's 24 year old female population ?","24", "females"},
				{"In Sandy Bay, how many females are 24?","24","females"},				

				{"What is the 20-24 population of Sandy Bay?","A20","persons"},
				{"What is the male population aged 20 - 24 of Sandy Bay?","A20","males"},
				{"What is the population of women aged between 20 and 24 in Sandy Bay?","A20","females"},				
				{"How many 20 to 24 year old men live in Sandy Bay?","A20","males"},
				{"How many 20 to 24 year olds live in Sandy Bay?","A20","persons"},
				
				{"What is the population aged over 20 in Sandy Bay?","A20","persons"},
				{"What is the population of men aged more than 20 in Sandy Bay?","A20","males"},				
				{"What is the population of women aged older than 20 in Sandy Bay?","A20","females"},				

				{"What is Sandy Bay's over 20 female population ?","A20","females"},
				{"In Sandy Bay, how many males are older than 20?","A20","males"},				
				{"In Sandy Bay, how many females are more than 20?","A20","females"},				
				
				{"What is Sandy Bay's under 24 population ?","A04","persons"},
				{"In Sandy Bay, how many females are younger than 24?","A04","females"},				
				{"In Sandy Bay, how many people are less than 24?","A04","persons"}				

		};
		boolean testResult = true;
		String testQueryValue1, testQueryValue2;
		
		for(int i=0;i<testQueries.length;i++){
			String userQuery = testQueries[i][0];
			SemanticParser semanticParser = new SemanticParser(userQuery);
			semanticParser.parseText();
			testQueryValue1 = semanticParser.dimensions.get("age");
			testQueryValue2 = semanticParser.dimensions.get("measure");
			
			testResult = testResult && testQueryValue1.equals(testQueries[i][1]) && testQueryValue2.equals(testQueries[i][2]);
			if(!testResult) {				
				System.out.println(
						"testSemanticParser.UnitTest #"+i+":failed!\n Input:"
						+ testQueries[i][0]
						+"\n TestResult: "+ testQueryValue1 + "\n" + testQueryValue2 + "\n"
				); 
			};
		};
		
		return testResult;
	}
	
	
	
	
	
	private boolean isaNumber(String aString)
	{
		try {
	        Integer.parseInt( aString );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
}
