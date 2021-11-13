//This file contains the main method which does the work.

package cashface;

//here we import the libraries that we need. analogous to #include
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Here we initialize our input reader and our list of people.
        ArrayList<Person> people = new ArrayList<Person>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //This is a "try-catch block". Everything in the "try" block is executed until an
        //error occurs. Then everything in the "catch" block is executed and the program
        //continues watever is after that. We need it here because we are doing I/O
        //operations. These type of operations are forbidden outside of a try-catch block.
        try {
            // this int stores which person it is for use later
            int which = 0;
            // do-while loop. each person gets one iteration of the loop. it's do-while
            // because we obvi want at least one person
            do {
                //increment which
                which++;
                //variables to store our info about the person
                String name;
                int age;
                long phone;
                String occupation;
                System.out.println("Enter name:");
                //br.readline gives you one line of input.
                name = br.readLine();
                System.out.println("Enter age:");
                //the input is returned as a String. Integer.parseInt() makes it an int.
                age = Integer.parseInt(br.readLine());
                System.out.println("Enter phone:");
                //here we make it a long cuz phone numbers can be long :P
                phone = Long.parseLong(br.readLine());
                System.out.println("Enter occupation:");
                occupation = br.readLine();
                //we add our new person to our list of ppl
                people.add(new Person(name, age, phone, occupation));
                System.out.println("Done with person " + which + ", type \"done\" to quit or anything else to enter another...");
                // keep looping as long as the user doesnt input "done"
            } while (!br.readLine().equals("done"));
            System.out.println();
            // loop through all the 'Person's in our people list and print their info
            for (Person p : people) {
                System.out.println(p);
            }
        } catch (Exception e) {
            //in case we get an error (called an "Exception"), print its details.
            e.printStackTrace();
        }
    }

}
