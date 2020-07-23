package io.tao.spring;

import io.tao.spring.model.Circle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
@Getter
@Setter
public class SimpleJdbcDaoImpl {

    // data source bean defined in spring.xml
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    // no more SimpleJdbcTemplate support

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public int getCircleCount() {
        String sql = "SELECT COUNT(*) FROM CIRCLE";
        // deprecated JdbcTemplate#queryForInt()
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count == null ? 0 : count;
    }

    public String getCircleName(int circleId) {
        String sql = "SELECT name FROM CIRCLE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ circleId }, String.class);
    }

    public Circle getCircleById(int circleId) {
        String sql = "SELECT * FROM CIRCLE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ circleId }, new CircleMapper());
    }

    public List<Circle> getAllCircles() {
        String sql = "SELECT * FROM CIRCLE";
        return jdbcTemplate.query(sql, new CircleMapper());
    }

//    public void insertCircle(Circle circle) {
//        String sql = "INSERT INTO CIRCLE (ID, NAME) VALUES(?, ?)";
//        jdbcTemplate.update(sql, circle.getId(), circle.getName());
//    }

    // use named parameter jdbc template
    public void insertCircle(final Circle circle) {
        String sql = "INSERT INTO CIRCLE (ID, NAME) VALUES(:id, :name)";
        SqlParameterSource namedParameters =
                new MapSqlParameterSource("id", circle.getId())
                        .addValue("name", circle.getName());
        namedParameterJdbcTemplate.update(sql, namedParameters);

        // another way using map
//        namedParameterJdbcTemplate.update(sql, new HashMap<String, Object>(){{
//            put("id", circle.getId());
//            put("name", circle.getName());
//        }});
    }

    // rare operation
    public void createTriangleTable() {
        String sql = "CREATE TABLE TRIANGLE (ID INTEGER, NAME VARCHAR(50))";
        jdbcTemplate.execute(sql);
    }

    private static final class CircleMapper implements RowMapper<Circle> {
        public Circle mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Circle(resultSet.getInt("id"), resultSet.getString("name"));
        }
    }

}
