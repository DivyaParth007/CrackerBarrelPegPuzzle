/* 

Name : Divyaben Ghinaiya
Course : CSCE 5450 Programming Languages 
Assignment 8 : Cracker Barrel Puzzle

*/


import java.util.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Cracker 
	{
  	public char c = 'x';
  	public char c1 = '.';
  	public int steps = 15;
  	public int on = 0;
	public boolean[] board  = new boolean[steps];
  	public int move_to = 1;
  	public int peg_start_position = 0;

	public int[][][] moves = 
	{
		{ {1, 3}, {2, 5} }, 
		{ {3, 6}, {4, 8} }, 
		{ {4, 7}, {5, 9} }, 
		{ {1, 0}, {6, 10}, 
		{7, 12}, {4, 5} },
		{ {7, 11}, {8, 13} }, 
		{ {2, 0}, {4, 3}, 
		{8, 12}, {9, 14} }, 
		{ {3, 1}, {7, 8} }, 
		{ {4, 2}, {8, 9} }, 
		{ {4, 1}, {7, 6} }, 
		{ {8, 7}, {5, 2} }, 
		{ {11, 12}, {6, 3} }, 
		{ {7, 4}, {12, 13} }, 
		{ {7, 3}, {8, 5},  
		{11, 10}, {13, 14} }, 
		{ {8, 4}, {12, 11} }, 
		{ {9, 5}, {13, 12} }  
	};
	public boolean answer = false;

	public char terse(int peg) 
	{
		return (check(peg)) ? c : c1;
	}

	public String puzzle() 
	{
		String arrangement = "";
		char[] put = new char[steps];
		for (int i = 0; i < steps; i++) 
		{
			put[i] = terse(i);
		}

	arrangement  = "     " + put[0] + "\n";
	arrangement += "    "  + put[1]  + " " + put[2] + "\n";
	arrangement += "   "   + put[3]  + " " + put[4]  + " " + put[5] + "\n";
	arrangement += "  "    + put[6]  + " " + put[7]  + " " + put[8]  + " " + put[9]  + "\n";
	arrangement += " "     + put[10] + " " + put[11] + " " + put[12] + " " + put[13] + " " + put[14] + "\n";

	return arrangement;
  	}

	public boolean check(int peg) 
	{
		if (peg < 0 || peg >= steps) 
		{
			return false;
		}

    	return board[peg];
  	}

	public boolean missed_peg(int peg) 
	{
		
		return !check(peg);
	}

	public int pegs_count() 
	{
		int pegsCount = 0;
		for (boolean present : board) 
		{
			if (present) 
			{
				pegsCount++;
     		}
    	}

    	return pegsCount;
  	}

  	public void movepeg(int peg, boolean set) 
	{
		if (peg < 0 || peg >= steps)
		{
			System.out.println("Index is invalid ..!");
		}
    
    	board[peg] = set;
  	}

  	public void shift(int peg) 
	{
    		movepeg(peg, false);
  	}

  	

  	public void reset() 
	{
    		result.clear();
    		for (int i = 0; i < steps; i++) 
		{
      			changepeg(i);
    		}
  	}


	public List<String> result = new ArrayList<String>();
 	public void go(int peg) 
	{
    		reset();
    		answer = false;
    		shift(peg);
    		result.add(puzzle());
  	}



	public void changepeg(int peg) 
	{
    		movepeg(peg, true);
  	}



  	public String play(int peg)
	{
    		if (peg < 0 || peg >= steps) 
		{
      			System.out.println("Invalid peg (" + peg + ") provided. Defaulting to " + peg_start_position);
      			peg = peg_start_position;
    		}
    	System.out.println("\n ***** " + peg + " ***** \n");
    	go(peg);
    	solution();
	return finalresult();
  	}


	public boolean replay() 
	{
    		return (pegs_count() == 1);
  	}


  	public String play() 
	{
    		System.out.println("Index is invalid ..!");
    		return play(peg_start_position);
  	}
 


  	public boolean step(int from, int over, int to) 
	{
		if (missed_peg(from) || missed_peg(over) || check(to))
		{
			return false;
		}

    	shift(from);
    	shift(over);
	changepeg(to);
    	return true;
  	}



	public void solution() 
	{
    		if (replay()) 
		{
      			answer = true;
      			return;
    		}

    		for (int peg = 0; peg < steps; peg++)
    		{
      			if (missed_peg(peg)) 
			{
        				continue;
      			}

      			for (int[] move : moves[peg]) 
			{
        				int from = peg;        // Move origin
        				int over = move[on]; // Peg moving over
        				int to   = move[move_to];   // Peg moving to
        				boolean[] savedBoard = board.clone();
				if (step(from, over, to) == false) 
				{
          				continue;
        				}

        				String currentBoard = puzzle();
        				result.add(currentBoard);
        				solution();

        				if (replay()) 
				{
          				answer = true;
          				return;
       				}

        				board = savedBoard;
        				result.remove(result.size() - 1);
      				}
    			}
  		}


	public String finalresult() 
	{
    		StringBuilder s = new StringBuilder();
    		for (String step : result) 
		{
      			s.append(step + "\n");
    		}

    	return s.toString();
  	}



  	public static void main(String[] args) 
	{
    		Cracker cracker = new Cracker();
    		for (int i = 0; i < 15; i++) 
		{
      			String ans = cracker.play(i);
      			System.out.println(ans);
    		}
  	}
}


