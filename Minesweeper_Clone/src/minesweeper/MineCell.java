package minesweeper;

import java.awt.Color;

import javax.swing.*;

@Header(
		   author = "James Ostrander",
		   date = "5/15/2012",
		   lastModified = "8/16/2012"
		)

@SuppressWarnings("serial")
public final class MineCell extends FirstCell {
	private Icon myExplodeIcon;
	
	public MineCell(MinePanel setParentGame, int setPosX, int setPosY)	{
		super(setParentGame, setPosX, setPosY);
		myExplodeIcon = new ImageIcon(createImage("images/i_mine.gif", "Flagged"));
	}
	
	@Override
	public void reveal() {
		super.myRevealed = true;
		setBackground(Color.WHITE);
		super.unflag();
		setIcon(myExplodeIcon);	
		super.myParentGame.setGameOver();
	}
	
}
