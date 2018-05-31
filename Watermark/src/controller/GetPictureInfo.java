package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import tool.DBUtil;
//获取数据库中所有的底图的id和名称
/**
 * Servlet implementation class GetPictureInfo
 */
@WebServlet("/GetPictureInfo")
public class GetPictureInfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPictureInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @return
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String table = request.getParameter("table");
        try {
            conn = DBUtil.getConn();
            String sql = "SELECT id,filename FROM " + table;
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            Map<String, String> info = new LinkedHashMap<String, String>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String fileName = resultSet.getString("filename");
                info.put(id, fileName);
            }
            request.setAttribute("info", info);
            System.out.println(info);
            JSONObject jsonobject = JSONObject.fromObject(info);
            response.getWriter().print(jsonobject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConn(conn, preparedStatement, resultSet);
        }
    }
}
