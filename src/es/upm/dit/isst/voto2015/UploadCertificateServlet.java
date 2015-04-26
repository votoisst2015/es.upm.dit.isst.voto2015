package es.upm.dit.isst.voto2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import es.upm.dit.isst.voto2015.dao.VotoDAOImpl;
import es.upm.dit.isst.voto2015.model.User;

public class UploadCertificateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	/**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	
	 @Override
	    public void doPost(HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {

	        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
	        List<BlobKey> blobKeys = blobs.get("file");
	        if (blobKeys == null || blobKeys.isEmpty()) {
	            res.sendRedirect("/");
	        } else {
	        	BlobKey blobKey = blobKeys.get(0);
	        	InputStream is = new BlobstoreInputStream(blobKey);
	        	try {
	        		X509Certificate cert = getCertificate(is);
					cert.verify(cert.getPublicKey());
				    cert.checkValidity();
	        		String[] parts = cert.getSubjectDN().toString().split("=");
					int dni = Integer.parseInt(parts[1]);


					List<User> users = BBDDCenso.getUsersCenso();

					
					
					User usuario = null;
					for(User u : users){
						if(u.getDni() == dni){
							usuario = new User(u.getDni(), u.getNombre(), u.getApellidos(), u.getSector(), u.getPonderacion(), false);
							u.setVotacionElectronica(true);
						}
					}
					
					if(usuario != null)
					{
						List<User> usuariosCEE = VotoDAOImpl.getInstance().getUsers(dni);
						if(usuariosCEE.size() == 0){
							VotoDAOImpl.getInstance().add(usuario.getDni(), usuario.getNombre(), usuario.getApellidos(), usuario.getSector(), usuario.getPonderacion());
						}
					}
					
					List<User> u = VotoDAOImpl.getInstance().getUsers(dni);
					if(u.get(0).votacionElectronica())
					{
						res.sendRedirect("/yavotado.jsp");
					}
					else{
						HttpSession session = req.getSession(true);
						session.setAttribute("user", u.get(0));
						res.sendRedirect("/papeleta.jsp");
					}
					
					
				} catch (CertificateException | NoSuchProviderException e) {
					res.sendRedirect("/errorcert.jsp");
				} catch (InvalidKeyException e) {
					res.sendRedirect("/errorcert.jsp");
				} catch (NoSuchAlgorithmException e) {
					res.sendRedirect("/errorcert.jsp");
				} catch (SignatureException e) {
					res.sendRedirect("/errorcert.jsp");
				}
	        	
	        }
	    }
	 
	 private X509Certificate getCertificate(InputStream in) throws IOException, CertificateException, NoSuchProviderException
		{
	    	CertificateFactory factory = CertificateFactory.getInstance("X.509");
	    	X509Certificate cert = (X509Certificate) factory.generateCertificate(in);
	    	return cert;       
		}
}


