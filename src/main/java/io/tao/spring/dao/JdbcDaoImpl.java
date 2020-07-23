package io.tao.spring.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcDaoImpl extends JdbcDaoSupport {

    public int getCircleCount() {
        String sql = "SELECT COUNT(*) FROM CIRCLE";
        Integer count = this.getJdbcTemplate().queryForObject(sql, Integer.class);
        return count == null ? 0 : count;
    }

}
