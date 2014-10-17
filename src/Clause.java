
public class Clause {

	
	//an index in literal array corresponds to variable ID number being set true/false in clause
	//truth value of literals[i] in this clause is clause[i]
	public int[] literals = new int[3];					
	
	//truth value of each literal in clause
	//the variable ID number of clause[i] is literals[i]
	public int[] clause = new int[3];

	//indices in input file are 1-based. must subtract
	public Clause(int l0, int l1, int l2) {
		literals[0] = Math.abs(l0) - 1;		
		literals[1] = Math.abs(l1) - 1;
		literals[2] = Math.abs(l2) - 1;

		if (l0 > 0) clause[0] = 1;
		else clause[0] = 0;
		
		if (l1 > 0) clause[1] = 1;
		else clause[1] = 0;
		
		if (l2 > 0) clause[2] = 1;
		else clause[2] = 0;
		
		
		
	}
}
