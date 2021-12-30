package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    public static int insBoardWithPk(BoardEntity entity){
        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "INSERT INTO t_board(title, ctnt, writer) " +
                "VALUES (?, ?, ?) ";
        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getWriter());
            result = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                int iboard = rs.getInt(1);
                entity.setIboard(iboard);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps, rs);
        }
        return result;
    }
    public static int insBoard(BoardEntity entity){
        Connection con = null;
        PreparedStatement ps =null;
        String sql = "INSERT INTO t_board (title, ctnt, writer) " +
                "VALUES (?, ?, ?)";
        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getWriter());
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
    private static String getSearchWhereString(BoardDTO param){
        if(param.getSearchText() != null && !"".equals(param.getSearchText())){
            switch (param.getSearchType()){
                case 1:
                    return String.format("WHERE A.title LIKE '%%%s%%'", param.getSearchText());
                case 2:
                    return String.format("WHERE A.ctnt LIKE '%%%s%%'", param.getSearchText());
                case 3:
                    return String.format("WHERE A.title LIKE '%%%s%%' OR A.ctnt LIKE '%%%s%%'", param.getSearchText(), param.getSearchText());
                case 4:
                    return String.format("WHERE B.nm LIKE '%%%s%%'", param.getSearchText());
                case 5:
                    return String.format("WHERE A.title LIKE '%%%s%%' OR A.ctnt LIKE '%%%s%%' OR B.nm LIKE '%%%s%%'"
                            , param.getSearchText() , param.getSearchText(), param.getSearchText());
            }
        }
        return "";
    }
    public static int getMaxPageNum(BoardDTO param){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT CEIL(COUNT(*) / ?) FROM t_board A " +
                "INNER JOIN t_user B " +
                "ON A.writer = B.iuser ";

        sql += getSearchWhereString(param);

        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getRowCnt());
            rs = ps.executeQuery();
            if(rs.next()){
                int maxPageNum = rs.getInt(1);
                return maxPageNum;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps, rs);
        }
        return  0;
    }
    public static List<BoardVO> selBoardList(BoardDTO param){
        List<BoardVO> list = new ArrayList();
        Connection con =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.iboard, A.title, A.writer, A.hit, A.rdt, B.nm as writerNm, B.profileImg " +
                "FROM t_board A " +
                "INNER JOIN t_user B " +
                "ON A.writer = B.iuser ";
        sql += getSearchWhereString(param);
        sql += " ORDER BY A.iboard DESC " +
                " LIMIT ?, ? ";
        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1,param.getStartIdx());
            ps.setInt(2,param.getRowCnt());
            rs = ps.executeQuery();
            while(rs.next()){
                int iboard = rs.getInt("iboard");
                String title = rs.getString("title");
                int writer = rs.getInt("writer");
                int hit = rs.getInt("hit");
                String rdt = rs.getString("rdt");
                String writerNm = rs.getString("writerNm");
                BoardVO vo = BoardVO.builder()
                        .iboard(iboard)
                        .title(title)
                        .writer(writer)
                        .hit(hit)
                        .rdt(rdt)
                        .writerNm(writerNm)
                        .profileImg(rs.getString("profileImg"))
                        .build();
                list.add(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con,ps,rs);
        }
        return list;
    }
    public static BoardVO selBoardDetail(BoardDTO param){
        Connection con =null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        String sql = "SELECT A.iboard, A.title, A.ctnt, A.hit, A.rdt, A.writer, B.nm as writerNm" +
                " FROM t_board A INNER JOIN t_user B ON A.writer = B.iuser " +
                "WHERE iboard = ? ";
        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            rs = ps.executeQuery();
            if(rs.next()){
                int iboard = rs.getInt("iboard");
                String title = rs.getString("title");
                String ctnt = rs.getString("ctnt");
                String writerNm = rs.getString("writerNm");
                int hit = rs.getInt("hit");
                String rdt = rs.getString("rdt");
                int writer = rs.getInt("writer");
                BoardVO vo = BoardVO.builder()
                        .iboard(iboard)
                        .title(title)
                        .ctnt(ctnt)
                        .writerNm(writerNm)
                        .hit(hit)
                        .rdt(rdt)
                        .writer(writer)
                        .build();

                return vo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps, rs);
        }
        return null;
    }
    public static void updBoardHitUp(BoardDTO param){
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE t_board " +
                "SET hit = hit + 1 " +
                "WHERE iboard = ?";
        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps);
        }
    }
    public static int delBoard(BoardEntity entity){
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM t_board WHERE iboard = ? AND writer = ?";
        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, entity.getIboard());
            ps.setInt(2, entity.getWriter());
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
    public static int updBoard(BoardEntity entity){
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE t_board SET title = ?, ctnt = ?, mdt = now() " +
                " WHERE iboard = ? AND writer = ?";
        try{
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getIboard());
            ps.setInt(4, entity.getWriter());
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
}
