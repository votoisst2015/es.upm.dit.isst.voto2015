<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="es.upm.dit.isst.voto2015.model.User" %>
<%@ page import="es.upm.dit.isst.voto2015.model.Voto" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Justificante - e-Vote de Grupo 1 (ISST)</title>
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
	<% User user = (User) session.getAttribute("user"); %>
	<% Voto voto= (Voto) session.getAttribute("voto"); %>
		<!-- Header -->
			<header id="header" class="skel-layers-fixed">
				<h1 id="logo"><a href="index.html">e-Vote <span>de Grupo 1 (ISST)</span></a></h1>
			</header>

		<!-- Main -->
			<article id="main">
				<header class="special container">
					<span class="icon fa-envelope"></span>
					<h2>JUSTIFICANTE</h2>
				</header>
				<section class="wrapper style4 special container 75%">
					<p>Esta página certifica que su votacion ha sido registrada. A continuación puede observar los datos de la votación. Imprímalo para usar de justificante. </p>
					<p>DNI del votante: <%=user.getDni() %></p>
					<% Date d = new Date(voto.timestampEmitido()); %>
					<p>Fecha de realización de la votación: <%=d %></p>
					<% if(user.getSector() == 1){ %>
						<% if(voto.idCandidato() == 1) %>
						<p>Opción votada: PP </p>
						<% else if(voto.idCandidato() == 2) %>
						<p>Opción votada: PSOE </p>
						<% else if(voto.idCandidato() == 3) %>
						<p>Opción votada: Ciudadanos </p>
						<% else if(voto.idCandidato() == 4) %>
						<p>Opción votada: Podemos </p>
					<% } else if(user.getSector() == 2) { %>
						<% if(voto.idCandidato() == 1) %>
						<p>Opción votada: PP </p>
						<% else if(voto.idCandidato() == 2) %>
						<p>Opción votada: PSOE </p>
						<% else if(voto.idCandidato() == 3) %>
						<p>Opción votada: Ciudadanos </p>
						<% else if(voto.idCandidato() == 4) %>
						<p>Opción votada: Podemos </p>
					<% } else if(user.getSector() == 3) { %>
						<% if(voto.idCandidato() == 1) %>
						<p>Opción votada: Juan Carlos Dueñas </p>
						<% else if(voto.idCandidato() == 2) %>
						<p>Opción votada: Juan Carlos Yelmo </p>
						<% else if(voto.idCandidato() == 3) %>
						<p>Opción votada: José María del Álamo </p>
					<%} %>
					
					<p>Código electrónico: <%=voto.nonce() %></p>
				</section>
			</article>
	</body>
</html>