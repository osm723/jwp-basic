package core.jdbc;

import core.Exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void insert(String sql, Object... parameters) throws DataAccessException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //pss.setValues(pstmt);
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i +1, parameters[i]);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Object... parameters) throws DataAccessException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //pss.setValues(pstmt);
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i +1, parameters[i]);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @SuppressWarnings ("rawtypes")
    public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pss.setValues(pstmt);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<T> userList = new ArrayList<T>();
            if (rs.next()) {
                userList.add(rowMapper.mapRow(rs));
            }

            return userList;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public <T> T queryForObject(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws SQLException {
        List<T> result = query(sql, pss, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public interface PreparedStatementSetter {
        void setValues(PreparedStatement pstmt) throws SQLException;
    }

    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}
