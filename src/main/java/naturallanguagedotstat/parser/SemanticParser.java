package naturallanguagedotstat.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;


public class SemanticParser {
	// public fields here
	public HashMap<String, String> dimensions;

	private HashMap<String, String> flatCodeList;
	private HashMap<String, String> synonyms;
	private Dimension ASGS2011;

	private GrammarParser grammarParser;
	
	
	// constructor
	public SemanticParser (String str, ArrayList<Dataset> datasets, Dimension ASGS2011) throws IOException, ClassNotFoundException{
		flatCodeList = new HashMap<String, String>();
		dimensions = new HashMap<String, String>();
		synonyms   = new HashMap<String, String>();
		grammarParser = new GrammarParser(str);
		
		this.ASGS2011 = ASGS2011;
		
		initializeSynonyms();
		createFlatCodeList(datasets); 
	}		



	// methods (in alphabetical order - hopefully)
	//TODO: Change dimensions field so that it is HashMap<String, ArrayList<String>> 
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
				if (!dim.getName().equals("Age")  && map != null) {
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

		synonyms.put("female","Females");
		synonyms.put("women","Females");
		synonyms.put("male","Males");
		synonyms.put("men","Males");

		//B03
		synonyms.put("at home on Census night","Counted at home on Census Night" ) ;
		synonyms.put("at home on the night","Counted at home on Census Night" ) ;
		synonyms.put("same Statistical Area Level 2","Same Statistical Area Level 2 (SA2)" ) ;
		synonyms.put("same SA2","Same Statistical Area Level 2 (SA2)" ) ;

		//B05
		synonyms.put("married","Married(a)" ) ;
		synonyms.put("marriages","Married(a)" );
		synonyms.put("widows","Widowed" );
		synonyms.put("widowers","Widowed" );
		synonyms.put("separations","Separated" );
		synonyms.put("divorces","Divorced" );

		//B06
		synonyms.put("registered marriage","Married in a registered marriage");
		synonyms.put("de facto","Married in a de facto marriage(b)");

		//B07
		synonyms.put("indigenous","Indigenous(a)");
		synonyms.put("non indigenous","Non-Indigenous");


		//B09
		synonyms.put("China","China (excl. SARs and Taiwan)(b)");
		synonyms.put("Yugoslavia","Former Yugoslav Republic of Macedonia (FYROM)");
		synonyms.put("Hong Kong","Hong Kong (SAR of China)(b)");
		synonyms.put("Korea","Korea, Republic of (South)");
		synonyms.put("United Kingdom","United Kingdom, Channel Islands and Isle of Man(d)");
		synonyms.put("UK","United Kingdom, Channel Islands and Isle of Man(d)");
		synonyms.put("USA","United States of America");
		synonyms.put("US","United States of America");
		synonyms.put("United States","United States of America");
		synonyms.put("America","United States of America");

		//B13
		synonyms.put("speak only English","Speaks English only");  // note that the word "English" is in B13 and B09
		synonyms.put("speak English","Speaks English only");
		synonyms.put("speak another language","Speaks other language total");
		synonyms.put("speak Chinese","Chinese languages total");
		synonyms.put("speak Iranian","Iranic Languages total");

		//B14
		synonyms.put("Buddhist","Buddhism");
		synonyms.put("Buddhists","Buddhism");
		synonyms.put("Christian","Christianity total");
		synonyms.put("Christians","Christianity total");
		synonyms.put("Christianity","Christianity total");
		synonyms.put("Anglicans","Anglican");
		synonyms.put("Assyrian Apostolics","Assyrian Apostolic");
		synonyms.put("Baptist","Baptist");
		synonyms.put("Catholics","Catholic");// note that the word "Catholoic" is in B14 and B15
		synonyms.put("Catholicism","Catholic");// note that the word "English" is in B14 and B15
		synonyms.put("Church of Christ","Churches of Christ");
		synonyms.put("Witnesses","Jehovah's Witnesses");
		synonyms.put("Latter Day saints","Latter-day Saints");
		synonyms.put("Mormons","Latter-day Saints");
		synonyms.put("Lutherans","Lutheran");
		synonyms.put("Pentecostals","Pentecostal");
		synonyms.put("Presbyterians","Presbyterian and Reformed");
		synonyms.put("Adventist","Seventh-day Adventist");
		synonyms.put("Adventists","Seventh-day Adventist");
		synonyms.put("Hindu","Hinduism");
		synonyms.put("Hindus","Hinduism");
		synonyms.put("muslim","Islam");
		synonyms.put("muslims","Islam");
		synonyms.put("jewish","Judaism");
		synonyms.put("jews","Judaism");
		synonyms.put("Aboriginal religions","Australian Aboriginal Traditional Religions");
		synonyms.put("Aboriginal traditional religions","Australian Aboriginal Traditional Religions");
		synonyms.put("no religion","No religion(b)");

		//B16
		synonyms.put("year 12","Year 12 or equivalent");
		synonyms.put("grade 12","Year 12 or equivalent");
		synonyms.put("year 11","Year 11 or equivalent");
		synonyms.put("grade 11","Year 11 or equivalent");
		synonyms.put("year 10","Year 10 or equivalent");
		synonyms.put("grade 10","Year 10 or equivalent");
		synonyms.put("year 9","Year 9 or equivalent");
		synonyms.put("grade 9","Year 9 or equivalent");
		synonyms.put("year 8","Year 8 or below");
		synonyms.put("grade 8","Year 8 or below");

		//B18
		synonyms.put("need assistance","Has need for assistance");
		synonyms.put("needs assistance","Has need for assistance");
		synonyms.put("has need for assistance","Has need for assistance");
		synonyms.put("have need for assistance","Has need for assistance");
		synonyms.put("does not have need for assistance","Does not have need for assistance");
		synonyms.put("do not have need for assistance","Does not have need for assistance");
		synonyms.put("does not need assistance","Does not have need for assistance");
		synonyms.put("do not need assistance","Does not have need for assistance");

		//B19
		synonyms.put("voluntary work","Volunteer");
		synonyms.put("volunteered","Volunteer");

		//B20
		synonyms.put("unpaid domestic","Did unpaid domestic work");
		synonyms.put("no unpaid domestic","no unpaid domestic work");

		//B21
		synonyms.put("provided unpaid assistance","Provided unpaid assistance");
		synonyms.put("not provide unpaid assistance","No unpaid assistance provided");

		//B40
		synonyms.put("postgraduate","Postgraduate Degree Level");
		synonyms.put("post graduate","Postgraduate Degree Level");
		synonyms.put("postgraduates","Postgraduate Degree Level");
		synonyms.put("post graduates","Postgraduate Degree Level");
		synonyms.put("diploma","Graduate Diploma and Graduate Certificate Level");
		synonyms.put("graduate diploma","Graduate Diploma and Graduate Certificate Level");
		synonyms.put("graduate diplomas","Graduate Diploma and Graduate Certificate Level");
		synonyms.put("bachelor","Bachelor Degree Level");
		synonyms.put("bachelors","Bachelor Degree Level");

		//B41 *
		synonyms.put("science","Natural and Physical Sciences");				
		//synonyms.put("study information technology","Information Technology"); 		// Clash with B44
		//synonyms.put("studied information technology","Information Technology");	// Clash with B44

		synonyms.put("engineering","Engineering and Related Technologies");
		synonyms.put("architecture","Architecture and Building");
		
		//synonyms.put("study agriculture","Agriculture, Environmental and Related Studies");	// Clash with B44
		//synonyms.put("studied agriculture","Agriculture, Environmental and Related Studies");	// Clash with B44
		synonyms.put("study environmental","Agriculture, Environmental and Related Studies");	// Clash with B44
		synonyms.put("studied environmental","Agriculture, Environmental and Related Studies");	// Clash with B44
		synonyms.put("manangement","Management and Commerce");
		synonyms.put("commerce","Management and Commerce");
		
		//B42
		synonyms.put("employed","Employed total");
		synonyms.put("employment","Employed total");
		synonyms.put("total employment","Employed total");
		synonyms.put("employed in total","Employed total");
		synonyms.put("full time","Full-time(a)");
		synonyms.put("part time","Part-time");
		synonyms.put("labour force","Total labour force");
  
		//B43
		synonyms.put("agriculture","Agriculture, forestry and fishing");   // note B41
		synonyms.put("forestry","Agriculture, forestry and fishing"); 
		synonyms.put("fishing","Agriculture, forestry and fishing"); 
		synonyms.put("agricultural","Agriculture, forestry and fishing"); 
		synonyms.put("utilities","Electricity, gas, water and waste services");
		synonyms.put("electricity","Electricity, gas, water and waste services");
		synonyms.put("electrical","Electricity, gas, water and waste services");
		synonyms.put("waste","Electricity, gas, water and waste services");
		synonyms.put("wholesale","Wholesale trade");
		synonyms.put("retail","Retail trade");
		synonyms.put("retailing","Retail trade");
		synonyms.put("accommodation","Accommodation and food services");
		synonyms.put("food","Accommodation and food services");
		synonyms.put("hospitality","Accommodation and food services");
		synonyms.put("transport","Transport, postal and warehousing");
		synonyms.put("transportation","Transport, postal and warehousing");
		synonyms.put("postal","Transport, postal and warehousing");
		synonyms.put("warehousing","Transport, postal and warehousing");
		synonyms.put("computing","Information media and telecommunications"); 	// note B41
		synonyms.put("computing industry","Information media and telecommunications"); // note B41
		synonyms.put("ICT","Information media and telecommunications");	// note B41
		synonyms.put("ICT industry","Information media and telecommunications");	// note B41
		synonyms.put("IT","Information media and telecommunications");	// note B41
		synonyms.put("IT industry","Information media and telecommunications");	// note B41
		synonyms.put("telecommunication","Information media and telecommunications");	
		synonyms.put("telecommunications","Information media and telecommunications");	
		synonyms.put("financial","Financial and insurance services");
		synonyms.put("finance","Financial and insurance services");
		synonyms.put("finances","Financial and insurance services");
		synonyms.put("insurance","Financial and insurance services");
		synonyms.put("real estate","Rental, hiring and real estate services");
		synonyms.put("professional","Professional, scientific and technical services");
		synonyms.put("scientific","Professional, scientific and technical services");
		// synonyms.put("science","Professional, scientific and technical services");	// Clash with B41
		synonyms.put("technical","Professional, scientific and technical services");
		synonyms.put("administrative","Administrative and support services");
		synonyms.put("support","Administrative and support services");
		synonyms.put("public","Public administration and safety");
		synonyms.put("safety","Public administration and safety");
		synonyms.put("work in education","Education and training");
		synonyms.put("education","Education and training");
		synonyms.put("training","Education and training");
		synonyms.put("health","Health care and social assistance");
		synonyms.put("healthcare","Health care and social assistance");
		synonyms.put("arts industry","Arts and recreation services");
		synonyms.put("recreation","Arts and recreation services");

		//B45
		synonyms.put("technicians","Technicians and trades workers");
		synonyms.put("community worker","Community and personal service  workers");
		synonyms.put("community workers","Community and personal service  workers");
		synonyms.put("trades","Technicians and trades workers");
		synonyms.put("trade","Technicians and trades workers");
		synonyms.put("clerical","Clerical and administrative workers");
		synonyms.put("clerks","Clerical and administrative workers");
		synonyms.put("administrative","Clerical and administrative workers");
		synonyms.put("administration","Clerical and administrative workers");
		synonyms.put("sales","Sales workers");
		synonyms.put("machinery","Machinery operators and drivers");
		synonyms.put("drivers","Machinery operators and drivers");

	};


