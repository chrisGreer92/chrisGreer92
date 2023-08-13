package reversi;

import java.util.Scanner;

/**
 * Wrapper class created for use in COMP1009 to simplify reading from keyboard
 * Note: we use the default 'whitespace' delimiter for this
 * @author jason.atkin@nottingham.ac.uk
 */

public class PGPInput
{
	// This is our single keyboard scanner - we only ever create one of these
	static Scanner keyboardScanner;
	
	// Retrieve the singleton-like scanner object that we created
	static Scanner getScanner() 
	{
		if ( keyboardScanner == null )
			keyboardScanner = new Scanner(System.in);
		return keyboardScanner;
	}
	
	/* Get next short from input */
	static short nextShort()
	{
		if ( !getScanner().hasNextShort() )
			return Short.MIN_VALUE; // Invalid
		return getScanner().nextShort();
	}
	
	/** Return true if the next value to be read is an integer */
	static boolean hasInt()
	{
		return getScanner().hasNextInt();
	}
	
	/** Get next integer from input */
	static int nextInt()
	{
		if ( !getScanner().hasNextInt() )
			return Integer.MIN_VALUE; // Invalid
		return getScanner().nextInt();
	}
	
	/* Get next long from input */
	static long nextLong()
	{
		if ( !getScanner().hasNextLong() )
			return Long.MIN_VALUE; // Invalid
		return getScanner().nextLong();
	}
	
	/* Ask for the next string from the input */
	static String nextString()
	{
		return getScanner().next();
	}
}
