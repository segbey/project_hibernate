package jm.task.core.jdbc.util;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Util {

//    private static final String URL = "jdbc:mysql://localhost:3306/users?useSSL=false";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "1234";
//
//    public static Connection getConnection() {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            System.out.println("Connection established.");
//        } catch (ClassNotFoundException | SQLException e) {
//            System.err.println("Connection failed: " + e.getMessage());
//        }
//        return connection;
//    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/users?useSSL=false");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "1234");
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl.auto", "none");

                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
