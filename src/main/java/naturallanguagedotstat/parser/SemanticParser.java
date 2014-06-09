package naturallanguagedotstat.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;


public class SemanticParser {
	// public fields here
	public HashMap<String, String> dimensions;

	private HashMap<String, String> flatCodeList;
	private HashMap<String, String> codeList;
	private HashMap<String, String> synonyms;
	
	private GrammarParser grammarParser;

	// constructor
	public SemanticParser (String str) throws IOException, ClassNotFoundException{
		flatCodeList = new HashMap<String, String>();
		dimensions = new HashMap<String, String>();
		codeList   = new HashMap<String, String>();
		synonyms   = new HashMap<String, String>();
		grammarParser = new GrammarParser(str);

		initializeSynonyms();
		createFlatCodeList(); // replaces initializeCodeList();	
		
		// System.out.println(flatCodeList.get("India") );

	}		
	
	

	// methods (in alphabetical order - hopefully)
	
	public HashMap<String, String> getDimensions() {
		return dimensions;
	}

	private void createFlatCodeList() throws IOException, ClassNotFoundException{
		Service service = new Service();
		boolean getData;
		ArrayList<Dataset>  datasets = service.loadDatasets();
		for(int i=0;i<46;i++){
			for(Dimension dim : datasets.get(i).getDimensions() ){
				HashMap<String, String> map = dim.getCodelist();
				getData = (dim.getName().equals("Age")  || dim.getName().equals("Region") || dim.getName().equals("Region Type") || dim.getName().equals("State") )? false : true;
				if (getData) {
					for(String key : map.keySet() ){
						flatCodeList.put(map.get(key), dim.getName() );
					}
				}
			}
		};
	}
	

	// This will ultimately come from a text file.
	// These mappings include:
	// 		* noun -> adjective , 
	// 		* singlualr -> generic plural, 
	// 		* abbreviations -> full, 
	// 		* component -> part of a group, 

	public void initializeSynonyms(){		
		
		
		/*

		// Eventually do synonyms to confirm identity of Dimensions.
		// However, currently we identify synonyms based purely on the identification of codelists.
		  
		synonyms.put("aged","Age");
		synonyms.put("year","Age");
		synonyms.put("years","Age");
		synonyms.put("old","Age");
		synonyms.put("older","Age");
		synonyms.put("younger","Age");
		
		synonyms.put("born","Country of Birth of Person");
		synonyms.put("birth","Country of Birth of Person");

		 */

		// .put("synonym", "rootWord");
		synonyms.put("people","Persons");
		synonyms.put("female","Females");
		synonyms.put("females","Females");
		synonyms.put("women","Females");
		synonyms.put("male","Males");
		synonyms.put("males","Males");
		synonyms.put("men","Males");

		
		//B05
		synonyms.put("married","Married(a)" ) ;
		synonyms.put("marriages","Married(a)" );
		synonyms.put("widowed","Widowed" );
		synonyms.put("widows","Widowed" );
		synonyms.put("widowers","Widowed" );
		synonyms.put("separated","Separated" );
		synonyms.put("separations","Separated" );
		synonyms.put("divorces","Divorced" );
		synonyms.put("divorced","Divorced" );
		
		//B06
		synonyms.put("registered","Married in a registered marriage");
		synonyms.put("marriage","Married in a registered marriage");
		synonyms.put("facto","Married in a de facto marriage(b)");

		//B07
		synonyms.put("indigenous","Indigenous(a)");
		
		//B09
		synonyms.put("China","China (excl. SARs and Taiwan)(b)");

		synonyms.put("Yugoslavia","Former Yugoslav Republic of Macedonia (FYROM)");
		synonyms.put("Hong","Hong Kong (SAR of China)(b)");
		synonyms.put("Kong","Hong Kong (SAR of China)(b)");
		synonyms.put("Korea","Korea, Republic of (South)");
		// synonyms.put("China","South Eastern Europe, nfd(c)");
		synonyms.put("Kingdom","United Kingdom, Channel Islands and Isle of Man(d)");
		synonyms.put("UK","United Kingdom, Channel Islands and Isle of Man(d)");
		synonyms.put("USA","United States of America");
		synonyms.put("America","United States of America");
		
		//B19
		synonyms.put("volunteer","Volunteer");
		synonyms.put("voluntary","Volunteer");

		//B40
		synonyms.put("postgraduate","Postgraduate Degree Level");
		synonyms.put("postgraduates","Postgraduate Degree Level");
		synonyms.put("diploma","Graduate Diploma and Graduate Certificate Level");
		synonyms.put("bachelor","Bachelor Degree Level");
		synonyms.put("bachelors","Bachelor Degree Level");
		
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
		synonyms.put("managers","Managers");
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

	//Returns the location of the first number in an array of Strings
	int getLocationOfNumber(String[] words){
		for (int i=0; i< words.length; i++) {
			  if(isNumber(words[i] )) {return i;};
		};	
		return -1;
	}
	
	private String getNumericString(String[] someWords){
		String str = new String();
		
		int j= getLocationOfNumber (someWords);
		if(j==-1) return str;
		str = someWords[j-1] + " " + someWords[j];

		if (someWords[j-1].equals("∈") ) {
			str = str + " " + someWords[j+1];
		};
		
		return str;
	}
	
	
	public void identifyDimensions(ArrayList<String> phrases){
		String[] words;
		String baseWord;
		String numericalString;
		
		for(String str: phrases){
			words = str.split("[\\s]+"); 
			
			// (Note that this code block currently assumes that all numeric data with str corresponds to the age dimension)
			numericalString = getNumericString(words);
			if(numericalString.length() > 0){
				dimensions.put("Age", numericalString ); 		
			};
			
			// check for all other dimensions
			for(String word: words){
				baseWord = (synonyms.get(word.toLowerCase()) == null ) ? word : synonyms.get(word.toLowerCase());
				if(flatCodeList.get(baseWord) != null){
					dimensions.put(flatCodeList.get(baseWord), baseWord);
				};
			};			
		};
		
		if (dimensions.get("Age") ==  null){
			dimensions.put("Age", "Total all ages");
			//dimensions.put("Selected Person Characteristics", "Total Persons");
		}; 

	}
	
	private boolean isNumber(String aString){
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
	
}
