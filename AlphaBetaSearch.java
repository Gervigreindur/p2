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
		int v = -1;
		int alpha = 0;
		int beta = 100;

		bestActionFrom = new Pair<Integer, Integer>(legalActions.get(0), legalActions.get(1));
		bestActionTo = new Pair<Integer, Integer>(legalActions.get(2), legalActions.get(3));
		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			
			State temp = new State(state);		
			temp.updateState(checkActionFrom, checkActionTo);


			int fromMin = minValue(temp, alpha, beta, 0);
			
			if(fromMin > v)
			{
				v = fromMin;
				alpha = Math.max(alpha, v);
				bestActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
				bestActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2), legalActions.get(i + 3));

				if(alpha >= beta )
				{
					//System.out.println("best action found!" + legalActions.get(i) + " " + legalActions.get(i + 1) + " " + legalActions.get(i + 2) + legalActions.get(i + 3));
					break;
				}
			}
		}
		
		bestResult.add(bestActionFrom);
		bestResult.add(bestActionTo);
		
		
		return bestResult;
	}
	
	private int minValue(State state, int alpha, int beta, int depth) {
		
		if(state.terminalTest() || depth >= cutoff)
		{
			int best = state.eval();
			//System.out.println("BEST MOVE SAMKVAEMT EVAL() from min: " + Math.abs(best - depth) + "best is: " + best + " depth is: " + depth);
			return best;
			//eval*0.99^depth
			//(eval-50)*0.99^depth
		}
		
		ArrayList<Integer> legalActions = state.legalActions();
		//System.out.println("min legalActions: " + legalActions);
		int v = 101;
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
			int fromMax = maxValue(temp, alpha, beta, depth++);
			
			if(fromMax < v)
			{
				v = fromMax;
				beta = Math.min(beta, v);
				if(beta >= alpha )
				{
					//System.out.println("best action found!" + legalActions.get(i) + " " + legalActions.get(i + 1) + " " + legalActions.get(i + 2) + legalActions.get(i + 3));
					break;
				}
			}
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
			//System.out.println("BEST MOVE SAMKVAEMT EVAL() from max: " + Math.abs(best - depth) + "best is: " + best + " depth is: " + depth); 

			return best;
		}
		
		ArrayList<Integer> legalActions = state.legalActions();
		//System.out.println("max legalActions: " + legalActions);
		int v = -1;
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
			int fromMin = minValue(temp, alpha, beta, depth++);
			
			if(fromMin > v)
			{
				v = fromMin;
				alpha = Math.max(alpha, v);
				if(alpha >= beta )
				{
					//System.out.println("best action found!" + legalActions.get(i) + " " + legalActions.get(i + 1) + " " + legalActions.get(i + 2) + legalActions.get(i + 3));
					break;
				}
			}
		}
		return v;
	}
}