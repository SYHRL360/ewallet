package com.assessment.ewallet.repository;

import com.assessment.ewallet.config.DatabaseConfiguration;
import com.assessment.ewallet.entity.Transaction;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@Repository
public class TransactionRepository {

    public int insert(Transaction transactionParam) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertTransactionUserSQL = "INSERT INTO transaction (invoice_number, transaction_type, description, total_amount, create_on) VALUES (?, ?, ?, ?, ?);";

        int result = 0;
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(insertTransactionUserSQL);
            preparedStatement.setString(1, transactionParam.getInvoiceNumber());
            preparedStatement.setString(2, transactionParam.getTransactionType());
            preparedStatement.setString(3, transactionParam.getDescription());
            preparedStatement.setLong(4, transactionParam.getTotalAmount());
            preparedStatement.setDate(5, transactionParam.getCreatedOn());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Transaction> selectAllTransactionOffset(int offset, int size) {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selectAllTransactionSQL = "SELECT invoice_number, transaction_type, description, total_amount, create_on FROM transaction LIMIT ?, ? ORDER BY create_on DESC;";

        List<Transaction> transactionList = new ArrayList<>();
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectAllTransactionSQL);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, size);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String invoiceNumber = rs.getString("invoice_number");
                String transactionType = rs.getString("transaction_type");
                String description = rs.getString("description");
                long totalAmount = rs.getLong("total_amount");
                Date createOn = rs.getDate("create_on");
                transactionList.add(new Transaction(invoiceNumber, transactionType, description, totalAmount, createOn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionList;
    }







}
