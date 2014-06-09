package naturallanguagedotstat.parser;

import java.util.ArrayList;

public class GrammarParser {
	// fields
	public String inputText;
	
	public String[] words;
	public String[] grammarTypes;
	
	
	public ArrayList<String> npSubjects; 
	public ArrayList<String> npObjects; 

	private String outputText;


	// Constructor
	public GrammarParser (String str){
		npSubjects  = new ArrayList<String>(); 
		npObjects   = new ArrayList<String>(); 

		inputText = str;
		splitTextIntoWords();
		grammarTypes = new String[words.length];  
	}		
		

	// Methods (in alphabetical order)

	
	//Returns the location of the first occurrence of the word, aWord 
	int getLocationOfWord(String aWord){
		for (int i=0; i< words.length; i++) {
			  if(words[i].equals(aWord) ) {return i;};
		};	
		return -1;
	}
	
	
	//Returns the location of the first occurrence of a word with a grammarType of aType 
	int getLocationOfGrammarType(String aType){
		for (int i=0; i< words.length; i++) {
			  if(grammarTypes[i].equals(aType) ) {return i;};
		};	
		return -1;
	}
	
	
	// Starting the search at position iMax, and searches backwards.
	// It returns the first location of the word that has already been grammatically identified.
	int getLocationOfPreviousIdentifiedType(int iMax){
		if (iMax != -1){
			for (int i=iMax; i>=0; i--) {
				  if(grammarTypes[i] != "_undefined" ) {return i;};  //if(grammarTypes[i] != null && !grammarTypes[i].isEmpty() ) {return i;};
			};
		}
		return -1;
	}
	

