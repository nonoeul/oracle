package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;

/**
 * Servlet implementation class BoardDetailServlet
 */
@WebServlet("/BoardDetailServlet")
// WebServlet
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송 방식
		response.setContentType("text/html;charset=UTF-8");
		// 클라이언트가 보낸 값을 받는다.
		// => BoardDetailServlet?no=1
		// a.jsp?page=1
		// 웹은 int가 없다. 전부나 String이다.
		String no=request.getParameter("no");
		/*
		 * 	?no=1&page=10&name=mmm
		 * 	-----------------------
		 * ajax
		 * 
		 */
		// 오라클에서 값을 얻어온다
		BoardDAO dao=BoardDAO.newInstance();
		BoardVO vo=dao.boardDetailData(Integer.parseInt(no));
		
		//브라우저가 읽을 수 있게 출력
		PrintWriter out=response.getWriter();
		
		out.print("<html>");
		out.print("<head>");
		out.print("<link rel=stylesheet href=html/table.css>");
		out.print("</head>");
		out.print("<body>");
		out.print("<center>");
		out.print("<<h1>내용보기</h1>");
		out.print("<table width=700 class=table_content>");
		
		out.print("<tr>");
		out.print("<th width=20%>번호</th>");
		out.print("<td width=30% align=center>"+vo.getNo()+"</td>");
		out.print("<th width=20%>작성일</th>");
		out.print("<td width=30% align=center"+vo.getDbday()+"</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<th width=20%>이름</th>");
		out.print("<td width=30% align=center>"+vo.getName()+"</td>");
		out.print("<th width=20%>조회수</th>");
		out.print("<td width=30% align=center>"+vo.getHit()+"</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<th width=20%>제목</th>");
		out.print("<td colspan=3>"+vo.getSubject()+"</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<tr colspan=4 height=200 align=left valign=top>");
		out.print("<pre style=\"white-space:pre-wrap\">"+vo.getContent()+"</pre>");
		out.print("</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<td colsapn=4 align=right>");
		out.print("<a href=#>수정</a>&nbsp");
		out.print("<a href=#>삭제</a>&nbsp");
		out.print("<a href=BoardListServlet>목록</a>");
		out.print("</td>");
		out.print("</tr>");
		out.print("</table>");
		out.print("</center>");
		out.print("</body>");
		out.print("</html>");
				
	}



}












