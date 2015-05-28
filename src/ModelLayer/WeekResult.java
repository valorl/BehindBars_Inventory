package ModelLayer;

import java.util.ArrayList;

public class WeekResult {
	
	private Week weekA;
	private Week weekB;
	
	private Week weekC;
	private Week weekD;
	private Week weekE;
	
	ArrayList<ItemResult> results;
	
	public WeekResult() {
		results = new ArrayList<ItemResult>();
	}

	public Week getWeekA() {
		return weekA;
	}
	public void setWeekA(Week weekA) {
		this.weekA = weekA;
	}

	public Week getWeekB() {
		return weekB;
	}
	public void setWeekB(Week weekB) {
		this.weekB = weekB;
	}
	
	public Week getWeekC() {
		return weekC;
	}

	public void setWeekC(Week weekC) {
		this.weekC = weekC;
	}

	public Week getWeekD() {
		return weekD;
	}

	public void setWeekD(Week weekD) {
		this.weekD = weekD;
	}

	public Week getWeekE() {
		return weekE;
	}

	public void setWeekE(Week weekE) {
		this.weekE = weekE;
	}

	// Results list

	public ArrayList<ItemResult> getResults() {
		return results;
	}
	public void setResults(ArrayList<ItemResult> results) {
		this.results = results;
	}
	
	public void addResult(ItemResult result) {
		if(!results.contains(result)) {
			results.add(result);
		}
	}
	
	
	

}
