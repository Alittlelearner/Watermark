package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import domain.User;
import domain.Watermark;
import tool.DBUtil;

public class UserDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    QueryRunner qRunner = new QueryRunner();
    
    public User getUserByID(String id) {
        User user = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM users where id =?";
            /*
             * ps = conn.prepareStatement(sql); ps.setString(1, id);
             */
            user = qRunner.query(conn, sql, new BeanHandler<User>(User.class), id);
            conn.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getUserList() {
        List<User> list = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM users";
            list = qRunner.query(conn, sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }

    public int AddUser(User user) {
        try {
            conn = DBUtil.getConn();
            String sql = "insert into watermark (id,username,password,email)values(?,?,?,?)";
            Object[] params = { user.getId(),user.getUsername(), user.getPassword(), user.getEmail() };
            int row = qRunner.update(conn, sql, params);
            return row;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }
}
