package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

public class ReplyDAO {

	// 댓글 작성
	public int insert(ReplyDTO dto) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into reply (rno,bno,writer,content) "
				+ " values (seq_reply.nextval,?,?,?)";
		
		int rno = -1;
		
		try {
			conn = DBUtil.getInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getBno());
			pstmt.setString(2, dto.getWriter());
			pstmt.setString(3, dto.getContent());

			pstmt.executeUpdate();
			
			try {
				PreparedStatement pstmt2 = conn.prepareStatement("select seq_reply.currval from dual");
					// .currval : 방금 insert 한 값
					// dual : oracle 제공 가상 테이블
				ResultSet rs = pstmt2.executeQuery();
				if(rs.next()) {
					rno = rs.getInt(1);
					// 검색된 결과의 첫 번째 값을 정수형으로 가져와라
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rno;
	}
	
	// 댓글 뷰
	
	public List<ReplyDTO> selectList(int bno){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select r.rno, r.bno, r.writer, m.nickname, r.content,\r\n"
				+ "       to_char(r.reg_date, 'YYYY-MM-DD') as reg_date\r\n"
				+ "from reply r\r\n"
				+ "join member m\r\n"
				+ "on r.writer = m.id\r\n"
				+ "where r.bno = ?"
				+ "order by r.rno asc";
		
		List<ReplyDTO> list = new ArrayList<ReplyDTO>();
		
		try {
			conn = DBUtil.getInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				
				dto.setRno(rs.getInt("rno"));
				dto.setBno(rs.getInt("bno"));
				dto.setWriter(rs.getString("writer"));
				dto.setNickName(rs.getString("nickname"));
				dto.setContent(rs.getString("content"));
				dto.setRegDate(rs.getString("reg_date"));
				
				list.add(dto);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
		
	}
	
	
	// 댓글 삭제
	public int delete(int rno, String writer) {
		
		String sql = "delete from reply where rno = ? and writer = ?";
		
		int result = 0;
		
		try(Connection conn = DBUtil.getInstance();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, rno);
			pstmt.setString(2, writer);
			
			result = pstmt.executeUpdate();
			// insert, delete, update는 정상적으로 처리되면 0보다 큰 값이 리턴된다

		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 댓글 수정
	public int update(int rno, String content, String writer) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update reply set content = ? where rno = ? and writer = ?";
		
		int result = 0;
		
		try {
			conn = DBUtil.getInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, content);
			pstmt.setInt(2, rno);
			pstmt.setString(3, writer);
			
			result = pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	// 마이페이지 - 내가 쓴 댓글
	public List<ReplyDTO> selectListByWriter(String writer){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select r.rno, r.bno, b.title as board_title, r.content,\r\n"
				+ "       to_char(r.reg_date, 'YYYY-MM-DD') as reg_date\r\n"
				+ "from reply r\r\n"
				+ "join board b\r\n"
				+ "on r.bno = b.bno\r\n"
				+ "where r.writer = ?\r\n"
				+ "order by r.bno desc";
		
		List<ReplyDTO> list = new ArrayList<ReplyDTO>();
		
		try {
			conn = DBUtil.getInstance();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, writer);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				
				dto.setRno(rs.getInt("rno"));
				dto.setBno(rs.getInt("bno"));
				dto.setBoardTitle(rs.getString("board_title"));
				dto.setContent(rs.getString("content"));
				dto.setRegDate(rs.getString("reg_date"));
				
				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
		
	}
	
	
	
	
}
