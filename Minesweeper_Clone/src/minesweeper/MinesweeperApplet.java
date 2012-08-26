package minesweeper;

import javax.swing.JApplet;

@Header(
		   author = "James Ostrander",
		   date = "7/16/2012",
		   lastModified = "8/16/2012"
		)

@SuppressWarnings("serial")
public final class MinesweeperApplet extends JApplet {
	@Override
	public void init() {
	    this.setContentPane(new Minesweeper());
	}
}
