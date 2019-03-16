
public class Triple {
	int x,y,z;
	int count = 0;
	public Triple(int x,int y,int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		count++;
	}
	public int getFirst() {
		return x;
	}
	public int getSecond() {
		return y;
	}
	public int getThird() {
		return z;
	}
	public int getCount() {
		return count;
	}
	public void increaseCount() {
		count++;
	}
	public int checkEquality(int a,int b,int c) {
		if(a == x) {
			if(b == y) {
				if(c == z) {
					return 1;
				}
			}
			else if(b == z) {
				if(c == y) {
					return 1;
				}
			}
		}
		else if(a == y) {
			if(b == x) {
				if(c == z) {
					return 1;
				}
			}
			else if(b == z) {
				if(c == x) {
					return 1;
				}
			}

		}
		else if(a == z) {
			if(b == y) {
				if(c == x) {
					return 1;
				}
			}
			else if(b == x) {
				if(c == y) {
					return 1;
				}
			}

		}
		return 0;
	}
}
