package com.sist.servlet;

import java.io.IOException;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.Date;
/**
 * Servlet implementation class servlet
 */
import java.util.*;
import com.sist.dao.*;


@WebServlet("/BoardListServlet")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	int curpage=1;
	
	
    /**
     * Default constructor. 
     */
    public BoardListServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//JSP
		//1. 변환 => 전송(HTML, XML)
		//브라우저로 미리 알려준다 => response
		response.setContentType("text/html;charset=UTF-8");
		//XML => text/xml, JSON => text/plain
		PrintWriter out = response.getWriter();
		//사용자가 요청한 페이지를 받는다 => request
		String strPage=request.getParameter("page"); // 사용자가 보낸 준 값은 getParmeter로 받자
		if(strPage==null)
		strPage="1"; // default
		int curpage=Integer.parseInt(strPage);
		//사용자의 브라우저에서 읽어가는 위치를 설정 => OutputStream
		BoardDAO dao = BoardDAO.newInstance();
		List<BoardVO> list =dao.boardListData(curpage);
		int totalpage=dao.boardTotalPage();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>자유게시판</h1>");
		out.println("<table width=700 class=table_content>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<a href=boardInsertServlet>새글</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<table width=700 class=table_content>");
		out.println("<tr>");
		out.println("<th width=10%>번호</th>");
		out.println("<th width=45%>제목</th>");
		out.println("<th width=15%>이름</th>");
		out.println("<th width=20%>작성일</th>");
		out.println("<th width=10%>조회수</th>");
		out.println("</tr>");
		Date data=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String today=sdf.format(data); // 오늘날짜
				
		for(BoardVO vo:list) {
			out.println("<tr class=dataTr>");
			out.println("<td widtd=10% align=center>"+vo.getNo()+"</td>");
			out.println("<td widtd=45%>"+vo.getSubject());
			if(today.equals(vo.getDbday()))
					{
				out.print("&nbsp;<sup style=\"color:red\">new<<sup>");
					}
			out.print("</td>");
			out.println("<td widtd=15% align=center>"+vo.getName()+"</td>");
			out.println("<td widtd=20% align=center>"+vo.getDbday()+"</td>");
			out.println("<td widtd=10% align=center>"+vo.getHit()+"</td>");
			out.println("</tr>");
		}
		out.println("<tr>");
		
		out.println("<td colspan=5 align=center>");
		out.println("<a href=BoardListServlet?page="+(curpage>1?curpage-1:curpage)+">이전</a>");
		out.println(curpage+"page /"+totalpage+" pages");
		out.println("<a href=BoardListServlet?page="+(curpage<totalpage?curpage+1:curpage)+">다음</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}
