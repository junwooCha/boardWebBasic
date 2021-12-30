<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/res/css/board/list.css?ver=2">
<div>
    <h1>${requestScope.title}</h1>
    <div>
        <c:if test="${fn:length(requestScope.list) == 0}">
            랭킹리스트가 없습니다.
        </c:if>
        <c:if test="${fn:length(requestScope.list) > 0}">
            <table id="boardTable">
                <colgroup>
                    <col width="20%">
                    <col>
                    <col>
                    <col width="100px">
                    <col>
                </colgroup>
                <tr>
                    <th>no</th>
                    <th>title</th>
                    <th>${param.type == 1 ? 'hit' : 'count'}</th>
                    <th>writer</th>
                    <th>reg-datetime</th>
                </tr>
                <c:forEach items="${requestScope.list}" var="item">
                    <c:set var="eachTitle"
                           value="${fn:replace(fn:replace(item.title, '>', '&gt;'), '<', '&lt;')}"/>
                    <c:if test="${param.searchText != null && (param.searchType == 1 || param.searchType == 3 || param.searchType == 5)}">
                        <c:set var="eachTitle"
                               value="${fn:replace(eachTitle, param.searchText, '<mark>' += param.searchText += '</mark>')}"/>
                    </c:if>
                    <c:set var="eachWriterNm" value="${item.writerNm}"/>
                    <c:if test="${param.searchText != null && (param.searchType == 4 || param.searchType == 5)}">
                        <c:set var="eachWriterNm"
                               value="${fn:replace(eachWriterNm, param.searchText, '<mark>' += param.searchText += '</mark>')}"/>
                    </c:if>
                    <tr class="record" onclick="moveToDetail(${item.iboard});">
                        <td>${item.iboard}</td>
                        <td>${eachTitle}"/></td>
                        <td>${item.cnt}</td>
                        <td>${eachWriterNm}</td>
                        <td>${item.rdt}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>
<script src="/res/js/board/list.js?ver=3"></script>