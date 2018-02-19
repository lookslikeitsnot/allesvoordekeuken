<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>

<!doctype html>
<html lang='nl'>
<head>
<v:head title='Kortingen'></v:head>
<style>
td {
	text-align: right;
}
</style>
</head>
<body>
	<v:menu />
	<h1>Artikels</h1>
	<c:if test='${not empty artikel}'>
		<h2>${artikel.naam}</h2>
		<table>
			<thead>
				<tr>
					<th>vanaf</th>
					<th>korting</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items='${artikel.kortingen}' var='korting'>
					<tr>
						<td>${korting.vanafAantal}</td>
						<td>${korting.kortingsPercentage}%</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</c:if>
	<ul>
		<c:forEach items='${artikels}' var='artikel'>
			<li><c:url value='' var='kortingUrl'>
					<c:param name='id' value='${artikel.id}' />
				</c:url> <a href="<c:out value='${kortingUrl}'/>"> ${artikel.naam}</a></li>
		</c:forEach>
	</ul>
</body>
</html>