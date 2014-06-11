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
	private HashMap<String, String> synonyms;

	private GrammarParser grammarParser;

	// constructor
	public SemanticParser (String str, ArrayList<Dataset> datasets) throws IOException, ClassNotFoundException{
		flatCodeList = new HashMap<String, String>();
		dimensions = new HashMap<String, String>();
		synonyms   = new HashMap<String, String>();
		grammarParser = new GrammarParser(str);

		initializeSynonyms();
		createFlatCodeList(datasets); 
	}		



	// methods (in alphabetical order - hopefully)

	public HashMap<String, String> getDimensions() {
		return dimensions;
	}

	private void createFlatCodeList(ArrayList<Dataset> datasets) throws IOException, ClassNotFoundException{
		//for(int i=0;i<46;i++){
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

		//B42
		synonyms.put("employed","Employed total");
		synonyms.put("employment","Employed total");
		synonyms.put("total employment","Employed total");
		synonyms.put("employed in total","Employed total");
		synonyms.put("employed full time","Full-time(a)");
		synonyms.put("full time employed","Full-time(a)");
		synonyms.put("full time employment","Full-time(a)");
		synonyms.put("employed part time","Part-time");
		synonyms.put("part time employed","Part-time");
		synonyms.put("part time employment","Part-time");
		// synonyms.put("agriculture","Employed, away from work(b)");
		synonyms.put("labour force","Total labour force");


		//B43
		synonyms.put("agriculture","Agriculture, forestry and fishing");
		synonyms.put("agricultural","Agriculture, forestry and fishing");
		synonyms.put("forestry","Agriculture, forestry and fishing");
		synonyms.put("fishing","Agriculture, forestry and fishing");
		synonyms.put("aquaculture","Agriculture, forestry and fishing");
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
		synonyms.put("real estate","Rental, hiring and real estate services");
		synonyms.put("professional","Professional, scientific and technical services");
		synonyms.put("scientific","Professional, scientific and technical services");
		synonyms.put("scientists","Professional, scientific and technical services");
		synonyms.put("science","Professional, scientific and technical services");
		synonyms.put("technical","Professional, scientific and technical services");
		synonyms.put("administrative","Administrative and support services");
		synonyms.put("support","Administrative and support services");
		synonyms.put("public","Public administration and safety");
		synonyms.put("safety","Public administration and safety");
		synonyms.put("education","Education and training");
		synonyms.put("training","Education and training");
		synonyms.put("health","Health care and social assistance");
		synonyms.put("healthcare","Health care and social assistance");
		synonyms.put("arts","Arts and recreation services");
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
				//System.out.println("Synonyms: "+flatCodeList.get(baseWord)+" <-- " + baseWord+"<--"+keyWord);
			};
		};		
	}


	private void matchCodeList(String str){
		String baseWord = new String();
		for (String keyWord : flatCodeList.keySet() ) {
			if(wholeWordContains(str, keyWord)  ){ 
				baseWord = keyWord ; 
				dimensions.put(flatCodeList.get(baseWord), baseWord);
				//System.out.println("Codelist: "+flatCodeList.get(baseWord)+":" + baseWord);
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
		grammarParser.parseText();
		if (grammarParser.npObjects.size() >0){
			dimensions.put("region", grammarParser.npObjects.get(0) ); 	//assumes that npObjects has only 1 item in it.
		};

		identifyDimensions(grammarParser.npSubjects);
		cleanUpDimensions();

	}

	private void cleanUpDimensions(){

		// .......................................................................
		// only temporary until we find a way to solve these ambiguities!
		if(dimensions.get("Ancestry") != null ){
			dimensions.remove("Ancestry");
		};

		if(dimensions.get("Type of Educational Institution Attending (Full/Part-Time Student Status by Age)") != null ){
			dimensions.remove("Type of Educational Institution Attending (Full/Part-Time Student Status by Age)");
		};
		// .......................................................................



		dimensions.remove("Selected Person Characteristics"); // This eliminates Statistical Collections that are merely summaries of other ones.


		if(dimensions.get("Sex") != null && dimensions.get("Sex").equals("Persons")){
			dimensions.remove("Sex");
		};

		if(dimensions.get("region") == null) {dimensions.put("region","Australia");};

		// if dimensions only has a region and no (remaining) other dimensions, insert some default key-values.
		if(dimensions.size() <= 2 ){
			if(dimensions.get("Sex") == null) {dimensions.put("Sex","Persons");};
		};

		if(dimensions.size() <= 2 ){
			if(dimensions.get("Age") == null) {dimensions.put("Age","Total all ages");};
		};
	}
}
