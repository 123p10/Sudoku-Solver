import java.util.ArrayList;

public class HiddenTriple {
	int x,y,z;
	public HiddenTriple(int x,int y,int z) {
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
}
