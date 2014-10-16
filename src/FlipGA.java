
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

	}

}
