<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span class="left">
    <a href="<c:url value="/" />"><img
    src="<c:url value="/resources" />/images/blog_logo_50.png"
    border="0"/></a>
</span>
<form action="<c:url value="/logout" />" method="POST" class="right">
    <input type="submit" value="Log out"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<div class="bar"/>
