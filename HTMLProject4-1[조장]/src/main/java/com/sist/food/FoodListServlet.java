ppackage com.sist.food;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

@WebServlet("/FoodListServlet")
public class FoodListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HTML -> 브라우저에 알림
		response.setContentType("text/html;charset=UTF-8");
		//클라이언트가 전송 값을 받는다
		int cno = Integer.parseInt(request.getParameter("cno"));
		//dao 연동
		FoodDAO dao = FoodDAO.newInstance();
		List<FoodVO> list = dao.food_category_data(cno);
		CategoryVO cvo = dao.food_category_info(cno);
		
		//화면에 출력
		PrintWriter out = response.getWriter();
		
		//HTML 출력 => 오라클에서 받은 결과값을 출력
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("</body>");
		out.println("</html>");
	}

}
