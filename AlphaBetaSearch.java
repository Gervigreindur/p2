package p2;

import java.util.ArrayList;

public class AlphaBetaSearch {

	private Pair<Integer, Integer> bestActionFrom;
	private Pair<Integer, Integer> bestActionTo;
	private int cutoff;
	private boolean first = true;
	private long time;
	ArrayList<Pair<Integer, Integer>> bestResult;
	
	AlphaBetaSearch()
	{
		this.cutoff = 1;
		bestResult = new ArrayList<Pair<Integer, Integer>>();
		bestActionFrom = new Pair<Integer, Integer>(-1, -1);
		bestActionTo = new Pair<Integer, Integer>(-1, -1);
	}
	
	public ArrayList<Pair<Integer, Integer>> alphaBetaSearch(State state, long playclock) {					
	    time = System.currentTimeMillis() + playclock * 1000 - 100;
		ArrayList<Integer> legalActions = state.legalActions();
		//System.out.println(legalActions);
		int v = -1;
		int alpha = 0;
		int beta = 100;
		
		bestActionFrom = new Pair<Integer, Integer>(legalActions.get(0), legalActions.get(1));
		bestActionTo = new Pair<Integer, Integer>(legalActions.get(2), legalActions.get(3));
		
		while(cutoff < 4 * state.getHeight() * state.width)
		{
			try
			{
				for(int i = 0; i < legalActions.size(); i += 4)
				{
					Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
					Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
					
					State temp = new State(state);		
					temp.updateState(checkActionFrom, checkActionTo);

					int co = cutoff;
					int fromMin = minValue(temp, alpha, beta, co);
					
					if(fromMin > v)
					{
						v = fromMin;
						alpha = Math.max(alpha, v);
						bestActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
						bestActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2), legalActions.get(i + 3));

						if(alpha >= beta )
						{
							break;
						}
					}
				}
				if(alpha == 100) { break; }
			}
			catch(TimeException x)
			{
				break;
			}
			cutoff++;
		}
		
		bestResult.add(bestActionFrom);
		bestResult.add(bestActionTo);
		
		return bestResult;
	}
	
	private int minValue(State state, int alpha, int beta, int depth) throws TimeException {
		if(System.currentTimeMillis() >= time)
		{
			System.out.println("time exceeded: " + depth + " " + System.currentTimeMillis());
			throw new TimeException();
		}
		
		if(state.terminalTest() || depth == 0)
		{
			
			int best = state.eval();
			//System.out.println("BEST MOVE SAMKVAEMT EVAL() from min: " + best + "best is: " + best + " depth is: " + depth);
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
			int fromMax = maxValue(temp, alpha, beta, depth - 1);
			
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

	private int maxValue(State state, int alpha, int beta, int depth) throws TimeException {
		
		//System.out.println("max: " + depth);
		//System.out.println("depth: " + depth + ", cutoff: " + cutoff);
		if(System.currentTimeMillis() >= time)
		{
			System.out.println("time exceeded: " + depth + " " + System.currentTimeMillis());
			throw new TimeException();
		}
		if(state.terminalTest() || depth == 0)
		{	
			int best = state.eval();
			//System.out.println("BEST MOVE SAMKVAEMT EVAL() from max: " + best + "best is: " + best + " depth is: " + depth); 
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

			temp.updateState(checkActionFrom, checkActionTo);
			temp.sort();
			//System.out.println("maxafter ? " + temp.isWhite());
			int fromMin = minValue(temp, alpha, beta, depth - 1);
			
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