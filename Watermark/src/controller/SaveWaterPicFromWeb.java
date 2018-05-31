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
//保存网上的水印图到数据库
/**
 * Servlet implementation class SaveWaterPicFromWeb
 */
@WebServlet("/SaveWaterPicFromWeb")
@MultipartConfig
public class SaveWaterPicFromWeb extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveWaterPicFromWeb() {
        super();
        // TODO Auto-generated constructor stub
    }

   
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = null;
        int width = 0;// 获取图片宽度，单位px
        int height = 0;// 获取图片高度，单位px
        Connection conn = null; // connection to the database
        

        //参数 
        String uid = "bbbbbbbb";
        height = Integer.parseInt(request.getParameter("WaterMarkHeight"));
        width = Integer.parseInt(request.getParameter("WaterMarkWidth"));
        String id = Mytool.get8UUID();
        System.out.println(width + ":" + height);
        Part filePart = request.getPart("WaterMark_upload"); 
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        
        /*String suffixName = fileName.substring(fileName.lastIndexOf("."));// 获取后缀名及文件类型
        String fileSaveName = System.currentTimeMillis() + suffixName; //防止重名 利用时间戳创造文件名*/
        InputStream fileContent = filePart.getInputStream();

        try {
            //填充参数
            conn = DBUtil.getConn();
            String sql = "insert into watermark (id,uid,filename,width,height,picture)values(?,?,?,?,?,?)";
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
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
