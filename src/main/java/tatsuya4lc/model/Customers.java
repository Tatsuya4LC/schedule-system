package tatsuya4lc.model;

/**
 * This class is for Customers objects.
 * This contains attributes for Customer objects.
 */
public class Customers {

    private int cus_ID, l_ID;
    private String cus_Name, cus_Address, cus_Location, cus_Country, cus_PostalCode, cus_Phone;

    /**
     * Constructor for Customer objects.
     *
     * @param cus_ID variable for Customer ID
     * @param cus_Name variable for Customer Name
     * @param cus_Address variable for Customer Address
     * @param cus_Location variable for Customer Location
     * @param cus_Country variable for Customer Country
     * @param cus_PostalCode  variable for Customer Postal Code
     * @param cus_Phone variable for Customer Phone
     * @param l_ID  variable for Location ID
     */
    public Customers(int cus_ID, String cus_Name, String cus_Address, String cus_Location, String cus_Country, String cus_PostalCode, String cus_Phone, int l_ID) {
        this.cus_ID = cus_ID;
        this.cus_Name = cus_Name;
        this.cus_Address = cus_Address;
        this.cus_Location = cus_Location;
        this.cus_Country = cus_Country;
        this.cus_PostalCode = cus_PostalCode;
        this.cus_Phone = cus_Phone;
        this.l_ID = l_ID;
    }

    /**
     *
     * @return cus_ID
     */
    public int getCus_ID() {
        return cus_ID;
    }

    /**
     *
     * @return cus_Name
     */
    public String getCus_Name() {
        return cus_Name;
    }

    /**
     *
     * @return cus_Address
     */
    public String getCus_Address() {
        return cus_Address;
    }

    /**
     *
     * @return cus_Location
     */
    public String getCus_Location() {
        return cus_Location;
    }

    /**
     *
     * @return cus_Country
     */
    public String getCus_Country() {
        return cus_Country;
    }

    /**
     *
     * @return cus_PostalCode
     */
    public String getCus_PostalCode() {
        return cus_PostalCode;
    }

    /**
     *
     * @return cus_Phone
     */
    public String getCus_Phone() {
        return cus_Phone;
    }

    /**
     *
     * @return l_ID
     */
    public int getL_ID() {
        return l_ID;
    }
}
