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
	private boolean white;  	//white = true, black = false
	

	public boolean isWhite() {
		return white;
	}

	private enum Square
	{
		white, black, empty
	}
	
	State(int height, int width, boolean initial)
	{
		this.height = height;
		this.width = width;
		coord = new Pair<Integer, Integer>(0, 0);
		whitePawns = new HashMap<Integer, Pair<Integer, Integer>>();
		blackPawns = new HashMap<Integer, Pair<Integer, Integer>>();
		board = new HashMap<Pair<Integer, Integer>, Square>();
		white = true;
		if(initial)
		{
			setInitialBoard();
			printBoard();
			printPawns();
		}
		
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

	public ArrayList<Integer>legalActions()
	{
		ArrayList<Integer> legal = new ArrayList<Integer>();
		if(!white)
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
		else if(white)
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

		return legal;
	}

	public void updateState(Pair<Integer, Integer> from,Pair<Integer, Integer> to)
	{	
		Set<Integer> set;
		board.put(from, Square.empty);	
		white = !white;
		if (white) {
			set = whitePawns.keySet();
			for(Integer key : set)
			{
				if(whitePawns.get(key).equals(from))
				{
					whitePawns.put(key, to);
					break;
				}				
			}
			if(board.get(to).equals(Square.black))
			{
				set = blackPawns.keySet();
				Integer temp = -1;
				for(Integer key : set)
				{
					if(blackPawns.get(key).equals(to))
					{
						temp = key;
					}
				}
				System.out.println("removed black pawn: " + blackPawns.remove(temp));
			}
			board.put(to, Square.white);
		} else {
			set = blackPawns.keySet();
			for(Integer key : set)
			{
				if(blackPawns.get(key).equals(from))
				{
					blackPawns.put(key, to);
					break;
				}				
			}
			if(board.get(to).equals(Square.white))
			{
				set = whitePawns.keySet();
				Integer temp = -1;
				for(Integer key : set)
				{
					if(whitePawns.get(key).equals(to))
					{
						temp = key;
					}
				}
				System.out.println("removed white pawn: " + whitePawns.remove(temp));
			}
			board.put(to, Square.black);
		}
	}
	
	public State result(Pair<Integer, Integer> from, Pair<Integer, Integer>to)
	{
		State nextState = new State(height, width, false);
		nextState.setBlackPawns(blackPawns);
		nextState.setWhitePawns(whitePawns);
		nextState.setBoard(board);
		nextState.updateState(from, to);
		return nextState;
	}
	
	public boolean terminalTest()
	{
		if(legalActions().size() == 0 || goalTest())
		{
			return true;
		}
		
		return false;
	}
	
	public boolean goalTest() 
	{
		if(white)
		{
			Set<Integer> set = whitePawns.keySet();
			for(Integer x : set)
			{
				if(whitePawns.get(x).getRight().equals(height))
				{
					return true;
				}
			}
		}
		else
		{
			Set<Integer> set = blackPawns.keySet();
			for(Integer x : set)
			{
				if(blackPawns.get(x).getRight().equals(0))
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
		else if(legalActions().size() == 0)
		{
			return 50;
		}
		
		return 0;
	}

	public void printPawns()
	{
		Set<Integer> set = blackPawns.keySet();
		System.out.println("black pawns:");
		for(Integer x : set)
		{
			System.out.print(" left: " + blackPawns.get(x).getLeft());
			System.out.print(" ");
			System.out.print(" right: " + blackPawns.get(x).getRight());
		}
		set = whitePawns.keySet();
		System.out.println("");
		System.out.println("white pawns:");
		for(Integer x : set)
		{
			System.out.print(" left: " + whitePawns.get(x).getLeft());
			System.out.print(" ");
			System.out.print(" right: " + whitePawns.get(x).getRight());
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
				System.out.print(board.get(coord) + " | ");
			}
			System.out.println("");
			System.out.println("--------------------");
		}
	}

	/**
	 * @return the whitePawns
	 */
	public Map<Integer, Pair<Integer, Integer>> getWhitePawns() {
		return whitePawns;
	}

	/**
	 * @param whitePawns the whitePawns to set
	 */
	public void setWhitePawns(Map<Integer, Pair<Integer, Integer>> whitePawns) {
		Map<Integer, Pair<Integer, Integer>> temp = new HashMap<Integer, Pair<Integer, Integer>>();
		temp = whitePawns;
		this.whitePawns = temp;
	}

	/**
	 * @return the blackPawns
	 */
	public Map<Integer, Pair<Integer, Integer>> getBlackPawns() {
		return blackPawns;
	}

	/**
	 * @param blackPawns the blackPawns to set
	 */
	public void setBlackPawns(Map<Integer, Pair<Integer, Integer>> blackPawns) {
		Map<Integer, Pair<Integer, Integer>> temp = new HashMap<Integer, Pair<Integer, Integer>>();
		this.blackPawns = temp;
				
	}

	/**
	 * @return the board
	 */
	public Map<Pair<Integer, Integer>, Square> getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(Map<Pair<Integer, Integer>, Square> board) {
		Map<Pair<Integer, Integer>, Square> temp = new HashMap<Pair<Integer, Integer>, Square>();
		
		this.board = board;
	}

}
