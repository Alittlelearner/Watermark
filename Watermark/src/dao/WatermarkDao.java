package dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import domain.Watermark;
import tool.DBUtil;
import tool.Mytool;

public class WatermarkDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    QueryRunner qRunner = new QueryRunner();

    public Watermark getWatermarkByID(String id) {
        Watermark watermark = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM watermark where id =?";
            /*
             * ps = conn.prepareStatement(sql); ps.setString(1, id);
             */
            watermark = qRunner.query(conn, sql, new BeanHandler<Watermark>(Watermark.class), id);
            return watermark;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return watermark;
    }

    public List<Watermark> getWatermarkList() {
        List<Watermark> list = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM watermark";
            list = qRunner.query(conn, sql, new BeanListHandler<>(Watermark.class));
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }

    public int AddWatermark(Watermark watermark) {
        try {
            conn = DBUtil.getConn();
            String sql = "insert into watermark (id,uid,filename,width,height,picture)values(?,?,?,?,?,?)";
            Object[] params = { watermark.getId(), watermark.getUid(), watermark.getFilename(), watermark.getWidth(),
                    watermark.getHeight(), watermark.getPicture() };
            int row = qRunner.update(conn, sql, params);
            return row;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }

   
}
