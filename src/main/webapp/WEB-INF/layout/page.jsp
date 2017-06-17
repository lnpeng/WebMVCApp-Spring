<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Microblogger</title>
    <link rel="stylesheet"
          type="text/css"
          href="<c:url value="/resources/style.css" />" >
  </head>
  <%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
  <security:authorize access="isAuthenticated()">
    <div>
      Hello <span><security:authentication property="principal.username" /></span>
    </div>
  </security:authorize>
  <body>
    <div id="header">
      <t:insertAttribute name="header" />
    </div>
    <div id="content">
      <t:insertAttribute name="body" />
    </div>
    <div id="footer">
      <t:insertAttribute name="footer" />
    </div>
  </body>
</html>
