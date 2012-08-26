package minesweeper;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@Author(
		   author = "James Ostrander",
		   date = "5/12/2012",
		   lastModified = "8/6/2012"
		)

@SuppressWarnings("serial")
public final class MinePanel extends JPanel {
	
	private GridLayout myGrid;
	private int mySizeY;
	private int mySizeX;
	private FirstCell[][] myCells2D;
	private int mySafeCellsUnrevealed;
	private int myNumberMines;
	
	public MinePanel() 
	{ 
		super();
		initialize(30, 16, 100);
	}
	
	public MinePanel(int setSizeX, int setSizeY, int setNumberMines) 
	{ 
		super();
		initialize(setSizeX, setSizeY, setNumberMines);
	}
	
	public void initialize(int setSizeX, int setSizeY, int setNumberMines)
	{
		setSizeY(setSizeY);
		setSizeX(setSizeX);
		myCells2D = new FirstCell[mySizeY][mySizeX];
		setNumberMines(setNumberMines);
		mySafeCellsUnrevealed = (mySizeX * mySizeY) - myNumberMines;
		
		myGrid = new GridLayout(mySizeY, mySizeX);
		myGrid.setHgap(1);
		myGrid.setVgap(1);
		
		setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		setLayout(myGrid);
		
		shallowPopulate();
		
		setVisible(true);	
		
		System.out.println("MinePanel generated with sizeX: " + mySizeX + ", sizeY: " + mySizeY);
	}
	
	public void initialize(int setSizeX, int setSizeY)
	{
		initialize(setSizeX, setSizeY, 0);
	}
	
	protected void setGameOver()  //Reveal every cell.
	{
		mySafeCellsUnrevealed = -1;
		
		for(int y = 0; y < mySizeY; y++)
		{
			for(int x = 0; x < mySizeX; x++)
			{
				if(myCells2D[y][x].myRevealed == false)
					myCells2D[y][x].reveal();
			}
		}
	}
	
	protected void setVictory()
	{
		mySafeCellsUnrevealed = -1;
		JOptionPane.showMessageDialog(new Frame(), "You have won! Congratulations!");
	}
	
	//Populates the panel with superficial FirstCell buttons which will be replaced by SafeCells or MineCells after the first one is clicked.
	private void shallowPopulate()
	{
		for(int y = 0; y < mySizeY; y++)		     
			for(int x = 0; x < mySizeX; x++)			
				add(new FirstCell(this, x, y));
	}

