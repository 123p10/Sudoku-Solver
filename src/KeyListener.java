import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter{
	public static boolean isSpacePressed = false;
	public void keyPressed(KeyEvent event) {
		synchronized(KeyListener.class) {
			char ch = event.getKeyChar();
			if(ch == ' ') {
				isSpacePressed = true;
			}
		}
	}
	public static boolean isSpacePressed() {
		synchronized(KeyListener.class) {
			return isSpacePressed;
		}
	}
}
