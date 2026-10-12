HOME SERVICE BOOKING SYSTEM - FSD-2 MINI PROJECT
Stack: HTML, CSS, JavaScript, Java Servlet, JDBC, MySQL, Maven, Tomcat 10.1

SETUP
1. Install JDK 17 or 21, MySQL Server/Workbench, and Apache Tomcat 10.1.
2. Open the project folder in IntelliJ as a Maven project.
3. In MySQL Workbench, open database.sql and execute it.
4. In src/main/java/com/homeservice/DBConnection.java, replace YOUR_MYSQL_PASSWORD with your real MySQL root password.
5. Reload Maven dependencies and build with Maven > Lifecycle > package.
6. Deploy target/HomeServiceBooking.war to Tomcat 10.1.
7. Open http://localhost:8080/HomeServiceBooking/
8. Check saved rows in MySQL Workbench: USE home_service_db; SELECT * FROM bookings;

This uses Jakarta Servlet 6, so use Tomcat 10.1 (not Tomcat 9). Set project JDK to 17 or 21 for easiest compatibility. IntelliJ Community may not include built-in Tomcat integration; you can deploy the generated WAR by copying it into Tomcat's webapps folder.
