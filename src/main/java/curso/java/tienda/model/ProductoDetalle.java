package curso.java.tienda.model;

public class ProductoDetalle {

	private Producto producto;
	private DetallePedido detallePedido;
	
	public ProductoDetalle() {
		super();
	}
	
	public ProductoDetalle(Producto producto, DetallePedido detallePedido) {
		super();
		this.producto = producto;
		this.detallePedido = detallePedido;
	}
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public DetallePedido getDetallePedido() {
		return detallePedido;
	}
	public void setDetallePedido(DetallePedido detallePedido) {
		this.detallePedido = detallePedido;
	}
}
