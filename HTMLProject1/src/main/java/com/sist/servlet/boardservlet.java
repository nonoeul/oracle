package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 	public class A
 	{
 		public static void main(String[] arg)
 		{
 		system.out.print
 
 */

/**
 * Servlet implementation class boardservlet
 */

import java.util.*;
import com.sist.dao.*;
@WebServlet("/boardservlet")
public class boardservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
     * @see HttpServlet#HttpServlet()
     */
    public boardservlet() {
        
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//JSP
	//1. 변환 => 전송 (HTML, XML, JSON)
	//브라우저 미리 알려준다 => respone
	response.setContentType("text/html;charset=UTF-8");
	//XML => text/xml , JSON => text/plain
	PrintWriter out=response.getWriter();
	// 사용자의 브라우저에서 읽어가는 위치를 설정 => OutPutStream
	BoardDAO dao= BoardDAO.newInstance();
	List<BoardVO> list =dao.boardListData(1);
	
	out.println("<html>");
	out.println("<body>");
	out.println("<center>");
	out.println("<h1>자유게시판(/h1>");
	out.println("<table width=700 board=1 bordercolor=black>");
	out.println("<tr>");
	out.println("<th width=10%>번호</th>");
	out.println("<th width=30%>제목</th>");
	out.println("<th width=10%>이름</th>");
	out.println("<th width=10%>작성일</th>");
	out.println("<th width=10%>조회수</th>");
	out.println("</tr>");
	for(BoardVO vo:list)
	{
		out.println("<tr>");
		out.println("<td widtd=10% align=center>"+vo.getNo()+"</td>");
		out.println("<td widtd=45%>"+vo.getSubject()+"</td>");
		out.println("<td widtd=15% align=center>"+vo.getName()+"</td>");
		out.println("<td widtd=20% align=center>"+vo.getDbday()+"</td>");
		out.println("<td widtd=10% align=center>"+vo.getHit()+"</td>");
		out.println("</tr>");
			
	}
	out.println("</table>");
	out.println("</center>");
	out.println("</body>");
	out.println("</html>");
}	
	
}













