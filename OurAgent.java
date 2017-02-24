package p2;

import java.util.ArrayList;
import java.util.Random;

public class OurAgent implements Agent
{
	//remember to remove random.
	private Random random = new Random();
	
	private String role; // the name of this agent's role (white or black)
	private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
	private boolean myTurn; // whether it is this agent's turn or not
	private int width, height; // dimensions of the board
	private State environment;

	/*
		init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "black" and playclock is the number of seconds after which nextAction must return.
	*/
    public void init(String role, int width, int height, int playclock) {
		this.role = role;
		this.playclock = playclock;
		myTurn = !role.equals("white");
		this.width = width;
		this.height = height;
		// TODO: add your own initialization code here
		environment = new State(height, width);
    }

	// lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
    	String roleOfLastPlayer = "";
    	if (lastMove != null) {
    		int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
    		
    		if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
    			roleOfLastPlayer = "white";
    		} else {
    			roleOfLastPlayer = "black";
    		}
   			System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
    		// TODO: 1. update your internal world model according to the action that was just executed
   			
    		environment.updateState(new Pair<Integer, Integer>(x1, y1), new Pair<Integer, Integer>(x2, y2));
    		environment.printBoard();
    		environment.printPawns();
    		
    	}
		
    	// update turn (above this line the myTurn is still for the previous state)
		myTurn = !myTurn;
		if (myTurn) {
			// TODO: 2. run alpha-beta search to determine the best move
			// Here we just construct a random move (that will most likely not even be possible),
			// this needs to be replaced with the actual best move.
			AlphaBetaSearch abs = new AlphaBetaSearch();
			
			ArrayList<Pair<Integer, Integer>> nextMove = abs.alphaBetaSearch(environment);
			System.out.println("legal moves: "  + nextMove.get(0).getLeft() + " " + nextMove.get(0).getRight() + " " + nextMove.get(1).getLeft() + " " + nextMove.get(1).getRight());
			
			return "(move " + nextMove.get(0).getLeft() + " " + nextMove.get(0).getRight() + " " + nextMove.get(1).getLeft() + " " + nextMove.get(1).getRight() + ")";
		} else {
			return "noop";
		}
		
	}

	// is called when the game is over or the match is aborted
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match
		environment.setInitialBoard();
	}
}
