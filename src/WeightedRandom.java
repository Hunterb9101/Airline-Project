import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class WeightedRandom {
	LinkedHashMap<String,Double> items;
	Random rand = new Random();
	
	public WeightedRandom(LinkedHashMap<String,Double> items){ //Currently Single-Purpose.
		this.items = items;
	}
	
	public String compute(){
		
		Set s = items.entrySet();
		Iterator i = s.iterator();
		double randNum = rand.nextDouble();
		double runningTotal = 0;
		String output = null;
		while(i.hasNext() && output == null){
			Map.Entry entry = (Map.Entry)i.next();
			if(randNum < runningTotal + (double)entry.getValue() && randNum > runningTotal){
				output = (String)entry.getKey();
			}
			else{
				runningTotal += (double)entry.getValue();
			}
		}
		return output;
	}
}
