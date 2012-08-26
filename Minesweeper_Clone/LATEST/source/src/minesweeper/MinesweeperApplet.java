package minesweeper;

import javax.swing.JApplet;

@Author(
		   author = "James Ostrander",
		   date = "7/16/2012",
		   lastModified = "8/6/2012"
		)

@SuppressWarnings("serial")
public class MinesweeperApplet extends JApplet 
{
	@Override
	public void init() 
	{
	    this.setContentPane(new Minesweeper());
	}
}
