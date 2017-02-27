package p2;

import java.util.Comparator;

public class Pair<L,R> {

  private L left;
  private R right;

  public Pair(L left, R right) {
	  this.left = left;
	  this.right = right;
  }

  public L getLeft() { return left; }
  public R getRight() { return right; }

  @Override
  public int hashCode() { return left.hashCode() ^ right.hashCode(); }

  @Override
  public boolean equals(Object o) {
	  if (!(o instanceof Pair)) return false;
	  Pair pairo = (Pair) o;
	  
	  return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
  }

  public void change(L x, R y) {
		this.left = x; 
		this.right = y;
	}
  
  public static Comparator<Pair<Integer, Integer>> ascendingComparator = new Comparator<Pair<Integer, Integer>>() {
	  
	@Override
	public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
		  Integer y1 = o1.getRight();
		  Integer y2 = o2.getRight();
		  /*if(y1 == State.getHeight())
		  {
			  return -1;
		  }
		  if(y2 == State.getHeight())
		  {
			  return 1;
		  }*/
		  
		  return y1 - y2;
	}
  };
  
  public static Comparator<Pair<Integer, Integer>> decendingComparator = new Comparator<Pair<Integer, Integer>>() {
	  
		@Override
		public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
			  Integer y1 = o1.getRight();
			  Integer y2 = o2.getRight();
			 /* if(y1 == 1)
			  {
				  return -1;
			  }
			  if(y2 == 1)
			  {
				  return 1;
			  }*/
			  return y2 - y1;
		}
	  };

}
