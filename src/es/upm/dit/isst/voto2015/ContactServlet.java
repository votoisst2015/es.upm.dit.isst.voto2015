package es.upm.dit.isst.voto2015;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

		@Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        String name = req.getParameter("name");
	        String message = req.getParameter("message");
	        String email = req.getParameter("email");
	        String subject = req.getParameter("subject");
	        Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	 
	        String msgBody = "Nombre: "+ name + "\n" + "Mensaje: "+ message + "\n" + "Email: "+ email;
	 
	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("votoisst2015@gmail.com",
	                    "Admin e-Vote"));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
	                    "votoisst2015@gmail.com", "Grupo 1"));
	            msg.setSubject(subject);
	            msg.setText(msgBody);
	            Transport.send(msg);
	 
	        } catch (Exception e) {
	            resp.setContentType("text/plain");
	            resp.getWriter().println("Algo  falló. Vuelva a intentarlo.");
	            throw new RuntimeException(e);
	        }
	 
	        resp.sendRedirect("/gracias.jsp");
	    }
	}