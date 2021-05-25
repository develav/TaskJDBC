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
    private Statement statement;
    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
        try{
            statement = connection.createStatement();
        }catch(SQLException e){

        }
    }

    public void createUsersTable()  {
        try{
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(50), " +
                    " lastName VARCHAR (50), " +
                    " age TINYINT UNSIGNED not NULL," +
                    "PRIMARY KEY(id));";
            statement.execute(SQL);
            System.out.println("Table successfully created...");
        } catch(SQLException e){
            System.out.println("Не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        try{
            statement.execute("DROP TABLE IF EXISTS users");
        } catch(SQLException e){
            System.out.println("Ошибка при возврате списка юзеров");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try{
            statement.executeUpdate("INSERT INTO users(name, lastName, age) " +
                    "VALUES('" + name + "', '" + lastName + "', " + age + ");");
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        }catch(SQLException e){
            System.out.println("Ошибка при добавлении юзера в таблицу");
        }
    }

    public void removeUserById(long id) {
        try{
            statement.executeUpdate("DELETE FROM users WHERE id =" + id + ")");
        }catch(SQLException e){
            System.out.println("Ошибка при удалении юзера");
        }
    }

    public List<User> getAllUsers() {
        ResultSet users;
        List<User> userList = new ArrayList<>();
        try{
            users = statement.executeQuery("SELECT * FROM users");
            while (users.next()){
                userList.add(new User(users.getString(2),
                        users.getString(3), users.getByte(4)));
            }
        } catch(SQLException e){
            System.out.println("Ошибка при возврате списка юзеров");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try{
            statement.executeUpdate("DELETE FROM users");
        }catch(SQLException e){
            System.out.println("Ошибка во время очистки таблицы");
        }
    }

}
