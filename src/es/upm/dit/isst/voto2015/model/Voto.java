package es.upm.dit.isst.voto2015.model;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Entidad en base de datos independiente del CEE.
 * El resto de entidades pueden/deben ser comunes.
 */
@Entity
public class Voto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Unowned
	private int idVotacion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Unowned
	private int idCEE;

	@ManyToOne(fetch=FetchType.EAGER)
	@Unowned
	private int idSector;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Unowned
	private int idCandidato;
	
	/**
	 * Cuando el CEE ha emitido el voto
	 */
	private long timestampEmitido;
	
	/**
	 * Cuando se ha registrado el voto en el CRV
	 */
	private long timestamp;
	
	/**
	 * Número aleatorio generado por el CEE, incluído en la firma.
	 */
	private long nonce;
	
	/**
	 * Base64 encoded SHA512withRSA, firma emitida por el CEE emisor del voto
	 * Incluye los campos id_votacion, id_cee, id_escuela, id_sector, id_candidato, timestamp (CEE), nonce (CEE)
	 */
	private String firma;
	
	public Voto(int idVotacion, int idCEE, int idSector, int idCandidato, long timestamp, long nonce)
	{
		this.idVotacion = idVotacion;
		this.idCEE = idCEE;
		this.idSector = idSector;
		this.idCandidato = idCandidato;
		
		this.timestampEmitido = timestamp;
		this.nonce = nonce;
		
		this.timestamp = new Date().getTime();
	}
	
	public Key id()
	{
		return id;
	}
	
	public int idVotacion()
	{
		return idVotacion;
	}
	
	public int idCEE()
	{
		return idCEE;
	}

	public int idSector()
	{
		return idSector;
	}
	
	public int idCandidato()
	{
		return idCandidato;
	}

	public long timestamp()
	{
		return timestamp;
	}

	public long timestampEmitido()
	{
		return timestampEmitido;
	}
	
	public String firma()
	{
		return firma;
	}
	
	public void setFirma(String firma)
	{
		this.firma = firma;
	}

	public long nonce()
	{
		return nonce;
	}
	
	/**
	 * Incluye los campos id_votacion, id_cee, id_mesa, id_candidato, timestamp (CEE), nonce (CEE)
	 * @return ByteBuffer preparado para comprobar la firma del voto con la clave pública del CEE
	 */
	public ByteBuffer datosParaValidarFirma()
	{
		ByteBuffer buffer = ByteBuffer.allocate(48);
		buffer.putLong(idVotacion);
		buffer.putLong(idCEE);
		buffer.putLong(idSector);
		buffer.putLong(idCandidato);
		buffer.putLong(timestampEmitido);
		buffer.putLong(nonce);
		return buffer;
	}
}