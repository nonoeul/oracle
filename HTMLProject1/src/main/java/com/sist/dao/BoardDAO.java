package com.sist.dao;
// 화면 출력하는 부분이다.
// 화면을 자바로만 출력이 가능하다 
// 오라클만 연결하는 부분, 다른 기능은 없다 
// 오라클 연결 = SELECT, UPDATE, INSERT, DELETE 기능만 수행 
	import java.util.*;

	import java.sql.*;
public class BoardDAO {
	// 연결 객체
	private Connection conn;
	// 송수신 객체 (오라클 (SQL문장 전송), 실행 결과값을 읽어온다.)
	private PreparedStatement ps;
	// 모든 사용자가 1개의 DAO만 사용할 수 있게 만든다. = 싱글턴
	private static BoardDAO dao;
	// 오라크 연결주소 => 상수형으로 
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";

	//1. 드라이브 등록
	public BoardDAO()
	{
		try
		{
			Class.forName("oralce.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	//2, 싱글턴 => new생성 => heap에서 계속 누적 => 오라클 연결되고 있다. 
	public static BoardDAO newInstance()
	{
		if(dao==null)
			dao=new BoardDAO();
		return dao;
	}
	//3. 오라클 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			// conn hr/happy => 오라클 연결
		}catch(Exception ex) {}
	}
	//4. 오라클 해제
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	/////////////// 오라클 해제까지는 필수과정 ==> 나중에 클래스화 (라이브러리)
	//5. 기능
	//5-1. 목록출력 => 페이지 나누기 (인라인뷰) ; SELECT
		// => 1page => 10개
		// => BoardVO(게시물1개)
	public List<BoardVO> boardListData(int page)
	{
		List<BoardVO> list=new ArrayList<BoardVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL문장 생성
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,num "
					+"FROM (SELECT no,subject,name,regdate,hit,rownum as num "
					+"FROM (SELECT no,subject,name,regdate,hit "
					+"FROM freeboard ORDER BY no DESC)) "
					+"WHERE num BETWEEN ? AND ?";
						// rownu,은 중간에서 데이터를 추출 할 수 없다.
						// SELECT 오라클에 있는 table의 값을 추출해온다.
			
			//3. SQL문장 전송 
			ps=conn.prepareStatement(sql);
			//4. 사용자 요청한 데이터를 첨부
			
			//4.1 ?에 값을 채운다
			int rowSize=10;
			int start=(page*rowSize)*(rowSize-1); // (page-1)*rowSize
			/*
			 * 1page 1페이지부터	== 10
			 * 2page 11페이지부터	== 20
			 * 3page 21페이지부터
			 */
			int end=(page*rowSize);
			ps.setInt(1, start);
			ps.setInt(2, end);
			//5. 실행을 요청하고 결과값을 받는다
			ResultSet rs=ps.executeQuery();
			//6. 받은 결과값을 list에 첨부해준다.
			while(rs.next())
			{
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace(); // 에러확인
		}
		finally
		{
			//해제
			disConnection();
		}
		return list;
	}
	//5-1-1 총페이지 구하기
		public int boardTotalPage()
		{
			int total=0;
			try
			{
				//연결
				getConnection(); // 반복 => 메소드
				//SQL문장 제작
				String sql="SELECT CEIL(COUNT(*)*10.0) FROM freeboard";
				// 43/10.0 => 4.3 => 5 // 총 페이지 구하기
				// 내장 함수 => 용도 
				ps=conn.prepareStatement(sql);
				// 실행요청
				ResultSet rs=ps.executeQuery();
				rs.next(); // 커서는 밑으로 내려가기 때문에 무조건 넣어야한다.
				// 값이 출력되어 있는 위치에 커서를 이동하는 것이다. 
				total=rs.getInt(1);
				rs.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				//해제
				disConnection();
			}
			return total;
		}
		
	//5-2. 상세보기 => 조회수 증가(UPDATE), 상세 볼 게시물 읽기(SELECT)
	//5-3. 게시물 등록 => INSERT 
	//5-4. 수정 (UPDATE) => 먼저 입력된 게시물 읽기 , 실제 수정 (비밀번호 검색)
	//5-5. 삭제 (DELETE) => 비밀번호검색
	//5-6. 찾기 (이름, 제목, 내용) = LIKE
}
















