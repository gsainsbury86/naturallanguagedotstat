package naturallanguagedotstat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SemanticParser {
	// public fields here
	public HashMap<String, String> dimensions;

	private ArrayList<String> phrases; 
	private HashMap<String, String> codeList;
	private HashMap<String, String> synonyms;
	
	private GrammarParser grammarParser;

	// constructor
	SemanticParser (String str){
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



	// the codeList HashMap is almost an inverse of the dimensions HashMap.
	// this will ultimately come from either a fixed text file or from one of George's methods.
	public void initializeCodeList(){		
		codeList.put("males","Sex");
		codeList.put("females","Sex");		
		codeList.put("persons","Sex");

		codeList.put("married","Registered Marital Status");
		codeList.put("separated","Registered Marital Status");
		codeList.put("divorced","Registered Marital Status");
		codeList.put("widowed","Registered Marital Status");

		codeList.put("indigenous","Indigenous Status");
		codeList.put("aboriginal","Indigenous Status");
		codeList.put("torres strait islander","Indigenous Status");

	};

	// This will ultimately come from a text file.
	// Many of these items cover the noun -> adjective transformation.
	public void initializeSynonyms(){		
		// .put("synonym", "rootWord");
		
		// Synonyms associated with the code-list of "Measure"
		
		codeList.put("Torres","Torres Strait Islander");
		codeList.put("Islander","Torres Strait Islander");
		codeList.put("aborigines","aboriginal");

		synonyms.put("marriages","married");
		synonyms.put("widows","widowed");
		synonyms.put("widowers","widowed");
		synonyms.put("separations","widowed");
		synonyms.put("divorces","divorced");
		
		synonyms.put("people","persons");
		synonyms.put("female","females");
		synonyms.put("women","females");
		synonyms.put("male","males");
		synonyms.put("men","males");

		synonyms.put("aged","age");
		synonyms.put("year","age");
		synonyms.put("years","age");
		synonyms.put("old","age");
		synonyms.put("older","age");
		synonyms.put("younger","age");
	};


	private String getNumericString(String[] someWords){
		for(String word:someWords){
			if(word.contains("~") || word.contains("+") || isaNumber(word) ){
				return word;
			}
		};
		return null;
	}
	
	public String getAgeCode(String str){
		if (isaNumber(str)){return str;};

		String[] numberStrings = str.split("[~+]+"); 
		int i = Integer.parseInt(numberStrings[0]);
		int i5 = i/5;
		
		if(i<5)		{return "A04";};
		if(i<1)		{return "A59";};
		if(i<100) 	{return "A"+Integer.toString(5*i5);};		
		return "A100";
	}
	
	
	
	public void identifyDimensions(ArrayList<String> phrases){
		String[] words;
		String baseWord;
		String numericalString;
		
		for(String str: phrases){
			words = str.split("[\\s]+"); 
			
			// (Note that this code block currently assumes that all numeric data with str corresponds to the age dimension)
			numericalString = getNumericString(words);
;			if(numericalString != null ){
				dimensions.put("age", getAgeCode(numericalString) ); 
			};
			
			// check for all other dimensions
			for(String word: words){
				baseWord = (synonyms.get(word) == null ) ? word : synonyms.get(word);
				if(codeList.get(baseWord) != null){
					dimensions.put(codeList.get(baseWord), baseWord);
				};
			};			
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
		dimensions.put("Selected Person Characteristics", "Total Persons"); 
		
		// set default values for dimensions.
		dimensions.put("Sex", "3"); 
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