	//Removes the FirstCells from the shallowPopulate, then populates the 2D array and panel with SafeCell and MineCell objects.
	//firstCellX/Y indicates the first cell the player clicked. This cell and all surrounding cells will be SafeCells, letting
	//the player always cascade and never lose on his first click.
	protected void deepPopulate(int firstCellX, int firstCellY)
	{
		removeAll();
		validate();
		
		myGrid = new GridLayout(mySizeY, mySizeX);
		myGrid.setHgap(1);
		myGrid.setVgap(1);
		
		setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		setLayout(myGrid);
		
		//First we get all cells surrounding firstCell and add them to a set, protectedCells
		Set<Point> protectedCells = new HashSet<Point>();
		
		//Define constraints on a 3x3 square to iterate through.
		//Iterated cells will be added to the protectedCells set.
		//Declaring start and end points for traversal:
		int xStart;
		if(firstCellX - 1 >= 0) xStart = firstCellX - 1;   //Make sure we don't go out of bounds
		else xStart = firstCellX;
		
		int xFinish;
		if(firstCellX + 1 < getSizeX()) xFinish = firstCellX + 1;
		else xFinish = firstCellX;
		
		int yStart = firstCellY - 1;
		if(firstCellY - 1 >= 0) yStart = firstCellY - 1;
		else yStart = firstCellY;
		
		int yFinish = firstCellY + 1;
		if(firstCellY + 1 < getSizeY()) yFinish = firstCellY + 1;
		else yFinish = firstCellY;
		
		for(int x = xStart; x <= xFinish; x++)   //Traverse 3x3 square from -1,-1 to +1,+1 (relative to cell clicked)
		{
			for(int y = yStart; y <= yFinish; y++)
			{
				protectedCells.add(new Point(x, y));
				System.out.println("Added to protected set: " + x + ", " + y);
			}
		}
		
		Set<Point> emptyCellsSet = new HashSet<Point>();             
		
		for(int y = 0; y < mySizeY; y++)		     //Fill emptyCells with a Point for each cell available in cells2D.
			for(int x = 0; x < mySizeX; x++)			
				emptyCellsSet.add(new Point(x, y));
	
		emptyCellsSet.removeAll(protectedCells); //Removes all of the protectedCells from the emptyCells set.
		
		List<Point> emptyCellsList = new ArrayList<Point>(emptyCellsSet);            //Convert sets to lists so we can iterate through them.
		List<Point> protectedCellsList = new ArrayList<Point>(protectedCells);
					
		for(int i = 0; i < protectedCellsList.size(); i++)
			myCells2D[protectedCellsList.get(i).y][protectedCellsList.get(i).x] 
					= new SafeCell(this, protectedCellsList.get(i).x, protectedCellsList.get(i).y);     //Protected cells are designated as SafeCells
	
		int pointX;
		int pointY;
		
		System.out.println("Adding mines - number of mines: " + myNumberMines + "  Empty cells list size: " + emptyCellsList.size());
		for(int y = 0; y < myNumberMines; y++)
		{
			Random rn = new Random();
			
			int n = emptyCellsList.size();
			int i = rn.nextInt() % n;   //Set i to a random number between 0 and the total number of emptyCells	
			if(i < 0) i = i * -1;       //Make sure the number is positive to prevent stack overflow
			
			pointY = emptyCellsList.get(i).y;
			pointX = emptyCellsList.get(i).x;
			
			myCells2D[pointY][pointX] = new MineCell(this, pointX, pointY);          //Put a MineCell at the random empty cell we chose
			System.out.println("added MineCell to " + pointX + ", " + pointY + "  Iterator: " + y);
			emptyCellsList.remove(i);                                                  //Remove the cell we chose from the list of emptyCells so we don't have overlap
		}
		
		System.out.println("Filling empty cell list of size " + emptyCellsList.size());
		for(int i = 0; i < emptyCellsList.size(); i++)		//Fill each remaining empty cell with a SafeCell
		{
			pointX = emptyCellsList.get(i).x;
			pointY = emptyCellsList.get(i).y;
			myCells2D[pointY][pointX] = new SafeCell(this, pointX, pointY);
			System.out.println("added SafeCell to " + pointX + ", " + pointY + ". Iterator: " + i);
		}   
		
		System.out.println("Adding array objects to panel");
		for(int y = 0; y < mySizeY; y++)       //Add the components to this panel.
			for(int x = 0; x < mySizeX; x++)
			{
				add(myCells2D[y][x]);	
				System.out.println("Added array cell x: " + x + ", y: " + y + "  object: " + myCells2D[y][x].getClass().toString());
			}
		
		validate();
	}
	
	//Validates numberMines. If there are more mines than cells, numberMines = number of cells. Also must be >0
	private void setNumberMines(int setNumberMines)
	{
		if(setNumberMines <= 0) myNumberMines = 1;
		else if(setNumberMines > ((mySizeX * mySizeY)-9))
			myNumberMines = (mySizeX * mySizeY) - 9;
		else myNumberMines = setNumberMines;
	}
	
	private void setSizeX(int setSizeX) //Validate sizeX >= 3 && <= 100
	{
		if(setSizeX < 3) mySizeX = 3;
		else if(setSizeX > 100) mySizeX = 100;
		else mySizeX = setSizeX;
	}
	
	private void setSizeY(int setSizeY) //Validate sizeY >= 3 && <= 40
	{
		if(setSizeY < 3) mySizeY = 3;
		else if(setSizeY > 40) mySizeY = 40;
		else mySizeY = setSizeY;
	}
	
	protected FirstCell getCellAt(int x, int y)
	{
		return myCells2D[y][x];
	}
	
	public int getSizeX()
	{
		return mySizeX;
	}
	
	public int getSizeY()
	{
		return mySizeY;
	}
	
	public int getSafeCellsUnrevealed()
	{
		return mySafeCellsUnrevealed;
	}
	
	public void decrementSafeCellsUnrevealed()
	{
		mySafeCellsUnrevealed--;
	}
}
