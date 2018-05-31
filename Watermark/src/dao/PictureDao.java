package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import domain.Picture;
import domain.Watermark;
import tool.DBUtil;

public class PictureDao {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    QueryRunner qRunner =new QueryRunner();
    
    public Picture getPictureByID(String id) {
        Picture picture = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM picture where id =?";
            /*
             * ps = conn.prepareStatement(sql); ps.setString(1, id);
             */
            picture = qRunner.query(conn, sql, new BeanHandler<Picture>(Picture.class), id);
            return picture;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picture;
    }

    public List<Picture> getPictureList() {
        List<Picture> list = null;
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM picture";
            list = qRunner.query(conn, sql, new BeanListHandler<>(Picture.class));
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }

    public int AddPicture(Picture picture) {
        try {
            conn = DBUtil.getConn();
            String sql = "insert into watermark (id,uid,filename,width,height,picture)values(?,?,?,?,?,?)";
            Object[] params = { picture.getId(), picture.getUid(), picture.getFilename(), picture.getWidth(),
                    picture.getHeight(), picture.getPicture() };
            int row = qRunner.update(conn, sql, params);
            return row;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }
}
