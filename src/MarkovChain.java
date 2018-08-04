import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class MarkovChain {
	public String filePath;
	public LinkedHashMap<String,LinkedHashMap<String,Double>> states = new LinkedHashMap<String,LinkedHashMap<String,Double>>();
	
	public MarkovChain(String filePath){
		this.filePath = filePath;
	}
	public void generateProbabilities(String delimiter){ // Multi-character chains are partially implemented
		List<String> fileDat = readFile(new File(filePath));
		List<String> find = new ArrayList<String>(); // A list of all characters/strings that will be looked for
		
		
		// Populate "find"
		for(char c = 'a'; c<='z'; c++){
			find.add(String.valueOf(c));
		}
		find.add(" ");
		
		// Build Probabilities for each entry of "find"
		for(int i = 0; i< find.size(); i++){
			System.out.println("Probabilities for: " + find.get(i));
			LinkedHashMap<String,Integer> counts = new LinkedHashMap<String,Integer>(); // How many times are the strings found after the variable "Find"
			int totalCounts = 0; // Keeps a running total of the variable, LHM counts
			
			for(int j = 0; j<fileDat.size(); j++){ // Get the number of occurrences of strings
				int lastIdx = 0; // Last known character in string
				while(fileDat.get(j).indexOf(find.get(i),lastIdx) != -1){
					lastIdx = fileDat.get(j).indexOf(find.get(i),lastIdx)+find.get(i).length();
					try{
						String nextPhrase = fileDat.get(j).substring(lastIdx, fileDat.get(j).indexOf(delimiter,lastIdx)+1);
						totalCounts++;
						if(counts.get(nextPhrase) == null){
							counts.put(nextPhrase, 1);
						}
						else{
							counts.put(nextPhrase, counts.get(nextPhrase) + 1);			 
						}
					}
					catch(IndexOutOfBoundsException e){
						// End of word, Does not need to collect any data
						// (I think)
					}
				}
			}
			
		    Set set = counts.entrySet(); // Gets a set of Counts
		    Iterator iter = set.iterator(); // Builds an Iterator
		    LinkedHashMap<String,Double> probabilities = new LinkedHashMap<String,Double>();  
		    
		    while(iter.hasNext()){
				Map.Entry entry = (Map.Entry)iter.next();
		        probabilities.put((String) entry.getKey(), (double)((int)entry.getValue())/totalCounts); 
		        System.out.println("    Cnt: " + entry.getKey() + ": " + entry.getValue() + ", Prb: " + entry.getKey() + "%: " + probabilities.get(entry.getKey()));
			}
		    states.put(find.get(i),probabilities);
		}
	}
	
	public static List<String> readFile(File filePath) { 
		String fLine = "";
		List<String> fDataRaw = new ArrayList<String>(); // All Data in a file
		if (filePath.exists()) {
			try {
				Scanner scan = new Scanner(filePath);
				while (scan.hasNext()) {
					fLine = scan.nextLine(); // This records every line
					fDataRaw.add(fLine); // this adds the string into the entire
											// database
					fLine = "";// resets the line variable so the string doesn't
								// keep concatenating the lines before it.
				}
				scan.close();
			} catch (FileNotFoundException ignored) {
			}
		}

		else {
			System.out.println("ERROR: FileOps can't find the file!");
		}
		return fDataRaw;
	}
	
	public String generate(int iterations){
		Random rand = new Random();
		LinkedHashMap<String,Double> currProbabilities = states.get("a"); // Placeholder for random start state;
		String output = "";
		WeightedRandom weightRand;
		for(int i = 0; i<iterations; i++){
			weightRand = new WeightedRandom(currProbabilities);
			String newState = weightRand.compute();
			output+=newState;
			currProbabilities = states.get(newState);
		}
		
		return output;
	}
}
