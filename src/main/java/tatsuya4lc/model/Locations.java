package tatsuya4lc.model;

/** This class is for Locations objects.
 * This class contains attributes for Locations objects.
 */
public class Locations {
    String locationName, countryName;
    int locationID;

    /**
     * Constructor for Locations objects.
     *
     * @param locationName variable for Location Name
     * @param countryName variable for Country Name
     * @param locationID variable for Location ID
     */
    public Locations(String locationName, String countryName, int locationID) {
        this.locationName = locationName;
        this.countryName = countryName;
        this.locationID = locationID;
    }

    /**
     *
     * @return locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     *
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @return locationID
     */
    public int getLocationID() {
        return locationID;
    }

}
