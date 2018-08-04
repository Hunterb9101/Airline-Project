import java.util.LinkedHashMap;

public class MarkovState {
	public String stateName;
	public LinkedHashMap<String,Double> probabilities;
	public MarkovState(String stateName, LinkedHashMap<String,Double> probabilities){
		this.stateName = stateName;
		this.probabilities = probabilities;
	}
}
