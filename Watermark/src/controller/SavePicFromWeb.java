package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import tool.DBUtil;
import tool.Mytool;

//保存网上的底图到数据库
/**
 * Servlet implementation class SavePicFromWeb
 */
@WebServlet("/SavePicFromWeb")
@MultipartConfig
public class SavePicFromWeb extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SavePicFromWeb() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int width = 0;// 获取图片宽度，单位px
        int height = 0;// 获取图片高度，单位px
        Connection conn = null; // connection to the database
        String message = null; // message will be sent back to client

        // 穿入的参数
        String id = Mytool.get8UUID();
        String uid = "bbbbbbbb";
        height = Integer.parseInt(request.getParameter("imageHeight"));
        width = Integer.parseInt(request.getParameter("imageWidth"));
        System.out.println(height + ":" + width);
        Part filePart = request.getPart("img_upload"); // Retrieves <input type="file" name="file">
             
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        System.out.println(fileName);
        

        /*
         * String suffixName = fileName.substring(fileName.lastIndexOf("."));//
         * 获取后缀名及文件类型 String fileSaveName = System.currentTimeMillis() + suffixName;
         */

        InputStream fileContent = filePart.getInputStream();
        

        try {
            // connects to the database
            conn = DBUtil.getConn();

            // constructs SQL statement
            String sql = "insert into picture (id,uid,filename,width,height,picture)values(?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, uid);
            statement.setString(3, fileName);
            statement.setInt(4, width);
            statement.setInt(5, height);
            if (fileContent != null) {
                statement.setBlob(6, fileContent);
            }
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("succeed");
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            System.out.println("fail");
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
