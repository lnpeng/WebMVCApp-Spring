<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<h1>Your Profile</h1>
<c:out value="${blogger.username}" /><br/>
<c:out value="${blogger.firstName}" /> <c:out value="${blogger.lastName}" /><br/>
<c:out value="${blogger.email}" />
