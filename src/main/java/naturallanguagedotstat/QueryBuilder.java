package naturallanguagedotstat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.parser.NumericParser;
import naturallanguagedotstat.parser.SemanticParser;
import naturallanguagedotstat.utils.Utils;

public class QueryBuilder {

	private static final String SEX = "Sex";
	private static final String AGE = "Age";

	//TODO: Make static once we fix one-time loading
	private ArrayList<Dataset> datasets;
	private Dimension ASGS2011;

	private String query;
	private String region;
	private HashMap<String, ArrayList<String>> queryInputs;
	public String getRegion() {
		return region;
	}

	public HashMap<String, ArrayList<String>> getQueryInputs() {
		return queryInputs;
	}

	private String restfulURL;

	public QueryBuilder(String query, ArrayList<Dataset> datasets, Dimension ASGS2011) {
		this.query = query;
		this.datasets = datasets;
		this.ASGS2011 = ASGS2011;
	}

	public String build() throws IOException, ClassNotFoundException {

		SemanticParser semanticParser = new SemanticParser(this.query, this.datasets, this.ASGS2011);
		semanticParser.parseText();

		queryInputs = semanticParser.getDimensions();	

		region = queryInputs.get("region").get(0);
		queryInputs.remove("region");

		Dataset dataset = findBestMatchDatasetForDimensionNames();

		if(queryInputs.containsKey(AGE)){
			optimizeAgeCodeList(queryInputs, dataset);
		};

		setDefaultsForMissingDimensions(queryInputs, dataset);

		restfulURL = generateURL(dataset);
		//TODO: Return more details in some new object
		return restfulURL;


	}

	private void setDefaultsForMissingDimensions(
			HashMap<String, ArrayList<String>> queryInputs2, Dataset dataset) {
		for(Dimension dim : dataset.getDimensions()){
			if(dim.getName().equals(AGE) && queryInputs2.get(AGE) == null){
				ArrayList<String> list = new ArrayList<String>();
				list.add("Total all ages");
				queryInputs2.put(AGE, list);
				if(dataset.getName().equals("ABS_CENSUS2011_B20")  
						|| dataset.getName().equals("ABS_CENSUS2011_B21")  
						|| dataset.getName().equals("ABS_CENSUS2011_B40") 
						|| dataset.getName().equals("ABS_CENSUS2011_B42") 
						){
					ArrayList<String> list2 = new ArrayList<String>();
					list2.add("15 years and over");
					queryInputs2.put(AGE, list2);
				};
			};

			if(dim.getName().equals(SEX) && queryInputs2.get(SEX) == null){
				ArrayList<String> list3 = new ArrayList<String>();
				list3.add("Persons");
				queryInputs2.put(SEX, list3);
				//System.out.println("setting default Sex to : Persons");
			};
		};
	}

	private void optimizeAgeCodeList(HashMap<String, ArrayList<String>> queryInputs2, Dataset dataset) {
		if(!queryInputs2.containsKey(AGE)){return;};
		if(queryInputs2.get(AGE).get(0).equals("Total all ages")){return;}


		NumericParser ageQueryParser = new NumericParser(queryInputs2.get(AGE).get(0) );

		int a0 = Integer.parseInt(ageQueryParser.getExplicitNumbers().get(0) );
		int a1 = (ageQueryParser.getExplicitNumbers().size() >1) 
				? Integer.parseInt(ageQueryParser.getExplicitNumbers().get(1) ) : -1;



				HashMap<String, String> ageCodeList = null;
				for(Dimension dim : dataset.getDimensions()){
					if(dim.getName().equals(AGE)){
						ageCodeList = dim.getCodelist();
					};
				};

				List<String> ageCodeListDescriptions = new ArrayList<String>(ageCodeList.values());

				HashMap< String, Double> matches = new HashMap< String, Double>();

				Double overlapScore;
				for (String descr: ageCodeListDescriptions){
					NumericParser ageDescriptionParser = new NumericParser(descr);

					int b0 = (ageDescriptionParser.getExplicitNumbers().size() >0) 
							? Integer.parseInt(ageDescriptionParser.getExplicitNumbers().get(0) ) : -1;

							int b1 = (ageDescriptionParser.getExplicitNumbers().size() >1) 
									? Integer.parseInt(ageDescriptionParser.getExplicitNumbers().get(1) ) : -1;

									overlapScore =  getOverlapScore(a0, a1, b0, b1);
									if(overlapScore > 0 ){matches.put(descr, overlapScore);}
									ageDescriptionParser = null;
				};

				
				ArrayList<String> list = new ArrayList<String>();
				list.add(getKeyForMaxValue (matches));
				
				queryInputs2.put(AGE, list);
	}

