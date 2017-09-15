package com.pivotpayables.expensesimulator;
/**
 * 
 */

/**
 * @author John Toman
 * 1/8/2015
 * This class generates a GUID with a specify number of 4 character, blocks.
 * The blocks are either upper case letters or digits.
 * The blocks alternate between alpha and numeric.
 *
 */
import java.util.Random;

public class GUID {
	protected static String GUID="";
	protected static char myChar;
	protected static Random myRandom = new Random();
	protected static String BlockType ="Alpha";


	public static String getGUID (int blocks) {
		//blocks is the number of blocks for the GUID
		GUID = "";
		
		for (int blockcount = 0; blockcount < blocks; blockcount++) {
			for (int charcount =0; charcount<4; charcount++) {
				if (BlockType == "Alpha") {
					myChar = (char) (myRandom.nextInt(26) + 'A');
				} else {
					myChar = (char) (myRandom.nextInt(10) + '0'); 
				}
				GUID = GUID + myChar;
			}
			//switch the block type
			if (BlockType == "Alpha") {
				BlockType = "Numeric";
			} else {
				BlockType = "Alpha";
			}

		}

		return GUID;
	}

}
