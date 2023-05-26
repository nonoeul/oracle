package com.sist.dao;
// 오라클만 연결 ==> SELECT, UPDATE, INSERT, DELETE 
import java.util.*;
import java.sql.*;

public class BoardDAO {
   // 연결 객체
   private Connection conn;
   // 송수신 객체 (오라클에 sql문장 전송, 실행 결과값 읽어오기)
   private PreparedStatement ps;
   // 모든 사용자가 1개의 dao만 사용할 수 있게 만들기 (싱글턴)
   private static BoardDAO dao;
   // 오라클 연결주소 => 상수형
   private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
   
   // 1. 드라이버 설치
   public BoardDAO()
   {
      try 
      {
         Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (Exception ex) {}
   }
   // 2. 싱글턴 => new 생성 => heap에서 계속 누적 => 오라클 연결되고 있다.
   // 메모리 누수, Connection 객체 생성갯수를 제한
   // 한개의 메모리만 사용이 가능하게 만든다.
   // 서버프로그램, 데이터베이스, 프로그램에서 주로 사용
   // *** Spring은 모든 객체가 싱글턴이다.

   public static BoardDAO newInstance()
   {
      if(dao==null) {
         dao=new BoardDAO();
      }
      return dao;
   }
   // 3. 오라클 연결
   public void getConnection()
   {
      try 
      {
         conn=DriverManager.getConnection(URL,"hr","happy");
      } catch (Exception ex) {}
   }
   // 4. 오라클 해제
   public void disConnection()
   {
      try {
         if(ps!=null) ps.close();
         if(conn!=null) conn.close();
      } catch (Exception ex) {}
   }
   // --------------------------> 필수 : 클래스화 (라이브러리)
   // 5. 기능
   // 5-1. 목록 출력 => 페이지 나누기 (인라인뷰) => SELECT       <***어려움***>
   // 1page - 10ea      BoardVO (게시물 1개) --> list에 모으기
   public List<BoardVO> boardListData(int page)
   {
      List<BoardVO> list= new ArrayList<BoardVO>();
      try 
      {
         // 1) 연결
         getConnection();
         // 2) sql문장 제작
         String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,num "
                  +"FROM (SELECT no,subject,name,regdate,hit,rownum AS num "
                  +"FROM (SELECT no,subject,name,regdate,hit "
                  +"FROM freeboard ORDER BY no DESC)) "
                  +"WHERE num BETWEEN ? AND ?";
         // 오라클의 rownum은 중간에 자를 수 없기 때문에, 인라인뷰를 2번 사용하여 rownum으로
         // 뒤집어진 테이블을 제작(넘버가 반대로 고정)한 뒤 그 넘버를 기준으로 between을 사용
         // 3) sql문장 전송
         ps=conn.prepareStatement(sql);
         // 4) 사용자가 요청한 데이터 첨부하기 (?에 값 채우기)
         // 4-1) ?에 값 채우기
         int rowSize=10;
         int start=(page*rowSize)-(rowSize-1);
         int end=page*rowSize;
         ps.setInt(1, start);
         ps.setInt(2, end);
         // int start=(page-1)*rowSize+1;
         // 5) 실행요청 후, 결과값 받기 
         ResultSet rs=ps.executeQuery();
         // 6) 받은 결과값을 list에 첨부 
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
      } catch (Exception ex) 
      {
         ex.printStackTrace();
      }
      finally 
      {
         // 해제
         disConnection();
      }
      return list;
   }
   // 5-1-1. 총페이지
   public int boardTotalPage()
   {
      int total=0;
      try {
         // 1) 연결
         getConnection(); // 반복 --> 메소드로 사용
         // 2) sql문장제작
         String sql="SELECT CEIL(COUNT(*)/10.0) FROM freeboard";
         ps=conn.prepareStatement(sql);
         // 실행 요청
         ResultSet rs=ps.executeQuery();
         rs.next();
         total=rs.getInt(1);
         rs.close();
      } catch (Exception ex) 
      {
         ex.printStackTrace();
      }
      finally 
      {
         disConnection();
      }
      return total;
   }
   // 5-2. 상세보기 => 조회수 증가 => UPDATE / 상세볼 게시물 읽기 => SELECT
   // 5-3. 게시물 등록 => INSERT
   // 5-4. 수정하기 => UPDATE (먼저 입력된 게시물 읽기, 실제 수정(비밀번호 검색))
   // 5-5. 삭제 (DELETE) => 비밀번호 검색                 <***어려움***>
   // 5-6. 찾기 (이름, 제목, 내용) => LIKE 
   
}