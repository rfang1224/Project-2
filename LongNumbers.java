import java.util.Scanner;

/**
 * <p>This program performs numerical calculations based on user's input.
 * An operation needs to be provided using a three-token format:
 * <code>operand1 operator operand2</code>
 * in which the three tokens are separated by one of more white spaces.
 * The program terminates when the end of file (EOF) character is found.
 * (Note, this character is entered in the console by typing Ctrl+D sequence.)
 *
 * <p>Valid operands are any positive integers (no length limit).
 *
 * <p>Valid operators are:
 * <pre>
 * {@literal +}    addition
 * {@literal *}    multiplication
 * {@literal <}    less than
 * {@literal <=}   less than or equal
 * {@literal >}    greater than
 * {@literal >=}   greater than or equal
 * {@literal ==}   equal
 * {@literal <>}   not equal
 * </pre>
 *
 * <p>This is an interactive program. Here is a sample user interaction
 * (the user input and program's output are interleaved).
 *
 * <pre>
 * {@literal
 * 123 + 987
 * 1110
 * 123 * 1111111111111111111111111111111111111
 * 136666666666666666666666666666666666653
 * 11111111111111111111111111111111111111 + 99999999999999999999999999999999999999999999999999999
 * 100000000000000011111111111111111111111111111111111110
 * 123 < 987
 * true
 * 123 >= 9876
 * false
 * 123 == 123
 * true
 * 123 <> 123
 * false
 * 33333333333333333333333 * 11
 * 366666666666666666666663
 * 33333333333333333333333*11
 * Illegal expression found: 33333333333333333333333*11
 * 7 +3
 * Illegal expression found: 7 +3
 * 100000000000000000 - 9999999999999999
 * Illegal operator found: 100000000000000000 - 9999999999999999
 * }
 * </pre>
 * 
 * Compilation instructions:
 * 
 * <pre>
 * javac project2/LongNumbers.java
 * </pre>
 * 
 * Execution instructions:
 * 
 * <pre>
 * java project2.LongNumbers
 * </pre>
 */


public class LongNumbers {

	public static void main(String[] args) {
		
		
		Scanner in = new Scanner (System.in);
		
		String line = null;
		String[] tokens; 
		Number n1=null, n2=null, n3=null;
		
		while (in.hasNextLine() ) {
			//read input from user and break it into expected three tokens:
			//  operand1  operator  operand2 
			line = in.nextLine();
			tokens = line.split("\\s+"); 
			try {
				//get two operands 
				n1 = new Number(tokens[0]);
				n2 = new Number(tokens[2]);
				//perform the action of the operator 
				switch (tokens[1]) {
				case "+": 
					n3 = n1.add(n2); 
					System.out.println(n3);
					break;
				case "*": 
					n3 = n1.multiply(n2); 
					System.out.println(n3);
					break; 
				case "==":
					System.out.println(n1.equals(n2)  ? "true" : "false"); 
					break;
				case "<": 
					System.out.println(n1.compareTo(n2) < 0  ? "true" : "false"); 
					break;
				case "<=": 
					System.out.println(n1.compareTo(n2) <= 0  ? "true" : "false"); 
					break;
				case ">": 
					System.out.println(n1.compareTo(n2) > 0  ? "true" : "false"); 
					break;
				case ">=": 
					System.out.println(n1.compareTo(n2) >= 0  ? "true" : "false"); 
					break;
				case "<>": 
					System.out.println(n1.compareTo(n2) != 0  ? "true" : "false"); 
					break;
					
					
				default: //deals with unsupported and illegal operators 
					System.out.println("Illegal operator found: " + line);
					continue; 
				}
			}
			catch (IllegalArgumentException ex) {  //deals with illegal and misformatted requests 
				System.out.println("Illegal expression found: " + line);
				continue;
			}
			catch (ArrayIndexOutOfBoundsException ex) { //deals with illegal and misformatted requests 
				System.out.println("Illegal expression found: " + line);
				continue;
			}
		
		}
		
		in.close();
		
	}
	//prevent class instantiation (otherwise compiler generates default constructor) 
	private LongNumbers( ) {
		
	}

}
