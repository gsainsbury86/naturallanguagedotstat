package naturallanguagedotstat.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SemanticParser {
	// public fields here
	public HashMap<String, String> dimensions;

	private HashMap<String, String> codeList;
	private HashMap<String, String> synonyms;
	
	private GrammarParser grammarParser;

	// constructor
	public SemanticParser (String str){
		dimensions = new HashMap<String, String>();
		codeList   = new HashMap<String, String>();
		synonyms   = new HashMap<String, String>();
		grammarParser = new GrammarParser(str);

		initializeSynonyms();
		initializeCodeList();
	}		
	
	

	// methods (in alphabetical order - hopefully)
	
	public HashMap<String, String> getDimensions() {
		return dimensions;
	}


/*
 * 		
		ArrayList<Dataset> datasets = Service.loadDatasets_DEBUG();
		
		for(Dimension dim : datasets.get(3).getDimensions()){
			HashMap<String, String> map = dim.getCodelist();
			System.out.println(dim.getName() + " - " + map);
		}

 */
	// the codeList HashMap is almost an inverse of the dimensions HashMap.
	// this will ultimately come from either a fixed text file or from one of George's methods.
	public void initializeCodeList(){
		
		codeList.put("males","Sex");
		codeList.put("females","Sex");		
		codeList.put("persons","Sex");
		
		codeList.put("married(a)","Registered Marital Status");
		codeList.put("separated","Registered Marital Status");
		codeList.put("divorced","Registered Marital Status");
		codeList.put("widowed","Registered Marital Status");

		codeList.put("Married in a registered marriage","Social Marital Status");
		codeList.put("Married in a de facto marriage(b)","Social Marital Status");
			
		codeList.put("Indigenous(a)","Indigenous Status");

		codeList.put("Managers", "Occupation");
		codeList.put("Professionals", "Occupation");
		codeList.put("Technicians and trades workers", "Occupation");
		codeList.put("Community and personal service  workers", "Occupation");
		codeList.put("Clerical and administrative workers", "Occupation");
		codeList.put("Sales workers", "Occupation");
		codeList.put("Machinery operators and drivers", "Occupation");
		codeList.put("Labourers","Occupation");

	
		codeList.put("Agriculture, forestry and fishing","Industry of Employment");
		codeList.put("Mining","Industry of Employment");
		codeList.put("Manufacturing","Industry of Employment");
		codeList.put("Electricity, gas, water and waste services","Industry of Employment");
		codeList.put("Construction","Industry of Employment");
		codeList.put("Wholesale trade","Industry of Employment");
		codeList.put("Retail trade","Industry of Employment");
		codeList.put("Accommodation and food services","Industry of Employment");
		codeList.put("Information media and telecommunications","Industry of Employment");
		codeList.put("Rental, hiring and real estate services","Industry of Employment");
		codeList.put("Professional, scientific and technical services","Industry of Employment");
		codeList.put("Administrative and support services","Industry of Employment");
		codeList.put("Public administration and safety","Industry of Employment");
		codeList.put("Education and training","Industry of Employment");
		codeList.put("Arts and recreation services","Industry of Employment");
	};

	// This will ultimately come from a text file.
	// Many of these items cover the noun -> adjective transformation.
	public void initializeSynonyms(){		
		// .put("synonym", "rootWord");
		synonyms.put("people","persons");
		synonyms.put("female","females");
		synonyms.put("women","females");
		synonyms.put("male","males");
		synonyms.put("men","males");

		synonyms.put("aged","Age");
		synonyms.put("year","Age");
		synonyms.put("years","Age");
		synonyms.put("old","Age");
		synonyms.put("older","Age");
		synonyms.put("younger","Age");

		//B05
		synonyms.put("married","married(a)");
		synonyms.put("marriages","married(a)");
		synonyms.put("widows","widowed");
		synonyms.put("widowers","widowed");
		synonyms.put("separations","separated");
		synonyms.put("divorces","divorced");
		
		//B06
		synonyms.put("registered","Married in a registered marriage");
		synonyms.put("marriage","Married in a registered marriage");
		synonyms.put("facto","Married in a de facto marriage(b)");

		//B07
		synonyms.put("indigenous","Indigenous(a)");
		
		//B43
		synonyms.put("agriculture","Agriculture, forestry and fishing");
		synonyms.put("agricultural","Agriculture, forestry and fishing");
		synonyms.put("forestry","Agriculture, forestry and fishing");
		synonyms.put("fishing","Agriculture, forestry and fishing");
		synonyms.put("aquaculture","Agriculture, forestry and fishing");
		synonyms.put("mining","Mining");
		synonyms.put("manufacturing","Manufacturing");
		synonyms.put("utilities","Electricity, gas, water and waste services");
		synonyms.put("electricity","Electricity, gas, water and waste services");
		synonyms.put("waste","Electricity, gas, water and waste services");
		synonyms.put("construction","Construction");
		synonyms.put("wholesale","Wholesale trade");
		synonyms.put("retail","Retail trade");
		synonyms.put("accommodation","Accommodation and food services");
		synonyms.put("food","Accommodation and food services");
		synonyms.put("hospitality","Accommodation and food services");
		synonyms.put("transport","Transport, postal and warehousing");
		synonyms.put("postal","Transport, postal and warehousing");
		synonyms.put("warehousing","Transport, postal and warehousing");
		synonyms.put("technology","Information media and telecommunications");
		synonyms.put("computing","Information media and telecommunications");
		synonyms.put("information","Information media and telecommunications");
		synonyms.put("ICT","Information media and telecommunications");
		synonyms.put("IT","Information media and telecommunications");
		synonyms.put("telecommunication","Information media and telecommunications");
		synonyms.put("telecommunications","Information media and telecommunications");
		synonyms.put("financial","Financial and insurance services");
		synonyms.put("finance","Financial and insurance services");
		synonyms.put("finances","Financial and insurance services");
		synonyms.put("insurance","Financial and insurance services");
		synonyms.put("estate","Rental, hiring and real estate services");
		synonyms.put("professional","Professional, scientific and technical services");
		synonyms.put("scientific","Professional, scientific and technical services");
		synonyms.put("science","Professional, scientific and technical services");
		synonyms.put("technical","Professional, scientific and technical services");
		synonyms.put("administrative","Administrative and support services");
		synonyms.put("support","Administrative and support services");
		synonyms.put("public","Public administration and safety");
		synonyms.put("safety","Public administration and safety");
		synonyms.put("education","Education and training");
		synonyms.put("training","Education and training");
		synonyms.put("health","Health care and social assistance");
		synonyms.put("assistance","Health care and social assistance");
		synonyms.put("arts","Arts and recreation services");
		synonyms.put("recreation","Arts and recreation services");

		//B45
		synonyms.put("managers","Mangers");
		synonyms.put("professionals","Professionals");
		synonyms.put("technicians","Technicians and trades workers");
		synonyms.put("community","Community and personal service  workers");
		synonyms.put("trades","Technicians and trades workers");
		synonyms.put("trade","Technicians and trades workers");
		synonyms.put("clerical","Clerical and administrative workers");
		synonyms.put("labourers","Labourers");
		synonyms.put("administrative","Clerical and administrative workers");
		synonyms.put("sales","Sales workers");
		synonyms.put("machinery","Machinery operators and drivers");
		synonyms.put("drivers","Machinery operators and drivers");

	};


	private String getNumericString(String[] someWords){
		for(String word:someWords){
			if(word.contains("~") || word.contains("+") || isaNumber(word) ){
				return word;
			}
		};
		return null;
	}
	
	
	public void identifyDimensions(ArrayList<String> phrases){
		String[] words;
		String baseWord;
		String numericalString;
		
		for(String str: phrases){
			words = str.split("[\\s]+"); 
			
			// (Note that this code block currently assumes that all numeric data with str corresponds to the age dimension)
			numericalString = getNumericString(words);
			if(numericalString != null ){
				dimensions.put("Age", numericalString ); 
			};
			
			// check for all other dimensions
			for(String word: words){
				baseWord = (synonyms.get(word) == null ) ? word : synonyms.get(word);
				if(codeList.get(baseWord) != null){
					dimensions.put(codeList.get(baseWord), baseWord);
				};
			};			
		};
		
		if (dimensions.get("Age") ==  null){
			dimensions.put("Age", "Total all ages");
			//dimensions.put("Selected Person Characteristics", "Total Persons");
		}; 

	}
	
	private boolean isaNumber(String aString){
		try {
	        Integer.parseInt( aString );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}

	
	public void parseText(){
		grammarParser.parseText();
		setCoreDimensions(grammarParser.npObjects.get(0)); //assumes that npObjects has only 1 item in it.
		identifyDimensions(grammarParser.npSubjects);
	}



	private void setCoreDimensions(String location) {
		dimensions.put("region", location ); 
		
		// set default values for dimensions.
		dimensions.put("Sex", "persons"); 
	};
	
	
	// Prints the HashMap dimension .
	// only used for debugging and testing purposes.
	public void printOutput() {
		System.out.println("\nContents of the dimension HashMap are... ");
		Iterator iter = dimensions.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry mEntry = (Map.Entry) iter.next();
			System.out.println(mEntry.getKey() + " : " + mEntry.getValue());			
		};
	};
}
