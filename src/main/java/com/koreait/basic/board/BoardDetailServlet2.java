package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.cmt.model.BoardCmtDTO;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardHeartEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardCmtDAO;
import com.koreait.basic.dao.BoardDAO;
import com.koreait.basic.dao.BoardHeartDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/detail2")
public class BoardDetailServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int nohits = Utils.getParameterInt(req, "nohits");
        int iboard = Utils.getParameterInt(req, "iboard");
        BoardDTO dto = new BoardDTO();
        dto.setIboard(iboard);

        BoardVO data = BoardDAO.selBoardDetail(dto);
        req.setAttribute("data", data);

        int loginUserPk = Utils.getLoginUserPk(req);
        if(loginUserPk > 0){
            BoardHeartEntity bhparam = new BoardHeartEntity();
            bhparam.setIuser(loginUserPk);
            bhparam.setIboard(iboard);
            req.setAttribute("isHeart", BoardHeartDAO.selIsHeart(bhparam));
        }

        if(data.getWriter() != loginUserPk && nohits != 1){
            BoardDAO.updBoardHitUp(dto);
        }
        Utils.displayView(data.getTitle(),"board/detail2",req,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
