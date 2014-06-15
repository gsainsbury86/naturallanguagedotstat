package naturallanguagedotstat.parser;

import java.util.ArrayList;
import java.util.HashMap;

import naturallanguagedotstat.model.Dimension;

public class GrammarParser {
	public String inputText;
	public String[] words;
	public String[] grammarTypes;
	public ArrayList<String> keyPhrases; 


	// Constructor
	public GrammarParser (String str){
		inputText = str;
		words = splitTextIntoWords(str);
		keyPhrases  = new ArrayList<String>(); 
		grammarTypes = new String[words.length];  
	}		
		
	
	// Identifies core prepositions, interrogatives, articles and some verbs.
	private void identifyAuxiliaryTerms(){

		String[] auxiliaryWords = {
				"what","how","many","there", "where", 
				"the", "a", 
				"that", "which", "who", 
				"of", "in", 
				"is", "are","were",
				"do", "did","will", "can","have",
				"only"
		};
				
		for (String word: auxiliaryWords){
			tagWordWithGrammarType(word, "aux");	
		};
	}

	public void numericallyNormalizePhrases(){
		ArrayList<String> newKeyPhrases = new ArrayList<String>(); 
		String s1, s2; 

		if (keyPhrases.size() == 0)
			return;

		for (int i=0; i< keyPhrases.size(); i++) {
			s1 = keyPhrases.get(i);
			NumericParser numericParser = new NumericParser(s1);
			s2 = numericParser.getOutputString();	
			newKeyPhrases.add(s2);
			numericParser = null;
		};
		keyPhrases.clear();
		keyPhrases.addAll(newKeyPhrases);
	}
	
	
	public void parseText(){
		identifyAuxiliaryTerms();
		tagAllKeyPhrases();
		numericallyNormalizePhrases();
		// printOutput();
	}
	
	
	private String[]  splitTextIntoWords(String phrase){
		return inputText.replaceAll("'s", " 's").split("[\\s?,-]+");
	}
	
	
	// Tags all occurrences of the word aWord with a grammarType of aType.
	private void tagWordWithGrammarType(String aWord, String aType) {
		for (int i=0; i< words.length; i++) {
			  if(words[i].equalsIgnoreCase(aWord) ) 
				  grammarTypes[i]=aType;
		  };
	}
	
	
	private void tagAllKeyPhrases(){
		String str = "";
		
		for(int i=0; i< words.length; i++){
			if(grammarTypes[i]==null){
				str = (str.equals("")) ? words[i] : str + " " + words[i];
			} else {
				if(str.length() >0 )
					keyPhrases.add(str);
				str = "";
			}
		};

		if(str.length() >0 )
			keyPhrases.add(str);
	};	
}
