package com.assessment.ewallet.repository;

import com.assessment.ewallet.entity.Transaction;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Repository
public class TransactionRepository {

    private final DataSource dataSource;

    public TransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int insert(Transaction transactionParam) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String insertTransactionUserSQL = "INSERT INTO transaction (invoice_number, transaction_type, description, total_amount, create_on) VALUES (?, ?, ?, ?, ?);";

        int result = 0;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(insertTransactionUserSQL);
            preparedStatement.setString(1, transactionParam.getInvoiceNumber());
            preparedStatement.setString(2, transactionParam.getTransactionType());
            preparedStatement.setString(3, transactionParam.getDescription());
            preparedStatement.setLong(4, transactionParam.getTotalAmount());
            preparedStatement.setObject(5, transactionParam.getCreatedOn());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }





    public List<Transaction> selectAllTransactionOffset(int offset, int size) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selectAllTransactionOffsetSQL = "SELECT invoice_number, transaction_type, description, total_amount, create_on FROM transaction ORDER BY create_on DESC LIMIT ?, ?;";

        List<Transaction> transactionList = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectAllTransactionOffsetSQL);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, size);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String invoiceNumber = rs.getString("invoice_number");
                String transactionType = rs.getString("transaction_type");
                String description = rs.getString("description");
                long totalAmount = rs.getLong("total_amount");
                LocalDateTime createOn = rs.getObject("create_on", LocalDateTime.class);
                transactionList.add(new Transaction(invoiceNumber, transactionType, description, totalAmount, createOn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionList;
    }

    public List<Transaction> selectAllTransaction() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selectAllTransactionSQL = "SELECT invoice_number, transaction_type, description, total_amount, create_on FROM transaction ORDER BY create_on DESC;";

        List<Transaction> transactionList = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectAllTransactionSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String invoiceNumber = rs.getString("invoice_number");
                String transactionType = rs.getString("transaction_type");
                String description = rs.getString("description");
                long totalAmount = rs.getLong("total_amount");
                LocalDateTime createOn = rs.getObject("create_on", LocalDateTime.class);
                transactionList.add(new Transaction(invoiceNumber, transactionType, description, totalAmount, createOn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionList;
    }






}
