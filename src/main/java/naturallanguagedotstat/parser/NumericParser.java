package naturallanguagedotstat.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class NumericParser {
	public enum Comparator {
	    equals,
	    lessThan,
	    lessThanOrEqualTo,
	    greaterThan,
	    greaterThanOrEqualTo,
	    between
	};

	// properties with getters and/or setters
	private ArrayList<String> explicitNumbers; // note the numbers are still currently stored as Strings.
	private Comparator comparator;
	private String outputString;
	
	
	// private fields	
	private String inputString;
	private ArrayList<String> inputWords; 
	private ArrayList<String> outputWords; 

	
	// Constructors
	public NumericParser (String str){
		inputString = null;
		inputWords = new ArrayList<String>();
		outputWords = new ArrayList<String>();
		
		
		inputString = str;
		inputWords = splitTextIntoWords(inputString);
		explicitNumbers = extractExplicitNumbers(inputWords);
		identifyComparator();
		outputString = createStandardizedString();
	}		


	private String createStandardizedString(){
		String str = new String();
		boolean previousNumbersFound = false;
		for (int i = 0; i< inputWords.size(); i++ ){
			if(isNumber(inputWords.get(i) ) ){
				if( !previousNumbersFound){
					outputWords.add(comparatorAsString(comparator));
					outputWords.add(explicitNumbers.get(0));
					if(comparator == Comparator.between){
						outputWords.add(explicitNumbers.get(1));
					};
					previousNumbersFound = true;
				}
			} else {
				outputWords.add(inputWords.get(i) );
			};
		};		
		
		for (int j=0; j< outputWords.size(); j++ ){
			str += outputWords.get(j) + " ";
		};
		return str;
	};
	

	public String comparatorAsString(Comparator comparator){
		String str = null;
		switch(comparator){
			case equals: 				str = "="; break;
			case lessThan: 				str = "<"; break;
			case greaterThan: 			str = "="; break;
			case lessThanOrEqualTo: 	str = "≤"; break;
			case greaterThanOrEqualTo: 	str = "≥"; break;
			case between: 				str = "∈"; 
		}
		
		return str;
	}
	
	
	
	// Note the numbers are still currently stored as Strings.
	public ArrayList<String> getExplicitNumbers(){
		return explicitNumbers;
	}

	
	public Comparator getComparator(){
		return comparator;
	}


	public String getOutputString(){
		return outputString;
	}

	
	// returns true iff no numbers are found.
	public boolean isEmpty(){
		return  (explicitNumbers.size() == 0);
	}

	
	// returns true iff the mathematical comparator is Equals.
	public boolean isSingleValued(){
		// Note that this is different to explicitNumbers.size()==1 as comparator might imply an open-interval.
		return  (comparator == Comparator.equals);
		
	}
	
	// splits aString into an ArrayList of separate words.
	private ArrayList<String> splitTextIntoWords(String aString){
		String[] words;
		
		words = aString.split("[\\s?,-]+"); 	// delimit on: white space,commas, hyphens and question marks.
		
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(words));
		// Collections.addAll(arrayList, words);
		return arrayList;
	};

	
	// Extracts all explicit numbers from an ArrayList of strings.	
	private ArrayList<String> extractExplicitNumbers(ArrayList<String> strings){
		ArrayList<String >arrayList = new ArrayList<String>();
		for (String str:strings) {
			if(isNumber(str) ){
				arrayList.add(str);
			};
		};
		
		Collections.sort(arrayList);
		return arrayList;
	};

	
	// returns true only if aString could be definitively and unambiguously converted to an integer.
	private boolean isNumber(String aString)
	{
		try {
	        Integer.parseInt( aString );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	};

	
	// defines the comparator operator based on the existence of key auxiliary words
	private void identifyComparator(){
		
		// default value for comparator is <equals>.
		if(!explicitNumbers.isEmpty() ){ comparator = Comparator.equals;};

		String[] greaterThanWords = {"greater", "over", "above", "more"}; 
		setComparatorWithTheseWords(greaterThanWords, Comparator.greaterThan, true);
		
		String[] lessThanWords = {"smaller", "under", "below", "less"};
		setComparatorWithTheseWords(lessThanWords, Comparator.lessThan, true);

		String[] inWords = {"between", "to", "from"};
		setComparatorWithTheseWords(inWords, Comparator.between, true);
		
		// the reason why we don't want to delete these words as they also help indicate the Dimension,
		// especially if it is not explicitly identified in the string.
		String[] greaterThanWords2 = {"older"}; 
		setComparatorWithTheseWords(greaterThanWords2, Comparator.greaterThan, false);

		String[] lessThanWords2 = {"younger"};
		setComparatorWithTheseWords(lessThanWords2, Comparator.lessThan, false);
		
		// insert code to do greaterThanOrEqualTo, and lessThanOrEqualTo...

		
		if(explicitNumbers.size() ==2){
			comparator = Comparator.between;
		};
	}
	
	
	// Sets the comparator based on the existence of key auxiliary words
	private void setComparatorWithTheseWords(String[] auxiliaryWords, Comparator comparator, boolean doDelete){
		if(explicitNumbers.isEmpty() ){return;}; 
		if(auxiliaryWords.length == 0) {return;};
		
		for(String word:auxiliaryWords){
			if(inputWords.contains(word) ){
				this.comparator = comparator;
				if(doDelete) {
					inputWords.remove(word);
				};
			};
		};
	};	
	
	
}
