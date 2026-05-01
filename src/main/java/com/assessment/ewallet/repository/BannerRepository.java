package com.assessment.ewallet.repository;

import com.assessment.ewallet.config.DatabaseConfiguration;
import com.assessment.ewallet.entity.Banner;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BannerRepository {

    public List<Banner> selectAllBanner() {
        DataSource dataSource = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String selectAllBannerSQL = "SELECT banner_name, banner_image, description FROM banner;";

        List<Banner> banners = new ArrayList<>();
        try {
            dataSource = DatabaseConfiguration.source();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(selectAllBannerSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String bannerName = rs.getString("banner_name");
                String bannerImage = rs.getString("banner_image");
                String description = rs.getString("description");
                banners.add(new Banner(bannerName, bannerImage, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return banners;
    }
}
