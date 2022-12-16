package tatsuya4lc.model;

import java.time.LocalDateTime;

/**
 * This class is for Appointments objects.
 * This contains the attributes for Product objects.
 */
public class Appointments {
    private int appnt_ID, cus_ID, u_ID, con_ID;
    private String appnt_Title, appnt_Description, appnt_Location, appnt_Type, appnt_Contact;
    private LocalDateTime appnt_StartDateTime, appnt_EndDateTime;

    /**
     * Constructor for Appointment objects.
     *
     * @param appnt_ID variable for Appointment ID
     * @param cus_ID variable for Customer ID
     * @param u_ID variable for User ID
     * @param con_ID variable for Contact ID
     * @param appnt_Title variable for Appointment Title
     * @param appnt_Description variable for Appointment Description
     * @param appnt_Location variable for Appointment Location
     * @param appnt_Type variable for Appointment Type
     * @param appnt_Contact variable for Appointment Contact
     * @param appnt_StartDateTime variable for Appointment Start Date and Time
     * @param appnt_EndDateTime variable for Appointment End Date and Time
     */
    public Appointments(int appnt_ID, int cus_ID, int u_ID, int con_ID, String appnt_Title, String appnt_Description, String appnt_Location, String appnt_Type, String appnt_Contact, LocalDateTime appnt_StartDateTime, LocalDateTime appnt_EndDateTime) {
        this.appnt_ID = appnt_ID;
        this.cus_ID = cus_ID;
        this.u_ID = u_ID;
        this.con_ID = con_ID;
        this.appnt_Title = appnt_Title;
        this.appnt_Description = appnt_Description;
        this.appnt_Location = appnt_Location;
        this.appnt_Type = appnt_Type;
        this.appnt_Contact = appnt_Contact;
        this.appnt_StartDateTime = appnt_StartDateTime;
        this.appnt_EndDateTime = appnt_EndDateTime;
    }

    /**
     *
     * @return appnt_ID
     */
    public int getAppnt_ID() {
        return appnt_ID;
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
     * @return u_ID
     */
    public int getU_ID() {
        return u_ID;
    }

    /**
     *
     * @return con_ID
     */
    public int getCon_ID() {
        return con_ID;
    }

    /**
     *
     * @return appnt_Title
     */
    public String getAppnt_Title() {
        return appnt_Title;
    }

    /**
     *
     * @return appnt_Description
     */
    public String getAppnt_Description() {
        return appnt_Description;
    }

    /**
     *
     * @return appnt_Location
     */
    public String getAppnt_Location() {
        return appnt_Location;
    }

    /**
     *
     * @return appnt_Type
     */
    public String getAppnt_Type() {
        return appnt_Type;
    }

    /**
     *
     * @return appnt_Contact
     */
    public String getAppnt_Contact() {
        return appnt_Contact;
    }

    /**
     *
     * @return appnt_StartDateTime
     */
    public LocalDateTime getAppnt_StartDateTime() {
        return appnt_StartDateTime;
    }

    /**
     *
     * @return appnt_EndDateTime
     */
    public LocalDateTime getAppnt_EndDateTime() {
        return appnt_EndDateTime;
    }

}
