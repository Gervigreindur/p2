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
	private Map<Integer, Pair<Integer, Integer>> whitePawns;
	private Map<Integer, Pair<Integer, Integer>> blackPawns;
	
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
		whitePawns = new HashMap<Integer, Pair<Integer, Integer>>();
		blackPawns = new HashMap<Integer, Pair<Integer, Integer>>();
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
					whitePawns.put(whitePawns.size() , new Pair<Integer, Integer>(y, x));
				}
				else if(y > (height - 3))
				{
					board.get(y).add(Square.black);
					blackPawns.put(blackPawns.size(), new Pair<Integer, Integer>(y, x));
				}
				else
				{
					board.get(y).add(Square.empty);
				}
			}
		}
		System.out.println("black size: " + blackPawns.size() );
		System.out.println("white size: " + whitePawns.size() );
	}
	
	public int getBlackPawnsLeft() {
		return blackPawns.size();
	}

	public int getWhitePawnsLeft() {
		return whitePawns.size();
	}

	public ArrayList<Integer>legalActions(String lastRole)
	{
		//printPawns();
		ArrayList<Integer> legal = new ArrayList<Integer>();
		if(lastRole.equals("black"))
		{
			Set<Integer> set = whitePawns.keySet();
			for(Integer x : set)
			{
				//todo: send all possible actions for white player
				int y1 = (int) whitePawns.get(x).getLeft();
				int x1 = (int) whitePawns.get(x).getRight();
				
				if((y1+ 1) < height)
				{
					if(board.get(y1 + 1).get(x1).equals(Square.empty))
					{
						legal.add(x1 + 1);
						legal.add(y1 + 1);
						legal.add(x1 + 1);
						legal.add(y1 + 2);
					}
				}
				if(((y1+1) < height && (x1 - 1) >= 0))
				{
					if(board.get(y1 + 1).get(x1 - 1).equals(Square.black))
					{
						legal.add(x1 + 1);
						legal.add(y1 + 1);
						legal.add(x1 - 2);
						legal.add(y1 + 2);
					}
				}
				if((y1+1) < height && (x1 + 1) < width)
				{
					if(board.get(y1 + 1).get(x1 + 1).equals(Square.black))
					{
						legal.add(x1 + 1);
						legal.add(y1 + 1);
						legal.add(x1 + 2);
						legal.add(y1 + 2);
					}
				}				
			}
		}
		else if(lastRole.equals("white"))
		{
			Set<Integer> set = blackPawns.keySet();
			for(Integer x : set)
			{
				//todo: send all possible actions for white player
				int y1 = (int) blackPawns.get(x).getLeft();
				int x1 = (int) blackPawns.get(x).getRight();
				System.out.println(x1);
				System.out.println(y1);
				if(y1 - 1 >= 0)
				{
					if(board.get(y1 - 1).get(x1).equals(Square.empty))
					{
						legal.add(x1 + 1);
						legal.add(y1 + 1);
						legal.add(x1 + 1);						
						legal.add(y1);
					}
				}
				if( (y1 - 1) >= 0 && (x1 - 1) >= 0)
				{
					if(board.get(y1 - 1).get(x1 - 1).equals(Square.white))
					{
						legal.add(x1 + 1);
						legal.add(y1 + 1);
						legal.add(x1);
						legal.add(y1);
					}
				}
				if((y1 - 1) >= 0 && (x1 + 1) < width)
				{
					if(board.get(y1 - 1).get(x1 + 1).equals(Square.white))
					{
						legal.add(x1 + 1);
						legal.add(y1 + 1);
						legal.add(x1 + 2);
						legal.add(y1);					
					}		
				}
			}
		}
		else
		{
			System.out.println("role not accepted in legal Actions function!");
		}
		return legal;
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
	
	
	public Square getSquare(int x, int y)
	{
		return board.get(y - 1).get(x - 1);
	}
	
	public void printPawns()
	{
		Set<Integer> set = blackPawns.keySet();
		System.out.println("black pawns:");
		for(Integer x : set)
		{
			
			System.out.println("left: " + blackPawns.get(x).getLeft());
			System.out.println("right: " + blackPawns.get(x).getRight());
		}
		set = whitePawns.keySet();
		System.out.println("white pawns:");
		for(Integer x : set)
		{
			
			System.out.println("left: " + whitePawns.get(x).getLeft());
			System.out.println("right: " + whitePawns.get(x).getRight());
		}
	}
	
	public void printBoard()
	{
        for (int i = 0; i < board.size(); i++) 
        {
        	for(int j = 0; j < board.get(i).size(); j++)
        	{
        		System.out.println("square x,y: " + (j+1) + ", " + (i+1) + " " + board.get(i).get(j));
        	}
        }
	}
}
