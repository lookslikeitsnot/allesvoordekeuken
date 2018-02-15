<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<!doctype html>
<html lang='nl'>
<head>
<v:head title='Artikel toevoegen' />
</head>
<body>
	<v:menu />
	<h1>Artikel toevoegen</h1>
	<form method='post' id='toevoegform'>
		<label>Naam:<span>${fouten.naam}</span> <input name='naam'
			value='${param.naam}' autofocus required></label> <label>Aankoopprijs:<span>${fouten.aankoopprijs}</span>
			<input name='aankoopprijs' value='${param.aankoopprijs}' required
			type='number' min='0.01' step='0.01'></label> <label>Verkoopprijs:<span>${fouten.verkoopprijs}</span>
			<input name='verkoopprijs' value='${param.verkoopprijs}' required
			type='number' min='0.01' step='0.01'></label> <input type="radio"
			name="artikeltype" value="food" id="food" />Food<br />
		<div id="hdb">
			<label>Houdbaarheid:</label> <input type="number" name="houdbaarheid"
				id="houdbaarheid" min='0' value='${param.houdbaarheid}'>
		</div>

		<input type="radio" name="artikeltype" value="nonfood" id="nonfood" />Non-Food<br />
		<div id="grt">
			<label>Garantie:</label> <input type="number" name="garantie"
				id="garantie" min='0' value='${param.garantie}'>
		</div>
		<input type='submit' value='Toevoegen' id='toevoegknop'>
	</form>
	<script>
		document.getElementById('toevoegform').onsubmit = function() {
			document.getElementById('toevoegknop').disabled = true;
		};
		document.getElementById('food').onclick = hideOrShow;
		document.getElementById('nonfood').onclick = hideOrShow;

		hideOrShow();

		function hideOrShow() {
			document.getElementById('hdb').style.display = document
					.getElementById('food').checked ? 'block' : 'none';
			document.getElementById('grt').style.display = document
					.getElementById('nonfood').checked ? 'block' : 'none';
		}
	</script>
</body>
</html>