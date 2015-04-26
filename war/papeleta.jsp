<%@ page import="es.upm.dit.isst.voto2015.model.User" %>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Papeleta - e-Vote de Grupo 1 (ISST)</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
		<script src="js/jquery.min.js"></script>
		<script src="js/jquery.dropotron.min.js"></script>
		<script src="js/jquery.scrolly.min.js"></script>
		<script src="js/jquery.scrollgress.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/skel-layers.min.js"></script>
		<script src="js/init.js"></script>
		<noscript>
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-wide.css" />
			<link rel="stylesheet" href="css/style-noscript.css" />
		</noscript>
	</head>
	<body class="contact">
	<% User user = (User)session.getAttribute("user"); %>
		<!-- Header -->
			<header id="header" class="skel-layers-fixed">
				<h1 id="logo"><a href="index.html">e-Vote <span>de Grupo 1 (ISST)</span></a></h1>
				<nav id="nav">
					<ul>
						<li class="current"><a href="index.html">Bienvenid@ <%=user.getNombre()%></a></li>
						<li class="submenu">
							<a href="">Vota ya!</a>
							<ul>
								<li><a href="generales.html">Generales</a></li>
								<li><a href="autonomicas.html">AutonÃ³micas</a></li>
								<li><a href="universitarias.html">Universitarias</a></li>
								<li><a href="contact.html">Contacto</a></li>
							</ul>
						</li>
					</ul>
				</nav>
			</header>

		<!-- Main -->
			<article id="main">

				<header class="special container">
					<span class="icon fa-envelope"></span>
					<% if(user.getSector() == 1){ %>
					<h2>Seleccione al partido para las Elecciones Generales</h2>
					<% } else if(user.getSector() == 2) { %>
					<h2>Seleccione al  partido para las Elecciones Autonómicas </h2>
					<% } else if(user.getSector() == 3) { %>
					<h2>Seleccione al Rector de la UPM </h2>
					<%} %>
				</header>
				<section class="wrapper style4 special container 75%">
				<form action="/VotoServlet" method="post">
				<% if(user.getSector() == 1){ %>
					<INPUT TYPE="radio" NAME="candidate" value="1">PP<BR>
					<INPUT TYPE="radio" NAME="candidate" value="2">PSOE<BR>
					<INPUT TYPE="radio" NAME="candidate" value="3">Ciudadanos<BR>
					<INPUT TYPE="radio" NAME="candidate" value="4">Podemos<BR>
					<% } else if(user.getSector() == 2) { %>
					<INPUT TYPE="radio" NAME="candidate" value="1">PP<BR>
					<INPUT TYPE="radio" NAME="candidate" value="2">PSOE<BR>
					<INPUT TYPE="radio" NAME="candidate" value="3">Ciudadanos<BR>
					<INPUT TYPE="radio" NAME="candidate" value="4">Podemos<BR>
					<% } else if(user.getSector() == 3) { %>
					<INPUT TYPE="radio" NAME="candidate" value="1">Juan Carlos Dueñas<BR>
					<INPUT TYPE="radio" NAME="candidate" value="2">Juan Carlos Yelmo<BR>
					<INPUT TYPE="radio" NAME="candidate" value="3">José María del Álamo<BR>
					<% } %>
					<INPUT TYPE="submit" name="submit" value="Submit">
				</form>
				</section>
			</article>

		<!-- Footer -->
			<footer id="footer">

				<ul class="icons">
					<li><a href="#" class="icon circle fa-twitter"><span class="label">Twitter</span></a></li>
					<li><a href="#" class="icon circle fa-facebook"><span class="label">Facebook</span></a></li>
					<li><a href="#" class="icon circle fa-google-plus"><span class="label">Google+</span></a></li>
				</ul>

				<ul class="copyright">
					<li>&copy; e-Vote</li><li>Grupo 1 (ISST)</li>
				</ul>

			</footer>

	</body>
</html>