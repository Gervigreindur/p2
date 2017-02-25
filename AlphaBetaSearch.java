package p2;

import java.util.ArrayList;

public class AlphaBetaSearch {

	private Pair<Integer, Integer> bestActionFrom;
	private Pair<Integer, Integer> bestActionTo;
	private int cutoff;
	
	AlphaBetaSearch(int cutoff)
	{
		this.cutoff = cutoff;
		bestActionFrom = new Pair<Integer, Integer>(-1, -1);
		bestActionTo = new Pair<Integer, Integer>(-1, -1);
	}
	
	public ArrayList<Pair<Integer, Integer>> alphaBetaSearch(State state) {					
		
		maxValue(state, 0, 100, 0);
		ArrayList<Pair<Integer, Integer>> bestResult = new ArrayList<Pair<Integer, Integer>>();
		bestResult.add(bestActionFrom);
		bestResult.add(bestActionTo);
		return bestResult;
	}

	private int maxValue(State state, int alpha, int beta, int depth) {
		State temp = new State(state);
		
		
		if(temp.terminalTest() || depth == cutoff) 
		{
			//System.out.println("max depth: " + depth);
			return temp.utility();
		}
		
		ArrayList<Integer> legalActions = temp.legalActions();
		int v = 0;
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			//System.out.println("before min called: is white? " + temp.isWhite() );
			//System.out.println("from: " + checkActionFrom.getLeft() + " " + checkActionFrom.getRight());
			//System.out.println("to: " + checkActionTo.getLeft() + " " + checkActionTo.getRight());
			//System.out.println(legalActions);
			//temp.printBoard();
			//temp.printPawns();
			temp.updateState(checkActionFrom, checkActionTo);
			v = Math.max(v, minValue(temp, alpha, beta, depth++));
			
			if(v >= beta)
			{
				bestActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
				bestActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2), legalActions.get(i + 3));
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}

	private int minValue(State state, int alpha, int beta, int depth) {
		
		State temp = new State(state);
		
		if(temp.terminalTest() || depth == cutoff) 
		{
			//System.out.println("min depth: " + depth);
			return temp.utility();
		}
		
		ArrayList<Integer> legalActions = temp.legalActions();
		int v = 100;
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			//System.out.println("before max called: is white? " + temp.isWhite() );
			//System.out.println("from: " + checkActionFrom.getLeft() + " " + checkActionFrom.getRight());
			//System.out.println("to: " + checkActionTo.getLeft() + " " + checkActionTo.getRight());
			//System.out.println(legalActions);
			//temp.printBoard();
			//temp.printPawns();
			temp.updateState(checkActionFrom, checkActionTo);
			v = Math.min(v, maxValue(temp, alpha, beta, depth++));
			
			if(v <= alpha)
			{	
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}
}
