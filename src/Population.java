import java.util.BitSet;
import java.util.Random;

public class Population {

	public BitSet[] population = new BitSet[10];


	//Population is initially random. Creating 10 random bit strings of length n, number of variables.
	//Bit strings correspond to an assignment of variables.
	public Population(int n) {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {

			BitSet b = new BitSet(n);

			for (int j = 0; j< n; j++) {
				int bit = r.nextInt(2);
				if (bit == 1) b.set(j, true);
				else b.set(j, false);
			}
			population[i] = b;
		}
	}

	public int evaluate(BitSet bstring) {
		
		int fitness = 0;
		
		for (int i = 0; i < Expression.formula.length; i++) {
			
			//indices in candidate solution bstring which we are substituting into clause. 
			int i0 = Expression.formula[i].literals[0];
			int i1 = Expression.formula[i].literals[1];
			int i2 = Expression.formula[i].literals[2];
			
			//converting into truth values 0 or 1
			int l0 = bstring.get(i0) == true? 1 : 0;
			int l1 = bstring.get(i1) == true? 1 : 0;
			int l2 = bstring.get(i2) == true? 1 : 0;
			
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


}
