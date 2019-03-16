import java.util.ArrayList;

public class SpecialNakedTriple {
	int x,y,z;
	public SpecialNakedTriple(int x,int y,int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public int getNth(int i) {
		if(i == 0) {return x;}
		if(i == 1) {return y;}
		if(i == 2) {return z;}
		return 0;
	}
	public int numContains(ArrayList<Integer> val) {
		int count = 0;
		int[] set = {x,y,z};
		for(int i = 0;i < set.length;i++) {
			int j = 0;
			for(j = 0;j < val.size();j++) {
				if(set[i] == val.get(j)) {
					count++;
					
				}
			}
		}
		return count;
	}
	public int numContains(int[] val) {
		int count = 0;
		int[] set = {x,y,z};
		for(int i = 0;i < set.length;i++) {
			int j = 0;
			for(j = 0;j < val.length;j++) {
				if(set[i] == val[j]) {
					count++;
					
				}
			}
		}
		return count;
	}
	public int numContains(int a,int b) {
		int count = 0;
		int[] set = {x,y,z};
		for(int i = 0;i < set.length;i++) {
			int j = 0;
			if(set[i] == a || set[i] == b) {
				count++;
			}
		}
		return count;
	}


}
