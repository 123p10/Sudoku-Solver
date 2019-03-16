import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Board {
	Cell cells[][];
	final int sizeCells = 70;
	Font lf;
	Font sf;
	Box boxes[][];
	Row rows[];
	Column columns[];
	long startTime = 0;
	long elapsed = 0;
	boolean firstScan = true;
	public static boolean stop = false;
	public static int lastChangedCounter = 0;
	public Board() {
		lf = new Font("serif", Font.PLAIN, 30);
		sf = new Font("serif", Font.PLAIN, 10);

		cells = new Cell[9][9];
		boxes = new Box[3][3];
		rows = new Row[9];
		columns = new Column[9];
		for(int y = 0;y < 3;y++) {
			for(int x = 0;x < 3;x++) {
				boxes[y][x] = new Box();
			}
		}
		for(int x = 0;x < 9;x++) {
			columns[x] = new Column();
		}
		for(int y = 0;y < 9;y++) {
			rows[y] = new Row();
			for(int x = 0;x < 9;x++) {
				cells[y][x] = new Cell(-1);
				boxes[y / 3][x/3].addCell(cells[y][x]);
				rows[y].addCell(cells[y][x]);
				columns[x].addCell(cells[y][x]);
			}
		}
		//scanBoard();
		
	}
	public void scanBoard() {
		if(firstScan == true) {
			startTime = System.nanoTime();
			firstScan = false;
		}
		//if(stop == true) {return;}
		
		for(int y = 0;y < 9;y++) {
			rows[y].checkCells();
		}
		for(int x = 0;x < 9;x++) {
			columns[x].checkCells();
		}

		//Boxes
		for(int y = 0;y < 3;y++) {
			for(int x = 0;x < 3;x++) {
				boxes[y][x].checkCells();
			}
		}
		Board.lastChangedCounter++;
		
	}
	public int isSolved() {
		for(int y = 0;y < 9;y++) {
			for(int x = 0;x < 9;x++) {
				if(cells[y][x].getValue() == -1) {
					return 0;
				}
			}
		}
		elapsed = System.nanoTime() - startTime;
		return 1;	
	}
	public int checkSolution() {
		for(int y = 0;y < 9;y++) {
			if(rows[y].checkSolution() == 0) {
				return 0;
			}
		}
		for(int x = 0;x < 9;x++) {
			if(columns[x].checkSolution() == 0) {
				return 0;
			}
		}

		//Boxes
		for(int y = 0;y < 3;y++) {
			for(int x = 0;x < 3;x++) {
				
				if(boxes[y][x].checkSolution() == 0) {
					return 0;
				}
			}
		}
		return 1;
	}
	public long getElapsed() {
		return elapsed;
	}
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.black);
		for(int y = 0;y < 9;y++) {
			for(int x = 0;x < 9;x++) {
				g.drawRect(x*sizeCells, y*sizeCells+31, sizeCells, sizeCells);
				if(cells[y][x].getValue() != -1) {
					g.setFont(lf);
					g.drawString(cells[y][x].getValue() + "", x*sizeCells + sizeCells/2-5, y*sizeCells + sizeCells+5);
					//int stringx = 0;
				}
				else {
					g.setFont(sf);
					for(int i = 0;i < cells[y][x].possibleValues.size();i++) {
						if(i < 5) {
							g.drawString(cells[y][x].possibleValues.get(i) + " ", x*sizeCells +10 + i*10, y*sizeCells + sizeCells+5);

						}
						else {
							g.drawString(cells[y][x].possibleValues.get(i) + " ", x*sizeCells +10 + (i-5)*10, y*sizeCells + sizeCells+15);

						}
					}

				}
			}
		}
		for(int y = 0;y <= 3;y++) {
			g.fillRect(0, y*(sizeCells)*3+31, sizeCells*9, 3);
			//g.drawLine(0, y*(sizeCells)*3+31, sizeCells*9, y*(sizeCells)*3+31);
		}
		for(int x = 0;x <= 3;x++) {
			g.fillRect(x*sizeCells*3, 0, 3, sizeCells*9+31);
			//g.drawLine(0, y*(sizeCells)*3+31, sizeCells*9, y*(sizeCells)*3+31);
		}

	}
	public void setBoardStyle(int option) {
		//Simple
		if(option == 0) {
			cells[0][0].setValue(4);
			cells[0][1].setValue(9);
			cells[0][4].setValue(8);
			cells[0][5].setValue(6);
			cells[0][8].setValue(2);
			
			cells[1][2].setValue(6);
			cells[1][6].setValue(5);
			cells[1][7].setValue(9);
			
			cells[2][2].setValue(3);
			cells[2][5].setValue(2);
			cells[2][6].setValue(7);
			cells[2][7].setValue(6);
			cells[2][8].setValue(4);
			
			cells[3][5].setValue(8);
			
			
			cells[4][1].setValue(5);
			cells[4][8].setValue(1);

			cells[5][0].setValue(8);
			cells[5][1].setValue(1);
			cells[5][2].setValue(9);
			cells[5][3].setValue(3);
			cells[5][7].setValue(5);

			cells[6][3].setValue(7);
			cells[6][5].setValue(1);
			cells[6][6].setValue(6);
			
			cells[7][1].setValue(7);
			cells[7][3].setValue(2);
			cells[7][4].setValue(4);
			cells[7][6].setValue(9);
			cells[7][7].setValue(1);
			cells[7][8].setValue(5);
		}
		//Expert ABSURD requires pointing pairs and X-Cycles
		if(option == 1) {
			cells[0][2].setValue(9);
			cells[0][5].setValue(2);
			
			cells[1][0].setValue(1);
			cells[1][1].setValue(6);
			cells[1][4].setValue(5);
			
			cells[2][4].setValue(7);
			cells[2][8].setValue(2);
			
			cells[3][8].setValue(5);
			
			
			cells[4][1].setValue(5);
			cells[4][3].setValue(8);
			cells[4][5].setValue(3);
			cells[4][7].setValue(9);

			cells[5][0].setValue(6);
			cells[5][2].setValue(7);
			cells[5][4].setValue(9);
			cells[5][6].setValue(2);
			cells[5][7].setValue(4);

			cells[6][5].setValue(1);
			cells[6][6].setValue(6);
			
			cells[7][0].setValue(8);
			cells[7][1].setValue(9);
			cells[7][6].setValue(3);
			
			cells[8][3].setValue(4);

		}
		//Naked triple
		if(option == 2) {
			
			cells[1][2].setValue(1);
			cells[1][3].setValue(9);
			cells[1][6].setValue(5);
			
			cells[2][0].setValue(5);
			cells[2][1].setValue(6);
			cells[2][3].setValue(3);
			cells[2][4].setValue(1);
			cells[2][7].setValue(9);
			
			cells[3][0].setValue(1);
			cells[3][3].setValue(6);
			cells[3][7].setValue(2);
			cells[3][8].setValue(8);

			
			cells[4][2].setValue(4);
			cells[4][6].setValue(7);

			cells[5][0].setValue(2);
			cells[5][1].setValue(7);
			cells[5][5].setValue(4);
			cells[5][8].setValue(3);

			cells[6][1].setValue(4);
			cells[6][4].setValue(6);
			cells[6][5].setValue(8);
			cells[6][7].setValue(3);
			cells[6][8].setValue(5);

			cells[7][2].setValue(2);
			cells[7][5].setValue(5);
			cells[7][6].setValue(9);

		}
		//Hidden Triple
		if(option == 3) {
			cells[0][0].setValue(3);
			
			cells[1][0].setValue(9);
			cells[1][1].setValue(7);
			cells[1][4].setValue(1);
			
			cells[2][0].setValue(6);
			cells[2][3].setValue(5);
			cells[2][4].setValue(8);
			cells[2][5].setValue(3);
			
			cells[3][0].setValue(2);
			cells[3][6].setValue(9);

			
			cells[4][0].setValue(5);
			cells[4][3].setValue(6);
			cells[4][4].setValue(2);
			cells[4][5].setValue(1);
			cells[4][8].setValue(3);

			cells[5][2].setValue(8);
			cells[5][8].setValue(5);

			cells[6][3].setValue(4);
			cells[6][4].setValue(3);
			cells[6][5].setValue(5);
			cells[6][8].setValue(2);

			cells[7][4].setValue(9);
			cells[7][7].setValue(5);
			cells[7][8].setValue(6);
			
			cells[8][8].setValue(1);

		}
		//Naked Triple
		if(option == 4) {
			cells[0][2].setValue(3);
			cells[0][8].setValue(1);

			cells[1][1].setValue(9);
			cells[1][4].setValue(3);
			cells[1][5].setValue(5);
			cells[1][6].setValue(2);
			cells[1][7].setValue(6);
			cells[1][8].setValue(8);

			cells[3][1].setValue(7);
			cells[3][6].setValue(1);
			cells[3][7].setValue(8);
			cells[3][8].setValue(6);
			
			cells[4][0].setValue(1);
			cells[4][1].setValue(3);
			cells[4][3].setValue(8);
			cells[4][4].setValue(6);
			cells[4][6].setValue(7);
			cells[4][7].setValue(2);
			cells[4][8].setValue(5);

			
			cells[5][0].setValue(2);
			cells[5][1].setValue(8);
			cells[5][2].setValue(6);
			cells[5][6].setValue(9);
			cells[5][7].setValue(4);
			cells[5][8].setValue(3);

			cells[6][1].setValue(4);
			cells[6][2].setValue(1);
			cells[6][4].setValue(8);
			cells[6][6].setValue(3);

			cells[7][1].setValue(5);
			cells[7][3].setValue(2);
			cells[7][5].setValue(6);
			cells[7][7].setValue(1);

			cells[8][5].setValue(3);
			cells[8][7].setValue(7);

		}
		//Hard websudoku.com
		if(option == 5) {
			cells[0][7].setValue(8);

			cells[1][0].setValue(9);
			cells[1][1].setValue(8);
			cells[1][3].setValue(5);
			cells[1][6].setValue(2);
			cells[1][7].setValue(1);
			cells[1][8].setValue(7);
			
			cells[2][2].setValue(6);
			cells[2][3].setValue(7);
			cells[2][7].setValue(5);

			cells[3][4].setValue(7);
			cells[3][7].setValue(2);
			
			cells[4][0].setValue(1);
			cells[4][4].setValue(3);
			cells[4][8].setValue(4);

			
			cells[5][1].setValue(3);
			cells[5][4].setValue(4);

			cells[6][1].setValue(1);
			cells[6][5].setValue(2);
			cells[6][6].setValue(7);

			cells[7][0].setValue(5);
			cells[7][1].setValue(4);
			cells[7][2].setValue(2);
			cells[7][5].setValue(7);
			cells[7][7].setValue(3);
			cells[7][8].setValue(9);

			cells[8][1].setValue(9);

		}
		//Evil websudoku.com
		if(option == 6) {
			cells[0][2].setValue(3);
			cells[0][6].setValue(7);
			cells[0][7].setValue(9);

			cells[1][4].setValue(5);
			cells[1][7].setValue(3);
			
			cells[2][2].setValue(9);
			cells[2][3].setValue(7);
			cells[2][4].setValue(1);
			cells[2][6].setValue(2);

			cells[3][6].setValue(3);
			cells[3][7].setValue(6);
			
			cells[4][3].setValue(6);
			cells[4][5].setValue(7);

			
			cells[5][1].setValue(7);
			cells[5][2].setValue(4);

			cells[6][2].setValue(5);
			cells[6][4].setValue(3);
			cells[6][5].setValue(8);
			cells[6][6].setValue(1);

			cells[7][1].setValue(1);
			cells[7][4].setValue(4);

			cells[8][1].setValue(4);
			cells[8][2].setValue(2);
			cells[8][6].setValue(8);

		}
		
		
		
		
		
		/*option 3 partially solved
		if(option == 5) {
			cells[0][0].setValue(3);
			cells[0][1].setValue(8);

			cells[1][0].setValue(9);
			cells[1][1].setValue(7);
			cells[1][3].setValue(2);
			cells[1][4].setValue(1);
			
			cells[2][0].setValue(6);
			cells[2][3].setValue(5);
			cells[2][4].setValue(8);
			cells[2][5].setValue(3);
			
			cells[3][0].setValue(2);
			cells[3][4].setValue(5);
			cells[3][6].setValue(9);

			
			cells[4][0].setValue(5);
			cells[4][3].setValue(6);
			cells[4][4].setValue(2);
			cells[4][5].setValue(1);
			cells[4][8].setValue(3);

			cells[5][2].setValue(8);
			cells[5][8].setValue(5);

			cells[6][3].setValue(4);
			cells[6][4].setValue(3);
			cells[6][5].setValue(5);
			cells[6][8].setValue(2);
		
			cells[7][3].setValue(1);
			cells[7][4].setValue(9);
			cells[7][7].setValue(5);
			cells[7][8].setValue(6);
			
			cells[8][1].setValue(5);
			cells[8][8].setValue(1);

		}
		//option 3 more solved
		if(option == 6) {
			cells[0][0].setValue(3);
			cells[0][1].setValue(8);
			cells[0][6].setValue(5);
			cells[0][7].setValue(2);
			cells[0][8].setValue(4);

			cells[1][0].setValue(9);
			cells[1][1].setValue(7);
			cells[1][2].setValue(5);
			cells[1][3].setValue(2);
			cells[1][4].setValue(1);
			cells[1][5].setValue(4);
			cells[1][6].setValue(6);
			cells[1][7].setValue(3);
			cells[1][8].setValue(8);

			cells[2][0].setValue(6);
			cells[2][3].setValue(5);
			cells[2][4].setValue(8);
			cells[2][5].setValue(3);
			cells[2][6].setValue(1);
			cells[2][7].setValue(7);
			cells[2][8].setValue(9);

			cells[3][0].setValue(2);
			cells[3][3].setValue(3);
			cells[3][4].setValue(5);
			cells[3][5].setValue(8);
			cells[3][6].setValue(9);
			cells[3][7].setValue(1);
			cells[3][8].setValue(7);
			
			cells[4][0].setValue(5);
			cells[4][1].setValue(9);
			cells[4][2].setValue(7);
			cells[4][3].setValue(6);
			cells[4][4].setValue(2);
			cells[4][5].setValue(1);
			cells[4][8].setValue(3);
			
			cells[5][0].setValue(1);
			cells[5][1].setValue(3);
			cells[5][2].setValue(8);
			cells[5][4].setValue(4);
			cells[5][6].setValue(2);
			cells[5][7].setValue(6);
			cells[5][8].setValue(5);

			cells[6][3].setValue(4);
			cells[6][4].setValue(3);
			cells[6][5].setValue(5);
			cells[6][8].setValue(2);
		
			cells[7][3].setValue(1);
			cells[7][4].setValue(9);
			cells[7][7].setValue(5);
			cells[7][8].setValue(6);
			
			cells[8][1].setValue(5);
			cells[8][3].setValue(8);

			cells[8][8].setValue(1);

		}
		if(option == 7) {
			cells[0][0].setValue(3);
			cells[0][1].setValue(8);
			cells[0][6].setValue(5);
			cells[0][7].setValue(2);
			cells[0][8].setValue(4);

			cells[1][0].setValue(9);
			cells[1][1].setValue(7);
			cells[1][2].setValue(5);
			cells[1][3].setValue(2);
			cells[1][4].setValue(1);
			cells[1][5].setValue(4);
			cells[1][6].setValue(6);
			cells[1][7].setValue(3);
			cells[1][8].setValue(8);

			cells[2][0].setValue(6);
			cells[2][3].setValue(5);
			cells[2][4].setValue(8);
			cells[2][5].setValue(3);
			cells[2][6].setValue(1);
			cells[2][7].setValue(7);
			cells[2][8].setValue(9);

			cells[3][0].setValue(2);
			cells[3][3].setValue(3);
			cells[3][4].setValue(5);
			cells[3][5].setValue(8);
			cells[3][6].setValue(9);
			cells[3][7].setValue(1);
			cells[3][8].setValue(7);
			
			cells[4][0].setValue(5);
			cells[4][1].setValue(9);
			cells[4][2].setValue(7);
			cells[4][3].setValue(6);
			cells[4][4].setValue(2);
			cells[4][5].setValue(1);
			cells[4][8].setValue(3);
			
			cells[5][0].setValue(1);
			cells[5][1].setValue(3);
			cells[5][2].setValue(8);
			cells[5][4].setValue(4);
			cells[5][6].setValue(2);
			cells[5][7].setValue(6);
			cells[5][8].setValue(5);

			cells[6][3].setValue(4);
			cells[6][4].setValue(3);
			cells[6][5].setValue(5);
			cells[6][8].setValue(2);
		
			cells[7][3].setValue(1);
			cells[7][4].setValue(9);
			cells[7][7].setValue(5);
			cells[7][8].setValue(6);
			
			cells[8][1].setValue(5);
			cells[8][3].setValue(8);

			cells[8][8].setValue(1);

		}*/
	}
}
