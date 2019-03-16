import java.util.ArrayList;

public class Cell {
	int value;
	int row;
	int box;
	int col;
	int uid;
	ArrayList<Integer> possibleValues;
	//	int possibleValues[] = {1,2,3,4,5,6,7,8,9};
	public Cell(int iv) {
		value = iv;
		possibleValues = new ArrayList<Integer>();

		for(int i = 1;i <= 9;i++) {
			possibleValues.add(i);
		}
	}
	public int getValue() {
		return value;
	}
	public void setValue(int v) {
		value = v;
		possibleValues.clear();
		Board.lastChangedCounter = 0;
	}
	public void checkValue() {
		if(possibleValues.size() == 1) {
		//	System.out.println("Checked Value: " + possibleValues.get(0));
			setValue(possibleValues.get(0));

		}
	}
	public void removePossibleValue(int i) {
		possibleValues.remove(Integer.valueOf(i));
	}

	public int hasPossibleValue(int i ) {
		if(possibleValues.contains(Integer.valueOf(i))) {
			return 1;
		}
		return 0;
	}
	public void resetCell() {
		possibleValues.clear();
		for(int i = 1;i <= 9;i++) {
			possibleValues.add(i);
		}

	}
	public void setRow(int r) {row = r;}
	public void setCol(int c) {col = c;}
	public void setBox(int b) {box = b;}
	public int getRow() {return row;}
	public int getBox() {return box;}
	public int getCol() {return col;}
}
