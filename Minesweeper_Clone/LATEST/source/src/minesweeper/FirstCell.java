package minesweeper;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

@Author(
		   author = "James Ostrander",
		   date = "5/12/2012",
		   lastModified = "8/7/2012"
		)

@SuppressWarnings("serial")
public class FirstCell extends JButton implements MouseListener
{
	protected MinePanel myParentGame;
	protected int myPosX;
	protected int myPosY;
	protected boolean myRevealed;
	private enum Flags { NONE, FLAGGED, QUESTION }
	private Flags myFlag;
	private Icon myFlaggedIcon;
	private boolean mouseover; //For use with MouseListener events. 
	
	public FirstCell(MinePanel setParentGame, int setPosX, int setPosY) 
	{
		super();
		
		myParentGame = setParentGame;
		myPosX = setPosX;
		myPosY = setPosY;
		myFlag = Flags.NONE;
		myFlaggedIcon = new ImageIcon(createImage("images/i_flag.gif", "Flagged"));
		myRevealed = false;
			
		setMinimumSize(new Dimension(12,12));
		setPreferredSize(new Dimension(24,24));
		setMargin(new java.awt.Insets(1, 1, 1, 1));
		this.setFont(new Font("Helvetica", Font.BOLD, 15));
		
		addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{		}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		mouseover = true;	
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		mouseover = false;		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		getModel().setArmed(true);
        getModel().setPressed(true);
        mouseover = true;		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		getModel().setArmed(false);
        getModel().setPressed(false);

        if (mouseover) 
        {
            if (SwingUtilities.isRightMouseButton(e)) //Right click logic
            {
                flag();
            }
            else if (SwingUtilities.isMiddleMouseButton(e)) //Middle click logic
            {
            	System.out.println(this.getClass().getName() + " x: " + myPosX + "  y: " + myPosY + "  Revealed? " + myRevealed);
            }
            else  //Left click logic
            {
            	System.out.println(this.myPosX + ", " + this.myPosY + "; Object: " + this.getClass().getName());
        		if(!myRevealed) reveal();
            }
        }
        
        mouseover = false;	
	}
	
	protected void flag()
	{
		if(this.myRevealed) return;
		
		switch(myFlag)
		{
		case NONE:	
			setIcon(myFlaggedIcon);
			myFlag = Flags.FLAGGED;
			break;
			
		case FLAGGED:
			setIcon(null);
			setText("?"); 
			myFlag = Flags.QUESTION;
			break;
			
		case QUESTION:
			unflag();
			break;
		}
	}
	
	protected void unflag()
	{
		setText("");
		setIcon(null);
		myFlag = Flags.NONE;
	}
	
	public void reveal()
	{
		myParentGame.deepPopulate(myPosX, myPosY);
		myParentGame.getCellAt(myPosX,  myPosY).reveal();
	}
	
	public boolean getIsRevealed()
	{
		return myRevealed;
	}
	
	protected static Image createImage(String path, String description) 
	{
        URL imageURL = Minesweeper.class.getResource(path);
        
        if (imageURL == null) 
        {
            System.err.println("Resource not found: " + path);
            return null;
        } 
        else 
        {
            return (new ImageIcon(imageURL, description)).getImage();
        }
	}

}
