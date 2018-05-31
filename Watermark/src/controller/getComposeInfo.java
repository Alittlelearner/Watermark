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
/*1  获取 底图pid 和水印 wid 还有描述  
 * 2 用这三个生成水印后的图片 
 * 3 将水印后的图片保存到数据库中 
 * 4 最后再从数据库中读取水印后的图片到网页上*/
/**
 * Servlet implementation class getComposeInfo合成 水印的信息
 */
@WebServlet("/getComposeInfo")
public class getComposeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getComposeInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String description=request.getParameter("description");  
        String pid=request.getParameter("pid");  
        String wid=request.getParameter("wid");  
        String mode=request.getParameter("mode");          
        String uid = "bbbbbbbb";
        System.out.println(pid+":"+wid+":"+mode);
        String id = WaterMarkIn.IN(uid,mode,pid, wid, description);
        request.setAttribute("wid", id);
        System.out.println("succeed and wid ="+ id);
       
        
        String table = "afterwatermark";       
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	}

}
