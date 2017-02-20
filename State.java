package prog2;

import java.util.ArrayList;
import java.util.List;

public class State {
	private int whitePawnsLeft;
	private int blackPawnsLeft;
	private int height;
	private int width;
	private List<ArrayList<Square>> board;
	
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
		setInitialBoard();
	}
	
	public void setInitialBoard()
	{
		whitePawnsLeft = 0;
		blackPawnsLeft = 0;
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				if(y < 2)
				{
					board.get(y).add(Square.white);
					whitePawnsLeft++;
				}
				else if(y > (height - 3))
				{
					board.get(y).add(Square.black);
					blackPawnsLeft++;
				}
				else
				{
					board.get(y).add(Square.empty);
				}
			}
		}
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
					whitePawnsLeft--;
				}
				else
				{
					blackPawnsLeft--;
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
