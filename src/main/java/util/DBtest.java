package util;

import java.sql.Connection;

public class DBtest {

	public static void main(String[] args) {
		
		try {
			Connection conn = DBUtil.getInstance();
			if(conn != null) {
				System.out.println("성공");
			}else {
				System.out.println("실패");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		// run as -> java appication 으로 실행해야 함!!!
	}

}
