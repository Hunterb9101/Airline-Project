
public class Temp {
	public static void main(String[] args){
		MarkovChain markov = new MarkovChain("res/Markov.txt");
		markov.generateProbabilities("");
		System.out.println("");
		System.out.println(markov.generate(7));
	}
}
