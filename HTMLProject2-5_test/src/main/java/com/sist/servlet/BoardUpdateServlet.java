package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력폼 전송 => HTML (HTML를 브라우저 보낸다>
				response.setContentType("text/html;charset=UTF-8");
				// 메모리에 HTML에 저장 => 브라우저에서 읽어서 출력해라 
				String no=request.getParameter("no");
				
				BoardDAO dao=BoardDAO.newInstance();
				BoardVO vo=dao.boardDetailData(Integer.parseInt(no));
				
				PrintWriter out=response.getWriter();
				
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=stylesheet href=html/table.css>");
				out.println("</head>");
				out.println("<body>");
				out.println("<center>");
				out.println("<h1>글쓰기</h1>");
				out.println("<form method=post action=boardInsertServlet>");
				// 입력된 데이터를 한번에 => action에 등록된 클래스로 전송
				// 메소드 => method=post => doPost()
				out.println("<table class=table_content width=700>");
				out.println("<tr>");
				out.println("<th width=25%>이름</th>");
				out.println("<td width=85%><input type=text name=name size=15>"+vo.getName()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>제목</th>");
				out.println("<td width=85%><input type=text name=subject size=50 >"+vo.getSubject()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>내용</th>");
				out.println("<td width=85%><textarea rows=10 cols=50 name=content></textarea required>"+vo.getContent()+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>비밀번호</th>");
				out.println("<td width=85%><input type=password name=pwd size=10></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td colspan=2 align=center>");
				out.println("<input type=submit value=글쓰기>");
				out.println("<input type=button value=취소 onclick=\"Javascript:history.back()\">");
				out.println("</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</center>");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
				
				
			}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		// 사용자 전송한 값 받기
		String no=request.getParameter("no");
		String pwd=request.getParameter("pwd");
		
		// 디코딩 => 한글 있는 경우에만 사용한다 .
		// 숫자, 알파벳은 => 1, 2 byte 동시에 처리한다. 
		PrintWriter out=response.getWriter();
		// DAO연동 
		BoardDAO dao=BoardDAO.newInstance();
		boolean bCheck=dao.boardDelete(Integer.parseInt(no), pwd);
		// 수정 => 상세보기 
		if(bCheck==true)
		{	
			String name=request.getParameter("name");
			String subject=request.getParameter("subject");
			String content=request.getParameter("content");
			String pwd=request.getParameter("pwd");
			
			BoardVO vo=new BoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);
			
			//오라클로 INSERT 요청 
			BoardDAO dao=BoardDAO.newInstance();
			dao.boardInsert(vo);
			
			response.sendRedirect("BoardListServlet");
		}
		else
		{
			// 삭제창으로 이동 
			out.println("<script>");
			out.println("alert(\"비밀번호 틀립니다\");");
			out.print("history.back();");
			out.print("</script>");
		}
	}

}
