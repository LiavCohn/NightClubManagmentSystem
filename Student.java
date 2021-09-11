import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.regex.*;
/**
* This class implements the state and behaviour of Student. It inherits from {@link Person}.
*/
public class Student extends Person
{
	private String studentID;
	private JTextField textField_SID;
	private JLabel errorDot;
	/**
	 * Student no-argument constructor ,initializes id,name,surName,tel and studentID data to empty string.
	 */
	public Student()
	{
		this("","","","","");
	}
	/**
	* Student constructor initializes id,name,surName,tel,studentID data and gui component.
	* it's calling super constructor {@link Person}
	* it create panel whis label textfiels and anthoer label, after the super constructor.
	* the panel it's to be centered in the frame by calling the method {@link ClubAbstractEntity#addToCenter}
	* @param id string value id of the Student.
	* @param name string value name of the Student.
	* @param surName string value surName of the Student.
	* @param tel string value tel of the Student.
	* @param studentID string value studentID of the Student.
	*/
	public Student(String id,String name,String surName,String tel,String studentID)
	{
		super(id,name,surName,tel);
		this.studentID=studentID;
		initGuiComponent();
	}
	/**
	* This private method initialize the gui comoponent of Student.
	* it create panel whis label textfiels and anthoer label.
	* the panel it's to be centered in the frame by calling the method {@link ClubAbstractEntity#addToCenter}
	*/
	private void initGuiComponent()
	{
		errorDot=new JLabel(" ");
		errorDot.setForeground(Color.RED);
		textField_SID = new JTextField(30);
		textField_SID.setText(studentID);
		JLabel label=new JLabel("StudentID");
		JPanel p= new JPanel(new FlowLayout());
		p.add(label);
		p.add(textField_SID);
		p.add(errorDot);
		setSize(450,250);
		addToCenter(p);
	}
	
	
		/**
   * This method find a key match specified to Student.
   * check if the key inpute is equal to the id of the Student, by calling the super {@link Person#match}.
   * if super match method return false this method check if the key input is equals to the studentID.
   * @param key string key to check if it match.
   * @return boolean if it's found a match with id or studentID
   */
	@Override
	public boolean match(String key)
	{
		if(super.match(key))
			return true;
		// very extream case that the user tried to create new student and stop and now trying to search for a new objects.
		else if (!studentID.equals(""))
			return key.equals(studentID.substring(4));
		return false;
	}
	/**
    * This method checking validate Data specified to Student and been called by handler of ok button in {@link ClubAbstractEntity}.
   * validateData check if the textfields that the user inpute is valid by the rules of the patten of each of the four first textfield,
   * by calling the super {@link Person#validateData}.
   * if the super return false then this method will stop and return false, else it wil check if the fifth textfields of the studentID is valid by his pattern .
   * if it faild the regularization a error_dot label next to the specified textfield will show. 
   * if regularization pass the error_dot will disappear incase he have faild before.
   * The validation check start from the top and finish at the bottom of the frame.
   * @return boolean true if the validation check pass for everyone or false at the first one to failed .
   */
	@Override
		protected boolean validateData()
	{
		Pattern pattern= Pattern.compile("[A-Z]{3}/[1-9]\\d{4}");
		if(!super.validateData())
			{
				return false;
			}
			if(!pattern.matcher(textField_SID.getText()).matches())
				{
					errorDot.setText("*");
					return false;
				}
				errorDot.setText(" ");
		return true;
	}
		/**
   * This method saving the data to the instance variables specified to Student and been called by ok button in {@link ClubAbstractEntity}
   * commit is been preformed after {@link #validateData} if it return ture we can take the data that the user inpute in all
   * the textfields and save it in to the instance variables of the Student.
   * the four first textfields will be saved by the super {@link Person#commit}.
   */
	@Override
	protected void commit()
	{
			super.commit();
			this.studentID=textField_SID.getText();
	}
		/**
   * This method changing the data in the textfields back to original from the instance variables, 
   * specified to Student and been called by cancel button in {@link ClubAbstractEntity}.
   * rollBack is been preformed after clicking the cancel button.
   * incase the user tried to change the data of the and faild to validate hance thare are red dot next to the textfields the method we will make sure to clear them. 
   * the four first textfields will be changed by the super {@link Person#rollBack}.
   */
	@Override
	protected void rollBack()
	{
		super.rollBack();
		textField_SID.setText(studentID);
		errorDot.setText(" ");
	}
}