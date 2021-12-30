package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardHeartEntity;
import com.koreait.basic.dao.BoardHeartDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/heart")
public class BoardHeartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String proc = req.getParameter("proc");
        int iboard = Utils.getParameterInt(req, "iboard");

        BoardHeartEntity bhEntity = new BoardHeartEntity();
        bhEntity.setIboard(iboard);
        bhEntity.setIuser(Utils.getLoginUserPk(req));

        switch (proc){
            case "1":
                BoardHeartDAO.insBoardHeart(bhEntity);
                break;
            case "2":
                BoardHeartDAO.delBoardHeart(bhEntity);
                break;
        }
        res.sendRedirect("/board/detail?nohits=1&iboard=" + iboard);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
