import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Population {

	public Individual[] population = new Individual[10];
	private String[] selected = new String[8];
	private String[] crossover = new String[8];
	private Individual[] next_gen = new Individual[10];
	private final static Random generator = new Random();
	private final static int[] ar = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
		10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
	int fitness_sum = 0;

	//Population is initially random. Constructor makes 10 random bit strings of length n, number of variables.
	//Bit strings correspond to an assignment of variables.
	public Population(int n) {

		for (int i = 0; i < 10; i++) {

			String b = "";

			for (int j = 0; j< n; j++) {
				int bit = generator.nextInt(2);
				if (bit == 1) b+="1";
				else b+="0";
			}
			int fitness = evaluateFitness(b);
			fitness_sum += fitness;
			Individual indiv = new Individual(b, fitness);
			population[i] = indiv;
		}


		Arrays.sort(population, Collections.reverseOrder());
		normalize(population);

		next_gen[0] = population[0];
		next_gen[1] = population[1];



	}

	public void select() {

		for (int i = 0; i < 8; i++) {
			double r = generator.nextDouble();
			for (int j = 0; j < 10; j++) {
				if (population[j].accumulated_normalized_fitness > r) {
					selected[i] = population[j].bitString;
					break;
				}
			}
		}

	}

	public void crossOver() {

		for (int i = 0; i < 8; i++) {

			int x = generator.nextInt(8);
			int y = generator.nextInt(8);
			String offspring = "";
			for (int j = 0; j < 20; j++)  {
				int r = generator.nextInt(2);
				if (r == 0) offspring+=selected[x].charAt(j);
				else offspring += selected[y].charAt(j);
			}
			crossover[i] = offspring;
		}
	}

	public void mutation() {

		for (int i = 0; i < 8; i++) {

			StringBuilder temp = null;
			int x = generator.nextInt(11);
			if (x == 10) continue;

			for (int j = 0; j < 20; j++) {

				int y = generator.nextInt(2);

				if (y == 0) {
					temp = new StringBuilder(crossover[i]);
					char flip = '0';
					if (crossover[i].charAt(j) == '0') flip = '1';
					temp.setCharAt(j, flip);
				}

			}

			crossover[i] = temp.toString();
		}
	}

	public void flipHeuristic() {
		
		shuffleArray(ar);
		
		int k = 2;
		for (int i = 0; i < 8; i++) {
			
			int fitness = evaluateFitness(crossover[i]);
			for (int j = 0; j < 20; j++) {
				StringBuilder temp = new StringBuilder(crossover[i]);
				int before = evaluateFitness(crossover[i]);
				char flip = '0';
				if (crossover[i].charAt(ar[j]) == '0') flip = '1';
				temp.setCharAt(ar[j], flip);
				int after = evaluateFitness(temp.toString());
				if (after - before > 0) {
					crossover[i] = temp.toString();
					fitness = after;
				}
			}
			next_gen[k++] = new Individual(crossover[i], fitness);
		}
		
		population = next_gen;
		Arrays.sort(population, Collections.reverseOrder());
		normalize(population);

		next_gen[0] = population[0];
		next_gen[1] = population[1];
		
		
	}

	public int evaluateFitness(String bstring) {

		int fitness = 0;

		for (int i = 0; i < Expression.formula.length; i++) {

			//indices in candidate solution bstring which we are substituting into clause. 
			int i0 = Expression.formula[i].literals[0];
			int i1 = Expression.formula[i].literals[1];
			int i2 = Expression.formula[i].literals[2];

			//converting into truth values 0 or 1
			int l0 = bstring.charAt(i0) == '1'? 1 : 0;
			int l1 = bstring.charAt(i1) == '1'? 1 : 0;
			int l2 = bstring.charAt(i2) == '1'? 1 : 0;

			//if e is 0, then the index at i0/1/2 is negated

			int e0 = Expression.formula[i].clause[0];
			int e1 = Expression.formula[i].clause[1];
			int e2 = Expression.formula[i].clause[2];

			e0 = e0 == 0 ? l0^1 : l0;
			e1 = e1 == 0 ? l1^1 : l1;
			e2 = e2 == 0 ? l2^1 : l2;

			if (e0 + e1 + e2 > 0) fitness++;
		}

		return fitness;
	}

	private void normalize(Individual[] population) {
		for (int i = 0; i < population.length ; i++) {
			population[i].normalized_fitness =  (double) population[i].fitness/fitness_sum;
			double accum = 0;
			for (int j = 0; j < i+1; j++) {
				accum+= population[j].normalized_fitness;
			}
			population[i].accumulated_normalized_fitness = accum;
		}


	}

	static void shuffleArray(int[] ar)
	{

		for (int i = ar.length - 1; i > 0; i--)
		{
			int index = generator.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}


	class Individual implements Comparable<Individual> {

		public String bitString;
		public  int fitness;
		public double normalized_fitness;
		public double accumulated_normalized_fitness;

		public Individual(String bString, int fit) {
			this.bitString = bString;
			this.fitness = fit;
		}

		@Override
		public int compareTo(Individual indiv) {
			if (this.fitness > indiv.fitness) return 1;
			if (this.fitness < indiv.fitness) return -1;
			return 0;

		}
	}
}

