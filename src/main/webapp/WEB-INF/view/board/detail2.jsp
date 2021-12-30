<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/res/css/board/detail.css?ver=1">
<div>
    <c:if test="${sessionScope.loginUser.iuser == requestScope.data.writer}">
    <div>
        <a href="/board/del?iboard=${requestScope.data.iboard}"><button>삭제</button></a>
        <a href="/board/regmod?iboard=${requestScope.data.iboard}"><button>수정</button></a>
    </div>
    </c:if>


    <div>
        <c:if test="${sessionScope.loginUser != null}">
        <c:choose>
            <c:when test="${requestScope.isHeart == 1}">
                <a href="/board/heart?proc=2&iboard=${requestScope.data.iboard}"><i class="fas fa-heart"></i></a>
            </c:when>
            <c:otherwise>
                <a href="/board/heart?proc=1&iboard=${requestScope.data.iboard}"><i class="far fa-heart"></i></a>
            </c:otherwise>
        </c:choose>
        </c:if>
    </div>


    <div>번호 : ${requestScope.data.iboard}</div>
    <div>제목 : <c:out value="${requestScope.data.title}"/></div>
    <div>작성자 : <c:out value="${requestScope.data.writerNm}"/></div>
    <div>조회수 : <c:out value="${requestScope.data.hit}"/></div>
    <div>작성일시 : <c:out value="${requestScope.data.rdt}"/></div>
    <div>- 내용 - </div>
    <div><c:out value="${requestScope.data.ctnt}"></c:out></div>
    <c:if test="${sessionScope.loginUser != null}">
        <div>
            <form id="cmtNewFrm">
                <input type="text" name="ctnt" placeholder="댓글 내용">
                <input type="submit" value="댓글달기">
            </form>
        </div>
    </c:if>
    <div id="cmtListContainer" data-iboard="${requestScope.data.iboard}" data-loginuserpk="${sessionScope.loginUser.iuser}"></div>
</div>
<div class="cmtModContainer">
    <div class="cmtModBody">
        <form id="cmtModFrm" onsubmit="return false">
            <input type="hidden" name="icmt">
            <div><input type="text" name="ctnt" value="" placeholder="댓글 내용"></div>
            <div>
                <input type="submit" value="수정">
                <input type="button" value="취소" id="btnCancel">
            </div>
        </form>
    </div>
</div>
<script src="/res/js/board/detail2.js?ver=3"></script>