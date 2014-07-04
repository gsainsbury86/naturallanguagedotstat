package naturallanguagedotstat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import naturallanguagedotstat.model.Dataset;
import naturallanguagedotstat.model.Dimension;
import naturallanguagedotstat.parser.NumericParser;
import naturallanguagedotstat.parser.SemanticParser;
import naturallanguagedotstat.utils.Utils;

public class QueryBuilder {

	private static final String SEX = "Sex";
	private static final String AGE = "Age";

	private boolean doAggregateAges;
	//TODO: Make static once we fix one-time loading
	private ArrayList<Dataset> datasets;
	private Dimension ASGS2011;

	private String query;
	private HashMap<String, ArrayList<String>> queryInputs;
	
	public String getRegion() {
		return queryInputs.get("Region").get(0);
	}

	public HashMap<String, ArrayList<String>> getQueryInputs() {
		return queryInputs;
	}

	private String restfulURL;

	public QueryBuilder(String query, ArrayList<Dataset> datasets, Dimension ASGS2011) {
		this.query = query;
		this.datasets = datasets;
		this.ASGS2011 = ASGS2011;

		doAggregateAges = true;
	}

	public String build() throws IOException, ClassNotFoundException {

		SemanticParser semanticParser = new SemanticParser(this.query, this.datasets, this.ASGS2011);
		semanticParser.parseText();

		queryInputs = semanticParser.getDimensions();	

		//TODO: hijack queryInputs for CPI
		if(query.contains("CPI")){

			queryInputs = new HashMap<String, ArrayList<String>>();

			ArrayList<String> a = new ArrayList<String>();
			ArrayList<String> b = new ArrayList<String>();
			ArrayList<String> c = new ArrayList<String>();
			ArrayList<String> d = new ArrayList<String>();
			ArrayList<String> e = new ArrayList<String>();

			a.add("Index Numbers");
			b.add("Weighted average of eight capital cities");
			c.add("All groups CPI");
			d.add("Original");
			e.add("Quarterly");

			queryInputs.put("Measure",a);
			queryInputs.put("Region",b);
			queryInputs.put("Index",c);
			queryInputs.put("Adjustment Type",d);
			queryInputs.put("Frequency",e);

		}/* end CPI hijack */

		Dataset dataset = findBestMatchDatasetForDimensionNames();

		if(queryInputs.containsKey(AGE)){
			getBestAgeCodeLists(queryInputs, dataset);
		};

		setDefaultsForMissingDimensions(queryInputs, dataset);

//		if(dataset.getName().contains("CENSUS")){
//			queryInputs.remove("State");
//		}

		restfulURL = generateURL(dataset);
		return restfulURL;
	}

	private void setDefaultsForMissingDimensions(
			HashMap<String, ArrayList<String>> queryInputs2, Dataset dataset) {

		//TODO: New way of dealing with CENSUS datasets.
		// this should probably be a better check.
		/* Manually add frequency key/value */
			if(dataset.getName().contains("ABS_CENSUS2011")){
				ArrayList<String> freq = new ArrayList<String>();
				freq.add("Annual");
				queryInputs2.put("Frequency", freq);
			}
			
			if(dataset.getName().contains("ABS_CENSUS2011")){
				String regionCode = Utils.findValue(ASGS2011.getCodelist(), queryInputs.get("Region").get(0));
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
					if(dim.getName().equals("Region Type")){
						regionTypeDim = dim;
					}
				}

				ArrayList<String> regionTypeList = new ArrayList<String>();
				regionTypeList.add(regionTypeDim.getCodelist().get(regionType));
				
				queryInputs.put("State",stateList);
				queryInputs.put("Region Type", regionTypeList);
			}
			
		

		for(Dimension dim : dataset.getDimensions()){
			if(dim.getName().equals(AGE) && queryInputs2.get(AGE) == null){
				ArrayList<String> list = new ArrayList<String>();
				list.add("Total all ages");
				queryInputs2.put(AGE, list);
				if(dataset.getName().equals("ABS_CENSUS2011_B20")  
						|| dataset.getName().equals("ABS_CENSUS2011_B21")  
						|| dataset.getName().equals("ABS_CENSUS2011_B40") 
						|| dataset.getName().equals("ABS_CENSUS2011_B41") 
						|| dataset.getName().equals("ABS_CENSUS2011_B42") 
						|| dataset.getName().equals("ABS_CENSUS2011_B43") 
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
			};
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
											b1 = 999;

										if (comparatorDescriptor.equals("<") ){
											b1 = b0;
											b0 = 0;
										};
									}


									overlapScore =  getOverlapScore(a0, a1, b0, b1);
									matches.put(descr, overlapScore);
									ageDescriptionParser = null;
				};

				ArrayList<String> list = new ArrayList<String>();
				Double scoreMax = matches.get(getKeyForMaxValue(matches) );

				double epsilon = 0.000001;
				for (String descr: ageCodeListDescriptions){
					if(Math.abs(matches.get(descr) - scoreMax ) < epsilon ){
						list.add(descr);
					}
				}

				// Treat B04 differently as it is the only dataset with hierarchical age ranges.
				if(doAggregateAges && dataset.getName().equals("ABS_CENSUS2011_B04")){
					for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
						String s = iter.next();
						if (!isNumber(s) ) 
							iter.remove();
					};
				};

				// System.out.println("Matched AGE intervals are:" + list);
				queryInputs2.put(AGE, list);
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

		//if query and codelist are both intervals
		if(a1 !=-1  && b1 !=-1 ){
			if(a0 <= b0 && b0 <= a1){
				return 1.0* (a1-b0+1) / Math.max(a1-a0+1, b1-b0+1);
			};
			if(b0 <= a0 && a0 <= b1){
				return 1.0* (b1-a0+1) / Math.max(a1-a0+1, b1-b0+1);
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
					for(String str: queryInputs.get(dimKey)){
						if(dimKey.equals("Region") && ds.getName().contains("CENSUS")){
							String regionCode = Utils.findValue(ASGS2011.getCodelist(), str);
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
		url += "?startTime=";
		url += findStartTime(ds.getTimeDimension());
		url += "&endTime=";
		url += findStartTime(ds.getTimeDimension());

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


	//TODO: This has fundamentally changed. The "best" dataset
	// is now the one for which there is the highest number of matches
	// this means that if you had 5 dimensions identified of which 4
	// were in one dataset and 3 of those 4 and the 5th were in another
	// dataset, it will just pick the first one is finds, rather than
	// breaking. It may make more sense to return an error when too
	// many selectors are specified.
	public Dataset findBestMatchDatasetForDimensionNames(){

		Dataset toReturn = null;
		int numMatches = 0;
		for(Dataset ds : datasets){
			if(toReturn == null){
				toReturn = ds;
			}

			int localMatches = 0;

			for(Dimension dim : ds.getDimensions()){
				for(String dimensionName : queryInputs.keySet()){
					if(dim.getName().equals(dimensionName)){
						localMatches++;
					}
				}
			}			

			if(localMatches > numMatches){
				toReturn = ds;
				numMatches = localMatches;
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
