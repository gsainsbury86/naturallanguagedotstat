package naturallanguagedotstat;

import java.util.ArrayList;
import java.util.Collections;


public class NumericParser {
	// public fields	

	// private fields	
	private String inputText;
	private String outputText;
	private ArrayList<String> segments; 
	private ArrayList<String> explicitNumbers; // (but still stored as Strings).

	
	// Constructors
	NumericParser (String str){
		inputText = str;
		segments = new ArrayList<String>(); 
		explicitNumbers= new ArrayList<String>(); 

	}		

	
	// Methods (in alphabetic order, hopefully!)

	private void createOutputText(){
		StringBuilder sb = new StringBuilder();
		for (int i =0; i<segments.size(); i++){
			if(!isaNumber(segments.get(i)) ){
				if(i==0){
					sb.append(segments.get(i));
				} else {
					sb.append(" ");
					sb.append(segments.get(i));
				};
			};
		};
		
		if(explicitNumbers.size()>0){
			sb.append(" ");
			sb.append(getNumericRange() );			
		};
		
		outputText = sb.toString();
	}
	
	
	public String getOutput(){
		return outputText;
	}
	
	
	public String getNumericRange(){
		
		Collections.sort(explicitNumbers);
		StringBuilder sb = new StringBuilder();
		
		if (explicitNumbers.size() ==1 ) {
			sb.append(explicitNumbers.get(0) );
		};
		
		if (explicitNumbers.size() == 2) {
			sb.append(explicitNumbers.get(0) );
			if(explicitNumbers.get(1).equals("9999") ){
				sb.append("+"); // This is open-ended interval character				
			} else {
				sb.append("~"); // This is the interval notation character
				sb.append(explicitNumbers.get(1) );				
			}
		};		
		//System.out.println( sb.toString() );
		return sb.toString();
	}

	
	private void identifyAuxiliaryWords(){
		processAuxiliaryWord("and",		true, false, false);
		processAuxiliaryWord("to",		true, false, false);
		processAuxiliaryWord("from",	true, false, false);
		processAuxiliaryWord("between",	true, false, false);
		processAuxiliaryWord("than",	true, false, false);
		
		processAuxiliaryWord("under",	true, true, false);
		processAuxiliaryWord("below",	true, true, false);
		processAuxiliaryWord("less",	true, true, false);
		
		processAuxiliaryWord("over",	true, false, true);
		processAuxiliaryWord("above",	true, false, true);
		processAuxiliaryWord("more",	true, false, true);
	
		processAuxiliaryWord("younger",	false, true, false);
		processAuxiliaryWord("older",	false, false, true);
		
		Collections.sort(explicitNumbers);
	}
	
	
	// Test if a string aString directly represents a number.	
	private void identifyNumbers(){
		for (String segment:segments) {
			if(isaNumber(segment) ){
				explicitNumbers.add(segment);
			};
		};
	};
	
	
	private boolean isaNumber(String aString)
	{
		try {
	        Integer.parseInt( aString );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}

	
	// Identifies auxiliary numeric words associated implicit ranges.
	private void processAuxiliaryWord(String aWord, boolean deleteWord, boolean setMinimum, boolean setMaximum){
		if(explicitNumbers.size() == 0){return;};
		
		if( !segments.contains(aWord) ){return;};
		
		if(deleteWord){
			segments.remove(aWord);
		};
		
		// this is when the phrase implicitly defines an interval.
		if(explicitNumbers.size() ==1){
			if(setMinimum){
				explicitNumbers.add("0"); //if the lower bound is not explicitly stated, we assume it is zero.
			};

			if(setMaximum){
				explicitNumbers.add("9999"); //if the lower bound is not explicitly stated, we notationally set it to 9999.
			};			
		};
		

	};	
	

	// main method
	public void parseText(){
		splitTextIntoWords();
		identifyNumbers();
		identifyAuxiliaryWords();
		createOutputText();
	}

	
	private void splitTextIntoWords(){
		String[] words;
		words = inputText.split("[\\s?,-]+"); 	// delimit on: white space,commas,hyphens and question marks.
		segments.clear();
		Collections.addAll(segments, words); 		
	};
	

}
