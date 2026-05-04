package com.assessment.ewallet.repository;

import com.assessment.ewallet.config.DatabaseConfiguration;
import com.assessment.ewallet.entity.PPOBService;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PPOBServiceRepository {


    private final DataSource dataSource;

    public PPOBServiceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PPOBService selectByServiceCode(String serviceCodeParam) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selectByServiceCodeSQL = "SELECT service_code, service_name, service_icon, service_tariff FROM ppob_service WHERE service_code = ?;";

        PPOBService ppobService = null;
        try {

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectByServiceCodeSQL);
            preparedStatement.setString(1, serviceCodeParam);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String serviceCode = rs.getString("service_code");
                String serviceName = rs.getString("service_name");
                String serviceIcon = rs.getString("service_icon");
                long serviceTariff = rs.getLong("service_tariff");
                ppobService = new PPOBService(serviceCode, serviceName, serviceIcon, serviceTariff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ppobService;
    }

    public List<PPOBService> selectAllPPOBService() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selectAllPPOBServiceSQL = "SELECT service_code, service_name, service_icon, service_tariff FROM ppob_service;";

        List<PPOBService> ppobServicesList = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectAllPPOBServiceSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String serviceCode = rs.getString("service_code");
                String serviceName = rs.getString("service_name");
                String serviceIcon = rs.getString("service_icon");
                long serviceTariff = rs.getLong("service_tariff");
                ppobServicesList.add(new PPOBService(serviceCode, serviceName, serviceIcon, serviceTariff));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ppobServicesList;
    }
}
