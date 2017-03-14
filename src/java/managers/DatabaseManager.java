package managers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Sapan
 */
public class DatabaseManager {

    private String DB_USERNAME, DB_PASSWORD, DB_NAME;
    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            return new DatabaseManager();
        } else {
            return instance;
        }
    }

    private DatabaseManager() {
        init();//DB_USERNAME, DB_PASSWORD, DB_NAME are initialized;
    }

    private void init() {
        //Get Database Username, Password, Name from Web.xml
        try {
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            DB_USERNAME = (String) env.lookup("DB_USERNAME");
            DB_PASSWORD = (String) env.lookup("DB_PASSWORD");
        } catch (NamingException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    public Connection getConnection() {
//        Connection con = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return con;
//    }
}
