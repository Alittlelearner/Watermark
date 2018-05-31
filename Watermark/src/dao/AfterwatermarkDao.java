package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import domain.Afterwatermark;
import domain.User;
import tool.DBUtil;

public class AfterwatermarkDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
  QueryRunner qRunner = new QueryRunner();
    
    public Afterwatermark getAfterwatermarByID(String id) {
        Afterwatermark afterwatermark = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM afterwatermark where id =?";
            /*
             * ps = conn.prepareStatement(sql); ps.setString(1, id);
             */
            afterwatermark = qRunner.query(conn, sql, new BeanHandler<Afterwatermark>(Afterwatermark.class), id);
            conn.close();
            return afterwatermark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return afterwatermark;
    }

    public List<Afterwatermark> getAfterwatermarkList() {
        List<Afterwatermark> list = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM afterwatermark";
            list = qRunner.query(conn, sql, new BeanListHandler<>(Afterwatermark.class));
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }

    public int AddAfterwatermark(Afterwatermark afterwatermark) {
        try {
            conn = DBUtil.getConn();
            String sql = "insert into afterwatermark (id, uid, pid, wid, width, height, mode, message, picture, filename)values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = { afterwatermark.getId(), afterwatermark.getUid(), afterwatermark.getPid(),afterwatermark.getWid(),
                    afterwatermark.getWidth(), afterwatermark.getHeight() , afterwatermark.getMode(), afterwatermark.getMessage(), afterwatermark.getPicture(), afterwatermark.getWid()};
            int row = qRunner.update(conn, sql, params);
            return row;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }
}
