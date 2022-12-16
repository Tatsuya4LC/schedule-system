package tatsuya4lc.model;

/**
 * This class is for Users objects.
 * This class contains attributes inherited from Contacts and a Password attribute.
 */
public class Users extends Contacts{
    private String password;

    /**
     * Constructor for Users objects.
     * contains super() constructor;
     *
     * @param id variable for User ID
     * @param name variable for User Name
     * @param password variable for User Password
     */
    public Users(int id, String name, String password) {
        super(id, name);
        this.password = password;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

}
