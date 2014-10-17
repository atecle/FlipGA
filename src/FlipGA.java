
import java.util.*;
import java.io.*;

public class FlipGA {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Enter path to .cnf file");
		}

		File file = new File(args[0]);
		if (!file.exists()) {
			System.err.println("File " + args[0] + " not found.");
			System.exit(1);
		}

		Expression exp = new Expression(file);
		Population pop = new Population(exp.numVars());

 
		while (pop.evaluateFitness(pop.population[0].bitString) != exp.numClauses()) {
			pop.select();
			pop.crossOver();
			pop.mutation();
			pop.flipHeuristic();
		}
		
		System.out.println("Satisfying assignment: " + pop.population[0].bitString);

	}

}
