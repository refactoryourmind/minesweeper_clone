package minesweeper;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@Author(
		   author = "James Ostrander",
		   date = "5/29/2012",
		   lastModified = "8/6/2012"
		)

@SuppressWarnings("serial")
public class ResetButton extends JButton implements ActionListener 
{
	private Minesweeper myParentGame;
	
	public ResetButton(Minesweeper setParentGame)
	{
		myParentGame = setParentGame;
		setMinimumSize(new Dimension(40, 14));
		setMargin(new java.awt.Insets(3, 3, 3, 3));
		setText("New Game");
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) 
	{
		myParentGame.restartGame();
		setText("New Game");
	}

}

