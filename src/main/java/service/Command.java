package service;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 모든 기능(로그인, 글쓰기, 댓글쓰기...)이 공통으로 구현할 인터페이스
 컨트롤러는 이 인터페이스만 다룬다
 */

public interface Command {

	boolean doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
