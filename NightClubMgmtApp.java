// File: NightClubMgmtApp.java
import java.util.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

/**
* This class implements the state and behaviour of NightClubMgmtApp. It inherits from {@link JFrame} .
* This class is the main class it can search for clubber or create a new one.
*/

public class NightClubMgmtApp extends JFrame
{	
	//Night-Club Regular Customers Repository
	private ArrayList<ClubAbstractEntity> clubbers;
	
	/**
	* NightClubMgmtApp constructor initializes private variables and gui Component of the window.
	*/
	public NightClubMgmtApp()
	{
		setLayout(new BorderLayout(5,35));
		JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel insertContainer=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton searchButton=new JButton("Search");
		JButton insertButton=new JButton("Insert");
		String[] options={"Person","Student","Soldier"};
		JLabel chooseLbl=new JLabel("Insert a new clubber");
		JLabel searchLbl=new JLabel("Search a clubber ");
		JComboBox<String> cb=new JComboBox<>(options);
		searchContainer.add(searchLbl);
		searchContainer.add(searchButton);
		insertContainer.add(chooseLbl);
		insertContainer.add(cb);
		insertContainer.add(insertButton);
		searchButton.addActionListener(
			/**
			* an anonymous class ActionListener that handled the action event of search button.
			*/
			new ActionListener()
			{
				/**
				* This method actionPerformed is been overrided to handle the action event of the search button.
				* This method call for {@link #manipulateDB} method.
				* @param e The action event.
				*/
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String res=JOptionPane.showInputDialog(NightClubMgmtApp.this,"Please Enter The Clubber's Key");
					if(res==null)
						return;
					manipulateDB(res);
				}
			}
		);
		insertButton.addActionListener(
			/**
			* an anonymous class ActionListener that handled the action event of insert button.
			*/
			new ActionListener()
			{
				/**
				* This method actionPerformed is been overrided to handle the action event of the insert button.
				* This method create a new clubber with empty values and add it to the array list clubber and show it to the user
				* for insret the data of the clubber.
				* the user can create one of three clubber : {@link Person},{@link Student} or {@link Soldier}.
				* @param e The action event.
				*/
				@Override
				public void actionPerformed(ActionEvent e)
				{
					ClubAbstractEntity c;
					String res = cb.getSelectedItem().toString();
					if(res.equals("Person"))
						c = new Person();
					else if(res.equals("Soldier"))
						c = new Soldier();
					else 
						c = new Student();
					c.setVisible(true);
					clubbers.add(c);
				}
			}
		);
		add(searchContainer,BorderLayout.NORTH);
		add(insertContainer,BorderLayout.CENTER);
		setTitle("Clubber's App");
		clubbers = new ArrayList<>();
		loadClubbersDBFromFile();
		this.addWindowListener(new WindowAdapter()
			{
				@Override
                public void windowClosing(WindowEvent e)
                {
                    writeClubbersDBtoFile();
                }
            });
	}
	/**
	*This method manipulateDB check if there any clubber with the same key that user input.
	*If the method find a match he show the clubber.
	*this method call the mathod {@link ClubAbstractEntity#match}.
	*@param input user string input.
	*/
	private void manipulateDB(String input)
	{
		
			for(ClubAbstractEntity clubber : clubbers)
			{
				if(clubber.match(input))
				{
					clubber.setVisible(true); 
					return;
				}
			}
			JOptionPane.showMessageDialog(this,"Clubber with key "+input+" does not exist","Clubber's Not Found",JOptionPane.INFORMATION_MESSAGE);

	}// End of method manipulateDB
	/**
	*This method loadClubbersDBFromFile read the array list clubber from file calld BKCustomers.dat .
	*incase File Not Found Exception it will show the message to the user.
	*/
	private void loadClubbersDBFromFile()
	{
		//Read data from file, create the corresponding objects and put them
		try
		{    
	           FileInputStream readData = new FileInputStream("BKCustomers.dat");
	           ObjectInputStream readStream = new ObjectInputStream(readData);
	           clubbers = (ArrayList<ClubAbstractEntity>) readStream.readObject();
	           readStream.close();
		}
		catch(FileNotFoundException e)
		{
			//into clubbers ArrayList. For example:
			JOptionPane.showMessageDialog(this,"File BKCustomers.dat not found.\nCreating new data clubbers.","File Not Found",JOptionPane.ERROR_MESSAGE);
		}
		catch(ClassNotFoundException e)
        {
        	System.out.println(e);
		}
		catch(IOException e)
        {
        	System.out.println(e);
		}
	}
	
	/**
	* This method checkForEmptyClubbers check if the user tried to create new clubbers but exits from the app before he 
	* activetd the {@link ClubAbstractEntity#commit} method. 
	* In that case checkForEmptyClubbers will remove them from the arraylist clubbers.
	* This method is activetd by {@link #writeClubbersDBtoFile}.
	*/
	private void checkForEmptyClubbers()
	{
			//check if in arraylist clubbers have empty textfield of person/student/soldier\
			//it can be amount of empty_clubber if the user click insert multiplay time he will create a lots of windows
			//and the if he puts value in some of them and the other not we will have to check all of the arraylist
			ArrayList<ClubAbstractEntity> empty_clubber= new ArrayList<>();
			for(ClubAbstractEntity c:clubbers)
			{
				if(c.match(""))
					empty_clubber.add(c);
			}
			for(ClubAbstractEntity empty_c:empty_clubber)
				clubbers.remove(empty_c);
	}
	/**
	*This method writeClubbersDBtoFile write to file called BKCustomers.dat the array list clubbers
	*but before he write the file , he checked if there are any clubbers with empty values by calling the medthod {@link #checkForEmptyClubbers}.
	*/
	private void writeClubbersDBtoFile()
	{

		try	
		{
			checkForEmptyClubbers();
	        FileOutputStream writeData = new FileOutputStream("BKCustomers.dat");
	        ObjectOutputStream writeStream = new ObjectOutputStream(writeData);	
    		writeStream.writeObject(clubbers);
   			writeStream.flush();
   			writeStream.close();
   		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	/**
	* static main method simply defines and initializes a new NightClubMgmtApp object.
	* @param args The command line parameters
	*/
	public static void main(String[] args)
	{
		NightClubMgmtApp appliction = new NightClubMgmtApp();
		appliction.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
		appliction.setSize(350, 210);
		appliction.setResizable(false);
		appliction.setVisible(true);
	}
}//End of class NightClubMgmtApp