Schedule Management System
An application that manages and set up appointments, additionally generates schedule

Tristan Lozano
tlozan7@wgu.edu
version 1.0
12/07/2022

IntelliJ 2022.2 (Community Edition)
Java SE Development Kit 17.0.4
JavaFX-SDK-18.0.2

- run with Index.java to start the application
- Filtering Appointments:
    Dropdown List (ComboBox) Type and Contacts
        - shows a filtered view with the selected Type and/or Contact for the current week/month
        - this can be further filtered by using Dropdown List (ComboBox) Filter by Week and Filter by Month
            - shows a filtered view with the selected Type and/or Contact for the selected week/month

- Generating contacts schedule
        - select a contact, this shows the contact's schedule for the current week and month
        - able to show the week before, current week and the week after by using the Dropdown List (ComboBox) inside the
        Weekly Tab
        - able to select which month by using the Dropdown List (ComboBox) inside the Monthly Tab

- Total number of Customer Appointments are generated automatically in the Monthly tab

Additional report:
Able to check Contacts schedule in the Weekly tab by selecting Type to "All" selecting a Contact and choosing from
 "Last Week" or "Next Week" in the Weekly tab
    - Includes appointment ID, title, type and description, start date and time, end date and time, and customer ID
Able to check total number of Appointments in a month for each Contact by selecting Type to "All" selecting a Contact
 and choosing a Month in the Monthly tab
    - Includes appointment ID, title, type and description, start date and time, end date and time, and customer ID

Driver Reference: "com.mysql.cj.jdbc.Driver"
MySQL Connector driver version number: mysql-connector-j-8.0.31