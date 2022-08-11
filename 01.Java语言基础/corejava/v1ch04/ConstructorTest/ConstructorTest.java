import java.util.*;

/**
 * This program demonstrates object construction.
 * @version 1.02 2018-04-10
 * @author Cay Horstmann
 */
public class ConstructorTest
{
   public static void main(String[] args)
   {
      // fill the staff array with three Employee objects
      Employee1[] staff = new Employee1[3];

      staff[0] = new Employee1("Harry", 40000);
      staff[1] = new Employee1(60000);
      staff[2] = new Employee1();

      // print out information about all Employee objects
      for (Employee1 e : staff)
         System.out.println("name=" + e.getName() + ",id=" + e.getId() + ",salary="
            + e.getSalary());
   }
}

class Employee1
{
   private static int nextId;

   private int id;
   private String name = ""; // instance field initialization
   private double salary;

   // static initialization block
   static
   {
      Random generator = new Random();
      // set nextId to a random number between 0 and 9999
      nextId = generator.nextInt(10000);
   }

   // object initialization block
   {
      id = nextId;
      nextId++;
   }

   // three overloaded constructors
   public Employee1(String n, double s)
   {
      name = n;
      salary = s;
   }

   public Employee1(double s)
   {
      // calls the Employee(String, double) constructor
      this("Employee #" + nextId, s);
   }

   // the default constructor
   public Employee1()
   {
      // name initialized to ""--see above
      // salary not explicitly set--initialized to 0
      // id initialized in initialization block
   }

   public String getName()
   {
      return name;
   }

   public double getSalary()
   {
      return salary;
   }

   public int getId()
   {
      return id;
   }
}
