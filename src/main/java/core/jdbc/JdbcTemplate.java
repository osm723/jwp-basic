package core.jdbc;

import core.Exception.DataAccessException;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

//    public void insert() throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = createQuery();
//            pstmt = con.prepareStatement(sql);
//            setValues(pstmt);
//
//            pstmt.executeUpdate();
//        } finally {
//            if (pstmt != null) {
//                pstmt.close();
//            }
//
//            if (con != null) {
//                con.close();
//            }
//        }
//    }
//
//    public void update() throws SQLException {
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            con = ConnectionManager.getConnection();
//            String sql = createQuery();
//            pstmt = con.prepareStatement(sql);
//            setValues(pstmt);
//
//            pstmt.executeUpdate();
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (pstmt != null) {
//                pstmt.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }
//    }

    public void insert(String sql, PreparedStatementSetter pss) throws DataAccessException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
        }
    }

    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
        }
    }

    @SuppressWarnings ("rawtypes")
    public List query(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws DataAccessException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);
            rs = pstmt.executeQuery();

            ArrayList<Object> userList = new ArrayList<>();
            if (rs.next()) {
                userList.add(rowMapper.mapRow(rs));
            }

            return userList;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public Object queryForObject(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        List result = query(sql, pss, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    //protected abstract String createQuery();
    //protected abstract void setValues(PreparedStatement pstmt) throws SQLException;

    //protected abstract Object mapRow(ResultSet rs) throws SQLException;

    public interface PreparedStatementSetter {
        void setValues(PreparedStatement pstmt) throws SQLException;
    }

    public interface RowMapper {
        Object mapRow(ResultSet rs) throws SQLException;
    }
}
