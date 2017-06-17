<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="blogView">
  <div class="blogMessage"><c:out value="${blog.message}" /></div>
  <div>
    <span class="blogTime"><c:out value="${blog.time}" /></span>
  </div>
</div>
