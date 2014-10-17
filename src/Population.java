import java.util.BitSet;
import java.util.Random;

public class Population {

	public String[] population = new String[10];
	

	//Population is initially random. Creating 10 random bit strings of length n, number of variables.
	//Bit strings correspond to an assignment of variables.
	public Population(int n) {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			
			String b = "";
			
			for (int j = 0; j< n; j++) {
				int bit = r.nextInt(2);
				if (bit == 1) b+="1";
				else b+="0";
			}
			population[i] = b;
		}
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


}