/* Output : 


(base) Parths-MacBook-Pro:Java parth$ javac Cracker.java
(base) Parths-MacBook-Pro:Java parth$ java Cracker

 ***** 0 ***** 

     .
    x x
   x x x
  x x x x
 x x x x x

     x
    . x
   . x x
  x x x x
 x x x x x

     x
    . x
   x . .
  x x x x
 x x x x x

     .
    . .
   x . x
  x x x x
 x x x x x

     .
    x .
   . . x
  . x x x
 x x x x x

     .
    x x
   . . .
  . x x .
 x x x x x

     .
    x x
   . x .
  . . x .
 x . x x x

     .
    x x
   . x x
  . . . .
 x . . x x

     .
    . x
   . . x
  . . x .
 x . . x x

     .
    . .
   . . .
  . . x x
 x . . x x

     .
    . .
   . . x
  . . x .
 x . . x .

     .
    . .
   . . .
  . . . .
 x . x x .

     .
    . .
   . . .
  . . . .
 x x . . .

     .
    . .
   . . .
  . . . .
 . . x . .



 ***** 1 ***** 

     x
    . x
   x x x
  x x x x
 x x x x x

     x
    x x
   . x x
  . x x x
 x x x x x

     .
    . x
   x x x
  . x x x
 x x x x x

     .
    x x
   x . x
  . x . x
 x x x x x

     .
    . x
   . . x
  x x . x
 x x x x x

     .
    . x
   x . x
  . x . x
 . x x x x

     .
    . x
   x x x
  . . . x
 . . x x x

     .
    . .
   x . x
  . x . x
 . . x x x

     .
    . x
   x . .
  . x . .
 . . x x x

     .
    . x
   x . .
  . x . .
 . x . . x

     .
    . x
   x x .
  . . . .
 . . . . x

     .
    . x
   . . x
  . . . .
 . . . . x

     .
    . .
   . . .
  . . . x
 . . . . x

     .
    . .
   . . x
  . . . .
 . . . . .



 ***** 2 ***** 

     x
    x .
   x x x
  x x x x
 x x x x x

     x
    x x
   x x .
  x x x .
 x x x x x

     .
    x .
   x x x
  x x x .
 x x x x x

     .
    x x
   x . x
  x . x .
 x x x x x

     .
    x .
   x . .
  x . x x
 x x x x x

     .
    x .
   x x .
  x . . x
 x x x . x

     .
    . .
   x . .
  x . x x
 x x x . x

     .
    x .
   . . .
  . . x x
 x x x . x

     .
    x .
   . . .
  . x . .
 x x x . x

     .
    x .
   . . .
  . x . .
 x . . x x

     .
    x .
   . . .
  . x . .
 x . x . .

     .
    x .
   x . .
  . . . .
 x . . . .

     .
    . .
   . . .
  x . . .
 x . . . .

     .
    . .
   x . .
  . . . .
 . . . . .



 ***** 3 ***** 

     x
    x x
   . x x
  x x x x
 x x x x x

     .
    . x
   x x x
  x x x x
 x x x x x

     x
    . .
   x x .
  x x x x
 x x x x x

     x
    . .
   . . x
  x x x x
 x x x x x

     x
    . x
   . . .
  x x x .
 x x x x x

     .
    . .
   . . x
  x x x .
 x x x x x

     .
    . .
   x . x
  x . x .
 x x . x x

     .
    x .
   . . x
  . . x .
 x x . x x

     .
    x .
   . . x
  . . x .
 x x x . .

     .
    x .
   . . x
  . . x .
 x . . x .

     .
    x .
   . x x
  . . . .
 x . . . .

     .
    x .
   x . .
  . . . .
 x . . . .

     .
    . .
   . . .
  x . . .
 x . . . .

     .
    . .
   x . .
  . . . .
 . . . . .



 ***** 4 ***** 

     x
    x x
   x . x
  x x x x
 x x x x x

     x
    x x
   x x x
  x . x x
 x . x x x

     x
    x x
   x x x
  x x . .
 x . x x x

     x
    . x
   x . x
  x x x .
 x . x x x

     x
    . .
   x . .
  x x x x
 x . x x x

     x
    x .
   . . .
  . x x x
 x . x x x

     .
    . .
   x . .
  . x x x
 x . x x x

     .
    . .
   x . .
  . x x x
 x x . . x

     .
    . .
   . . .
  . . x x
 x x x . x

     .
    . .
   . . .
  . . x x
 x . . x x

     .
    . .
   . . x
  . . x .
 x . . x .

     .
    . .
   . . .
  . . . .
 x . x x .

     .
    . .
   . . .
  . . . .
 x x . . .

     .
    . .
   . . .
  . . . .
 . . x . .



 ***** 5 ***** 

     x
    x x
   x x .
  x x x x
 x x x x x

     .
    x .
   x x x
  x x x x
 x x x x x

     x
    . .
   . x x
  x x x x
 x x x x x

     x
    . .
   x . .
  x x x x
 x x x x x

     x
    x .
   . . .
  . x x x
 x x x x x

     .
    . .
   x . .
  . x x x
 x x x x x

     .
    . .
   x x .
  . . x x
 x . x x x

     .
    . .
   . . x
  . . x x
 x . x x x

     .
    . x
   . . .
  . . x .
 x . x x x

     .
    . x
   . . .
  . . x .
 x x . . x

     .
    . x
   . . .
  . . x .
 . . x . x

     .
    . x
   . . x
  . . . .
 . . . . x

     .
    . .
   . . .
  . . . x
 . . . . x

     .
    . .
   . . x
  . . . .
 . . . . .



 ***** 6 ***** 

     x
    x x
   x x x
  . x x x
 x x x x x

     x
    . x
   . x x
  x x x x
 x x x x x

     x
    . x
   x . .
  x x x x
 x x x x x

     .
    . .
   x . x
  x x x x
 x x x x x

     .
    x .
   . . x
  . x x x
 x x x x x

     .
    x x
   . . .
  . x x .
 x x x x x

     .
    x x
   . x .
  . . x .
 x . x x x

     .
    x x
   . x x
  . . . .
 x . . x x

     .
    . x
   . . x
  . . x .
 x . . x x

     .
    . .
   . . .
  . . x x
 x . . x x

     .
    . .
   . . x
  . . x .
 x . . x .

     .
    . .
   . . .
  . . . .
 x . x x .

     .
    . .
   . . .
  . . . .
 x x . . .

     .
    . .
   . . .
  . . . .
 . . x . .



 ***** 7 ***** 

     x
    x x
   x x x
  x . x x
 x x x x x

     x
    x .
   x . x
  x x x x
 x x x x x

     x
    x x
   x . .
  x x x .
 x x x x x

     x
    x x
   x . x
  x x . .
 x x . x x

     x
    x .
   x . .
  x x . x
 x x . x x

     x
    x .
   x . .
  . . x x
 x x . x x

     x
    . .
   . . .
  x . x x
 x x . x x

     x
    . .
   x . .
  . . x x
 . x . x x

     x
    . .
   x . .
  . . x x
 . x x . .

     x
    . .
   x . .
  . . x x
 . . . x .

     x
    . .
   x x .
  . . . x
 . . . . .

     x
    . .
   . . x
  . . . x
 . . . . .

     x
    . x
   . . .
  . . . .
 . . . . .

     .
    . .
   . . x
  . . . .
 . . . . .



 ***** 8 ***** 

     x
    x x
   x x x
  x x . x
 x x x x x

     x
    . x
   x . x
  x x x x
 x x x x x

     x
    x x
   . . x
  . x x x
 x x x x x

     x
    x x
   . x x
  . . x x
 x . x x x

     x
    x .
   . . x
  . x x x
 x . x x x

     x
    x x
   . . .
  . x x .
 x . x x x

     .
    x .
   . . x
  . x x .
 x . x x x

     .
    x .
   . . x
  . x x .
 x x . . x

     .
    x .
   . . .
  . x . .
 x x x . x

     .
    x .
   . . .
  . x . .
 x . . x x

     .
    x .
   . . .
  . x . .
 x . x . .

     .
    x .
   x . .
  . . . .
 x . . . .

     .
    . .
   . . .
  x . . .
 x . . . .

     .
    . .
   x . .
  . . . .
 . . . . .



 ***** 9 ***** 

     x
    x x
   x x x
  x x x .
 x x x x x

     x
    x .
   x x .
  x x x x
 x x x x x

     x
    x .
   . . x
  x x x x
 x x x x x

     .
    . .
   x . x
  x x x x
 x x x x x

     .
    x .
   . . x
  . x x x
 x x x x x

     .
    x x
   . . .
  . x x .
 x x x x x

     .
    x x
   . x .
  . . x .
 x . x x x

     .
    x x
   . x x
  . . . .
 x . . x x

     .
    . x
   . . x
  . . x .
 x . . x x

     .
    . .
   . . .
  . . x x
 x . . x x

     .
    . .
   . . x
  . . x .
 x . . x .

     .
    . .
   . . .
  . . . .
 x . x x .

     .
    . .
   . . .
  . . . .
 x x . . .

     .
    . .
   . . .
  . . . .
 . . x . .



 ***** 10 ***** 

     x
    x x
   x x x
  x x x x
 . x x x x

     x
    x x
   . x x
  . x x x
 x x x x x

     .
    . x
   x x x
  . x x x
 x x x x x

     .
    x x
   x . x
  . x . x
 x x x x x

     .
    . x
   . . x
  x x . x
 x x x x x

     .
    . x
   x . x
  . x . x
 . x x x x

     .
    . x
   x x x
  . . . x
 . . x x x

     .
    . .
   x . x
  . x . x
 . . x x x

     .
    . x
   x . .
  . x . .
 . . x x x

     .
    . x
   x . .
  . x . .
 . x . . x

     .
    . x
   x x .
  . . . .
 . . . . x

     .
    . x
   . . x
  . . . .
 . . . . x

     .
    . .
   . . .
  . . . x
 . . . . x

     .
    . .
   . . x
  . . . .
 . . . . .



 ***** 11 ***** 

     x
    x x
   x x x
  x x x x
 x . x x x

     x
    x x
   x x x
  x x x x
 x x . . x

     x
    x x
   . x x
  x . x x
 x x x . x

     .
    . x
   x x x
  x . x x
 x x x . x

     .
    . .
   x . x
  x x x x
 x x x . x

     .
    x .
   . . x
  . x x x
 x x x . x

     .
    x x
   . . .
  . x x .
 x x x . x

     .
    x x
   . x .
  . . x .
 x . x . x

     .
    x x
   . x x
  . . . .
 x . . . x

     .
    x .
   . x .
  . . . x
 x . . . x

     .
    x .
   . x x
  . . . .
 x . . . .

     .
    x .
   x . .
  . . . .
 x . . . .

     .
    . .
   . . .
  x . . .
 x . . . .

     .
    . .
   x . .
  . . . .
 . . . . .



 ***** 12 ***** 

     x
    x x
   x x x
  x x x x
 x x . x x

     x
    x x
   . x x
  x . x x
 x x x x x

     .
    . x
   x x x
  x . x x
 x x x x x

     .
    . .
   x . x
  x x x x
 x x x x x

     .
    x .
   . . x
  . x x x
 x x x x x

     .
    x x
   . . .
  . x x .
 x x x x x

     .
    x x
   . x .
  . . x .
 x . x x x

     .
    x x
   . x x
  . . . .
 x . . x x

     .
    . x
   . . x
  . . x .
 x . . x x

     .
    . .
   . . .
  . . x x
 x . . x x

     .
    . .
   . . x
  . . x .
 x . . x .

     .
    . .
   . . .
  . . . .
 x . x x .

     .
    . .
   . . .
  . . . .
 x x . . .

     .
    . .
   . . .
  . . . .
 . . x . .



 ***** 13 ***** 

     x
    x x
   x x x
  x x x x
 x x x . x

     x
    x x
   x x x
  x x x x
 x . . x x

     x
    x x
   . x x
  x . x x
 x . x x x

     .
    . x
   x x x
  x . x x
 x . x x x

     .
    . .
   x . x
  x x x x
 x . x x x

     .
    x .
   . . x
  . x x x
 x . x x x

     .
    x x
   . . .
  . x x .
 x . x x x

     .
    x x
   x . .
  . . x .
 x . . x x

     .
    . x
   . . .
  x . x .
 x . . x x

     .
    . x
   x . .
  . . x .
 . . . x x

     .
    . x
   x x .
  . . . .
 . . . . x

     .
    . x
   . . x
  . . . .
 . . . . x

     .
    . .
   . . .
  . . . x
 . . . . x

     .
    . .
   . . x
  . . . .
 . . . . .



 ***** 14 ***** 

     x
    x x
   x x x
  x x x x
 x x x x .

     x
    x x
   x x .
  x x x .
 x x x x x

     .
    x .
   x x x
  x x x .
 x x x x x

     .
    x x
   x . x
  x . x .
 x x x x x

     .
    x .
   x . .
  x . x x
 x x x x x

     .
    x .
   x x .
  x . . x
 x x x . x

     .
    . .
   x . .
  x . x x
 x x x . x

     .
    x .
   . . .
  . . x x
 x x x . x

     .
    x .
   . . .
  . x . .
 x x x . x

     .
    x .
   . . .
  . x . .
 x . . x x

     .
    x .
   . . .
  . x . .
 x . x . .

     .
    x .
   x . .
  . . . .
 x . . . .

     .
    . .
   . . .
  x . . .
 x . . . .

     .
    . .
   x . .
  . . . .
 . . . . .


*/
