package naturallanguagedotstat.parser;

import java.util.ArrayList;
import java.util.HashMap;

import naturallanguagedotstat.model.Dimension;

public class GrammarParser {
	public String inputText;
	public String[] words;
	public ArrayList<String> keyPhrases; 
	
	private String[] auxiliaryWords = {
			"what","how","many","there", "where", 
			"the", "a", 
			"that", "which", "who", 
			"of", "in", 
			"is", "are","were",
			"do", "did","will", "can","have",
			"only"
	};

	// Constructor
	public GrammarParser (String str){
		inputText = str;
		keyPhrases  = new ArrayList<String>(); 
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
		words = splitTextIntoWords(inputText);
		tagAllKeyPhrases();
		numericallyNormalizePhrases();
	}
	
	
	private String[]  splitTextIntoWords(String phrase){
		return phrase.replaceAll("'s", " 's").split("[\\s?,-]+");
	}
	
	
	private void tagAllKeyPhrases(){
		String str = "";
		
		for(int i=0; i< words.length; i++){
			if(!isAuxiliary(words[i] ) ){
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
	
	private boolean isAuxiliary(String str){	
		for (String word: auxiliaryWords){
			if(str.equalsIgnoreCase(word) )
				return true;
		};
		return false;
	}
}
