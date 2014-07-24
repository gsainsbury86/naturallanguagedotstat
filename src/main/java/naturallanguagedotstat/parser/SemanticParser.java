package naturallanguagedotstat.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import naturallanguagedotstat.Service;
import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;


public class SemanticParser {
	// public fields here
	public HashMap<String, String> dimensions;

	private HashMap<String, String> flatCodeList;
	public static LinkedHashMap<String, String> synonyms;
	private Dimension ASGS2011;

	private GrammarParser grammarParser;
	
	// constructor
	public SemanticParser (String str, Dimension ASGS2011) throws IOException, ClassNotFoundException{
		flatCodeList = new HashMap<String, String>();
		dimensions = new HashMap<String, String>();
		grammarParser = new GrammarParser(str);
		
		this.ASGS2011 = ASGS2011;		
		
		
		createFlatCodeList(Service.datasets); 
	}		

	public HashMap<String, ArrayList<String>> getDimensions() {

		HashMap<String, ArrayList<String>> toReturn = new HashMap<String, ArrayList<String>>();
		
		for(String key : dimensions.keySet()){
			ArrayList<String> list = new ArrayList<String>();
			list.add(dimensions.get(key));
			toReturn.put(key,list);
		}
				
		return toReturn;
	}

	private void createFlatCodeList(ArrayList<Dataset> datasets) throws IOException, ClassNotFoundException{
		for(Dataset dataset : datasets){
			for(Dimension dim : dataset.getDimensions() ){
				HashMap<String, String> map = dim.getCodelist();
				if (!dim.getName().equals("Age") &&  !dim.getName().equals("Region Type") &&  !dim.getName().equals("State")   && map != null) {
					for(String key : map.keySet()){
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

	private boolean wholeWordContains (String aPhrase, String aSubstr){
		// we apply spaces before and after the main phrase and substr,
		// as a trick to search for whole words only.
		String phrase = " " + aPhrase.replace("?", "").toLowerCase() + " ";  
		String substr = " " + aSubstr.replace("?", "").toLowerCase() + " ";
		return phrase.contains(substr);   
	}


	private void searchCodeListForRootWord(String str){
		for(Dataset dataset : Service.datasets){
			for(Dimension dim : dataset.getDimensions() ){
				HashMap<String, String> map = dim.getCodelist();
				if (!dim.getName().equals("Age") &&  !dim.getName().equals("Region Type") &&  !dim.getName().equals("State")) {
					for(String key: map.keySet ()){
						if(str.equalsIgnoreCase(map.get(key) )){
							//System.out.println(dataset.getName() + " ~ "+ dim.getName() +" ~ " + map.get(key) ) ;
							dimensions.put(dim.getName(), map.get(key) );
						}
					}
				}
			}
		};
	}
	
	private void matchSynonymsOfCodeList(String str){
		String rootWord = new String();
		for (String keyWord : synonyms.keySet() ) {
			if(wholeWordContains(str, keyWord)  ){ 
				rootWord = synonyms.get(keyWord) ; 
				//System.out.println("Found the word: "+ keyWord + "~" + rootWord);
				searchCodeListForRootWord(rootWord);
			};
		};				
	}


	private void matchCodeList(String str){
		for(Dataset dataset : Service.datasets){
			for(Dimension dim : dataset.getDimensions() ){
				HashMap<String, String> map = dim.getCodelist();
				if (!dim.getName().equals("Age") &&  !dim.getName().equals("Region Type") &&  !dim.getName().equals("State")) {
					for(String key: map.keySet ()){
						if(! map.get(key).equalsIgnoreCase("total") &&  wholeWordContains(str,map.get(key) )){
							//System.out.println(dataset.getName() + " ~ "+ dim.getName() +" ~ " + map.get(key) ) ;
							dimensions.put(dim.getName(), map.get(key) );
						}
					}
				}
			}
		};
	}

	public void identifyDimensions(ArrayList<String> phrases){
		String[] words;
		String numericalString;

		for(String phrase: phrases){
			words = phrase.split("[\\s]+"); 
			// (Note that this code block currently assumes that all numeric data with str corresponds to the age dimension)
			numericalString = getNumericString(words);
			if(numericalString.length() > 0){
				dimensions.put("Age", numericalString ); 		
			};
			
			// check for all other dimensions
			matchCodeList(phrase);
			matchSynonymsOfCodeList(phrase);
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


	
	public void parseText(){
		
		grammarParser.parseText();
		identifyDimensions(grammarParser.keyPhrases);
		//System.out.println("Dimensions are :" + dimensions);
		identifyRegion(grammarParser.inputText.toLowerCase());
		cleanUpDimensions();
		//System.out.println("After cleanUp: Dimensions are :" + dimensions);
	}

	private void identifyRegion(String inputString) {
		identifyAbbreviatedRegions(inputString);
	
		String str = inputString;
		if(!str.contains("cpi") && !str.contains("inflation") && !str.contains("index")){
			String region = identifyASGSRegion(inputString);
			if (region != null){
				dimensions.put("Region", region );
			};
		};
	}

	private void cleanUpDimensions(){
		
		dimensions.remove("Selected Person Characteristics"); // This eliminates Statistical Collections that are merely summaries of other ones.
		dimensions.remove("Year of Arrival in Australia"); // This eliminates Statistical Collections that are merely summaries of other ones.
		dimensions.remove("Ancestry");
		dimensions.remove("Method of Travel to Work");
		dimensions.remove("Count of Families");
		dimensions.remove("Type of Educational Institution Attending (Full/Part-Time Student Status by Age)");
		
		dimensions.remove("Count of Dwellings");
		dimensions.remove("Count of Families and Persons in Families");
		dimensions.remove("Type of Internet Connection");
		dimensions.remove("Number of Motor Vehicles");
		dimensions.remove("Place of Usual Residence 5 Years Ago");
		dimensions.remove("Place of Usual Residence 1 Year Ago");
		dimensions.remove("Proficiency in Spoken English/Language");
		dimensions.remove("Proficiency in Spoken English/Language of Male Parent");
		dimensions.remove("Proficiency in Spoken English/Language of Female Parent");
		
		// dimensions.remove("Selected Labour Force, Education and Migration Characteristics");

		dimensions.remove("State of Destination");
		dimensions.remove("State of Origin");

		// Begin the conditional checking.....
		
		
		if(dimensions.containsKey("Registered Marital Status") && dimensions.containsKey("Social Marital Status")){
			dimensions.remove("Registered Marital Status");
		};

		if(dimensions.containsKey("Sex") ){
			dimensions.remove("Country of Origin"); //EXPORTS
			dimensions.remove("Country of Destination"); //IMPORTS
			dimensions.remove("Index"); //CPI
		};

		
		if(grammarParser.inputText.contains("born") ||	grammarParser.inputText.contains("birth")   ){
			if(!dimensions.containsKey("Country of Birth of Person")){
				dimensions.put("Selected Person Characteristics", "Birthplace Elsewhere");
			};
			dimensions.remove("Country of Origin");
			dimensions.remove("Country of Destination");
		};

		if(	!grammarParser.inputText.toLowerCase().contains("census") ){
				dimensions.remove("Place of Usual Residence on Census Night");
			};
			

  		if(		!grammarParser.inputText.contains("born") 
			&&	!grammarParser.inputText.contains("birth") 
			&&  !grammarParser.inputText.contains("from") 
			&&  !grammarParser.inputText.contains("live") 
			 ){
			dimensions.remove("Country of Birth of Person");
		};
 

		if(grammarParser.inputText.contains("lived") ){
			dimensions.remove("Country of Birth of Person");
		};

		
		if( grammarParser.inputText.contains("speak") ){
			dimensions.remove("Country of Birth of Person");
			dimensions.remove("Ancestry");			
		};


		
		if(grammarParser.inputText.contains("assistance") ){
			dimensions.remove("Country of Birth of Person");
		};

		if(grammarParser.inputText.contains("employed") ){
			dimensions.remove("Country of Birth of Person");
		};

		
		if(grammarParser.inputText.contains("voluntary") ||  grammarParser.inputText.contains("volunteer")  ){
				dimensions.remove("Country of Birth of Person");
		};

		if(grammarParser.inputText.toLowerCase().contains("cpi") 
		|| grammarParser.inputText.toLowerCase().contains("price index") 
		|| grammarParser.inputText.toLowerCase().contains("inflation") 
		){
			dimensions.remove("Industry of Employment");
		};


		// ...................................
		
		int c = (dimensions.containsKey("Region")) ? 1 : 0;
		
		if(dimensions.containsKey("Country of Birth of Person") && dimensions.size() == c){
			dimensions.put("Age","Total all ages");
		};

		// if query only has <region>, make sure it chooses B04;
		if(dimensions.size() == c ){
			dimensions.put("Sex","Persons");
			dimensions.put("Age","Total all ages");
		};

		// if query only has <Sex> and <Region>, make sure it chooses B04;
		if(dimensions.size() == c+1  && dimensions.containsKey("Sex")){
			dimensions.put("Age","Total all ages");
		};

		// if query only has <Age> and <Region>, make sure it chooses B04;
		if(dimensions.size() == c+1  && dimensions.containsKey("Age")){
			dimensions.put("Sex","persons");
		};
		
		// the word "persons" is often used as a generic grammar term and not as a direct semantic indicator of sum of males+females.
		if(	dimensions.containsKey("Place of Usual Residence on Census Night") || dimensions.containsKey("Selected Medians and Averages") ){
			dimensions.remove("Sex");
		};
		
		// if query does not have <Region>, set it to the default value of "Australia";
		if(!dimensions.containsKey("Region") ) {dimensions.put("Region","Australia");};
	
	}
	

	// ...........................................................................
		
	// converts to lowercase, eliminates hyphens and space delimits the string.
	private String normalise(String str){
		// we apply spaces before and after the main phrase so that we can do a whole-word only search,
		String[] words = str.toLowerCase().replaceAll("'s", " 's").replaceAll("-", " ").split("[\\s?,-]+"); // separate on whitespace, question marks, commas, and hyphens;
		String s = " "; // note that this is a space and not a null string.
		for (String w : words)
			s = s+ w+" ";
		return s;
	}
	
	public String identifyASGSRegion(String str){
		HashMap<String, String> identifiedRegions = new HashMap<String, String>() ;
		HashMap<String, String> regions = ASGS2011.getCodelist();
		String normalisedStr = normalise(str);
		String normalisedRegion = new String();
		
		String[] regionComponents;
		
		for(String key : regions.keySet()){
			normalisedRegion = normalise(regions.get(key));
			if(normalisedStr.contains(normalisedRegion) ){
				identifiedRegions.put(key, regions.get(key) );
				//System.out.println("1: "+key+" ~ "+ regions.get(key));
			}
		};
		
		if(identifiedRegions.isEmpty()){
			for(String key : regions.keySet()){
				if(regions.get(key).contains("-")){
					regionComponents = regions.get(key).split(" - ");
					for (String subregion : regionComponents){
						if (wholeWordContains(str, subregion.trim() ) ){						
							identifiedRegions.put(key, regions.get(key) );
							//System.out.println("2: "+key+" ~ "+ regions.get(key));
						};
					}
				};
			};
		};
		
		if (wholeWordContains(str, "Canberra"))						
			identifiedRegions.put("80105", "North Canberra");

		
		if (wholeWordContains(str, "Aust"))						
			identifiedRegions.put("0", "Australia");
		if (wholeWordContains(str, "Aus"))						
			identifiedRegions.put("0", "Australia");
		if (wholeWordContains(str, "NSW"))						
			identifiedRegions.put("1", "New South Wales");
		if (wholeWordContains(str, "Vic"))						
			identifiedRegions.put("2", "Victoria");
		if (wholeWordContains(str, "Qld"))						
			identifiedRegions.put("3", "Queensland");
		if (wholeWordContains(str, "SA"))						
			identifiedRegions.put("4", "South Australia");
		if (wholeWordContains(str, "WA"))						
			identifiedRegions.put("5", "Western Australia");
		if (wholeWordContains(str, "Tas"))						
			identifiedRegions.put("6", "Tasmania");
		if (wholeWordContains(str, "NT"))						
			identifiedRegions.put("7", "Northern Territory");		
		if (wholeWordContains(str, "ACT"))						
			identifiedRegions.put("8", "Australian Capital Territory");
		
		
		//System.out.println("Best match: "+ getLargestRegion(str,identifiedRegions) );
		return getLargestRegion(str,identifiedRegions);
	}

	private void identifyAbbreviatedRegions(String str) {
		String region = new String() ;
		if (wholeWordContains(str, "Aust"))	region =  "Australia";
		if (wholeWordContains(str, "Aus"))	region =  "Australia";
		if (wholeWordContains(str, "NSW"))	region =  "New South Wales";
		if (wholeWordContains(str, "Vic"))	region =  "Victoria";
		if (wholeWordContains(str, "Qld"))  region =  "Queensland";
		if (wholeWordContains(str, "SA"))	region =  "South Australia";
		if (wholeWordContains(str, "WA"))	region =  "Western Australia";
		if (wholeWordContains(str, "Tas")) 	region =  "Tasmania";
		if (wholeWordContains(str, "NT")) 	region =  "Northern Territory";		
		if (wholeWordContains(str, "ACT"))	region =  "Australian Capital Territory";
		if(!region.isEmpty()){
			dimensions.put("Region", region);
		}
	}
	
	
	public String eliminateHyphens(String str){
		return str.replaceAll("-", " ").replaceAll("\\s+", " ");
	}

	
	// Selects the best match region 
	private String getLargestRegion(String str, HashMap<String, String> hashmap){
		HashMap<String, String> regions = hashmap;
		
		// excludes Australia if multiple regions are identified, as it is most likely associated with phrases such as "born in Australia".
		if(regions.size() >1 && regions.containsKey("0")){
			// System.out.println("removing australia from regions. ");
			regions.remove("0");
		};

		for(String key : regions.keySet()){
			if(str.contains("SA4") && key.length() == 9) return regions.get(key);
			if(str.contains("SA3") && key.length() == 5) return regions.get(key);
			if(str.contains("SA2") && key.length() == 3) return regions.get(key);
		};		

		return getLargestRegion(regions); //default if user does not explicitly include the RegionType.
	};

	
	// Selects the largest region as the one at the highest level hierarchy.
	private String getLargestRegion(HashMap<String, String> regions){
		int minLengthKey = 9999;
		String largestRegion = new String();
		for(String key : regions.keySet()){
			if(key.length() < minLengthKey) {
				minLengthKey  = key.length();
				largestRegion = regions.get(key);
			}
		};	
		if(minLengthKey != 9999){
			return largestRegion;
		} else {
			return null;
		}
			
	};
	// ...........................................................................

}
