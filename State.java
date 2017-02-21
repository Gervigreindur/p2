package p2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class State {
	
	private int height;
	private int width;
	private List<ArrayList<Square>> board;
	private Map<Integer, Pair> whitePawns;
	private Map<Integer, Pair> blackPawns;
	
	private enum Square
	{
		white, black, empty
	}
	
	State(int height, int width)
	{
		this.height = height;
		this.width = width;
		board = new ArrayList<ArrayList<Square>>();
		for(int i = 0; i < height; i++)
		{
			board.add(new ArrayList<Square>());
		}
		whitePawns = new HashMap<Integer, Pair>();
		blackPawns = new HashMap<Integer, Pair>();
		setInitialBoard();
	}
	
	public void setInitialBoard()
	{
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				if(y < 2)
				{
					board.get(y).add(Square.white);
					whitePawns.put(whitePawns.size() + 1, new Pair(y, x));
				}
				else if(y > (height - 3))
				{
					board.get(y).add(Square.black);
					blackPawns.put(blackPawns.size() + 1, new Pair(y, x));
				}
				else
				{
					board.get(y).add(Square.empty);
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

	public ArrayList<Integer>legalActions(String role) throws Exception
	{
		ArrayList<Integer> legal = new ArrayList<Integer>();
		if(role.equals("white"))
		{
			Set<Integer> set = whitePawns.keySet();
			for(Integer x : set)
			{
				//todo: send all possible actions for white player
				int x1 = (int) whitePawns.get(x).getLeft();
				int y1 = (int) whitePawns.get(x).getRight();
				if(!(board.get(y1 + 1).get(x1).equals(Square.white)) && ((y1+ 1) > height))
				{
					legal.add(y1 + 1);
					legal.add(x1 + 1);
					legal.add(y1 + 2);
					legal.add(x1 + 1);
				}
				if(!(board.get(y1 + 1).get(x1 - 1).equals(Square.white)) && ((y1+1) > height && (x1 - 1) >= 0))
				{
					legal.add(y1 + 1);
					legal.add(x1 + 1);
					legal.add(y1 + 2);
					legal.add(x1 - 2);
				}
				if(!(board.get(y1 + 1).get(x1 + 1).equals(Square.white)) && ((y1+1) > height && (x1 + 1) >= width))
				{
					legal.add(y1 + 1);
					legal.add(x1 + 1);
					legal.add(y1 + 2);
					legal.add(x1 + 2);
				}
							
			}
		}
		else if(role.equals("black"))
		{
			Set<Integer> set = blackPawns.keySet();
			for(Integer x : set)
			{
				//todo: send all possible actions for white player
				int x1 = (int) blackPawns.get(x).getLeft();
				int y1 = (int) blackPawns.get(x).getRight();
				if(!(board.get(y1 - 1).get(x1).equals(Square.black)) && ((y1 - 1) > 0))
				{
					legal.add(y1 - 1);
					legal.add(x1 + 1);
					legal.add(y1 + 2);
					legal.add(x1 + 1);
				}
				if(!(board.get(y1 + 1).get(x1 - 1).equals(Square.black)) && ((y1+1) > height && (x1 - 1) >= 0))
				{
					legal.add(y1 + 1);
					legal.add(x1 + 1);
					legal.add(y1 + 2);
					legal.add(x1 - 2);
				}
				if(!(board.get(y1 + 1).get(x1 + 1).equals(Square.black)) && ((y1+1) > height && (x1 + 1) >= width))
				{
					legal.add(y1 + 1);
					legal.add(x1 + 1);
					legal.add(y1 + 2);
					legal.add(x1 + 2);
				}			
			}
		}
		else
		{
			throw new Exception("role not accepted in legal Actions function!");
		}
		return new ArrayList<Integer>();
	}

	public void updateState(int x1, int y1, int x2, int y2, String role)
	{
		x1 -= 1;
		x2 -= 1;
		y1 -= 1;
		y2 -= 1;

		if(board.get(y1).get(x1).equals(Square.valueOf(role)))
		{
			
			//Set old square as empty
			board.get(y1).set(x1, Square.empty);
			
			//set new position for player move
			if(!board.get(y2).get(x2).equals(Square.empty))
			{
				if(Square.valueOf(role).equals(Square.white))
				{
					
				}
				else
				{
					
				}
			}
			board.get(y2).set(x2, Square.valueOf(role));
			
		}
	}
	
	public boolean legalAction(Integer from, Integer to, String role)
	{
		if(board.get(from).equals(Square.valueOf(role)))
		{
			return true;
		}
			
		return false;
	}
	
	
	public Square getSquare(int x, int y)
	{
		return board.get(y - 1).get(x - 1);
	}
	
	public void printBoard()
	{
        for (int i = 0; i < board.size(); i++) 
        {
        	for(int j = 0; j < board.get(i).size(); j++)
        	{
        		System.out.println("square y,x: " + (i+1) + ", " + (j+1) + " " + board.get(i).get(j));
        	}
        }
	}
}
