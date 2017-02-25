package p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class State {
	
	private int height;
	private int width;
	private List<Pair<Integer, Integer>> whitePawns;
	private List<Pair<Integer, Integer>> blackPawns;
	private Pair<Integer, Integer> coord;
	public boolean white;  	//white = true, black = false
	

	public boolean isWhite() {
		return white;
	}
	
	public State(State barry)
	{
		this.height = barry.height;
		this.width = barry.width;
		coord = new Pair<Integer, Integer>(-1, -1);
		whitePawns = new ArrayList<Pair<Integer, Integer>>();
		blackPawns = new ArrayList<Pair<Integer, Integer>>();
		for(Pair<Integer, Integer> white : barry.whitePawns)
		{
			//System.out.print("white: " + white.getLeft() + " " +  white.getRight());
			whitePawns.add(new Pair<Integer, Integer>(white.getLeft(), white.getRight()));
		}
		for(Pair<Integer, Integer> black : barry.blackPawns)
		{
			blackPawns.add(new Pair<Integer, Integer>(black.getLeft(), black.getRight()));
		}
		white = barry.white;
	}
	
	State(int height, int width)
	{
		this.height = height;
		this.width = width;
		coord = new Pair<Integer, Integer>(0, 0);
		whitePawns = new ArrayList<Pair<Integer, Integer>>();
		blackPawns = new ArrayList<Pair<Integer, Integer>>();
		white = true;
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

	public ArrayList<Integer>legalActions(String role)
	{
		ArrayList<Integer> legal = new ArrayList<Integer>();
		
		if(role.equals("white"))
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
		else if(role.equals("black"))
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

	public void updateState(Pair<Integer, Integer> from,Pair<Integer, Integer> to, String role)
	{	
		//System.out.println("UpdateState white is: " + white);
		if (role.equals("white")) 
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
		else if(role.equals("black"))
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
		if(blackPawns.size() == 0 || whitePawns.size() == 0 || goalTest())
		{
			return true;
		}
		
		return false;
	}
	
	public boolean goalTest() 
	{
		if(white)
		{			
			for(int i = 0; i < whitePawns.size(); i++)
			{
				if(whitePawns.get(i).getRight().equals(height))
				{
					return true;
				}
			}
		}
		else
		{
			for(int i = 0; i < blackPawns.size(); i++)
			{
				if(blackPawns.get(i).getRight().equals(1))
				{
					return true;
				}
			}
		}	
		return false;
	}
	
	public Integer eval()
	{
		return 50 - (height - whitePawns.get(0).getRight()) + blackPawns.get(0).getRight() - 1;	
	}
	
	public Integer utility()
	{
		if(goalTest())
		{
			return 100;
		}
		else if(this.legalActions("white").size() == 0 || this.legalActions("black").size() == 0)
		{
			return 50;
		}
		
		return 0;
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

}
