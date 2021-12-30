package com.koreait.basic.board.cmt;

import com.google.gson.Gson;
import com.koreait.basic.Utils;
import com.koreait.basic.board.cmt.model.BoardCmtDTO;
import com.koreait.basic.board.cmt.model.BoardCmtEntity;
import com.koreait.basic.board.cmt.model.BoardCmtVO;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/board/cmt")
public class BoardCmtServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //리스트
        int iboard = Utils.getParameterInt(req, "iboard");
        BoardCmtDTO cmtParam = new BoardCmtDTO();
        cmtParam.setIboard(iboard);

        List<BoardCmtVO> cmtlist = BoardCmtDAO.selBoardCmtList(cmtParam);

        Gson gson = new Gson();
        String json = gson.toJson(cmtlist);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.print(gson.toJson(cmtlist));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //등록, 수정 ,삭제
        String proc = req.getParameter("proc");
        System.out.println("proc :" +proc);
        String json = Utils.getJson(req);
        Gson gson = new Gson();
        System.out.println("json :" + json);
        BoardCmtEntity entity = gson.fromJson(json, BoardCmtEntity.class);
        entity.setWriter(Utils.getLoginUserPk(req));

        int result = 0;
        switch (proc){
            case "upd":
                result = BoardCmtDAO.updBoardCmt(entity); //writer, icmt, ctnt
                break;
            case "del":
                result = BoardCmtDAO.delBoardCmt(entity);
                break;
            case "ins":
                result = BoardCmtDAO.insBoardCmt(entity);
                break;
        }
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();
        out.print(String.format("{\"result\": %d}", result));
    }
}
