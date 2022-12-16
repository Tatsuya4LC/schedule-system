package tatsuya4lc.model;

/**
 * This class is for Contacts objects.
 * This contains the attributes for Contacts objects.
 */
public class Contacts {
    private int id;
    private String name;

    /**
     * Constructor for Contacts objects.
     *
     * @param id variable for Contacts ID
     * @param name variable for Contacts Name
     */
    public Contacts(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

}
