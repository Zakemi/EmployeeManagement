package zakemi.solteq.Assignment.model;

import java.util.HashMap;
import java.util.Map;

public class Salary {
	
	private Map<Integer, Map<Integer, Double>> salary;
	
	public Salary(){
		salary = new HashMap<Integer, Map<Integer,Double>>();
	}

	public Double getByKey(Integer year, Integer month){
		return salary.get(year).get(month);
	}
	
	public void addAmount(Integer year, Integer month, Double amount){
		Map<Integer, Double> temp = new HashMap<Integer, Double>();
		temp.put(month, amount);
		salary.put(year, temp);
	}
}
