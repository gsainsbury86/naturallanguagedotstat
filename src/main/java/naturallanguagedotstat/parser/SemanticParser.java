package naturallanguagedotstat.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;


public class SemanticParser {
	// public fields here
	public HashMap<String, String> dimensions;

	private HashMap<String, String> flatCodeList;
	private LinkedHashMap<String, String> synonyms;
	private Dimension ASGS2011;

	private GrammarParser grammarParser;
	private ArrayList<Dataset>  datasets;
	
	// constructor
	public SemanticParser (String str, ArrayList<Dataset> datasets, Dimension ASGS2011) throws IOException, ClassNotFoundException{
		flatCodeList = new HashMap<String, String>();
		dimensions = new HashMap<String, String>();
		synonyms   = new LinkedHashMap<String, String>();
		grammarParser = new GrammarParser(str);
		
		this.ASGS2011 = ASGS2011;
		this.datasets = datasets;
		
		
		
		initializeSynonyms();
		createFlatCodeList(datasets); 
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

	public void initializeSynonyms(){		

		// .put("synonym", "rootWord");

		//B01
		synonyms.put("female","Females");
		synonyms.put("women","Females");
		synonyms.put("male","Males");
		synonyms.put("men","Males");

		//B02
		synonyms.put("median age","Median age of persons" ) ;
		synonyms.put("average age","Median age of persons" ) ;
		synonyms.put("personal income","Median total personal income ($/weekly)" ) ;		
		synonyms.put("income for people","Median total personal income ($/weekly)" ) ;		
		synonyms.put("income for persons","Median total personal income ($/weekly)" ) ;		
		synonyms.put("family income","Median total family income ($/weekly)" ) ;
		synonyms.put("income for families","Median total family income ($/weekly)" ) ;
		synonyms.put("family of families","Median total family income ($/weekly)" ) ;
		synonyms.put("household income","Median total household income ($/weekly)" ) ;
		synonyms.put("income for households","Median total household income ($/weekly)" ) ;
		synonyms.put("income of households","Median total household income ($/weekly)" ) ;
		synonyms.put("mortgage","Median mortgage repayment ($/monthly)" ) ;
		synonyms.put("repayment","Median mortgage repayment ($/monthly)" ) ;
		synonyms.put("repayments","Median mortgage repayment ($/monthly)" ) ;
		synonyms.put("rental","Median rent ($/weekly)" ) ;
		synonyms.put("payment","Median rent ($/weekly)" ) ;
		synonyms.put("rent","Median rent ($/weekly)" ) ;

		//B03
		synonyms.put("night of","Counted at home on Census Night" ) ;
		synonyms.put("at home on","Counted at home on Census Night" ) ;
		synonyms.put("same Statistical Area Level 2","Same Statistical Area Level 2 (SA2)" ) ;
		synonyms.put("same SA2","Same Statistical Area Level 2 (SA2)" ) ;
		
		//B05
		synonyms.put("married","Married(a)" ) ;  //must be before "never married"
		synonyms.put("marriages","Married(a)" );
		synonyms.put("widows","Widowed" );
		synonyms.put("widowers","Widowed" );
		synonyms.put("separations","Separated" );
		synonyms.put("divorces","Divorced" );
		synonyms.put("never married","Never married");
		synonyms.put("never been married","Never married");

		//B06
		synonyms.put("registered marriage","Married in a registered marriage");
		synonyms.put("de facto","Married in a de facto marriage(b)");
		// synonyms.put("not married","Not married");

		//B07
		synonyms.put("indigenous","Indigenous(a)");
		synonyms.put("non indigenous","Non-Indigenous");
		synonyms.put("not indigenous","Non-Indigenous");


		//B09
		synonyms.put("China","China (excl. SARs and Taiwan)(b)");
		synonyms.put("Yugoslavia","Former Yugoslav Republic of Macedonia (FYROM)");
		synonyms.put("Hong Kong","Hong Kong (SAR of China)(b)");
		synonyms.put("Korea","Korea, Republic of (South)");
		synonyms.put("United Kingdom","United Kingdom, Channel Islands and Isle of Man(d)");
		synonyms.put("UK","United Kingdom, Channel Islands and Isle of Man(d)");
		synonyms.put("USA","United States of America");
		synonyms.put("US","United States of America");
		synonyms.put("NZ","New Zealnd");
		synonyms.put("United States","United States of America");
		synonyms.put("America","United States of America");

		//B13
		synonyms.put("speak only English","Speaks English only");  // note that the word "English" is in B13 and B09
		synonyms.put("speak English","Speaks English only");
		synonyms.put("speak another language","Speaks other language total");
		synonyms.put("language other than English","Speaks other language total");  // note that the word "English" is in B13 and B09
		synonyms.put("speak Chinese","Chinese languages total");
		synonyms.put("Chinese speakers","Chinese languages total");
		synonyms.put("speak Iranian","Iranic Languages total");

		//B14
		synonyms.put("Buddhist","Buddhism");
		synonyms.put("Buddhists","Buddhism");
		synonyms.put("Christian","Christianity total");
		synonyms.put("Christians","Christianity total");
		synonyms.put("Christianity","Christianity total");
		synonyms.put("Anglicans","Anglican");
		synonyms.put("Assyrian Apostolics","Assyrian Apostolic");
		synonyms.put("apostolic","Assyrian Apostolic");
		synonyms.put("apostolics","Assyrian Apostolic");
		synonyms.put("Baptists","Baptist");
		synonyms.put("Catholics","Catholic");// note that the word "Catholic" is in B14 and B15
		synonyms.put("Catholicism","Catholic");// note that the word "English" is in B14 and B15
		synonyms.put("Church of Christ","Churches of Christ");
		synonyms.put("Witnesses","Jehovah's Witnesses");
		synonyms.put("Latter Day","Latter-day Saints");
		synonyms.put("Mormons","Latter-day Saints");
		synonyms.put("Lutherans","Lutheran");
		synonyms.put("Pentecostals","Pentecostal");
		synonyms.put("Presbyterian","Presbyterian and Reformed");
		synonyms.put("Presbyterians","Presbyterian and Reformed");
		synonyms.put("Adventist","Seventh-day Adventist");
		synonyms.put("Adventists","Seventh-day Adventist");
		synonyms.put("Hindu","Hinduism");
		synonyms.put("Hindus","Hinduism");
		synonyms.put("muslim","Islam");
		synonyms.put("muslims","Islam");
		synonyms.put("islamic","Islam");
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

		//B41 
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
		synonyms.put("full time","Full-time(a)");
		synonyms.put("part time","Part-time");
		synonyms.put("labour force","Total labour force");
  
		//B43
		synonyms.put("agricultural","Agriculture, forestry and fishing"); 
		synonyms.put("agriculture","Agriculture, forestry and fishing");   // note B41
		synonyms.put("farming","Agriculture, forestry and fishing");   // note B41
		synonyms.put("farmers","Agriculture, forestry and fishing");   // note B41
		synonyms.put("forestry","Agriculture, forestry and fishing"); 
		synonyms.put("fishing","Agriculture, forestry and fishing"); 
		synonyms.put("fishermen","Agriculture, forestry and fishing"); 
		synonyms.put("utilities","Electricity, gas, water and waste services");
		synonyms.put("electricity","Electricity, gas, water and waste services");
		synonyms.put("electrical","Electricity, gas, water and waste services");
		synonyms.put("electricians","Electricity, gas, water and waste services");
		synonyms.put("waste","Electricity, gas, water and waste services");
		synonyms.put("wholesale","Wholesale trade");
		synonyms.put("wholesalers","Wholesale trade");
		synonyms.put("retail","Retail trade");
		synonyms.put("retailing","Retail trade");
		synonyms.put("retailers","Retail trade");
		synonyms.put("accommodation","Accommodation and food services");
		synonyms.put("cooks","Accommodation and food services");
		synonyms.put("chefs","Accommodation and food services");
		// synonyms.put("food","Accommodation and food services"); Clash with CPI x industry
		synonyms.put("hospitality","Accommodation and food services");
		synonyms.put("transport","Transport, postal and warehousing");
		synonyms.put("transportation","Transport, postal and warehousing");
		synonyms.put("drivers","Transport, postal and warehousing");
		synonyms.put("postal","Transport, postal and warehousing");
		synonyms.put("postmen","Transport, postal and warehousing");
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
		synonyms.put("banking","Financial and insurance services");
		synonyms.put("bankers","Financial and insurance services");
		synonyms.put("insurance","Financial and insurance services");
		synonyms.put("real estate","Rental, hiring and real estate services");
		synonyms.put("professional","Professional, scientific and technical services");
		synonyms.put("scientific","Professional, scientific and technical services");
		// synonyms.put("science","Professional, scientific and technical services");	// Clash with B41
		synonyms.put("scienctists","Professional, scientific and technical services");	// Clash with B41
		synonyms.put("technical","Professional, scientific and technical services");	
		//synonyms.put("technicians","Professional, scientific and technical services");//Clash with B45
		synonyms.put("administrative","Administrative and support services");
		// synonyms.put("secretaries","Administrative and support services"); //Clash with B45
		// synonyms.put("clerks","Administrative and support services"); Clash with B45
		synonyms.put("support","Administrative and support services");
		synonyms.put("public","Public administration and safety");
		synonyms.put("safety","Public administration and safety");
		synonyms.put("work in education","Education and training");
		synonyms.put("education","Education and training");
		synonyms.put("teachers","Education and training");
		synonyms.put("training","Education and training");
		synonyms.put("health","Health care and social assistance");
		synonyms.put("healthcare","Health care and social assistance");
		synonyms.put("doctors","Health care and social assistance");
		synonyms.put("nurses","Health care and social assistance");
		synonyms.put("arts industry","Arts and recreation services");
		synonyms.put("recreation","Arts and recreation services");

		//B45
		synonyms.put("technicians","Technicians and trades workers");
		synonyms.put("community workers","Community and personal service  workers");
		synonyms.put("trades","Technicians and trades workers");
		synonyms.put("trade","Technicians and trades workers");
		synonyms.put("tradesmen","Technicians and trades workers");
		synonyms.put("trade workers","Technicians and trades workers");
		synonyms.put("plumbers","Technicians and trades workers");
		synonyms.put("electricians","Technicians and trades workers");
		synonyms.put("clerical","Clerical and administrative workers");
		synonyms.put("clerks","Clerical and administrative workers");
		synonyms.put("secretaries","Clerical and administrative workers");
		synonyms.put("administrative","Clerical and administrative workers");
		synonyms.put("administration","Clerical and administrative workers");
		synonyms.put("sales","Sales workers");
		synonyms.put("salesmen","Sales workers");
		synonyms.put("machinery","Machinery operators and drivers");
		synonyms.put("drivers","Machinery operators and drivers");

		//CPI
		synonyms.put("inflation","All groups CPI");

		synonyms.put("CPI","All groups CPI");
		synonyms.put("Consumer price index","All groups CPI");
		synonyms.put("CPI for food","Food and non-alcoholic beverages");
		synonyms.put("CPI for alcohol","Alcohol and tobacco");
		synonyms.put("CPI for tobacco","Alcohol and tobacco");
		synonyms.put("CPI for housing","Housing");
		synonyms.put("CPI for furnishing","Furnishings, household equipment and services");
		synonyms.put("CPI for health","Health");
		synonyms.put("CPI for transport","Transport");
		synonyms.put("CPI for communication","Communication");
		synonyms.put("CPI for recreation","Recreation and culture");
		synonyms.put("CPI for education","Education");
		
		synonyms.put("index for food","Food and non-alcoholic beverages");
		synonyms.put("index","Alcohol and tobacco");
		synonyms.put("index for tobacco","Alcohol and tobacco");
		synonyms.put("index for housing","Housing");
		synonyms.put("index for furnishing","Furnishings, household equipment and services");
		synonyms.put("index for health","Health");
		synonyms.put("index for transport","Transport");
		synonyms.put("index for communication","Communication");
		synonyms.put("index for recreation","Recreation and culture");
		synonyms.put("index for education","Education");

		synonyms.put("food CPI","Food and non-alcoholic beverages");
		synonyms.put("alcohol CPI","Alcohol and tobacco");
		synonyms.put("tobacco CPI","Alcohol and tobacco");
		synonyms.put("housing CPI","Housing");
		synonyms.put("furnishing CPI","Furnishings, household equipment and services");
		synonyms.put("health CPI","Health");
		synonyms.put("transport CPI","Transport");
		synonyms.put("communication CPI","Communication");
		synonyms.put("recreation CPI","Recreation and culture");
		synonyms.put("education CPI","Education");

	};


	private boolean wholeWordContains (String aPhrase, String aSubstr){
		// we apply spaces before and after the main phrase and substr,
		// as a trick to search for whole words only.
		String phrase = " " + aPhrase.replace("?", "").toLowerCase() + " ";  
		String substr = " " + aSubstr.replace("?", "").toLowerCase() + " ";
		return phrase.contains(substr);   
	}


	private void searchCodeListForRootWord(String str){
		for(Dataset dataset : datasets){
			for(Dimension dim : dataset.getDimensions() ){
				HashMap<String, String> map = dim.getCodelist();
				if (!dim.getName().equals("Age") &&  !dim.getName().equals("Region Type") &&  !dim.getName().equals("State")) {
					for(String key: map.keySet ()){
						if(str.equalsIgnoreCase(map.get(key) )){
							// System.out.println(dataset.getName() + " ~ "+ dim.getName() +" ~ " + map.get(key) ) ;
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
				// System.out.println("Found the word: "+ keyWord + "~" + rootWord);
				searchCodeListForRootWord(rootWord);
			};
		};				
	}


	private void matchCodeList(String str){
		for(Dataset dataset : datasets){
			for(Dimension dim : dataset.getDimensions() ){
				HashMap<String, String> map = dim.getCodelist();
				if (!dim.getName().equals("Age") &&  !dim.getName().equals("Region Type") &&  !dim.getName().equals("State")) {
					for(String key: map.keySet ()){
						if(! map.get(key).equalsIgnoreCase("total") &&  wholeWordContains(str,map.get(key) )){
							// System.out.println(dataset.getName() + " ~ "+ dim.getName() +" ~ " + map.get(key) ) ;
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
		String region = identifyASGSRegion(grammarParser.inputText);
		if (region != null){
			dimensions.put("Region", region );
		};
		
		grammarParser.parseText();
		identifyDimensions(grammarParser.keyPhrases);
		// System.out.println(dimensions);
		cleanUpDimensions();
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
		
		dimensions.remove("Selected Labour Force, Education and Migration Characteristics");

		dimensions.remove("State of Destination");
		dimensions.remove("State of Origin");

		// Begin the conditional checking.....
		
		
		if(dimensions.containsKey("Registered Marital Status") && dimensions.containsKey("Social Marital Status")){
			dimensions.remove("Registered Marital Status");
		};

		
		if(grammarParser.inputText.contains("born") ||	grammarParser.inputText.contains("birth")   ){
			dimensions.remove("Country of Origin");
			dimensions.remove("Country of Destination");
		};

		if(	!grammarParser.inputText.toLowerCase().contains("census") ){
				dimensions.remove("Place of Usual Residence on Census Night");
			};
			
		if(		!grammarParser.inputText.contains("born") 
			&&	!grammarParser.inputText.contains("birth") 
			&&  !grammarParser.inputText.contains("from") 
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
			}
		};
		
		if(identifiedRegions.isEmpty()){
			for(String key : regions.keySet()){
				if(regions.get(key).contains("-")){
					regionComponents = regions.get(key).split(" - ");
					for (String subregion : regionComponents){
						if (wholeWordContains(str, subregion.trim() ) ){						
							identifiedRegions.put(key, regions.get(key) );
						};
					}
				};
			};
		};
		
		// System.out.println(str);
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
