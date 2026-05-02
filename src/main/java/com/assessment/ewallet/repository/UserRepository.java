package com.assessment.ewallet.repository;

import com.assessment.ewallet.config.DatabaseConfiguration;
import com.assessment.ewallet.dto.ProfileDto;
import com.assessment.ewallet.entity.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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

    public Optional<User> findByEmail(String emailParam) {
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
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    public ProfileDto findProfileInfo(String emailParam) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String findByEmailSQL = "SELECT email, first_name, last_name, profile_image FROM user WHERE email = ?;";

        ProfileDto profileDto = null;
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
                String profileImage = rs.getString("profile_image");

                profileDto = new ProfileDto(email, firstName, lastName, profileImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profileDto;
    }


    public int updateProfileName(ProfileDto profileDto) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateProfileSql = "UPDATE user SET first_name = ?, last_name = ? WHERE email = ?;";
        int result = 0;
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(updateProfileSql);
            preparedStatement.setString(1, profileDto.getFirstName());
            preparedStatement.setString(2, profileDto.getLastName());
            preparedStatement.setString(3, profileDto.getEmail());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int updateProfileImage(ProfileDto profileDto) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateImageUrl = "UPDATE user SET profile_image = ? WHERE email = ?;";
        int result = 0;
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(updateImageUrl);
            preparedStatement.setString(1, profileDto.getProfileImage());
            preparedStatement.setString(2, profileDto.getEmail());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
