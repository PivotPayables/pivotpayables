package com.pivotpayables.expensesimulator;
/**
 * 
 */

/**
 * @author John Toman
 * 1/16/2015
 * This class provides custom functions.
 *
 */
import java.util.Random;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;




//import static java.lang.System.out;


public class CustomFunctions {

	public static int skewedDistibution (int max){
		/* This method calculates a random integer for a left skewed distribution.
		 * The parameter max specifies the maximum members in the distribution.  It can create results for max = 10, 100, or greater than 1,000
		 * A left skewed distribution is very common in business. 
		 * For example, the distribution for the number of employees, or the number of clients (accounts) a company has is a left skewed distribution.
		 * The parameter max is the maximum number of items (e.g., employees, accounts) any company can have.
		 * It returns an integer between 1 and max using a distribution skewed to the left.
		 * It simulates the skewed distribution by first determining what sample it falls.
		 *    
		 */
		Random myRandom = new Random();
		int sample;
		int section = myRandom.nextInt(100);//section is a random integer between 0 and 99.  It determines what section of the skewed distribution the result falls.
		int result =0;
		


		if (max == 10 ) {//distribution has 10 members

			if (section <25) {//25% chance of being a 1
				result = 1;
			} else if (section <43) {//18% of being 2
				result = 2;
			} else if (section <58) {//15% of being 3
				result = 3;
			} else if (section <70) {//12 % of being 4
				result = 4;
			} else if (section <80) {//10% of being 5
				result = 5;
			} else if (section <87) {//7 % of being 6
				result = 6;
			} else if (section <93) {//6 % of being 7
				result = 7;
			}else if (section <97) {//4 % of being 8
				result = 8;
			} else {// 3% of being 9
				result = 9;
			}
		} else if (max == 100) {//distribution has between 100 members
			if (section <20) {//20% chance of being between 1 and 10
				result = 1+ myRandom.nextInt(10);
			} else if (section <37) {//17% of being between 11 and 20
				result = 11+ myRandom.nextInt(10);
				
			} else if (section <51) {//14% of being between 21 and 30
				result = 21+ myRandom.nextInt(10);

			} else if (section <63) {//12% of being between 31 and 40
				result = 31+ myRandom.nextInt(10);
			} else if (section <73) {//10% of being between 41 and 50
				result = 41 + myRandom.nextInt(10);
			} else if (section <82) {//9 % of being between 51 and 60
				result = 51 + myRandom.nextInt(10);
			} else if (section <88) {//6 % of being between 61 and 70
				result = 61 + myRandom.nextInt(10);
			}else if (section <93) {//5 % of being between 71 and 80
				result = 71 + myRandom.nextInt(10);
			}else if (section <97) {//4 % of being between 81 and 90
				result = 81 + myRandom.nextInt(10);
			} else {//3% of being between 91 and 100
				result = 91 + myRandom.nextInt(10);
			}
		} else if (max >=1000) {//Distribution is at least 1,000
			sample = myRandom.nextInt(max)+1;//create a random integer between 1 and the maximum number of members for the distribution
			if (sample < .80*max) {//80% chance of there being fewer than 100;

				if (section <7) {//7% of having between 1 and 10
					result = 1+ myRandom.nextInt(10);
					
				} else if (section <25) {//18% of having between 11 and 25
					result = 11 + myRandom.nextInt(15);
					
				} else if (section <41) {//16% of having between 26 and 35
					result = 26 + myRandom.nextInt(10);
		
				} else if (section <56) {//15 % of having between 36 and 45
					result = 36 + myRandom.nextInt(10);
				} else if (section <68) {//12 % of having between 46 and 55
					result = 46 + myRandom.nextInt(10);
				} else if (section <78) {//10 % of having between 56 and 65
					result = 56 + myRandom.nextInt(10);
				} else if (section <85) {//7 % of having between 66 and 75
					result = 66 + myRandom.nextInt(10);
				}else if (section <91) {//6 % of having between 76 and 85
					result = 76 + myRandom.nextInt(10);
				} else if (section <96) {//5 % of having between 86 and 95
					result = 86 + myRandom.nextInt(10);
				} else {//4 % of having between 96 and 100 
					result = 96 + myRandom.nextInt(5);
				}
			
			} else if (sample <0.9 * max) {//10% chance of there being between 101 and 500
				result = 101 + myRandom.nextInt(400);
			} else if ((sample < 0.95 * max) || (max ==1000)) {//5% (10% when max is 1,000) chance of there being between 501 and 1000
				result = 501 + myRandom.nextInt(500);
			} else if ((sample < 0.98 * max)||(max <=5000)) {//3% chance of this section happening
				result = 1001 + myRandom.nextInt(4000);
			} else if ((sample < 0.997 * max) ||(max <= 10000)){//1.7% chance of this section happening
				result = 5001 + myRandom.nextInt(5000);
			} else {//0.3% chance of this section happening
				result = 10001 + myRandom.nextInt(max-10000);
			}
		}
		return result;
	}
	public static double convertMoney (double amount, int decimals) {
		//converts a double amount into a double suitable to represent an amount of money by rounding the amount to the nearest least significant digit the currency supports.
		// decimals is the number of decimal places the currency supports.  For example, the USD support two decimal places, so it would be 2.
		
		BigDecimal bd = new BigDecimal(amount).setScale(decimals, RoundingMode.HALF_EVEN);
		double money = bd.doubleValue();
		return money;
		
	}
	public static double[] splitMoney (double amount, int decimals, int howmany) {
		//this splits a specified amount of money into a specified number of shares.  howmany is the number of shares to split the amount into.
		// decimals is the number of decimal places the currency supports.  For example, the USD support two decimal places, so it would be 2.
		//It uses a penny tweaking algorithm to ensure each share is suitable to represent an amount of money AND the sum of the share equals the original amount.
		
		double share;//a share amount
		double shares[] = new double[howmany];//an array to hold the share amounts
		int intshare;//an integer that is a placeholder for a temporary share amount
		
		double a = amount;//placeholder for the amount
		double money;
		
		a = a*Math.pow(10,decimals);//move the dimes, cents, and mils to the left of the decimal place by multiplying 10^decimals.

		int intamt = (int)a;//cast the double as an integer
		a = amount;//reset a to the original amount
		
		intshare = (intamt/howmany);//the intamt split into the specified number of shares, 

		int remainder = intamt%howmany;//the portion of the intamt that the penny tweaking algorithm must spread evenly among the shares.


		for (int i=0; i<howmany; i++) {
			//first, give each share the same share amount
			share = intshare/Math.pow(10,decimals);//convert intshare into the share amount by dividing it by 10^decimals.
			share = CustomFunctions.convertMoney(share, decimals);
			shares[i] = share;
		}//for i loop	
			 
		if (remainder > 0) {//if there is a remainder (remainder > 0), then do penny tweaking
			for (int j=0; j<remainder; j++) {
				//starting with share[0] add one of the remainder; loop until all the remainder has been applied
				share = 1/Math.pow(10,decimals);//convert one of the remainder into a "penny" by dividing 1 by 10^decimals. "Penny" means the least significant amount the currency supports.
				share = CustomFunctions.convertMoney(share, decimals);

				money = shares[j] + share;//add the penny to this share
				shares[j] = CustomFunctions.convertMoney(money, decimals);

			}//for j loop
		}//if block


		return shares;
}



	public static byte[] readFully(InputStream stream) throws IOException {
	    byte[] buffer = new byte[100048576];//100 mega bytes
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    int bytesRead;
	    while ((bytesRead = stream.read(buffer)) != -1)
	    {
	        baos.write(buffer, 0, bytesRead);
	    }
	    return baos.toByteArray();
	}
	public static byte[] loadFile(String sourcePath) throws IOException
	{
	    InputStream inputStream = null;
	    try 
	    {
	        inputStream = new FileInputStream(sourcePath);
	        return readFully(inputStream);
	    } 
	    finally
	    {
	        if (inputStream != null)
	        {
	            inputStream.close();
	        }
	    }
	}
}


