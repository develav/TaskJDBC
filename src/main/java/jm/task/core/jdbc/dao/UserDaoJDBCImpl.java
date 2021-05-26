package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() throws SQLException {
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(50), " +
                    " lastName VARCHAR (50), " +
                    " age TINYINT UNSIGNED not NULL," +
                    "PRIMARY KEY(id));";
            statement.execute(SQL);
            connection.commit();
        } catch(SQLException e){
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void dropUsersTable() throws SQLException{
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.execute("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch(SQLException e){
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException{
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate("INSERT INTO users(name, lastName, age) " +
                    "VALUES('" + name + "', '" + lastName + "', " + age + ");");
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException{
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users WHERE id =" + id);
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() {
        ResultSet users;
        List<User> userList = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            users = statement.executeQuery("SELECT * FROM users");
            while (users.next()){
                userList.add(new User(users.getString(2),
                        users.getString(3), users.getByte(4)));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException{
        try(Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users");
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
