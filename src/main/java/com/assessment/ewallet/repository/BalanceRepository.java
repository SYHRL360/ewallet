package com.assessment.ewallet.repository;

import com.assessment.ewallet.config.DatabaseConfiguration;
import com.assessment.ewallet.entity.Balance;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class BalanceRepository {

    public int insert(Balance balanceParam) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertBalanceSQL = "INSERT INTO balance (email, balance) VALUES (?, ?);";

        int result = 0;
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(insertBalanceSQL);
            preparedStatement.setString(1, balanceParam.getEmail());
            preparedStatement.setLong(2, balanceParam.getBalance());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Balance selectByEmail(String emailParam) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selectByEmail = "SELECT id, email, balance FROM balance WHERE email = ?;";

        Balance balanceDto = null;
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectByEmail);
            preparedStatement.setString(1, emailParam);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //long id = rs.getLong("id");
                String email = rs.getString("email");
                long balance = rs.getLong("balance");

                balanceDto = new Balance(email, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balanceDto;
    }

    public int updateBalance(Balance balanceParam) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String updateBalanceSQL = "UPDATE balance SET balance = ? WHERE email = ?;";
        int result = 0;
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(updateBalanceSQL);
            preparedStatement.setLong(1, balanceParam.getBalance());
            preparedStatement.setString(2, balanceParam.getEmail());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
