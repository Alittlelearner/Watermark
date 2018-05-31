package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.DBUtil;
// 页面上显示从数据库获取的图片
/**
 * Servlet implementation class AjaxShowDatabasePic
 */
@WebServlet("/AjaxShowDatabasePic")
public class AjaxShowDatabasePic extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxShowDatabasePic() {
        super();
        // TODO Auto-generated constructor stub
    }
// use /Upload/AjaxShowDatabasePic?id=4
//     /Uplaod/AjaxShowDatabasePic?id=4
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = request.getParameter("id");
        String table = request.getParameter("table");       
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM  "  + table  + " where id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {                               
                Blob photo = rs.getBlob("picture");
                long size = photo.length();
                byte[] bs = photo.getBytes(1, (int) size);
                response.setContentType("image/png");
                OutputStream outs = response.getOutputStream();
                outs.write(bs);
                outs.flush();
                rs.last();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, ps, rs);
            
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
