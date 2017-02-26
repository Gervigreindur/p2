package p2;

import java.util.ArrayList;

public class AlphaBetaSearch {

	private Pair<Integer, Integer> bestActionFrom;
	private Pair<Integer, Integer> bestActionTo;
	private int cutoff;
	private String role;
	private String enemy;
	
	ArrayList<Pair<Integer, Integer>> bestResult;
	
	AlphaBetaSearch(int cutoff, String role)
	{
		this.cutoff = cutoff;
		this.role = role;
		if(role.equals("white"))
		{
			enemy = "black";
		}
		else
		{
			enemy = "white";
		}
		bestResult = new ArrayList<Pair<Integer, Integer>>();
		bestActionFrom = new Pair<Integer, Integer>(-1, -1);
		bestActionTo = new Pair<Integer, Integer>(-1, -1);
	}
	
	public ArrayList<Pair<Integer, Integer>> alphaBetaSearch(State state) {					
		System.out.println("MyRole: " + role);
		System.out.println("EnemyRole: " + enemy);
		
		ArrayList<Integer> legalActions = state.legalActions(role);
		//System.out.println(legalActions);
		int v = 0;
		int alpha = 0;
		int beta = 100;

		for(int i = 0; i < legalActions.size(); i += 4)
		{
			Pair<Integer, Integer> checkActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
			Pair<Integer, Integer> checkActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2 ), legalActions.get(i + 3));
			State temp = new State(state);
			
			temp.updateState(checkActionFrom, checkActionTo, role);
			v = Math.max(v, minValue(temp, alpha, beta, 0));
			System.out.println(v);
			if(v >= beta )
			{
				bestActionFrom = new Pair<Integer, Integer>(legalActions.get(i), legalActions.get(i + 1));
				bestActionTo = new Pair<Integer, Integer>(legalActions.get(i + 2), legalActions.get(i + 3));
				System.out.println("best action found!" + legalActions.get(i) + " " + legalActions.get(i + 1) + " " + legalActions.get(i + 2) + legalActions.get(i + 3));
				if(v == 100)
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

	private int maxValue(State state, int alpha, int beta, int depth) {
		
		if(state.terminalTest(role)) 
		{
			//System.out.println("max depth: " + depth);
			return state.utility(role);
		}
		
		//System.out.println("max: " + depth);
		if(depth == cutoff)
		{			
			return state.eval(role);
		}
		
		ArrayList<Integer> legalActions = state.legalActions(role);
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
			temp.updateState(checkActionFrom, checkActionTo, role);
			
			//System.out.println("maxafter ? " + temp.isWhite());
			v = Math.max(v, minValue(temp, alpha, beta, depth+1));

			if(v >= beta )
			{
				//System.out.println("best action found!" + legalActions.get(i) + " " + legalActions.get(i + 1) + " " + legalActions.get(i + 2) + legalActions.get(i + 3));
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}

	private int minValue(State state, int alpha, int beta, int depth) {
		
		
		
		if(state.terminalTest(enemy) ) 
		{
			//System.out.println("min depth: " + depth);
			
			return state.utility(enemy); 
		}
		
		//System.out.println("min: " + depth);
		if(depth == cutoff)
		{

			return state.eval(enemy);
		}
		
		ArrayList<Integer> legalActions = state.legalActions(enemy);
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
			temp.updateState(checkActionFrom, checkActionTo, enemy);
			//System.out.println("minafter ? " + temp.isWhite());
			v = Math.min(v, maxValue(temp, alpha, beta, depth+1));
			
			if(v <= alpha)
			{	
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}
}
