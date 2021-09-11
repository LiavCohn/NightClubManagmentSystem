import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.regex.*;
/**
* This class implements the state and behaviour of Person. It inherits from {@link ClubAbstractEntity}.
* This class is the base for clubbers members {@link Student} and {@link Soldier}.
*/
public class Person extends ClubAbstractEntity 
{
	protected String id;
	protected String name;
	protected String surName;
	protected String tel;
	protected JTextField[] jTextFields;
	protected JLabel[] errorDot;
	/**
	 * Person no-argument constructor ,initializes id,name,surName,tel data to empty string.
	 */
	public Person()
	{
		this("","","","");
	}
	/**
	* Person constructor initializes id,name,surName,tel data and gui component.
	* @param id string value id of the person.
	* @param name string value name of the person.
	* @param surName string value surName of the person.
	* @param tel string value tel of the person.
	*/
	public Person(String id,String name,String surName,String tel)
	{
		this.id=id;
		this.name=name;
		this.surName=surName;
		this.tel=tel;
		initGuiComponent();
		setTitle(this.getClass().getSimpleName()+" Clubber's Data");
		
		
	}
	
   /**
   * This private method initialize the gui comoponent of Person.
   * it create 4 panels each panel have label textfiels and anthoer label.
   * each panel it's to be centered in the frame by calling the method {@link ClubAbstractEntity#addToCenter}
   */
	private void initGuiComponent()
	{
		String[] filedTextInfo = {id,name,surName,tel};
		String[] labelsNames={"ID","Name","Surname","Tel"};
		JLabel[] labels=new JLabel[labelsNames.length];
		errorDot=new JLabel[labelsNames.length];
		jTextFields= new JTextField[labelsNames.length];
		for (int i = 0; i < labelsNames.length; i++) 
		{
			labels[i] = new JLabel(labelsNames[i]);
			errorDot[i]=new JLabel(" ");
			errorDot[i].setForeground(Color.RED);
			jTextFields[i]= new JTextField(30);
			jTextFields[i].setText(filedTextInfo[i]);
			JPanel p= new JPanel(new FlowLayout());
			p.add(labels[i]);
			p.add(jTextFields[i]);
			p.add(errorDot[i]);
			addToCenter(p);
		}
		setSize(450,220);
	}
   /**
   * This method find a key match specified to Person.
   * check if the key inpute is equal to the id of the Person.
   * @param key string key to check if it match.
   * @return boolean if it's found a match with id.
   */
	@Override
	public boolean match(String key)
	{
		return key.equals(id);
	}
	/**
   * This method checking validate Data specified to Person and been called by handler of ok button in {@link ClubAbstractEntity}
   * validateData check if the textfields that the user inpute is valid by the rules of the patten of each textfield.
   * if one of the textfield faild to pass the validation check, the method will show  the error_dot label next to the specified
   * textfield that faild the regularization and stop the validation check.
   * if regularization pass the error_dot will disappear incase he have faild before.
   * The validation check start from the top and finish at the bottom of the frame.
   * @return boolean true if the validation check pass for everyone or false at the first one to failed .
   */
	@Override
	protected boolean validateData()
	{
		Pattern[] patterns= {
		Pattern.compile("\\d-\\d{7}\\|[1-9]"),
		Pattern.compile("[A-Z][a-z]+"),
		Pattern.compile("([A-Z][a-z]*['-]?)+"),
		Pattern.compile("\\+\\([1-9]\\d{0,2}\\)[1-9]\\d{0,2}-[1-9]\\d{6}")
		};
		for(int i=0; i<patterns.length;i++)
			{
			if(!patterns[i].matcher(jTextFields[i].getText()).matches())
				{
					errorDot[i].setText("*");
					return false;
				}
				errorDot[i].setText(" ");
			}
		return true;
	}
		/**
   * This method saving the data to the instance variables specified to Person and been called by ok button in {@link ClubAbstractEntity}
   * commit is been preformed after {@link #validateData} if it return ture we can take the data that the user inpute in all
   * the textfields and save it in to the instance variables of the Person.
   */
	@Override
	protected void commit()
	{
			id=jTextFields[0].getText();
			name=jTextFields[1].getText();
			surName=jTextFields[2].getText();
			tel=jTextFields[3].getText();
		
	}
	/**
   * This method changing the data in the textfields back to original from the instance variables, 
   * specified to Person and been called by cancel button in {@link ClubAbstractEntity}
   * rollBack is been preformed after clicking the cancel button.
   * incase the user tried to change the data of the and faild to validate hance thare are red dot next to the textfields the method we will make sure to clear them. 
   */
	@Override
	protected void rollBack()
	{
		String[] setToText = {id,name,surName,tel};
		for(int i=0;i<setToText.length;i++)
		{
			jTextFields[i].setText(setToText[i]);
			errorDot[i].setText(" ");
		}
	}
}