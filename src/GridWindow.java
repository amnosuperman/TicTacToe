import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
//import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

public class GridWindow {

	int gridsize = 3;
	
	AIGrid g;

	boolean ai = false,prevai = false;
	boolean playerflag = false;
	int movecount = 0;
	int p1_count = 0, p2_count = 0;
	int wcount1 = 0,wcount2 =0;

	JFrame mainFrame;
	JPanel gridPanel;
	JButton[][] gridButtons;
	JLabel statusLabel;
	JLabel wstat;

	ImageIcon ic = createImageIcon("o.jpeg", "o");
	ImageIcon o = new ImageIcon(getScaledImage(ic.getImage(), 120,120));
	ImageIcon ic1 = createImageIcon("x.jpeg", "x");
	ImageIcon x = new ImageIcon(getScaledImage(ic1.getImage(), 120,120));

	GridWindow(int size) {
		gridsize = size;
	}

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
    
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public void setAI(boolean val)
	{
		ai = val;
	}
	
	public void createAndShowGUI() {
		// Creates the main window. JFrame is a generic top level container.
		mainFrame = new JFrame("Tic Tac Toe");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// We should set a minimum size to make the GUI look a little nicer
		// while making it.
		mainFrame.setMinimumSize(new Dimension(400, 400));

		Container mainPane = mainFrame.getContentPane();

		// Create a menu bar
		JMenuBar mBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		// Create new game
		JMenuItem newItem = menu.add("New");
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame(ai);
			}
		});
		JMenu submenu = new JMenu("Options");
		JRadioButtonMenuItem ai,pl;
		pl = new JRadioButtonMenuItem("Player vs Player");
		ai = new JRadioButtonMenuItem("Player vs Computer");
		pl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setAI(false);
			}
		});
		ai.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setAI(true);
			}
		});
		pl.setSelected(true);
		ai.setSelected(false);
		submenu.add(pl);
		submenu.add(ai);
		menu.add(submenu);
		JMenuItem quitItem = menu.add("Quit");
		quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});
		mBar.add(menu);

		mainPane.add(mBar, BorderLayout.PAGE_START);

		// Create status bar
		statusLabel = new JLabel("Start a new game");
		mainPane.add(statusLabel, BorderLayout.PAGE_END);

		gridPanel = new JPanel(new GridLayout(gridsize, gridsize));

		mainPane.add(gridPanel, BorderLayout.CENTER);
		// Pack is required actually decide how components should be laid out.
		// It should be called before the GUI is displayed.
		mainFrame.pack();
		// Of course, the frame needs to be made visible.
		mainFrame.setVisible(true);
	}

	private void newGame(boolean val) {
		playerflag = false;
		movecount = 0;
		g = new AIGrid(gridsize,val);
		// Remove everything in the panel, and reset it
		gridPanel.removeAll();
		gridButtons = new JButton[gridsize][gridsize];
		for (int i = 0; i < gridsize; i++) {
			for (int j = 0; j < gridsize; j++) {
				final int mi = i;
				final int mj = j;
				gridButtons[i][j] = new JButton("");
				gridButtons[i][j].addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							revealSquare(mi, mj);
						}

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				});
				gridPanel.add(gridButtons[i][j]);
			}
		}
		mainFrame.pack();

		updateStatus(0);
	}

	private void revealSquare(int i, int j) {
		// First check if the square has already been revealed.
		if ((g.revealed[i][j] > 0)||(g.gameover))
			return;
		else {
			if (playerflag)
				g.revealed[i][j] = 2;
			else
				g.revealed[i][j] = 1;
			if (playerflag == false) {
				gridButtons[i][j].setIcon(x);

			} else {
				gridButtons[i][j].setIcon(o);
			}
			playerflag = !playerflag;
			movecount++;
			if(movecount<gridsize*gridsize){
				
				int[] m = g.nextmove();
				if(m[0]!=-1){
					if(playerflag)
						gridButtons[m[0]][m[1]].setIcon(o);
					else
						gridButtons[m[0]][m[1]].setIcon(x);
					playerflag = !playerflag;
					g.revealed[m[0]][m[1]] = 2;
					movecount++;
				}
			}
			updateStatus(g.checkstatus());
		}
	}

	private void updateStatus(int val) {
		if (val == 0) {
			if (movecount == gridsize * gridsize) {
				statusLabel.setText("Game drawn!");
				return;
			}
			int t = 1;
			if (playerflag)
				t++;
			statusLabel.setText(String.format("Player %d's turn", t));
		}

		if((val==2)&&(ai))
		{
			statusLabel.setText(String.format("Computer wins!", val));
			int i, j;
			for (i = 0; i < gridsize; i++)
				for (j = 0; j < gridsize; j++)
					if (g.revealed[i][j] == 0)
						gridButtons[i][j].setEnabled(false);
			
		}
		else if ((val == 1) || (val == 2)) {
			statusLabel.setText(String.format("Player %d wins!", val));
			int i, j;
			for (i = 0; i < gridsize; i++)
				for (j = 0; j < gridsize; j++)
					if (g.revealed[i][j] == 0)
						gridButtons[i][j].setEnabled(false);
		}

	}
}
