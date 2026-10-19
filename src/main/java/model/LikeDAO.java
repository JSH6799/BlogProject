package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBUtil;

public class LikeDAO {

	// 회원이 이 글에 좋아요를 눌렀는지 확인
	public boolean isLiked(int bno, String memberId) {
		
		String sql = "select 1 from board_like where bno=? and member_id=?";
		// bno와 member_id 조건에 만족하는 데이터가 있으면 숫자 1을 리턴시켜라
		
		try(Connection conn = DBUtil.getInstance();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, bno);
			pstmt.setString(2, memberId);
			
			try(ResultSet rs = pstmt.executeQuery()){
				return rs.next();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 좋아요 토글 : 안 눌렀으면 추가, 이미 눌렀으면 취소
	// board_like 변경 + board.like_cnt 증감을 한 묶음(트랜잭션)으로 처리
	// 하나라도 변경이 안 되면 롤백
	public void toggle(int bno, String memberId) {
		
		String checkSql = "select 1 from board_like where bno=? and member_id=?";
		String insertSql = "insert into board_like (bno, member_id) values (?,?)";
		String deleteSql = "delete from board_like where bno=? and member_id=?";
		String plusSql = "update board set like_cnt=like_cnt+1 where bno=?";
		String minusSql = "update board set like_cnt=like_cnt-1 where bno=?";
		// 트랜잭션 : 
		
		try(Connection conn = DBUtil.getInstance()){
			conn.setAutoCommit(false);	// 자동 커밋 끄기
			try {
				// 1) 이미 눌렀는지 확인
				boolean exists;
				try(PreparedStatement pstmt = conn.prepareStatement(checkSql)){
					pstmt.setInt(1, bno);
					pstmt.setString(2, memberId);
					try(ResultSet rs = pstmt.executeQuery()){
						exists = rs.next();
					}
				}
				// 2) 눌렀으면 (delete+개수 -1), 안 눌렀으면 (insert+개수 +1)
				try(PreparedStatement pstmt = conn.prepareStatement(exists ? deleteSql : insertSql)){
					pstmt.setInt(1, bno);
					pstmt.setString(2, memberId);
					pstmt.executeUpdate();
				}
				try(PreparedStatement pstmt = conn.prepareStatement(exists ? minusSql : plusSql)){
					pstmt.setInt(1, bno);
					pstmt.executeUpdate();
				}
				conn.commit();		// 두 작업이 모두 성공했을 때만 한꺼번에 확정
			}catch (SQLException e) {
				conn.rollback();	// 하나라도 실패하면 둘 다 없던 일로 되돌림
				throw e;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 현재 좋아요 개수 조회
	public int selectLikeCnt(int bno) {
		
		String sql = "select like_cnt from board where bno=?";
		
		try(Connection conn = DBUtil.getInstance();
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, bno);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return rs.getInt("like_cnt");
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
}
