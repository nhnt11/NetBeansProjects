//In this file we define our Person class. We define the name,
//age, etc etc.

package cashface;

/**
 *
 * @author Nihanth
 */
public class Person {

    //All data is stored in private variables. To retrieve them later,
    //we add methods to "get" them. Think about it, you would obviously
    //rather YOU told someone about urself than them finding out about u
    //behind your back.
    private String mName;
    private int mAge;
    private long mPhone;
    private String mOccupation;

    //This is called the "constructor" of the class. notice it is the only
    //method allowed which does not have a return type. This method creates
    //a new Person object and returns it (when new Person() is called)
    //We initialize the person by setting its properties.
    public Person(String name, int age, long phone, String occupation) {
        mName = name;
        mAge = age;
        mPhone = phone;
        mOccupation = occupation;
    }

    //Methods to get info of the person if we so wish later.
    String getName() {
        return mName;
    }

    int getAge() {
        return mAge;
    }

    long getPhone() {
        return mPhone;
    }

    String getOccupation() {
        return mOccupation;
    }

    //When you use the println() or print() functions of an output stream (like System.out)
    //objects which are not strings are converted using their "toString" method.
    //In java, all classes automatically extend the Object class which has an arbitrary
    //toString method. Here we "override" it by defining our own toString method
    //to suit our needs (in this case, print the person's details)
    @Override
    public String toString() {
        return "Name: " + mName +"\nAge: " + mAge + "\nPhone: " + mPhone + "\nOccupation: " + mOccupation;
    }

}
