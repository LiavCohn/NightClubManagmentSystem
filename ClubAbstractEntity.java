import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;  // Import the File class

/**
* This class implements the state and behaviour of ClubAbstractEntity. It inherits from {@link JFrame} and implements Serializable.
* This class is abstract for all clubbers members {@link Person},{@link Student} and {@link Soldier}.
*/
public abstract class ClubAbstractEntity extends JFrame implements Serializable
{
	private final JButton okButton;
	private final JButton cancelButton;// need to disable if the non parmeters constractor
	private ButtonsHandler handler;
	private JPanel centerPanel;
	//constructor Parameterless 
	/**
	* ClubAbstractEntity constructor initializes private variables and gui Component of the window.
	*/
	public ClubAbstractEntity()
	{
		centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		cancelButton.setEnabled(false);
		handler = new ButtonsHandler();
		okButton.addActionListener(handler);
		cancelButton.addActionListener(handler);
		setResizable(false);//this frame not resizable.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//cancel close button but still show.
		JPanel buttonsPanel= new JPanel();//making new panel so the both button can be in south layout.
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		add(buttonsPanel,BorderLayout.SOUTH);//add the buttons to south of this frame.s
		add(centerPanel);
	}
	/**
	* Method addToCenter get Component from the children and add it to the center of the frame.
	*@param guiComponent gui component that will be added to the center of the frame.
	*/
	protected void addToCenter(Component guiComponent)
	{
		centerPanel.add(guiComponent);
	}
	  /**
     * abstract match method to be implements by his childrens polymorphically.
     * @param key String to check if it's match to the childrens unique id.
     * @return boolean true if found a match else false.
     */
	public abstract boolean match(String key);
	  /**
     * abstract match method to be implements by his childrens polymorphically.
     * this method check if the user insert valid values to the textfields.
     * @return boolean true if it valid else false.
     */
	protected abstract boolean validateData();
	/**
     * abstract match method to be implements by his childrens polymorphically.
     * this method save all the textfields to instance varible of the children.
     */
	protected abstract void commit();
	/**
     * abstract match method to be implements by his childrens polymorphically.
     * this method copy to the textfields from the instance varible of the children.
     */	
	protected abstract void rollBack();
	
 	
	// inner class for handler buttons--------------------------------------
	/**
	*This is an inner class that is in charge of all the buttons event.
	*it implements the interface ActioListener and Serializable.
	*/
	private class ButtonsHandler implements ActionListener,Serializable
	{
		/**
		*This method handler the action event of the Okbutton and the Cancel button.
		*This method can call 3 mathods {@link #validateData},{@link #commit} and {@link #rollBack}.
		*@param e the action event to be handled.
		*/
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==okButton)
				{
					if(validateData() )
						{
							commit();
							cancelButton.setEnabled(true);
							setVisible(false);
						}
					return;
				}
			else if (e.getSource()==cancelButton)
			{
				rollBack();
				setVisible(false);
			}
		}
	}
}


