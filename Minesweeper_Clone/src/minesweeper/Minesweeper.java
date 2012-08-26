package minesweeper;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Frame;

@Header(
		   author = "James Ostrander",
		   date = "5/12/2012",
		   lastModified = "8/6/2012"
		)

@SuppressWarnings("serial")
public class Minesweeper extends JPanel {
	private JFrame myFrame;
	private MinePanel myGamefield;
	private JPanel myConfigRowPanel;
	private ResetButton myNewGameButton;
	private JTextField mySizeXField;
	private JTextField mySizeYField;
	private JTextField myNumMinesField;
	private JLabel mySizeXLabel;
	private JLabel mySizeYLabel;
	private JLabel myNumMinesLabel;
	private Box.Filler myLargeHorizFiller;
	private Box.Filler mySmallHorizFiller;
	private Box.Filler myBottomFiller;
	
	public Minesweeper() {
		super();
	
		addItems();
	}
	
	private void addItems(int newGameSizeX, int newGameSizeY, int newGameNumMines) {
		myGamefield = new MinePanel(newGameSizeX, newGameSizeY, newGameNumMines);
		myGamefield.setAlignmentX(CENTER_ALIGNMENT);
		myGamefield.setAlignmentY(CENTER_ALIGNMENT);
		
	    myConfigRowPanel = new JPanel();
		myConfigRowPanel.setLayout(new BoxLayout(myConfigRowPanel, BoxLayout.X_AXIS));
		
		mySizeXField = new JTextField(Integer.toString(newGameSizeX));
		mySizeXField.setMinimumSize(new Dimension(24, 24));
		mySizeXField.setMaximumSize(new Dimension(48, 24));
		mySizeXField.setPreferredSize(new Dimension(32, 24));
		mySizeYField = new JTextField(Integer.toString(newGameSizeY));
		mySizeYField.setMinimumSize(new Dimension(24, 24));
		mySizeYField.setMaximumSize(new Dimension(48, 24));
		mySizeYField.setPreferredSize(new Dimension(32, 24));
		myNumMinesField = new JTextField(Integer.toString(newGameNumMines));
		myNumMinesField.setMinimumSize(new Dimension(24, 24));
		myNumMinesField.setMaximumSize(new Dimension(48, 24));
		myNumMinesField.setPreferredSize(new Dimension(32, 24));
		
		mySizeXLabel = new JLabel("Size: ");
		mySizeYLabel = new JLabel(" x  ");
		myNumMinesLabel = new JLabel("Mines: ");
		
		myNewGameButton = new ResetButton(this);
		myNewGameButton.setAlignmentX(CENTER_ALIGNMENT);
		myNewGameButton.setAlignmentY(CENTER_ALIGNMENT);
		
		myLargeHorizFiller = new Box.Filler(new Dimension(6, 6), new Dimension(20, 6), new Dimension(20, 6));
		mySmallHorizFiller = new Box.Filler(new Dimension(6, 6), new Dimension(10, 6), new Dimension(10, 6));
		myBottomFiller = new Box.Filler(new Dimension(6,6), new Dimension(6,6), new Dimension(6,6));
		
		myConfigRowPanel.add(mySizeXLabel);
		myConfigRowPanel.add(mySizeXField);
		myConfigRowPanel.add(mySizeYLabel);
		myConfigRowPanel.add(mySizeYField);
		myConfigRowPanel.add(mySmallHorizFiller);
		myConfigRowPanel.add(myNumMinesLabel);
		myConfigRowPanel.add(myNumMinesField);
		myConfigRowPanel.add(myLargeHorizFiller);
		myConfigRowPanel.add(myNewGameButton);
		
		add(myBottomFiller);
		add(myConfigRowPanel);
		add(myGamefield);
	
		repaint();
	}
	
	private void addItems() {
		addItems(16, 16, 40);
	}
	
	public void restartGame() {
		try {
			final int newGameSizeX = Integer.parseInt(mySizeXField.getText());
			final int newGameSizeY = Integer.parseInt(mySizeYField.getText());
			final int newGameNumMines = Integer.parseInt(myNumMinesField.getText());
			
			myGamefield.removeAll();
			remove(myGamefield);
			
			System.out.println("Components removed");
			
			myGamefield = new MinePanel(newGameSizeX, newGameSizeY, newGameNumMines);
			myGamefield.setAlignmentX(CENTER_ALIGNMENT);
			myGamefield.setAlignmentY(CENTER_ALIGNMENT);
			add(myGamefield); 
			
			System.out.println("Components added");
			
			if(myFrame != null) myFrame.pack();
			
			validate();
		}
		catch(NumberFormatException e) {
			System.err.println("User input error - configuration fields did not consist solely of integers.");
			JOptionPane.showMessageDialog(new Frame(), "The new game fields must only contain whole numbers.");
		} 
		catch(ArithmeticException e) {
			System.err.println("Math error - Insane inputs?");
			JOptionPane.showMessageDialog(new Frame(), "Math error - make sure your inputs are sane.");
		}
		finally {}
	}	
	
	private void createAndShowGUI() {
        //Create and set up the window.
        myFrame = new JFrame("Minesweeper!");
        myFrame.setMinimumSize(new Dimension(300, 200));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        Minesweeper contentPanel = new Minesweeper();
        contentPanel.setOpaque(true); //content panes must be opaque
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        myFrame.setContentPane(contentPanel);
 
        //Display the window.
        myFrame.pack();
		myFrame.setVisible(true);
    }
	
	public static void main(String[] args) {	
		final Minesweeper game = new Minesweeper();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                game.createAndShowGUI();
            }
        });			 
	}

}