	private boolean wholeWordContains (String aPhrase, String aSubstr){
		// we apply spaces before and after the main phrase and substr,
		// as a trick to search for whole words only.
		String phrase = " "+ aPhrase.toLowerCase() +" ";  
		String substr = " "+ aSubstr.toLowerCase()+" ";
		return phrase.contains(substr);   
	}

	private void matchSynonymsOfCodeList(String str){
		String baseWord = new String();
		for (String keyWord : synonyms.keySet() ) {
			if(wholeWordContains(str, keyWord)  ){ 
				baseWord = synonyms.get(keyWord) ; 
				dimensions.put(flatCodeList.get(baseWord), baseWord);
			};
		};		
	}


	private void matchCodeList(String str){
		String baseWord = new String();
		for (String keyWord : flatCodeList.keySet() ) {
			if(wholeWordContains(str, keyWord)  ){ 
				baseWord = keyWord ; 
				dimensions.put(flatCodeList.get(baseWord), baseWord);
			};
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
			matchSynonymsOfCodeList(phrase);
			matchCodeList(phrase);
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
		String region = identifyASGSRegion(grammarParser.inputText);
		if (region != null){
			dimensions.put("region", region );
		};
		
		grammarParser.parseText();
		identifyDimensions(grammarParser.keyPhrases);
		cleanUpDimensions();

	}

	private void cleanUpDimensions(){
		
		dimensions.remove("Selected Person Characteristics"); // This eliminates Statistical Collections that are merely summaries of other ones.

		if(dimensions.containsKey("Ancestry") ){
			dimensions.remove("Ancestry");
		};

		if(dimensions.containsKey("Type of Educational Institution Attending (Full/Part-Time Student Status by Age)") ){
			dimensions.remove("Type of Educational Institution Attending (Full/Part-Time Student Status by Age)");
		};

		
		
		// Begin the conditional checking.....
		
		if(!grammarParser.inputText.contains("born") &&	!grammarParser.inputText.contains("from") 
				&& dimensions.containsKey("Country of Birth of Person") ){
			dimensions.remove("Country of Birth of Person");
		};

		if(!grammarParser.inputText.contains("years ago") 
				&& dimensions.containsKey("Place of Usual Residence 5 Years Ago") ){
			dimensions.remove("Place of Usual Residence 5 Years Ago");
		};

		if(grammarParser.inputText.contains("lived") 
				&& dimensions.containsKey("Country of Birth of Person") ){
			dimensions.remove("Country of Birth of Person");
		};

		
		if(grammarParser.inputText.contains("speak") 
				&& dimensions.containsKey("Country of Birth of Person")  ){
			dimensions.remove("Country of Birth of Person");
		};


		if(grammarParser.inputText.contains("assistance") ){
			if(dimensions.containsKey("Country of Birth of Person") )
				dimensions.remove("Country of Birth of Person");
		};

		if(grammarParser.inputText.contains("voluntary") ||  grammarParser.inputText.contains("volunteer") 
				&& dimensions.containsKey("Country of Birth of Person") ){
				dimensions.remove("Country of Birth of Person");
		};

		// ...................................
		
		int c = (dimensions.containsKey("region")) ? 1 : 0;
		
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
		if(dimensions.containsKey("Place of Usual Residence on Census Night")  && dimensions.containsKey("Sex") ){
			dimensions.remove("Sex");
		};
		
		// if query does not have <Region>, set it to the default value of "Australia";
		if(!dimensions.containsKey("region") ) {dimensions.put("region","Australia");};
	
	}
	

	// ...........................................................................

	public String identifyASGSRegion(String str){
		HashMap<String, String> identifiedRegions = new HashMap<String, String>() ;
		HashMap<String, String> regions = ASGS2011.getCodelist();

		for(String key : regions.keySet()){
			if(eliminateHyphens(str).contains(eliminateHyphens(regions.get(key) ) ) ){
				identifiedRegions.put(key, regions.get(key) );
			}
		};
		// System.out.println("Best match: "+ getBestMatchRegion(query,identifiedRegions) );
		return getBestMatchRegion(eliminateHyphens(str),identifiedRegions);
	}
	
	
	public String eliminateHyphens(String str){
		return str.replaceAll("-", " ").replaceAll("\\s+", " ");
	}

	
	// Selects the best match region 
	private String getBestMatchRegion(String str, HashMap<String, String> hashmap){
		HashMap<String, String> regions = hashmap;
		
		// excludes Australia if multiple regions are identified, as it is most likely associated with phrases such as "born in Australia".
		if(regions.size() >1 && regions.containsKey("0")){
			System.out.println("removing australia from regions. ");
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
