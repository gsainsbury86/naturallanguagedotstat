package naturallanguagedotstat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.parser.NumericParser;
import naturallanguagedotstat.parser.SemanticParser;
import naturallanguagedotstat.utils.Utils;

public class QueryBuilder {

	private static final String AGE = "Age";

	private boolean doAggregateAges;

	private Dataset dataset;

	private String query;
	private HashMap<String, ArrayList<String>> queryInputs;

	public String getRegion() {
		return queryInputs.get("Region").get(0);
	}

	public HashMap<String, ArrayList<String>> getQueryInputs() {
		return queryInputs;
	}

	private String restfulURL;

	public QueryBuilder(String query) {
		this.query = query;

		doAggregateAges = true;
	}

	public String build() throws IOException, ClassNotFoundException {

		SemanticParser semanticParser = new SemanticParser(this.query);
		semanticParser.parseText();

		queryInputs = semanticParser.getDimensions();

		System.out.println(queryInputs);
		dataset = findBestMatchDatasetForDimensionNames();

		//System.out.println("best matched dataset is "+dataset.getName());
		for (Dimension dim: dataset.getDimensions() ){
			if(dim.getName().equalsIgnoreCase("Age") ){
				getBestAgeCodeLists(queryInputs, dataset);
			};
		};

		System.out.println(dataset.getName());

		setDefaultsForMissingDimensions(dataset);
		removeExtraneousDimensions(dataset);
		

		System.out.println("Required Dimensions");
		System.out.println("----------------------");		
		for(Dimension dimension : dataset.getDimensions()){

			System.out.println(dimension.getName());
		}

		System.out.println("----------------------");

		System.out.println("Dimension Selectors found/created for:");
		System.out.println("----------------------");		
		for(String name : queryInputs.keySet()) {

			System.out.println(name);
		}
		System.out.println("----------------------");

		restfulURL = generateURL(dataset);
		return restfulURL;
	}

