package minesweeper;

import java.awt.Color;

@Header(
		   author = "James Ostrander",
		   date = "5/15/2012",
		   lastModified = "8/16/2012"
		)

@SuppressWarnings("serial")
public final class SafeCell extends FirstCell {
	
	public SafeCell(MinePanel setParentGame, int setPosX, int setPosY)	{
		super(setParentGame, setPosX, setPosY);
	}
	
	@Override
	public void reveal() {
		super.myRevealed = true;
		setBackground(Color.WHITE);
		super.unflag();
		super.myParentGame.decrementSafeCellsUnrevealed();
		System.out.println("Revealing cell:" + this.getClass().toString() + "  X: " + super.myPosX + "  Y: " + super.myPosY + "  Remaining safecells: " + super.myParentGame.getSafeCellsUnrevealed());
		
		int mineCounter = 0;
		
		//Checking for mines in all 8 directions...
		//First, declaring start and end points for traversal
		int xStart;
		if(myPosX - 1 >= 0) xStart = myPosX - 1;   //Make sure we don't go out of bounds
		else xStart = myPosX;
		
		int xFinish;
		if(myPosX + 1 < myParentGame.getSizeX()) xFinish = myPosX + 1;
		else xFinish = myPosX;
		
		int yStart = myPosY - 1;
		if(myPosY - 1 >= 0) yStart = myPosY - 1;
		else yStart = myPosY;
		
		int yFinish = myPosY + 1;
		if(myPosY + 1 < myParentGame.getSizeY()) yFinish = myPosY + 1;
		else yFinish = myPosY;
		
		for(int x = xStart; x <= xFinish; x++) {                                    //Traverse 3x3 square from -1,-1 to +1,+1 (relative to cell clicked)
			for(int y = yStart; y <= yFinish; y++) {
				System.out.println("Checking " + x + ", " + y + " for mines");
				if(x != myPosX || y != myPosY) {                                    //Skip checking if we've come across the cell the user clicked
					if(super.myParentGame.getCellAt(x,  y) instanceof MineCell) {   //Is it a mine?
						mineCounter++;   
						System.out.println("MineCounter incremented");
						}		
				}
				else System.out.println("Same cell as clicked. Skipped.");
				System.out.println("MineCounter: " + mineCounter);
			}
		}
		
		
		//Let's set our number to the proper color...
		switch(mineCounter) {
		case 0: 
			this.setText("");
			break;
		case 1: 
			this.setForeground(Color.BLUE);
			this.setText("1");
			break;
		case 2: 
			this.setForeground(new Color(0, 175, 128));
			this.setText("2");
			break;
		case 3: 
			this.setForeground(Color.RED);
			this.setText("3");
			break;
		case 4: 
			this.setForeground(Color.MAGENTA);
			this.setText("4");
			break;
		case 5: 
			this.setForeground(Color.ORANGE);
			this.setText("5");
			break;
		case 6: 
			this.setForeground(Color.CYAN);
			this.setText("6");
			break;
		case 7: 
			this.setForeground(Color.PINK);
			this.setText("7");
			break;
		case 8: 
			this.setForeground(Color.DARK_GRAY);
			this.setText("8");
			break;
		default: 
			this.setText("!");
			System.err.println("Mine counting error. MineCounter did not result in a 0-8 value.");
			break;
		}
		
		
		if(mineCounter == 0) {	
			for(int x = xStart; x <= xFinish; x++) {
				for(int y = yStart; y <= yFinish; y++) {
					System.out.println("Checking " + x + ", " + y + " for mines");
					if(x != myPosX || y != myPosY) {
						if(super.myParentGame.getCellAt(x, y).getIsRevealed() == false) {
							super.myParentGame.getCellAt(x, y).reveal();    //Make sure not to check the clicked cell for mines
							System.out.println("MineCounter incremented");
						}
					}
					else System.out.println("Same cell as clicked. Skipped.");
					System.out.println("MineCounter: " + mineCounter);
				}
			}
			
		}
		
		if(super.myParentGame.getSafeCellsUnrevealed() == 0) {
			myParentGame.setVictory();
		}
	}

	
	
}
