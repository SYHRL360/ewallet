package com.assessment.ewallet.repository;

import com.assessment.ewallet.config.DatabaseConfiguration;
import com.assessment.ewallet.entity.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {


    public int insert(User user) {

        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertUserSQL = "INSERT INTO user (email, first_name, last_name, password) VALUES (?, ?, ?, ?);";

        int result = 0;
        try  {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(insertUserSQL);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User findByEmail(String emailParam) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String findByEmailSQL = "SELECT email, first_name, last_name, password FROM user WHERE email = ?;";

        User user = null;
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(findByEmailSQL);
            preparedStatement.setString(1, emailParam);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String password = rs.getString("password");

                user = new User(email, firstName, lastName, password);
            }
        } catch (SQLException e) {

        }

    }
}
