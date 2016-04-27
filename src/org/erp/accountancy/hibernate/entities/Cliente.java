package org.erp.accountancy.hibernate.entities;

import java.util.Collection;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.erp.accountancy.ui.tables.TableRow;

/**
 * This class represents a Client with all its respective information:
 * - Id
 * - Name
 * - Telephone number
 * - Address
 * - City
 * - CUIT
 * - Account movements
 * - Taxes to pay
 * 
 * It also represents an entity in Hibernate framework.
 * @author mathias
 *
 */
@Entity
@Table(name="CLIENTE")
public class Cliente implements TableRow {

	
	private long idCliente;
	private String nombre;
	private String telefono;
	private String direccion;
	private String ciudad;
	private String cuit;
	private float honorarios;
	private Collection<Movimiento> movimientos;
	private Collection<Impuesto> impuestos;
	private Map<String, String> otherFeatures;

	@Column(name="honorarios")
	public float getHonorarios() {
		return honorarios;
	}
	public void setHonorarios(float honorarios) {
		this.honorarios = honorarios;
	}
	
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, targetEntity = Movimiento.class, cascade = CascadeType.REMOVE)
	public Collection<Movimiento> getMovimientos() {
		return movimientos;
	}
	public void setMovimientos(Collection<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	@Id @Column(name="id_cliente")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="telefono")
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name="direccion")
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name="ciudad")
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	@Column(name="cuit")
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
	@ManyToMany(fetch=FetchType.LAZY, targetEntity = Movimiento.class, cascade = CascadeType.REMOVE)
	@JoinTable(name="IMPUESTO_CLIENTE", joinColumns=@JoinColumn(name="id_cliente") 
								, inverseJoinColumns=@JoinColumn(name="id_impuesto")) 
	public Collection<Impuesto> getImpuestos(){
		return impuestos;
	}
	public void setImpuestos(Collection<Impuesto> impuestos){
		this.impuestos = impuestos;
	}
	
	@ElementCollection // this is a collection of primitives
	@MapKeyColumn(name="key") // column name for map "key"
    @Column(name="value") // column name for map "value"
	public Map<String, String> getOtherFeatures(){
		return otherFeatures;
	}
	public void setOtherFeatures(Map<String, String> hash){
		this.otherFeatures = hash;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCliente ^ (idCliente >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (idCliente != other.idCliente)
			return false;
		return true;
	}
	@Override
	public int compareTo(TableRow o) {
		return 0;
	}
	@Override
	public Object getValueAt(int columnIndex) {
		switch (columnIndex){
		case 0: return idCliente;
		case 1: return nombre;
		case 2: return direccion;
		case 3: return ciudad;
		case 4: return cuit;
		case 5: return telefono;
		}
		return null;
	}
	
	@Override
	public Cliente clone() {
		Cliente cliente = new Cliente();
		
		cliente.setCiudad(this.getCiudad());
		cliente.setCuit(this.getCuit());
		cliente.setDireccion(this.getDireccion());
		cliente.setHonorarios(this.getHonorarios());
		cliente.setIdCliente(this.getIdCliente());
		cliente.setImpuestos(this.getImpuestos());
		cliente.setMovimientos(this.getMovimientos());
		cliente.setNombre(this.getNombre());
		cliente.setOtherFeatures(this.getOtherFeatures());
		cliente.setTelefono(this.getTelefono());
		
		return cliente;
	}
}
