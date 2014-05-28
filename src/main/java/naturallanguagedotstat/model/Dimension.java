package naturallanguagedotstat.model;

import java.io.Serializable;
import java.util.HashMap;

public class Dimension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String label;
	private HashMap<String,String> codelist;
	
	public Dimension(String name, String label, HashMap<String, String> codelist) {
		super();
		this.name = name;
		this.setLabel(label);
		this.codelist = codelist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<String, String> getCodelist() {
		return codelist;
	}
	public void setCodelist(HashMap<String, String> codelist) {
		this.codelist = codelist;
	}
	
	public String toString(){
		String toReturn = name;
		return toReturn += ": " + codelist;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
