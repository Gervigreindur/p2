package p2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class State {
	
	private int height;
	private int width;
	private Map<Integer, Pair<Integer, Integer>> whitePawns;
	private Map<Integer, Pair<Integer, Integer>> blackPawns;
	private Map<Pair<Integer, Integer>, Square> board;
	private Pair<Integer, Integer> coord;
	private enum Square
	{
		white, black, empty
	}
	
	State(int height, int width)
	{
		this.height = height;
		this.width = width;
		coord = new Pair<Integer, Integer>(0, 0);
		whitePawns = new HashMap<Integer, Pair<Integer, Integer>>();
		blackPawns = new HashMap<Integer, Pair<Integer, Integer>>();
		board = new HashMap<Pair<Integer, Integer>, Square>();
		setInitialBoard();
	}

	public void setInitialBoard()
	{
		for(int y = 1; y <= height; y++)
		{
			for(int x = 1; x <= width; x++)
			{
				if(y <= 2)
				{
					board.put(new Pair<Integer, Integer>(x, y), Square.white);
					whitePawns.put(whitePawns.size() , new Pair<Integer, Integer>(x, y));
				}
				else if(y > (height - 2))
				{
					board.put(new Pair<Integer, Integer>(x, y), Square.black);
					blackPawns.put(blackPawns.size() , new Pair<Integer, Integer>(x, y));
				}
				else
				{
					board.put(new Pair<Integer, Integer>(x, y), Square.empty);
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

	public ArrayList<Integer>legalActions(String lastRole)
	{
		//printPawns();
		ArrayList<Integer> legal = new ArrayList<Integer>();
		if(lastRole.equals("black"))
		{
			Set<Integer> set = whitePawns.keySet();
			for(Integer key : set)
			{
				//todo: send all possible actions for white player
				int x1 = (int) whitePawns.get(key).getLeft();
				int y1 = (int) whitePawns.get(key).getRight();
				if(y1+1 <= height)
				{
					coord.change(x1, y1+1);
					if(board.get(coord).equals(Square.empty))
					{
						legal.add(x1);
						legal.add(y1);
						legal.add(x1);
						legal.add(y1 + 1);
					}
				}
				if(y1+1 <= height && (x1 - 1) > 0)
				{
					coord.change(x1 - 1, y1+1);
					if(board.get(coord).equals(Square.black))
					{
						legal.add(x1);
						legal.add(y1);
						legal.add(x1 - 1);
						legal.add(y1 + 1);
					}
				}
				if(y1+1 <= height && x1 + 1 <= width)
				{
					coord.change(x1 + 1, y1 + 1);
					if(board.get(coord).equals(Square.black))
					{
						legal.add(x1);
						legal.add(y1);
						legal.add(x1 + 1);
						legal.add(y1 + 1);
					}
				}				
			}
		}
		else if(lastRole.equals("white"))
		{
			Set<Integer> set = blackPawns.keySet();
			for(Integer key : set)
			{
				//todo: send all possible actions for white player
				int x1 = (int) blackPawns.get(key).getLeft();
				int y1 = (int) blackPawns.get(key).getRight();
				Pair<Integer, Integer> coord = new Pair<Integer,Integer>(x1,y1);
				if(y1 > 0)
				{
					coord.change(x1, y1 - 1);
					if(board.get(coord).equals(Square.empty))
					{
						legal.add(x1);
						legal.add(y1);
						legal.add(x1);
						legal.add(y1 - 1);
					}
				}
				if(y1 - 1 > 0 && (x1 - 1) > 0)
				{
					coord.change(x1 - 1, y1 - 1);
					if(board.get(coord).equals(Square.white))
					{
						legal.add(x1);
						legal.add(y1);
						legal.add(x1 - 1);
						legal.add(y1 - 1);
					}
				}
				if(y1 - 1 > 0 && x1 + 1 <= width)
				{
					coord.change(x1 + 1, y1 - 1);
					if(board.get(coord).equals(Square.white))
					{
						legal.add(x1);
						legal.add(y1);
						legal.add(x1 + 1);
						legal.add(y1 - 1);
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
		Set<Integer> set;
		coord.change(x1, y1);
		board.put(coord, Square.empty);	
		if (role == "white") {
			set = whitePawns.keySet();
			for(Integer key : set)
			{
				if(whitePawns.get(key).equals(coord))
				{
					coord.change(x2, y2);
					whitePawns.put(key, coord);
				}				
			}
			if(board.get(coord).equals(Square.black))
			{
				set = blackPawns.keySet();
				Integer temp = -1;
				for(Integer key : set)
				{
					if(blackPawns.get(key).equals(coord))
					{
						temp = key;
					}
				}
				System.out.println("removed black pawn: " + blackPawns.remove(temp));
			}
			board.put(coord, Square.white);
		} else {
			set = blackPawns.keySet();
			for(Integer key : set)
			{
				if(blackPawns.get(key).equals(coord))
				{
					coord.change(x2, y2);
					blackPawns.put(key, coord);
				}				
			}
			if(board.get(coord).equals(Square.white))
			{
				set = whitePawns.keySet();
				Integer temp = -1;
				for(Integer key : set)
				{
					
					if(whitePawns.get(key).equals(coord))
					{
						temp = key;
					}
				}
				System.out.println("removed white pawn: " + whitePawns.remove(temp));
			}
			board.put(coord, Square.black);
		}
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
		for(int i = height; i > 0; i--)
		{
			for(int j = 1; j <= width; j++)
			{
				coord.change(j, i);
				System.out.print(board.get(coord) + " | ");
			}
			System.out.println("");
			System.out.println("--------------------");
		}
	}
}
