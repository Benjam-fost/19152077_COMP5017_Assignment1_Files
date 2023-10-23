package comp5017.cw1.pkg2023;
import java.util.ArrayList;
import java.util.Objects;

public class StaffHash implements IStaffDB{
    int tableSize = 10;
    int numEntries;
    Employee[] table ; // declares table
    boolean[] live;
    boolean resizing = false;
    public StaffHash() {
        table = new Employee[tableSize];
        live = new boolean[tableSize];
        System.out.println("Hash table");
        numEntries= 0;
        clearDB();
    }
    /**
     * Empties the database and sets all elements of live to true.
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
     * @return true if the name exists as a key in the database
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
        assert name != null && !name.isBlank();
        Employee returned = null;
        int i = findPos(name);
        // table[i] == null || name.equals(table[i])
        if (live[i] && table[i] != null) {
            returned = table[i];
        }

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
     * @return true if the database is empty
     */
    @Override
    public boolean isEmpty() { return size() == 0; }
    private double getLoadFactor() {
        return (double)numEntries/(double)tableSize;
    }
    private int hash(String name) { // using weighting
        assert name != null && !name.isBlank();
        int val = 0, hash = 0;
        for (int i = 0; i != name.length(); i++) {
            hash = (i * val + (int)name.charAt(i));
            val = hash % tableSize;

        }
        assert val >= 0 && val < tableSize;
        System.out.println("Hash value generated from " + name + ": " + hash);
        return val;
    }
    // return first empty bucket or bucket with this name

    /**
     * Calls hash() to hash a key
     * Finds an index that refers to null, is not live or is identical.
     * If not then performs quadratic probing.
     * @param name The key used for hashing.
     * @return the index, calculated from the hash value % tableSize, after probing if needed.
     */
    private int findPos(String name) {
        assert name != null && !name.isBlank();
        ArrayList<Integer> buckets = new ArrayList<>(); // for showing buckets visited in logging
        int index = hash(name), i = 1;
        buckets.add(index);
        while (!((table[index] == null || !live[index]) || name.equals(table[index].getName()))) {
            index = (index + (int)Math.pow(i, 2)) % tableSize;
            buckets.add(index);
            i++;
        }
        assert table[index] == null || name.equals(table[index].getName());
        System.out.println("Sequence of buckets visited:\n" + buckets);
        return index;
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
        assert member != null;
        String name = member.getName();
        Employee returned;
        int pos = findPos(name);
        //  table[pos] == null || name.equals(table[pos])
        if (table[pos] != null || resizing) { // overwrites
            returned = table[pos];
            table[pos] = member;
        }
        else { // Inserts new member at null index
            returned = null;
            table[pos] = member;
            numEntries ++;
        }
        System.out.println("Size: " + size() + "\nLoad Factor: " + getLoadFactor());
        assert Objects.equals(member.getName(), table[pos].getName());
        System.out.println("Employee added with\nName: " + member.getName() + "\nAffiliation: " + member.getAffiliation() + "\n");
        if (!resizing) {
            resize();
        }
        return returned; //fixed!
    }

    /**
     * Resizes table when the load factor exceeds 0.5, as specified in appendix.
     * Outputs messages for logging purposes and to makes code clearer to read.
     */
    private void resize() {
        if (getLoadFactor() > 0.5) {
            resizing = true;
            System.out.println("\n***Load factor > 0.5, Table is being resized***\n");
            int tempSize = tableSize;
            tableSize *= 2;
            assert tableSize != tempSize : "Was not deep copied";
            Employee[] tempTable = new Employee[tempSize];
            for (int i = 0; i < tempSize; i++) {
                Employee item = table[i];
                tempTable[i] = item;
            }
            table = new Employee[tableSize];
            live = new boolean[tableSize];
            clearDB();
            System.out.println("\n***Hashing over Employees START***\n{");
            for (int i = 0; i < tempSize; i++) {
                Employee item = tempTable[i];
                if (item != null) {
                    put(item);
                }
            }
            resizing = false;
            System.out.println("}\n***Table has been resized to " + tableSize + "***\n");
            resize();
        }
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
    public Employee remove(String name) {
        assert name != null && !name.isBlank();
        Employee removed;
        int i = findPos(name);
        live[i] = false;
        removed = table[i];
        numEntries--;
        System.out.println("\n***" + removed.getName() + " deleted with***\n"+ removed);
        System.out.println("Size: " + size() + "\nLoad Factor: " + getLoadFactor());
        return removed;
    }

    /**
     * Prints the names and affiliations of all the members in the database.
     * @pre true
     */
    @Override
    public void displayDB(){
        int count = 1;
        for (Employee i : table) {
            System.out.println(count + "\n^---------");
            System.out.println(i + "\n----------\n");
            count++;
        }
    }

}
