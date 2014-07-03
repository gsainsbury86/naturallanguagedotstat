package naturallanguagedotstat.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Dataset implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
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
	
}
