package p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class State {
	
	private static int height;
	public int width;
	private List<Pair<Integer, Integer>> whitePawns;
	private List<Pair<Integer, Integer>> blackPawns;
	private Pair<Integer, Integer> coord;
	public boolean whiteTurn;  	//white = true, black = false
	public String max;

	public boolean isWhite() {
		return whiteTurn;
	}
	
	public State(State barry)
	{
		this.width = barry.width;
		coord = new Pair<Integer, Integer>(-1, -1);
		this.whitePawns = new ArrayList<Pair<Integer, Integer>>();
		this.blackPawns = new ArrayList<Pair<Integer, Integer>>();
		this.max = new String(barry.max);
		for(Pair<Integer, Integer> white : barry.whitePawns)
		{
			//System.out.print("white: " + white.getLeft() + " " +  white.getRight());
			whitePawns.add(new Pair<Integer, Integer>(white.getLeft(), white.getRight()));
		}
		for(Pair<Integer, Integer> black : barry.blackPawns)
		{
			blackPawns.add(new Pair<Integer, Integer>(black.getLeft(), black.getRight()));
		}
		whiteTurn = barry.whiteTurn;
	}
	
	State(int height, int width, String role)
	{
		State.height = height;
		this.width = width;
		this.max = new String(role);
		coord = new Pair<Integer, Integer>(0, 0);
		whitePawns = new ArrayList<Pair<Integer, Integer>>();
		blackPawns = new ArrayList<Pair<Integer, Integer>>();
		whiteTurn = true;
		System.out.println("Initial!!");
		setInitialBoard();
		printBoard();
		printPawns();
	}

	public void setInitialBoard()
	{
		for(int y = 1; y <= height; y++)
		{
			for(int x = 1; x <= width; x++)
			{
				if(y <= 2)
				{
					whitePawns.add(new Pair<Integer, Integer>(x, y));
				}
				else if(y > (height - 2))
				{
					blackPawns.add(new Pair<Integer, Integer>(x, y));
				}
			}
		}
	}
	
	public int getBlackPawnsLeft() {
		return blackPawns.size();
	}

	public int getWhitePawnsLeft() {
		return whitePawns.size();
	}

	public ArrayList<Integer>legalActions()
	{
		ArrayList<Integer> legal = new ArrayList<Integer>();
		
		if(whiteTurn)
		{	
			for(int i = 0; i < whitePawns.size(); i++)
			{
				//todo: send all possible actions for white player
				int x1 = (int) whitePawns.get(i).getLeft();
				int y1 = (int) whitePawns.get(i).getRight();
				coord.change(x1, y1+1);
				if(inBounds(x1, y1 + 1) && !blackPawns.contains(coord) && !whitePawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1);
					legal.add(y1 + 1);					
				}
				coord.change(x1 - 1, y1+1);
				if(inBounds(x1 - 1, y1 + 1)  && blackPawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1 - 1);
					legal.add(y1 + 1);
					
				}
				coord.change(x1 + 1, y1 + 1);
				if(inBounds(x1 + 1, y1 + 1) && blackPawns.contains(coord))
				{					
					legal.add(x1);
					legal.add(y1);
					legal.add(x1 + 1);
					legal.add(y1 + 1);
				}				
			}
		}
		else if(!whiteTurn)
		{
			for(int i = 0; i < blackPawns.size(); i++)
			{
				//todo: send all possible actions for black player
				int x1 = (int) blackPawns.get(i).getLeft();
				int y1 = (int) blackPawns.get(i).getRight();
				coord.change(x1, y1 - 1);
				if(inBounds(x1, y1 - 1) && !blackPawns.contains(coord) && !whitePawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1);
					legal.add(y1 - 1);
					
				}
				coord.change(x1 - 1, y1 - 1);
				if(inBounds(x1 -1, y1 - 1) && whitePawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1 - 1);
					legal.add(y1 - 1);
				
				}
				coord.change(x1 + 1, y1 - 1);
				if(inBounds(x1 + 1, y1 - 1) && whitePawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1 + 1);
					legal.add(y1 - 1);
				}				
			}
		}
		
		return legal;
	}

	public void updateState(Pair<Integer, Integer> from,Pair<Integer, Integer> to)
	{	
		//System.out.println("UpdateState white is: " + white);
		if (whiteTurn) 
		{
			int temp = whitePawns.indexOf(from);
			if(temp >= 0)
			{
				whitePawns.remove(temp);
				whitePawns.add(to);
				if(blackPawns.contains(to))
				{
					//System.out.println("removed BLACK Pawn at: " + blackPawns.indexOf(to) + " " + blackPawns.get(blackPawns.indexOf(to)).getLeft() + " " + blackPawns.get(blackPawns.indexOf(to)).getRight());
					blackPawns.remove(blackPawns.indexOf(to));
				}
			}	
		} 
		else if(!whiteTurn)
		{
			int temp = blackPawns.indexOf(from);
			if(temp >= 0)
			{
				blackPawns.remove(temp);
				blackPawns.add(to);
				if(whitePawns.contains(to))
				{
					//System.out.println("removed White Pawn at: " + whitePawns.indexOf(to) + " " + whitePawns.get(whitePawns.indexOf(to)).getLeft() + " " + whitePawns.get(whitePawns.indexOf(to)).getRight());
					whitePawns.remove(whitePawns.indexOf(to));
				}
			}
		}
		whiteTurn = !whiteTurn;
	}
	/*
	public State result(Pair<Integer, Integer> from, Pair<Integer, Integer>to)
	{
		State nextState = new State(state);
		nextState.updateState(from, to);
		return nextState;
	}
	*/
	public boolean terminalTest()
	{
		if(this.legalActions().isEmpty() || goalTest())
		{
			return true;
		}
		
		return false;
	}
	
	public boolean goalTest() 
	{
		/* Check if the player who did in previous turn won */
		if(whiteTurn)
		{
			for(int i = 0; i < blackPawns.size(); i++)
			{
				if(blackPawns.get(i).getRight().equals(1))
				{
					return true;
				}
			}
		}
		else if(!whiteTurn)
		{
			for(int i = 0; i < whitePawns.size(); i++)
			{
				if(whitePawns.get(i).getRight().equals(height))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int eval2()
	{
		if(goalTest())
		{
			return utility();
		}
		
		int Wmax = 0;
		int Bmin = height;

		if(!whitePawns.isEmpty())
		{
			Wmax = whitePawns.get(0).getRight();
		}
 
		if(!blackPawns.isEmpty())
		{
			Bmin = blackPawns.get(0).getRight();
		}
		
		if(isWhite())
		{
			if(max.equals("white"))
			{
				return (50 - (height - Wmax) - (Bmin - 1) - whitePawns.size() + blackPawns.size());

			}
			else if(max.equals("black"))
			{
				return -(50 - (height - Wmax) + (Bmin - 1) + whitePawns.size() - blackPawns.size());
			}
		}
		else
		{
			if(max.equals("black"))
			{
				return (50 + (height - Wmax) - (Bmin - 1) - whitePawns.size() + blackPawns.size());
			}
			else if(max.equals("white"))
			{

				return -(50 + (height - Wmax) + (Bmin - 1) + whitePawns.size() - blackPawns.size());

			}
		}
		
		
		System.out.println("framhjá öllu");
		return 0;
		
		
	}
	public int eval() 
	{       
		if(goalTest())
		{
			return utility();
		}
		
		int Wmax = 0;
		int Bmin = 0;
		int blackDefended = 0;
		int blackUnDefended = 0;
		int whiteDefended = 0;
		int whiteUnDefended = 0;
		int whiteAttackable= 0;
		int blackAttackable = 0;
		int whiteExceeded = 0;
		int blackExceeded = 0;
		
		if(!whitePawns.isEmpty())
		{
			Wmax = whitePawns.get(0).getRight();
			
		}
		if(!blackPawns.isEmpty())
		{
			Bmin = blackPawns.get(0).getRight();
		}
		
		for(Pair<Integer, Integer> x : blackPawns)
		{
			if(x.getRight() < Wmax)
			{
				blackExceeded++;
			}
		}
		for(Pair<Integer, Integer> x : whitePawns)
		{
			if(x.getRight() > Bmin)
			{
				whiteExceeded++;
			}
		}
		
		for(int i = 0; i < blackPawns.size(); i++)
		{
			coord.change(blackPawns.get(i).getLeft() - 1, blackPawns.get(i).getRight() - 1);
			if(whitePawns.contains(coord))
			{
				blackAttackable++;
			}
			coord.change(blackPawns.get(i).getLeft() + 1, blackPawns.get(i).getRight() - 1);
			if(whitePawns.contains(coord))
			{
				blackAttackable++;
			}
			
			for(int j = 0; j < blackPawns.size(); j++)
			{
				if(i == j)
				{
					continue;
				}
				if(blackPawns.get(i).getLeft().equals(blackPawns.get(j).getLeft() - 1) && blackPawns.get(i).getRight().equals(blackPawns.get(j).getRight() + 1) )
				{
					blackDefended++;
				}
				else
				{
					blackUnDefended++;
				}
				
				if(blackPawns.get(i).getLeft().equals(blackPawns.get(j).getLeft() + 1) && blackPawns.get(i).getRight().equals(blackPawns.get(j).getRight() + 1) )
				{
					blackDefended++;
				}
				else
				{
					blackUnDefended++;
				}
			}	
		}
		
		for(int i = 0; i < whitePawns.size(); i++)
		{
			coord.change(whitePawns.get(i).getLeft() - 1, whitePawns.get(i).getRight() + 1);
			if(blackPawns.contains(coord))
			{
				whiteAttackable++;
			}
			coord.change(whitePawns.get(i).getLeft() + 1, whitePawns.get(i).getRight() + 1);
			if(blackPawns.contains(coord))
			{
				whiteAttackable++;
			}
			for(int j = 0; j < whitePawns.size(); j++)
			{
				if(i == j)
				{
					continue;
				}
				if(whitePawns.get(i).getLeft().equals(whitePawns.get(j).getLeft() - 1) && whitePawns.get(i).getRight().equals(whitePawns.get(j).getRight() - 1) )
				{
					whiteDefended++;
				}
				else
				{
					whiteUnDefended++;
				}
				
				if(whitePawns.get(i).getLeft().equals(whitePawns.get(j).getLeft() + 1) && whitePawns.get(i).getRight().equals(whitePawns.get(j).getRight() - 1) )
				{
					whiteDefended++;
				}
				else
				{
					whiteUnDefended++;
				}
			}
		}	
		if(!isWhite())
		{
			ArrayList<Integer> legal = this.legalActions();
			for(int i = 0; i < legal.size(); i +=4)
			{
				if(legal.get(i+3).equals(1))
				{
					
					if(max.equals("white"))
					{
						//System.out.println("eval() white turn: " + !isWhite() + "maxis" + player);
						return 0;
					}
					else
					{
						return 100;
					}
				}
			}
			if(max.equals("white"))
			{
				
				//System.out.println("isblack and max equals white: Wmax = " + Wmax + " Bmin = " + Bmin + " defended: " + defended + " killers: " + killers + "white ex: " + whiteExceeded + " black ex: " + blackExceeded);
				return (50 + Wmax - (height - Bmin + 1) + whitePawns.size() - blackPawns.size() + whiteDefended - blackDefended + blackUnDefended - whiteUnDefended -whiteAttackable + whiteExceeded - blackExceeded);
			}
			else 
			{
				
				//System.out.println("isBlack and max equals black: Wmax = " + Wmax + " Bmin = " + Bmin + " defended: " + defended + " killers: " + killers + "white ex: " + whiteExceeded + " black ex: " + blackExceeded);
				return (50 - Wmax + (height - Bmin + 1) - whitePawns.size() + blackPawns.size() - whiteDefended + blackDefended - blackUnDefended + whiteUnDefended + whiteAttackable - whiteExceeded + blackExceeded);
			}
			
		}
		else if(isWhite())
		{
			ArrayList<Integer> legal = this.legalActions();
			for(int i = 0; i < legal.size(); i +=4)
			{
				if(legal.get(i+3).equals(height))
				{
					if(max.equals("black"))
					{
						//System.out.println("eval() white turn: " + !isWhite() + "maxis" + player);
						return 0;
					}
					else
					{
						return 100;
					}
				}
			}
			if(max.equals("white"))
			{
				//System.out.println("iswhite and max equals white: Wmax = " + Wmax + " Bmin = " + Bmin + " defended: " + defended + " killers: " + killers + "white ex: " + whiteExceeded + " black ex: " + blackExceeded);
				return (50 + Wmax - (height - Bmin + 1 ) + whitePawns.size() - blackPawns.size() + whiteDefended - whiteUnDefended - blackDefended + blackUnDefended + blackAttackable + whiteExceeded - blackExceeded );
			}
			else 
			{
				
				//System.out.println("iswhite and max equals black: Wmax = " + Wmax + " Bmin = " + Bmin + " defended: " + defended + " killers: " + killers + "white ex: " + whiteExceeded + " black ex: " + blackExceeded);
				return (50 - Wmax + (height - Bmin + 1) - whitePawns.size() + blackPawns.size() -whiteDefended + whiteUnDefended + blackDefended - blackUnDefended - blackAttackable - whiteExceeded + blackExceeded);
			}
			//return (50 + (height - Wmax) - (Bmin - 1) - whitePawns.size() + blackPawns.size());
		}
		System.out.println("Should not be here: from eval()");
		return -100;
	}
	
	public Integer utility()
	{
		if(max.equals("white") && !isWhite())
		{
			return 100;
		}
		else if(max.equals("black") && isWhite())
		{
			return 100;
		}
		else if(max.equals("white") && isWhite())
		{
			return 0;
		}
		else if(max.equals("black") && !isWhite())
		{
			return 0;
		}
		else
		{
			return 50;
		}
	}

	public void printPawns()
	{
		System.out.println("black pawns:");
		for(int i = 0; i < blackPawns.size(); i++)
		{
			System.out.print(" left: " + blackPawns.get(i).getLeft());
			System.out.print(" ");
			System.out.print(" right: " + blackPawns.get(i).getRight());
		}
		System.out.println("");
		System.out.println("white pawns:");
		for(int i = 0 ; i < whitePawns.size(); i++)
		{
			System.out.print(" left: " + whitePawns.get(i).getLeft());
			System.out.print(" ");
			System.out.print(" right: " + whitePawns.get(i).getRight());
		}
		System.out.println("");
	}
	
	public void printBoard()
	{
		for(int i = height; i > 0; i--)
		{
			for(int j = 1; j <= width; j++)
			{
				coord.change(j, i);
				if(blackPawns.contains(coord))
				{
					System.out.print("BLACK | ");
				}
				else if(whitePawns.contains(coord))
				{
					System.out.print("WHITE | ");
				}
				else
				{
					System.out.print("      | ");
				}
			}
			System.out.println("");
			System.out.println("--------------------");
		}
	}

	
	/**
	 * @return the whitePawns
	 */
	public List<Pair<Integer, Integer>> getWhitePawns() {
		return whitePawns;
	}

	/**
	 * @param whitePawns the whitePawns to set
	 */
	public void setWhitePawns(ArrayList<Pair<Integer, Integer>> whitePawns) {
		this.whitePawns = whitePawns;
	}

	/**
	 * @return the blackPawns
	 */
	public List<Pair<Integer, Integer>> getBlackPawns() {
		return blackPawns;
	}

	/**
	 * @param blackPawns the blackPawns to set
	 */
	public void setBlackPawns(ArrayList<Pair<Integer, Integer>> blackPawns) {
		this.blackPawns = blackPawns;
	}

	private boolean inBounds(int x, int y)
	{
		return (x > 0 && x <= width) && (y > 0 && y <= height);
	}

	public void sort() {
		Collections.sort(whitePawns, Pair.decendingComparator);
		Collections.sort(blackPawns, Pair.ascendingComparator);
	}
	
	public static int getHeight()
	{
		return height;
	}

}