	//TODO: Fix these! Work out which dimensions are in which datasets and set defaults.
	private void setDefaultsForMissingDimensions(Dataset dataset) {

		setDefaultsForMissingCensusRegionDimensions(dataset);
		setDefaultsForMissingCensusAgeDimension(dataset);




		try {
			BufferedReader abc = new BufferedReader(new FileReader(Service.resourcesDir+"/defaultDimensionSelectors.csv"));
			List<String[]> lines = new ArrayList<String[]>();
			String thisLine;

			while ((thisLine = abc.readLine()) != null) {
				lines.add(thisLine.trim().split("\\s*,\\s*"));

			}
			for(String[] defaults : lines) {
				//TODO: This for collection specific ones. I think whether dimensions exist is already checked when you set defaults so you don't actually need this.
				//if(dataset.getName().contains(defaults[0])) {
				//	setDefaultValueForDimension(dataset, defaults[1], defaults[2]);
				//}

				setDefaultValueForDimension(dataset, defaults[0], defaults[1]);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(dataset.getName().contains("ABS_CENSUS2011_B44")){
			setDefaultValueForDimension(dataset, "Industry of Employment", "Total");									
			setDefaultValueForDimension(dataset, "Occupation", "Total");												
		}


		if(dataset.getName().contains("LF")){
			setDefaultValueForDimension(dataset, "Region", "Total");									
			setDefaultValueForDimension(dataset, "Age", "Total");
			setDefaultValueForDimension(dataset, "Frequency", "Monthly");									
			setDefaultValueForDimension(dataset, "Adjustment Type", "Original");
		}

		//Begin MEI defaults
		setDefaultRegionForCPI(dataset);
		setDefaultValueForDimension(dataset, "Measure", 		"Percentage Change from Previous Period"); 		// CPI
		setDefaultValueForDimension(dataset, "Region", 			"Weighted average of eight capital cities"); 	// CPI
		setDefaultValueForDimension(dataset, "Index", 			"All groups CPI"); 								// CPI
		setDefaultValueForDimension(dataset, "Adjustment Type", "Original");									// CPI
		setDefaultValueForDimension(dataset, "Frequency", 		"Quarterly");									// CPI


		setDefaultValueForDimension(dataset, "Age", "Total all ages");
		setDefaultValueForDimension(dataset, "Sex", "Persons");
		setDefaultValueForDimension(dataset, "Selected Person Characteristics", "Total persons");


	}

	private void setDefaultRegionForCPI(Dataset dataset) {
		if(dataset.getName().contains("CPI") ){
			ArrayList<String> a1 = new ArrayList<String>();

			String str = queryInputs.get("Region").get(0);
			if(str.equalsIgnoreCase("Australia"))
				a1.add("Weighted average of eight capital cities");

			if(str.equalsIgnoreCase("New South Wales") || str.equalsIgnoreCase("Sydney") )
				a1.add("Sydney");

			if(str.equalsIgnoreCase("Victoria") || str.equalsIgnoreCase("Melbourne") )
				a1.add("Melbourne");

			if(str.equalsIgnoreCase("Queensland")|| str.equalsIgnoreCase("Brisbane") )
				a1.add("Brisbane");

			if(str.equalsIgnoreCase("South Australia")|| str.equalsIgnoreCase("Adelaide") )
				a1.add("Adelaide");

			if(str.equalsIgnoreCase("Western Australia")|| str.equalsIgnoreCase("Perth") )
				a1.add("Perth");

			if(str.equalsIgnoreCase("Tasmania")|| str.equalsIgnoreCase("Hobart") )
				a1.add("Hobart");

			if(str.equalsIgnoreCase("Northern Territory")|| str.equalsIgnoreCase("Darwin") )
				a1.add("Darwin");

			if(str.equalsIgnoreCase("Australian Capital Territory")|| str.equalsIgnoreCase("Canberra") )
				a1.add("Canberra");

			//System.out.println("a1 = "+a1);
			queryInputs.put("Region",a1);

		};
	}

	private void setDefaultsForMissingCensusAgeDimension(Dataset dataset) {
		if(!dataset.getName().contains("ABS_CENSUS2011")) 
			return;

		for(Dimension dim : dataset.getDimensions()){
			if(dim.getName().equals(AGE) && queryInputs.get(AGE) == null){
				if(dataset.getName().equals("ABS_CENSUS2011_B19")  
						|| dataset.getName().equals("ABS_CENSUS2011_B20")  
						|| dataset.getName().equals("ABS_CENSUS2011_B21")  
						|| dataset.getName().equals("ABS_CENSUS2011_B40") 
						|| dataset.getName().equals("ABS_CENSUS2011_B41") 
						|| dataset.getName().equals("ABS_CENSUS2011_B42") 
						|| dataset.getName().equals("ABS_CENSUS2011_B43") 
						|| dataset.getName().equals("ABS_CENSUS2011_B45") 
						){
					ArrayList<String> list2 = new ArrayList<String>();
					list2.add("15 years and over");
					queryInputs.put(AGE, list2);
				};
			};
		};
	}

	private void setDefaultsForMissingCensusRegionDimensions(Dataset dataset) {
		//TODO: Removing this probably breaks CPI etc.
		//if(!dataset.getName().contains("ABS_CENSUS2011")) 
		//	return;

		
	
		String regionCode = Utils.findValue(Service.regionDimension.getCodelist(), queryInputs.get("Region").get(0));
		String stateCode = regionCode.substring(0,1);
		String regionType = regionTypeForRegionCode(regionCode);




		ArrayList<String> stateList = new ArrayList<String>();

		Dimension state = null;
		for(Dimension dim : dataset.getDimensions()){
			if(dim.getName().equals("State")){
				state = dim;
			}
		}

		stateList.add(state.getCodelist().get(stateCode));

		Dimension regionTypeDim = null;
		for(Dimension dim : dataset.getDimensions()){
			if(dim.getName().equals(Service.regionTypeName)){
				regionTypeDim = dim;
				ArrayList<String> regionTypeList = new ArrayList<String>();
				regionTypeList.add(regionTypeDim.getCodelist().get(regionType));

				queryInputs.put("State",stateList);
				queryInputs.put(Service.regionTypeName, regionTypeList);
			}
		}


	}

	private void removeExtraneousDimensions(Dataset dataset) {

		queryInputs.keySet().retainAll(dataset.getDimensionNames());
	
	}

	static int count = 0;

	private void setDefaultValueForDimension(Dataset dataset, String dimName, String dimValue) {
		for(Dimension dim : dataset.getDimensions()){
			if(dim.getName().equals(dimName)) {
				if(queryInputs.get(dimName) == null){
					ArrayList<String> list = new ArrayList<String>();
					list.add(dimValue);
					queryInputs.put(dimName, list);
				}
			}
		};
	}

	private void getBestAgeCodeLists(HashMap<String, ArrayList<String>> queryInputs2, Dataset dataset) {
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

									if(ageDescriptionParser.getExplicitNumbers().size() >1){
										String comparatorDescriptor = ageDescriptionParser.comparatorAsString (ageDescriptionParser.getComparator() );

										if (comparatorDescriptor.equals("∈") )
											b1 = Integer.parseInt(ageDescriptionParser.getExplicitNumbers().get(1) );

										if (comparatorDescriptor.equals(">") )
											b1 = 199;

										if (comparatorDescriptor.equals("<") ){
											b1 = b0;
											b0 = 0;
										};
									}

									// This is a hack to prevent double-counting of age categories.
									// eg  15 years and over category for codeLists is merely the equivalent as "Total all ages", rather than a explicit age range.
									if(b0 == 15 && b1 == 199){
										b0 = -1;
										b1 = -1;
									};

									overlapScore =  getOverlapScore(a0, a1, b0, b1);
									matches.put(descr, overlapScore);
									ageDescriptionParser = null;
				};

				ArrayList<String> list = new ArrayList<String>();
				Double scoreMax = matches.get(getKeyForMaxValue(matches) );

				double epsilon = 0.000001;
				for (String descr: ageCodeListDescriptions){
					if(Math.abs(matches.get(descr) - scoreMax ) < epsilon  || matches.get(descr) > 0.5){
						list.add(descr);
					}
				};


				// Treat B04 differently as it is the only dataset with hierarchical age ranges.
				if(doAggregateAges && dataset.getName().equals("ABS_CENSUS2011_B04")){
					for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
						String s = iter.next();
						if (!isNumber(s) ) 
							iter.remove();
					};
				};

				// System.out.println("Matched AGE intervals are:" + list);
				if(scoreMax >= 0){
					queryInputs2.put(AGE, list);
				} else {
					// Remove any explicit age parameter, so that the default dimensional values will be inserted.
					queryInputs2.remove(AGE);
				}
	}

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