	private Double getOverlapScore(int a0, int a1, int b0, int b1) {
		if(b1 == -1){
			return 0.00; 
		};

		if(a1 ==-1  && b1 == -1){
			if(a0==b0){
				return 1.00;
			};
		};


		if(b1 != -1){
			if(b0 <= a0 && a0 <= b1){
				return Math.min( 1.0 * (b1-a0+1)/(b1-b0+1), 1.0* (b1-a0+1)/(a1-a0+1) );
			};
		};


		if(a1 != -1){
			if(a0 <= b0 && b0 <= a1){
				return Math.min(1.0*  (a1-b0+1)/(b1-b0+1), 1.0* (a1-b0+1)/(a1-a0+1) ) ;
			};
		};

		if(a1 !=-1  && b1 !=-1 ){
			if(b0 <= a0 && a1 <= b1){
				return Math.min( 1.0* (a1-a0+1)/(b1-b0+1), 1.0* (a1-a0+1)/(a1-a0+1) );
			};
		};

		// reutrn any negative value to signify a null result.
		return -1.00;
	};

	/**
	 * Builds the query for the given dataset and specified dimension values
	 * 
	 * @param ds the Dataset object
	 * @return
	 */
	public String generateURL(Dataset ds){
		String url;

		url = "http://";

		url += Service.serverName;

		url += "/restsdmx/sdmx.ashx/GetData/";

		url += ds.getName()+"/";

		/* ensure order */
		for(Dimension dim : ds.getDimensions()){
			for(String dimKey : queryInputs.keySet()){
				if(dim.getName().equals(dimKey)){
					//TODO: Loop over all in queryInputs
					url += Utils.findValue(dim.getCodelist(),queryInputs.get(dimKey).get(0)) + ".";
				}
			}
		}

		String regionCode = Utils.findValue(ASGS2011.getCodelist(), region);
		String stateCode = regionCode.substring(0,1);
		String regionType = regionTypeForRegionCode(regionCode);

		url += stateCode + ".";
		url += regionType + ".";
		url += regionCode + ".";
		url += "A";

		url += "/ABS";

		return url;
	}

	// ........................................................................................

	private String getKeyForMaxValue (HashMap< String, Double> map){
		double maxValue = -9999999;
		String keyForMaxValue = null;

		for (String key : map.keySet()) {
			if(map.get(key) > maxValue){keyForMaxValue = key; maxValue = map.get(key);};
		};
		return keyForMaxValue;
	};

	/**
	 * Find a list of Datasets which contain the given dimensions (by name).
	 *  
	 * @param set an ArrayList of Strings with names of Dimensions
	 * @return an ArrayList of Dimension objects
	 */
	public ArrayList<Dataset> findDatasetsWithDimensionNames(){
		ArrayList<Dataset> toReturn = new ArrayList<Dataset>();

		for(Dataset dataset : datasets){
			int c = 0;
			for(Dimension dimension : dataset.getDimensions()){
				for(String dimensionName : queryInputs.keySet()){
					if(dimension.getName().equals(dimensionName)){
						c++;
					}
				}
			}
			if(c == queryInputs.keySet().size()){
				toReturn.add(dataset);
			}
		}
		return toReturn;
	}

	public Dataset findBestMatchDatasetForDimensionNames(){
		ArrayList<Dataset> datasetsWithDimensions = findDatasetsWithDimensionNames();

		Dataset toReturn = null;
		for(Dataset ds : datasetsWithDimensions){
			if(toReturn == null){
				toReturn = ds;
			}
			if(ds.getDimensions().size() < toReturn.getDimensions().size()){
				toReturn = ds;
			}
		};
		return toReturn;
	}

	/**
	 * returns the ASGS region type String for the given ASGS region code.
	 * 
	 * @param regionCode the region Code
	 * @return the region Type
	 */
	public String regionTypeForRegionCode(String regionCode) {
		String regionType = "";

		switch(regionCode.length()){
		case 1:
			regionType = "STE";
			break;
		case 3:
			regionType = "SA4";
			break;
		case 5: 
			regionType = "SA3";
			break;
		case 9: 
			regionType = "SA2";
			break;
		default:
			break;
		}

		//System.err.println(regionCode);

		if(regionCode.equals("0")){
			regionType = "AUS";
		}
		return regionType;
	}



}
