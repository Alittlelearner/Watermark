package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Part;

public class ImageUtil {
    public static final String TEMP_PATH = new File("").getAbsolutePath() + File.separator + "temp"; // 获取tem的的根目录

    // 读取本地图片获取输入流
    public static FileInputStream readImage(String path) throws IOException {
        return new FileInputStream(new File(path));
    }

    // 获取本地图片的名字
    public static String getFileName(String path) {
        return new File(path).getName();
    }

    // 根据输出流保存图片到本地
    public static String saveStream(InputStream in, String table, String filename) {
        File file = new File(TEMP_PATH + File.separator + table + File.separator + filename);// 传入文件流和完整路径
        if (!new File(TEMP_PATH + File.separator + table).exists()) {
            new File(TEMP_PATH + File.separator + table).mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 判断是不是存在某个字段
    public static boolean isExistColumn(ResultSet rs, String columnName) {
        try {
            if (rs.findColumn(columnName) > 0) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }

        return false;
    }

    // 根据id 表 从数据库中取出图片 并保存到 temp/table/所在目录
    // 读取数据库中图片
    public static Map<Object, Object> readDBImage(String id, String table) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConn();
            String sql = "select * from " + table + " where id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String fileName = rs.getString("filename");
                InputStream inputStream = rs.getBinaryStream("picture");
                String picpath = ImageUtil.saveStream(inputStream, table, fileName);
                System.out.println("IN IMageUtil" + picpath);
                int width = rs.getInt("width");
                int heigth = rs.getInt("height");
                Map<Object, Object> resultMap = new HashMap<>();
                resultMap.put("picpath", picpath);
                resultMap.put("width", width);
                resultMap.put("height", heigth);

                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, ps, rs);
        }
        return null;
    }

    // 保存水印图片
    public static String SaveAfterWarterMark(String uid, String pid, String wid, String message, int width, int height,
            String mode, String waterMarkedPath) {

        Connection conn = null; // connection to the database

        // 参数
        // String uid = "bbbbbbbb";
        String id = Mytool.get8UUID();
        System.out.println(width + ":##:" + height);
        InputStream fileContent;

        try {
            // 填充参数
            fileContent = ImageUtil.readImage(waterMarkedPath);
            String filename = Mytool.getFileNameByPath(waterMarkedPath);
            conn = DBUtil.getConn();
            String sql = "insert into afterwatermark (id,uid,pid,wid,width,height,mode,message,picture,filename)values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, uid);
            statement.setString(3, pid);
            statement.setString(4, wid);
            statement.setInt(5, width);
            statement.setInt(6, height);
            statement.setString(7, mode);
            statement.setString(8, message);
            statement.setString(10, filename);
            if (fileContent != null) {
                statement.setBlob(9, fileContent);
            }
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("succeed");
            }
            return id;
        } catch (SQLException | IOException ex) {
            message = "ERROR: " + ex.getMessage();
            System.out.println("fail");
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return id;

    }
}
