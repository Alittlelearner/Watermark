package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.DBUtil;
import tool.ImageUtil;

/**
 * Servlet implementation class getWaterFromAttacked
 */
@WebServlet("/getWaterFromAttacked")
public class getWaterFromAttacked extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getWaterFromAttacked() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String wayforattack = "afterwatermark";
        String pathforattact = ImageUtil.TEMP_PATH + File.separator + wayforattack + File.separator;
       
        String id = request.getParameter("pid");
        String uid;
        String mode;
        String pid;
        System.out.println("&&&&&&&&&&&&&&"+id);
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT * FROM afterwatermark where id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            String attackedPath = pathforattact + "attacked.png";
            while (rs.next()) {
                uid = rs.getString("uid");
                mode = rs.getString("mode");
                pid = rs.getString("pid");
                String wmarkPath = WaterMarkIn.OUTAttacked(uid, mode, id, pid, attackedPath);
                System.out.println(wmarkPath);
                response.setHeader("Content-Type","image/png");
                FileInputStream inputStream =ImageUtil.readImage(wmarkPath);
                int length = inputStream.available();
                byte  data[] = new byte[length];
                inputStream.read(data);
                OutputStream toClient = response.getOutputStream();
                toClient.write(data);
                toClient.flush();
                toClient.close();
                inputStream.close();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
