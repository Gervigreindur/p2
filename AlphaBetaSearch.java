package p2;

import java.util.ArrayList;

public class AlphaBetaSearch {

	private Pair<Integer, Integer> bestActionFrom;
	private Pair<Integer, Integer> bestActionTo;
	private int cutoff;
	private boolean first = true;
	ArrayList<Pair<Integer, Integer>> bestResult;
	
	AlphaBetaSearch(int cutoff)
	{
		this.cutoff = cutoff;
		bestResult = new ArrayList<Pair<Integer, Integer>>();
		bestActionFrom = new Pair<Integer, Integer>(-1, -1);
		bestActionTo = new Pair<Integer, Integer>(-1, -1);
	}
	
	public ArrayList<Pair<Integer, Integer>> alphaBetaSearch(State state) {					
		
		ArrayList<Integer> legalActions = state.legalActions();
		//System.out.println(legalActions);
		int v = 0;
		int alpha = 0;
		int beta = 80;

		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			
			State temp = new State(state);		
			temp.updateState(checkActionFrom, checkActionTo);

			v = Math.max(v, minValue(temp, alpha, 100, 0));
			System.out.println("from main v is: " + v + " and beta is: " + beta);

			
			if(v > beta )
			{
				beta = v;
				bestActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
				bestActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2), legalActions.get(i + 3));
				System.out.println("best action found!" + legalActions.get(i) + " " + legalActions.get(i + 1) + " " + legalActions.get(i + 2) + legalActions.get(i + 3));
				if(v >= 99)
				{
					break;
				}
			}
			alpha = Math.max(alpha, v);

		}
		bestResult.add(bestActionFrom);
		bestResult.add(bestActionTo);
		
		
		return bestResult;
	}
	
	private int minValue(State state, int alpha, int beta, int depth) {
		
		/*if(state.terminalTest() ) 
		{
			//System.out.println("min depth: " + depth);
			return state.utility(); 
		}*/
		
		//System.out.println("min: " + depth);
		//System.out.println("depth: " + depth + ", cutoff: " + cutoff);
		if(state.terminalTest() || depth >= cutoff)
		{
			int best = state.eval();
			//System.out.println("BEST MOVE SAMKVAEMT EVAL() from min: " + Math.abs(best - depth) + "best is: " + best + " depth is: " + depth);
			return Math.abs(best - depth);
		}
		
		ArrayList<Integer> legalActions = state.legalActions();
		//System.out.println("min legalActions: " + legalActions);
		int v = 100;
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			State temp = new State(state);
			//System.out.println("before max called: is white? " + temp.isWhite() );
			//System.out.println("from: " + checkActionFrom.getLeft() + " " + checkActionFrom.getRight());
			//System.out.println("to: " + checkActionTo.getLeft() + " " + checkActionTo.getRight());
			//System.out.println(legalActions);
			//temp.printBoard();
			//temp.printPawns();
			//System.out.println("min before ? " + temp.isWhite());
			temp.updateState(checkActionFrom, checkActionTo);
			temp.sort();
			//System.out.println("minafter ? " + temp.isWhite());
			v = Math.min(v, maxValue(temp, alpha, beta, depth+1));
			if(v > 80)
			{
				//System.out.println("min: " + v);
				break;
			}
			if(v <= alpha)
			{	
				//System.out.println("Depth : " + depth + " i: " + i);
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}

	private int maxValue(State state, int alpha, int beta, int depth) {
		
		/*if(state.terminalTest()) 
		{
			//System.out.println("max depth: " + depth);
			return state.utility();
		}*/
		
		//System.out.println("max: " + depth);
		//System.out.println("depth: " + depth + ", cutoff: " + cutoff);
		if(state.terminalTest() || depth == cutoff)
		{	
			int best = state.eval();
//			System.out.println("BEST MOVE SAMKVAEMT EVAL() from max: " + Math.abs(best - depth) + "best is: " + best + " depth is: " + depth); 

			return Math.abs(best - depth);
		}
		
		ArrayList<Integer> legalActions = state.legalActions();
		//System.out.println("max legalActions: " + legalActions);
		int v = 0;
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			State temp = new State(state);
			//System.out.println("before min called: is white? " + temp.isWhite() );
			//System.out.println("from: " + checkActionFrom.getLeft() + " " + checkActionFrom.getRight());
			//System.out.println("to: " + checkActionTo.getLeft() + " " + checkActionTo.getRight());
			//System.out.println(legalActions);
			//temp.printBoard();
			//temp.printPawns();
			//System.out.println("max before ? " + temp.isWhite());
			temp.updateState(checkActionFrom, checkActionTo);
			temp.sort();
			//System.out.println("maxafter ? " + temp.isWhite());
			v = Math.max(v, minValue(temp, alpha, beta, depth+1));
			if(v > 80)
			{
				//System.out.println("max: " + v);
				break;
			}
			if(v >= beta )
			{
				//System.out.println("Depth : " + depth + " i: " + i);
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}
}