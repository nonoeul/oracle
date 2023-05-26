package com.sist.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
import java.util.*;



@WebServlet("/foodList_Servlet")
public class foodListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTML- 출력할거다. 브라우저에 알림 
		response.setContentType("text/html;charest=UTF-8");
		// 클라이언트가 전송값을 받는다.
		String cno=request.getParameter("cno");
		// DAO연동
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.foodCatory_data(Integer.parseInt(cno));
		List<FoodVO> cvo=dao.foodCatory_data(Integer.parseInt(cno));
		
		// 화면에 출력
		PrintWriter out=response.getWriter();
		//HTML를 출력 => 오라클에서 받은 결과값 출력
		out.print("<html>");
		out.print("<head>");
		out.print("</head>");
		out.print("<body>");
		out.print("</body>");
		out.print("</html>");
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".rows{");
		out.println("margin:0xp auto;");
		out.println("width:800px");
		
		out.println("</style>");
		out.println("</head>");
		
		out.println("<body>");
		
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<div class=junmbotron>");
		
		out.println("<center>");
		
		out.println("<h3>");
		out.println("<h4>");
		
		out.println("</center>");
	
		out.println("</div>");
		out.println("</table class=table>");
		out.println("<tr>");
		out.println("<td>");
		
		for(FoodVO vo:list)
		{
			out.println("<table class=table>");
			out.println("<tr>");
			out.println("<td width=30% align=center rowspan=4>");
			out.println("<img src="+vo.getPoster()+"class=img.rounded style=\"width:240px;height:200px>");
			out.println("</td>");
			out.println("<td width=70%>");
			out.println(vo.getName()+"&nbsp;<span style=\"color:orange\">"+vo.getScore()+"</span>)");
			out.println("<td>");
			out.println("</tr>");
			out.println("<td width=70%>");
			out.println("<td>");
			out.println("</tr>");
			out.println("</table>");out.println("<td width=70%>");
			out.println(vo.getPhone());
			
			
		}
		
		out.println("</td>");
		out.println("</tr>");
		out.println("</table class=table>");
		
		
		out.println("</div>");
		out.println("</div>");
		
		out.println("</body>");
		out.println("</html>");
	}
}











