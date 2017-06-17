<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<a href="<c:url value="/microblogger/register" />">Register</a>
<c:url value="/login" var="loginProcessingUrl"/>
<form action="${loginProcessingUrl}" method="POST">
    <table>
        <tr><td>User:</td>
            <td><input type="text" id="username" name="username"/></td></tr>
        <tr><td>Password:</td>
            <td><input type="password" id="password" name="password"/></td></tr>
        <tr><td colspan='2'>
            <input id="remember_me" name="remember-me" type="checkbox"/>
            <label for="remember_me" class="inline">Remember me</label></td></tr>
        <tr><td colspan='2'>
            <input name="submit" type="submit" value="Login"/></td></tr>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </table>
</form>