	private Double getOverlapScore(int a0, int a1, int b0, int b1) {
		//if both query and codelist are null
		if (a0==-1 || b0 ==-1) 
			return -1.0;

		//if both query and codelist are single-valued
		if(a1 ==-1  && b1 == -1){
			if(a0==b0){
				return +1.0; //perfect complete match
			};
		};

		//query is single-valued and codelist is an interval
		if(a1 == -1 && b1 != -1){
			if(b0 <= a0 && a0 <= b1){
				return +1.0 /(b1-b0+1);
			};
		};

		//if query is an interval, and codelist is single-valued
		if(a1 != -1 && b1==-1){
			if (!doAggregateAges)
				return -1.0;

			if(a0 <= b0 && b0 <= a1){
				return +1.0; //perfect element match .
			};
		};

		//if query and codelist are both intervals. 
		// returns what fraction of (b0,b1) is overlapping.
		if(a1 !=-1  && b1 !=-1 ){
			if(a0 <= b0 && b0 <= a1){
				return 1.0* Math.min(b1-b0+1, a1-b0+1) / (b1-b0+1);
			};
			if(b0 <= a0 && a0 <= b1){
				return 1.0* Math.min(b1-b0+1, b1-a0+1) / (b1-b0+1);
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

		url = String.format("http://%s/restsdmx/sdmx.ashx/GetData/%s/",Service.serverName, ds.getName());

		/* ensure order */
		for(Dimension dim : ds.getDimensions()){

			for(String dimKey : queryInputs.keySet()){
				if(dim.getName().equals(dimKey)){
					for(String str: queryInputs.get(dimKey)){
						if(dimKey.equals("Region") && ds.getName().contains("CENSUS")){
							String regionCode = Utils.findValue(Service.regionDimension.getCodelist(), str);
							url += regionCode + ".";
						}else{
							url += Utils.findValue(dim.getCodelist(),str) + "+";
						}
					}
					url = url.substring(0,url.length()-1);
					url+= ".";
				}
			}
		}


		url = url.substring(0,url.length()-1);
		url += "/ABS";

		//TODO: Add one for end time and have some better decision making
		//TODO: Removed I don't think this is important for Census queries.
		//url += "?startTime=";
		//url += findStartTime(ds.getTimeDimension());
		//url += "&endTime=";
		//url += findStartTime(ds.getTimeDimension());

		return url;
	}

	// ........................................................................................

	private String findStartTime(Dimension timeDimension) {

		ArrayList<String> list = new ArrayList<String>();
		list.addAll(timeDimension.getCodelist().keySet());
		Collections.sort(list);
		return list.get(list.size()-1);
	}

	private String getKeyForMaxValue (HashMap< String, Double> map){
		double maxValue = -9999999;
		String keyForMaxValue = null;

		for (String key : map.keySet()) {
			if(map.get(key) > maxValue){keyForMaxValue = key; maxValue = map.get(key);};
		};
		return keyForMaxValue;
	};



	public Dataset findBestMatchDatasetForDimensionNames(){

		HashMap<String, Integer> weights = new HashMap<String, Integer>();

		for(String dimensionName : queryInputs.keySet()){
			int cnt = 0;
			for(Dataset ds : Service.datasets){
				for(Dimension dim : ds.getDimensions()){
					if(dim.getName().equals(dimensionName)){
						cnt++;
					};
				};
			};
			weights.put(dimensionName, cnt );
		};

		System.out.println(weights);

		Dataset toReturn = null;
		double bestScore = -9999.0; //The higher the better.

		for(Dataset ds : Service.datasets){
			boolean b  = false;
			double datasetScore = 0;

			for(Dimension dim : ds.getDimensions()){
				for(String dimensionName : queryInputs.keySet()){
					if(dim.getName().equals(dimensionName)){
						b = true;
						datasetScore += 1.0 / weights.get(dimensionName);
					}
				}
			};

			if(datasetScore > bestScore && b){
				bestScore = datasetScore;
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

	public Dataset getDataset() {
		return dataset;
	}



}
