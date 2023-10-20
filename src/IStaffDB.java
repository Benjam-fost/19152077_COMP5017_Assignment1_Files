package comp5017.cw1.pkg2023; //or whatever

/**
 *
 * @author D Lightfoot 2022-09
 * an objects of a class implementing this interface holds a
 * database of employee information
 
 * DO NOT CHANGE THIS INTERFACE
 * You must create a class that implements this interface
 *
 */
 
public interface IStaffDB {
    
    /**
     * Empties the database.
     * @pre true
     */
    public void clearDB();
    
    /**
     * Determines whether a employee's name exists as a key inside the database
     * @pre name is not null and not empty string
     * @param name the employee name (key) to locate
     * @return true iff the name exists as a key in the database
     */
    public boolean containsName(String name);
        
    /**
     * Returns a Employee object mapped to the supplied name.
     * @pre name not null and not empty string
     * @param name The employee name (key) to locate
     * @return the Employee object mapped to the key name if the name
         exists as key in the database, otherwise null
     */
    public Employee get(String name);
    
    /**
     * @pre true
     * @return number of employees in the database. 
     */
    public int size();
	
    /**
     * @pre true
     * @return true iff the database is empty
     */
    public boolean isEmpty();
    
    /**
     * Inserts an Employee object into the database, with the key of the supplied
     * employee's name.
     * Note: If the name already exists as a key, then then the original entry
     * is overwritten. 
     * This method must return the previous associated value 
     * if one exists, otherwise null
     * 
     * @pre employee not null and employee name not empty string
     */
    public Employee put(Employee employee);
    
    /**
     * Removes and returns a employee from the database, with the key
     * the supplied name.
     * @param name The name (key) to remove.
     * @pre name not null and name not empty string
     * @return the removed Employee object mapped to the name, or null if
     * the name does not exist.
     */
    public Employee remove(String name);
    
    /**
     * Prints the names and affiliations of all the employees in the database in 
     * alphabetic order.
     * @pre true
     */
    public void displayDB();
}