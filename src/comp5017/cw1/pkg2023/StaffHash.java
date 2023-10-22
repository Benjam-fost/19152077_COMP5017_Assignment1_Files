package comp5017.cw1.pkg2023;

public class StaffHash implements IStaffDB{
    int tableSize = 10;
    int numEntries;
    Employee[] table ; // declares table
    boolean[] live;
    public StaffHash() {
        table = new Employee[tableSize]; // instantiates table
        live = new boolean[tableSize];
        System.out.println("Hash table");
        numEntries= 0;
        clearDB();
    }
    /**
     * Empties the database.
     * @pre true
     */
    @Override
    public void clearDB() {
        for(int i = 0; i != tableSize; i++) {
            table[i] = null;
            live[i] = true;
        }
    }
    /**
     * Determines whether a member's name exists as a key inside the database
     * @pre name is not null and not empty string or all blanks
     * @param name the member name (key) to locate
     * @return true iff the name exists as a key in the database
     */
    @Override
    public boolean containsName(String name){ return get(name) != null; }

    /**
     * Returns an Employee object mapped to the supplied name.
     * @pre name not null and not empty string or all blanks
     * @param name The Employee name (key) to locate
     * @return the Employee object mapped to the key name if the name
    exists as key in the database, otherwise null
     */
    @Override
    public Employee get(String name){
        // assert statements here
        Employee returned;
        int i = findPos(name);
        // table[i] == null || name.equals(table[i])
        if (table[i] == null) returned = null;
        else  returned = table[i];
        return returned;
    }

    /**
     * Returns the number of members in the database
     * @pre true
     * @return number of members in the database.
     */
    @Override
    public int size() {return numEntries;}


    /**
     * Determines if the database is empty or not.
     * @pre true
     * @return true iff the database is empty
     */
    @Override
    public boolean isEmpty() { return size() == 0; }

    int hash(String name) { // fairly bad; ok for now
        int val = name.charAt(0);
        return val % tableSize;
    }
    // return first empty bucket or bucket with this name
    int findPos(String name) {
        int i = hash(name);
        while (table[i] != null && ! name.equals(table[i].getName())) {
            // linear probing CHANGE LATER
            i = (i + 1) % tableSize;
        }
        //  table[i] == null || name.equals(table[i])
        return i;
    }
    /**
     * Inserts an Employee object into the database, with the key of the supplied
     * member's name.
     * Note: If the name already exists as a key, then the original entry
     * is overwritten.
     * This method must return the previous associated value
     * if one exists, otherwise null
     *
     * @pre member not null and member name not empty string or all blanks
     */
    @Override
    public Employee put(Employee member){
        String name = member.getName();
        Employee returned;
        int pos = findPos(name);
        //  table[pos] == null || name.equals(table[pos])
        if (table[pos] == null) { // put in new member
            // something about resizing here
            table[pos] = member;
            numEntries ++;
            returned = null;
        }
        else { // name matches-- override
            returned  = table[pos];
            table[pos] = member;
        }
        return returned; //fixed!
    }

    /**
     * Removes and returns a member from the database, with the key
     * the supplied name.
     * @param name The name (key) to remove.
     * @pre name not null and name not empty string or all blanks
     * @return the removed member object mapped to the name, or null if
     * the name does not exist.
     */
    @Override
    public Employee remove(String name){
        int i = findPos(name);
        //???
        live[i] = false;
        return null; // not really;  replace with sensible value; see comment above
    }

    /**
     * Prints the names and affiliations of all the members in the database in
     * alphabetic order.
     * @pre true
     */
    @Override
    public void displayDB(){
        for(int i = 0; i != tableSize; i++) {
            System.out.print(i + ": ");
            if (table[i] == null)
                System.out.println("****");
            else
                System.out.println(table[i]);
        }
        // print out all the entries -- show all, unsorted to start with
    }

}
