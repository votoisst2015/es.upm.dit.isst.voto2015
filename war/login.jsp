<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Login - e-Vote de Grupo 1 (ISST)</title>
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
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="css/ie/v9.css" /><![endif]-->
	</head>
	<body class="contact">

		<!-- Header -->
			<header id="header" class="skel-layers-fixed">
				<h1 id="logo"><a href="index.html">e-Vote <span>de Grupo 1 (ISST)</span></a></h1>
				<nav id="nav">
					<ul>
						<li class="current"><a href="index.html">Bienvenid@</a></li>
						<li class="submenu">
							<a href="">Vota ya!</a>
							<ul>
								<li><a href="generales.html">Generales</a></li>
								<li><a href="autonomicas.html">Autonómicas</a></li>
								<li><a href="universitarias.html">Universitarias</a></li>
								<li><a href="contact.html">Contacto</a></li>
							</ul>
						</li>
						<li><a href="login.jsp" class="button special">Acceda ya</a></li>
					</ul>
				</nav>
			</header>

		<!-- Main -->
			<article id="main">

				<header class="special container">
					<span class="icon fa-envelope"></span>
					<h2>Empiece a votar ya</h2>
					<p>Exporte su certificado electrónico para empezar a hacerlo</p>
				</header>

				<!-- One -->
					<section class="wrapper style4 special container 75%">

						<!-- Content -->
							<div class="content">
									<div class="row 50%">
										<div class="12u">
											<form action="<%= blobstoreService.createUploadUrl("/UploadCertificateServlet") %>" method="post" enctype="multipart/form-data">
    											<input type="file" name="file" />
											    <input type="submit" />
											</form>
										</div>
									</div>
							</div>
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