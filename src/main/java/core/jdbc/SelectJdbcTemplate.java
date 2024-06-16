package core.jdbc;

import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {

    @SuppressWarnings ("rawtypes")
    public List findAll(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);
            rs = pstmt.executeQuery();

            ArrayList<Object> userList = new ArrayList<>();
            if (rs.next()) {
                userList.add(mapRow(rs));
            }

            return userList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public Object findByUserId(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            Object user = null;
            if (rs.next()) {
                user = mapRow(rs);
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public Object queryForObject(String sql) throws SQLException {
        List result = findAll(sql);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    protected abstract void setValues(PreparedStatement pstm) throws SQLException;

    protected abstract Object mapRow(ResultSet rs) throws SQLException;
}
