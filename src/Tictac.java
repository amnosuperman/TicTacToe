

public class Tictac {

	private static int size = 3;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				GridWindow win = new GridWindow( size );
				win.createAndShowGUI();
			}
		});
	}
}
