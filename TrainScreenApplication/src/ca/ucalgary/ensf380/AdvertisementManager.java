package ca.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementManager {
    private List<Advertisement> advertisements;

    public AdvertisementManager() {
        try {
            advertisements = loadAdvertisementsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Advertisement> loadAdvertisementsFromDatabase() throws SQLException {
        List<Advertisement> adList = new ArrayList<>();
        String query = "SELECT * FROM advertisements WHERE ad_type=\"JPEG\" OR ad_type=\"BMP\"";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Advertisement ad = new Advertisement();
                ad.setId(resultSet.getInt("id"));
                ad.setAdName(resultSet.getString("ad_name"));
                ad.setAdType(resultSet.getString("ad_type"));
                ad.setAdFile(resultSet.getString("ad_file"));
                adList.add(ad);
            }
        }
        return adList;
    }

    public List<Advertisement> getAdvertisements() {
    	return advertisements;
    }

    public void displayNextAd() {
        // Logic to display the next advertisement
        // returns fileName of next ad
        for (Advertisement ad : advertisements) {
            System.out.println(ad.getAdName());
        }
    }
}
