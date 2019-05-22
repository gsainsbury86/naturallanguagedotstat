package naturallanguagedotstat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Dataset implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String englishName;
	private ArrayList<Dimension> dimensions;
	private Dimension timeDimension;
	
	public Dataset(String name, ArrayList<Dimension> dimensions) {
		super();
		this.name = name;
		this.dimensions = dimensions;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Dimension> getDimensions() {
		return dimensions;
	}
	
	public HashSet<String> getDimensionNames() {
		HashSet<String> dimensionNames = new HashSet<String>();
		for(Dimension dimension : dimensions) {
			dimensionNames.add(dimension.getName());
		}
		return dimensionNames;
	}
	
	public void setDimensions(ArrayList<Dimension> dimensions) {
		this.dimensions = dimensions;
	}

	public String toString(){
		String toReturn = name+"\n";
		for(Dimension dim : dimensions){
			toReturn += dim.toString()+"\n";
		}
		
		return toReturn;
	}

	public Dimension getTimeDimension() {
		return timeDimension;
	}

	public void setTimeDimension(Dimension timeDimension) {
		this.timeDimension = timeDimension;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
}
