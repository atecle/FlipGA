import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Expression {
	
	public static Clause[] formula;
	private static int num_clauses;
	private static int num_vars;
	private static Scanner sc = null;

	
	public Expression(File file) {
		formula = getFormula(file);
	}
	
	public int numVars() {
		return num_vars;
	}
	
	public int numClauses() {
		return num_clauses;
	}
	
	
	private static Clause[] getFormula(File file) {
		
		
		try {
			 sc = new Scanner(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//skipping comments
		for (int i = 0; i < 7; i++) {
			sc.nextLine();
		}
		
		while (sc.hasNext()) {
			if (sc.hasNextInt()) {
				num_vars = sc.nextInt();
				num_clauses = sc.nextInt();
				break;
			}
			sc.next();
		}
		
		formula = new Clause[num_clauses];
		for (int i = 0; i< num_clauses ; i++) {
			formula[i] = getClause(sc.nextLine());
		}
		
		return formula;
	}
	
	private static Clause getClause(String line) {
		
		int l0 = sc.nextInt();
		int l1 = sc.nextInt();
		int l2 = sc.nextInt();
		Clause c = new Clause(l0, l1, l2);
		
		return c;
	}
}
