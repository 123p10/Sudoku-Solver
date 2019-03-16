
public class Pair {
	int x,y;
	public Pair(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public int getFirst() {
		return x;
	}
	public int getSecond() {
		return y;
	}
	public int checkEquality(int a,int b) {
		if(a == x && b == y) {
			return 1;
		}
		if(a == y && b == x) {
			return 1;
		}
		return 0;
	}
}