	// Converts npObject to strings delimited by newlines
	// (used only in testing and debugging).
	public String convertArrayListToString (ArrayList<String> anArrayList){
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< anArrayList.size(); i++) {
			if(i>0){sb.append("\n");};
			sb.append(anArrayList.get(i) );
		};
		return sb.toString();
	}
	
	
	// Identifies core prepositions, interrogatives, articles and some verbs.
	private void identifyAuxiliaryTerms(){
		
		// Identify a small countable number of specific auxiliary non-NP words
		tagWordWithGrammarType("of", "prep.of");		//  preposition "in"
		tagWordWithGrammarType("in", "prep.in"); 		// preposition "of"
		tagWordWithGrammarType("what", "pron.int");		// interrogative pronoun
		tagWordWithGrammarType("how", "adv.int");		// interrogative adverb
		tagWordWithGrammarType("there", "adv.int");	
		tagWordWithGrammarType("many", "adj.int");		// interrogative adjective
		tagWordWithGrammarType("is", "v.be");			// the verb "to be"
		tagWordWithGrammarType("are", "v.be");	
		tagWordWithGrammarType("the", "art.def");		// definite article
		tagWordWithGrammarType("a", "art.indef");		// indefinite article
		tagWordWithGrammarType("'s", "NP.gen");			// NP.genitive
		
				
		// identify domain-specific verbs. This list might grow to several dozen!
		tagWordWithGrammarType("live", "v");		// verb
		tagWordWithGrammarType("lives", "v");	
		tagWordWithGrammarType("do", "v");	
		tagWordWithGrammarType("did", "v");	
		tagWordWithGrammarType("have", "v");	
	}
	

	// Identifies all noun phrases in the userQuery.
	private void identifyNounPhrases(){
	
		// Identify object NPs
		tagAllWordsBeforeGrammarType("NP.gen" , "NP.obj");
		tagAllWordsAfterGrammarType("prep.in", "NP.obj");
		
		if(!queryContainsType("NP.obj") ){
			tagAllWordsAfterGrammarType("prep.of", "NP.obj");
			}
		
		// Identify subject NPs
		tagAllWordsBeforeGrammarType("prep.in", "NP.subj");	// subject Noun Phrase
		tagAllWordsBeforeGrammarType("prep.of", "NP.subj");	// subject Noun Phrase
		tagAllWordsBeforeGrammarType("v", "NP.subj");
		tagAllWordsAfterGrammarType("v", "NP.subj");
		tagAllWordsAfterGrammarType("art.def", "NP.subj");
		tagAllWordsAfterGrammarType("art.indef", "NP.subj");
		tagAllWordsAfterGrammarType("NP.gen", "NP.subj");		
		tagAllWordsAfterGrammarType("v.be", "NP.subj"); 
		tagAllWordsAfterGrammarType("adj.int", "NP.subj");
		} 
	
	public void numericallyNormalizePhrases(){
		ArrayList<String> newNPsubjectList = new ArrayList<String>(); 
		String s1, s2; 

		if (npSubjects.size() == 0){
			System.out.println("error: trying to normalize a null string.");
			return;
		}

		for (int i=0; i< npSubjects.size(); i++) {
			s1 = npSubjects.get(i);
			NumericParser numericParser = new NumericParser(s1);

			s2 = numericParser.getOutputString();	

			newNPsubjectList.add(s2);
			numericParser = null;
		};
		
		// update npSubjects to be the same as newNPsubjects;
		
		npSubjects.clear();
		npSubjects.addAll(newNPsubjectList);
	}
	
	
	
	public void parseText(){
		for (int i=0; i< words.length; i++) {grammarTypes[i] = "_undefined"; }; // Possibly unnecessary line.
		identifyAuxiliaryTerms();
		identifyNounPhrases();
		numericallyNormalizePhrases();
		// printOutput();
	}
	

	public boolean queryContainsType( String aType){
		boolean b = false;
		for (int i=0; i< words.length; i++) {
			if(grammarTypes[i] == aType){
				b = true;
			}; 
		};
		return b;
	}
		
	
	// This method outputs key fields relating to the grammar of userQuery.
	// only used for debugging and testing purposes.
	public void printOutput() {
		boolean showDetailedOutput = true;
		
   		if(showDetailedOutput){
   			StringBuilder sb = new StringBuilder();
   			for (int i=0; i< words.length; i++) {
   				sb.append(grammarTypes[i]); 
   				sb.append(":");
   				sb.append(words[i]);
   				sb.append("\n");
   			  };
   			outputText = sb.toString();
   			System.out.println("Detailed output of grammarParser\n"+outputText);
   		};
	
		System.out.println("SubjectText: \n" + convertArrayListToString(npSubjects) );
		System.out.println("ObjectText: \n"  + convertArrayListToString(npObjects) );
	}
	
	
	private void splitTextIntoWords(){
		String str;
		str = inputText.replaceAll("'s", " 's");  // identify and separate apostrophes.
		words = str.split("[\\s?,-]+"); 	// delimit on: white space,commas,hyphens and question marks.
	}
	
	
	// Tags all occurrences of the word aWord with a grammarType of aType.
	private void tagWordWithGrammarType(String aWord, String aType) {
		for (int i=0; i< words.length; i++) {
			  if(words[i].equalsIgnoreCase(aWord) ) grammarTypes[i]=aType;
		  };
	}
	
	
	// Finds the location of grammarType, searchType, and then tags all words immediately after it
	// that are not yet tagged, (so up until but not including the next tagged word) with the grammarType aType.
	private void tagAllWordsAfterGrammarType(String searchType, String aType){
		int inx;
		String str=new String();
		
		inx = getLocationOfGrammarType(searchType);
		if (inx == -1) return;

		
		inx++;
		while(inx < words.length && grammarTypes[inx].equals("_undefined") ){
			grammarTypes[inx] = aType;
			str = (str.equals("")) ? words[inx] : str + " " + words[inx];
			inx++;
		};
		
		if (!str.equals("")) {
			updateNPfields(aType, str);
		};

		return;
	};	
	
	
	// Finds the location of grammarType, searchType, and then tags all words immediately before it
	// that are not yet tagged, (so up until but not including the next tagged word) with the grammarType aType.
	private void tagAllWordsBeforeGrammarType(String searchType, String aType){
		int inx;
		String str = new String();
		
		inx = getLocationOfGrammarType(searchType);
		if (inx == -1) return;
		inx--;
		while(inx >= 0 && grammarTypes[inx].equals("_undefined") ){
			grammarTypes[inx] = aType;
			str = (str.equals("")) ? words[inx] : words[inx] + " " + str;
			inx--;
		};

		if (!str.equals("")) {
			updateNPfields(aType, str);
		};

		return;
	};
	
	
	private void updateNPfields(String aType, String str){
		if(str.length() >0 ) {
			
			if(aType=="NP.subj"){
				npSubjects.add(str);
			}
			if(aType=="NP.obj") {
				npObjects.add(str);
			};
		};
	};
	
	
}
