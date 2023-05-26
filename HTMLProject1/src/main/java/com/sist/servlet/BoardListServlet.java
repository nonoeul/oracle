package com.sist.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;
import com.sist.dao.BoardVO;

import java.util.*;
/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/servlet")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BoardListServlet() {
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html:charset=UTF-8");
		PrintWriter out = response.getWriter();
		//사용자의 브라우저에서 읽어가는 위치를 설정 => OutputStream
		BoardDAO dao = BoardDAO.newInstance();
		List<BoardVO> list =dao.boardListData(1);
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>자유게시판</h1>");
		out.println("<table width=700 border=1 bordercolor=black>");
		out.println("<tr>");
		out.println("<th width=10%>번호</th>");
		out.println("<th width=45%>제목</th>");
		out.println("<th width=15%>이름</th>");
		out.println("<th width=20%>작성일</th>");
		out.println("<th width=10%>조회수</th>");
		out.println("</tr>");
	}

}
