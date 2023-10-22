package comp5017.cw1.pkg2023; // or whatever

/**
 *
 * @author D Lightfoot 2023-09
 * DO NOT CHANGE THIS INTERFACE
 * You must create a class that implements this interface
 *
 * an objects of a class implementing this interface holds information
 * about an employee
 */
 
public interface IEmployee {
 
    /**
     * @pre true
     * @return the name of the employee
     */
    public String getName();
    
    /**
     * @pre true
     * @return the employee's affiliation
     */
    public String getAffiliation();
    
    @Override
    /**
     * @return the employee's name and affiliation as string
     */
    public String toString();
}
