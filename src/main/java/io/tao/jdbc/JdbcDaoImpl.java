package io.tao.jdbc;

import java.sql.*;

public class JdbcDaoImpl {

    public Circle getCircle(int circleId) {

        Connection conn = null;

        try {

            // if there is a DataSource object being defined, you can use the data source to get connection
            // e.g. using spring to provide a data source bean and autowired to this component
//            conn = dataSource.getConnection();

//            these lines just make sure the driver is in the classpath
//            String driver = "org.postgresql.Driver";
//            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:5432/hibernatedb", "user", "password");

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM circle where id = ?");
            ps.setInt(1, circleId);

            Circle circle = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                circle = new Circle(circleId, rs.getString("name"));
            }
            rs.close();
            ps.close();

            return circle;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        throw new RuntimeException();
    }
}
