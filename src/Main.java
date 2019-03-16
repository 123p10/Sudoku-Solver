
public class Main {
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//One Test
		Board myBoard = new Board();
		Frame myFrame = new Frame();
		KeyListener key = new KeyListener();
		myFrame.addBoard(myBoard);
		myFrame.addKeyListener(key);
		myBoard.setBoardStyle(0);
		while(!KeyListener.isSpacePressed()) {}
		while(myBoard.isSolved() == 0) {
			myFrame.repaint();

			myBoard.scanBoard();

		}
		if(myBoard.checkSolution()==1) {
			System.out.println("Properly Solved after " + myBoard.getElapsed()/1000000000.0 + " seconds");
		}
		else {
			System.out.println("Improperly Solved");
		}		
	}
}
