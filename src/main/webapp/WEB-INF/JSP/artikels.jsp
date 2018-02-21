<%@ page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="v" uri='http://vdab.be/tags'%>
<html lang="nl">
<v:head title='Alle artikels' />
<body>
	<v:menu />
	<h1>Alle artikels</h1>
	<c:forEach items="${artikels}" var="artikel">
	<dl>
		<dt>${artikel.naam}</dt>
		<dd>${artikel.artikelgroep.naam}</dd>
	</dl>
	</c:forEach>
</body>
</html>
