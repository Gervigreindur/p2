package p2;

import java.util.ArrayList;

public class AlphaBetaSearch {

	private Pair<Integer, Integer> bestActionFrom;
	private Pair<Integer, Integer> bestActionTo;
	
	AlphaBetaSearch()
	{
		bestActionFrom = new Pair<Integer, Integer>(-1, -1);
		bestActionTo = new Pair<Integer, Integer>(-1, -1);
	}
	
	public ArrayList<Pair<Integer, Integer>> alphaBetaSearch(State state) {
		ArrayList<Integer> legalActions = state.legalActions();
		int bestV = -1;
		
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			
			int v = maxValue(state.result(checkActionFrom, checkActionTo), 0, 100);
			
			if (v > bestV)
			{
				bestActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
				bestActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2), legalActions.get(i + 3));
				bestV = v;
			}
		}
		ArrayList<Pair<Integer, Integer>> bestResult = new ArrayList<Pair<Integer, Integer>>();
		bestResult.add(bestActionFrom);
		bestResult.add(bestActionTo);
		return bestResult;
	}

	private int maxValue(State state, int alpha, int beta) {
		if(state.terminalTest()) 
		{
			return state.utility();
		}
		
		ArrayList<Integer> legalActions = state.legalActions();
		int v = 0;
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			
			v = Math.max(v, minValue(state.result(checkActionFrom, checkActionTo), alpha, beta));
			
			if(v >= beta)
			{
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}

	private int minValue(State state, int alpha, int beta) {
		if(state.terminalTest()) 
		{
			return state.utility();
		}
		
		ArrayList<Integer> legalActions = state.legalActions();
		int v = 100;
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			
			v = Math.min(v, maxValue(state.result(checkActionFrom, checkActionTo), alpha, beta));
			
			if(v <= alpha)
			{
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}
}
