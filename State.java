package p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class State {
	
	private int height;
	private int width;
	private ArrayList<Pair<Integer, Integer>> whitePawns;
	private ArrayList<Pair<Integer, Integer>> blackPawns;
	private Pair<Integer, Integer> coord;
	private boolean white;  	//white = true, black = false
	

	public boolean isWhite() {
		return white;
	}
	
	public State(State barry)
	{
		this.height = barry.height;
		this.width = barry.width;
		coord = barry.coord;
		whitePawns = barry.whitePawns;
		blackPawns = barry.blackPawns;
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

	public ArrayList<Integer>legalActions()
	{
		ArrayList<Integer> legal = new ArrayList<Integer>();
		if(blackPawns.size() == 0 || whitePawns.size() == 0)
		{
			return legal;
		}
		
		if(white)
		{
			for(int i = 0; i < whitePawns.size(); i++)
			{
				//todo: send all possible actions for white player
				int x1 = (int) whitePawns.get(i).getLeft();
				int y1 = (int) whitePawns.get(i).getRight();
				coord.change(x1, y1+1);
				if(y1+1 <= height && !blackPawns.contains(coord) && !whitePawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1);
					legal.add(y1 + 1);					
				}
				coord.change(x1 - 1, y1+1);
				if(y1+1 <= height && (x1 - 1) > 0 && blackPawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1 - 1);
					legal.add(y1 + 1);
					
				}
				coord.change(x1 + 1, y1 + 1);
				if(y1+1 <= height && x1 + 1 <= width && blackPawns.contains(coord))
				{					
					legal.add(x1);
					legal.add(y1);
					legal.add(x1 + 1);
					legal.add(y1 + 1);
				}				
			}
		}
		else
		{
			for(int i = 0; i < whitePawns.size(); i++)
			{
				//todo: send all possible actions for black player
				int x1 = (int) blackPawns.get(i).getLeft();
				int y1 = (int) blackPawns.get(i).getRight();
				coord.change(x1, y1 - 1);
				if(y1 - 1 > 0 && !blackPawns.contains(coord) && !whitePawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1);
					legal.add(y1 - 1);
					
				}
				coord.change(x1 - 1, y1 - 1);
				if(y1 - 1 > 0 && (x1 - 1) > 0 && whitePawns.contains(coord))
				{
					legal.add(x1);
					legal.add(y1);
					legal.add(x1 - 1);
					legal.add(y1 - 1);
				
				}
				coord.change(x1 + 1, y1 - 1);
				if(y1 - 1 > 0 && x1 + 1 <= width && whitePawns.contains(coord))
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
		System.out.println("UpdateState white is: " + white);
		if (white) 
		{
			int temp = whitePawns.indexOf(from);
			if(temp >= 0)
			{
				whitePawns.remove(temp);
				whitePawns.add(to);
				if(blackPawns.contains(to))
				{
					System.out.println("removed BLACK Pawn at: " + blackPawns.indexOf(to) + " " + blackPawns.get(blackPawns.indexOf(to)).getLeft() + " " + blackPawns.get(blackPawns.indexOf(to)).getRight());
					blackPawns.remove(blackPawns.indexOf(to));
				}
			}
			
		} 
		else 
		{
			int temp = blackPawns.indexOf(from);
			if(temp >= 0)
			{
				blackPawns.remove(temp);
				blackPawns.add(to);
				if(whitePawns.contains(to))
				{
					System.out.println("removed White Pawn at: " + whitePawns.indexOf(to) + " " + whitePawns.get(whitePawns.indexOf(to)).getLeft() + " " + whitePawns.get(whitePawns.indexOf(to)).getRight());
					whitePawns.remove(whitePawns.indexOf(to));
				}
			}
		}
		white = !white;
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
	
	public Integer utility()
	{
		if(goalTest())
		{
			return 100;
		}
		else if(this.legalActions().size() == 0)
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
	public ArrayList<Pair<Integer, Integer>> getWhitePawns() {
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
	public ArrayList<Pair<Integer, Integer>> getBlackPawns() {
		return blackPawns;
	}

	/**
	 * @param blackPawns the blackPawns to set
	 */
	public void setBlackPawns(ArrayList<Pair<Integer, Integer>> blackPawns) {
		this.blackPawns = blackPawns;
	}


}
