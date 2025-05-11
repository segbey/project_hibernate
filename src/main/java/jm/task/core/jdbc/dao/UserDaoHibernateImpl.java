package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT, " +
                "PRIMARY KEY (id))";

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error creating table: " + e.getMessage());
        }

    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error dropping table: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
            System.out.println("User with name – " + name + " added to the database.");
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error removing user: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error cleaning table: " + e.getMessage());
        }
    }

}
