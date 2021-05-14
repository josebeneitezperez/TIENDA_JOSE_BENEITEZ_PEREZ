package curso.java.tienda.model;

// default package
// Generated 7 may. 2021 1:20:11 by Hibernate Tools 5.2.12.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pedidos")
public class Pedido implements java.io.Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="id_usuario", unique = true)
	private int idUsuario;
	private Date fecha;
	@Column(name="metodo_pago", unique = true)
	private String metodoPago;
	private String estado;
	@Column(name="num_factura", unique = true)
	private String numFactura;
	private Double total;

	public Pedido() {
	}

	public Pedido(Date fecha) {
		this.fecha = fecha;
	}

	public Pedido(int idUsuario, Date fecha, String metodoPago, String estado, String numFactura, Double total) {
		this.idUsuario = idUsuario;
		this.fecha = fecha;
		this.metodoPago = metodoPago;
		this.estado = estado;
		this.numFactura = numFactura;
		this.total = total;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMetodoPago() {
		return this.metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumFactura() {
		return this.numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
