package es.upm.dit.isst.voto2015;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.x509.X509V1CertificateGenerator;

import com.google.appengine.labs.repackaged.com.google.common.base.Strings;
import com.google.appengine.repackaged.com.google.api.client.util.IOUtils;

import es.upm.dit.isst.voto2015.dao.VotoDAOImpl;
import es.upm.dit.isst.voto2015.model.User;
import es.upm.dit.isst.voto2015.model.Voto;
public class VotoServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
		
		
		public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
		{	 
		

        
		}
		 
		@Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
			
			HttpSession session = req.getSession(true);
			User user = (User)session.getAttribute("user");
			// adds the Bouncy castle provider to java security
		    Security.addProvider(new BouncyCastleProvider());
			
			PrintWriter pw = resp.getWriter();
			resp.setContentType("text/html");
			 
			String candidato = req.getParameter("candidate");
			pw.println(candidato);
			
			Date d = new Date();
			long timestamp = d.getTime();
			long nonce;
			try {
				nonce = generarNonce();
				PrivateKey key = getPrivateKey("keys/cee.key");
				
				Voto voto = new Voto(1, 1, user.getSector(), Integer.parseInt(candidato), timestamp , nonce );
				voto.setFirma(firma(key, voto, timestamp, nonce));
				
				session.setAttribute("voto", voto);
				
				//enviarVoto(voto);
				user.setVotacionElectronica(true);
				VotoDAOImpl.getInstance().updateUser(user);
				 
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			resp.sendRedirect("/justificante.jsp");
			
	    }

		
		/**
		 * Realiza una petición HTTP POST al CRV con el voto emitido
		 * @param voto emitido por el CEE y firmado
		 * @return si la operación tiene éxito
		 */
		public boolean enviarVoto(Voto voto)
		{
		    try 
		    {
		    	String hostname = "isst-evote.appspot.com/voto";
		    	String port = "8080";
		        String encodedData = "";
		        encodedData = "id_votacion=" + URLEncoder.encode(String.valueOf(voto.idVotacion()), "UTF-8");
		        encodedData += "&id_cee=" + URLEncoder.encode(String.valueOf(voto.idCEE()), "UTF-8");
		        encodedData += "&id_sector=" + URLEncoder.encode(String.valueOf(voto.idSector()), "UTF-8");
		        encodedData += "&id_candidato=" + URLEncoder.encode(String.valueOf(voto.idCandidato()), "UTF-8");
		        encodedData += "&timestamp=" + URLEncoder.encode(String.valueOf(voto.timestampEmitido()), "UTF-8");
		        //encodedData += "&nonce=" + URLEncoder.encode(String.valueOf(voto.nonce()), "UTF-8");
		        encodedData += "&firma=" + URLEncoder.encode(voto.firma(), "UTF-8");

		        URL url = new URL("http://" + hostname + ":" + port + "/voto");
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setRequestMethod("POST");
		        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        connection.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));

		        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		        writer.write(encodedData);
		        writer.close();

		        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
		        {
		            // OK
		            return true;
		        } 
		        else 
		        {
		            // Server returned HTTP error code.
		            return false;
		        }
		    } 
		    catch (MalformedURLException e) 
		    {
		        e.printStackTrace();
		        return false;
		    } 
		    catch (UnsupportedEncodingException e)
		    {
		        e.printStackTrace();
		        return false;
		    }
		    catch (IOException e) 
		    {
		        e.printStackTrace();
		        return false;
		    }
		}
			
		private PrivateKey getPrivateKey(String file) throws IOException
		{
			File privateKeyFile = new File(file); // private key file in PEM format
		    PEMParser pemParser = new PEMParser(new FileReader(privateKeyFile));
		    
		    Object object = pemParser.readObject();

		    KeyPair kp;
		    JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
	        kp = converter.getKeyPair((PEMKeyPair) object);
	        PrivateKey key = kp.getPrivate();
	        return key;
		}
		
		private String firma(PrivateKey privateKey, Voto voto, long timestamp, long nonce) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
		{
		    ByteBuffer buffer = ByteBuffer.allocate(48); // total, 48 Byte
		    buffer.putLong(voto.idVotacion());       // votación en curso
		    buffer.putLong(voto.idCEE());            // cee emisor (1 si solo hay uno)
		    buffer.putLong(voto.idSector());         // sector al que pertenece el votante (ponderación)
		    buffer.putLong(voto.idCandidato());      // candidato votado
		    buffer.putLong(timestamp);                   // timestamp con precisión de ms
		    buffer.putLong(nonce);                       // nonce aleatorio que usamos para confirmar y evitar replays, 64 bit

		    Signature signature = Signature.getInstance("SHA512withRSA");
		    signature.initSign(privateKey);
		    signature.update(buffer.array());
		    return Base64.encodeBase64String(signature.sign()); // la firma se codifica en base64
		}
		
		private Long generarNonce() throws NoSuchAlgorithmException
		{
		    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		    return sr.nextLong();
		}
}