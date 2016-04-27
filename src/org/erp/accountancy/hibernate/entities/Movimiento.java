package org.erp.accountancy.hibernate.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.erp.accountancy.ui.tables.TableRow;
import org.erp.accountancy.utils.Utils;

/**
 * This class represents a particular account movement
 * made by a particular client. Its attributes are:
 * - Date when the movement was done
 * - Client who made the movement
 * - Description of the movement
 * - Amount owing: amount of money added to the total owed money
 * - Amount in favor: amount of money added to the total of money in favor
 * 
 * This class also represents an entity in Hibernate framework.
 * @author mathias
 *
 */
@Entity
@Table(name="MOVIMIENTO")
public class Movimiento implements TableRow {

	@Column(name="fecha")
	@Temporal(value = TemporalType.DATE)
	private Date fecha;
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	@Column(name="descripcion")
	private String descripcion;
	@Column(name="monto_debe")
	private double montoDebe;
	@Column(name="monto_haber")
	private double montoHaber;
	@Id @Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name="comprobante")
	private long comprobante;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public double getMontoDebe() {
		return montoDebe;
	}
	public void setMontoDebe(double montoDebe) {
		this.montoDebe = montoDebe;
	}
	public double getMontoHaber() {
		return montoHaber;
	}
	public void setMontoHaber(double montoHaber) {
		this.montoHaber = montoHaber;
	}
	public long getComprobante() {
		return comprobante;
	}
	public void setComprobante(long comprobante) {
		this.comprobante = comprobante;
	}
	
	@Override
	public String toString(){
		String result = new String();
		
		result += "Id: " + this.getId() + "\n";
		result += "Fecha: " + this.getFecha() + "\n";
		result += "Cliente: " + this.getCliente().getIdCliente() + "\n";
		result += "Comprobante: " + this.getComprobante() + "\n";
		result += "Monto: " + (this.getMontoHaber() - this.getMontoDebe()) + "\n";
		result += "Concepto: " + this.getDescripcion() + "\n";
		
		return result;
	}
	@Override
	public int compareTo(TableRow o) {
		return 0;
	}
	@Override
	public Object getValueAt(int columnIndex) {
		switch (columnIndex){
		case 0: return this.id;
		case 1: return this.descripcion;
		case 2: return this.comprobante;
		case 3: return Utils.getDateAsString(this.getFecha());
		case 4: return this.montoDebe;
		case 5: return this.montoHaber;
		}
		
		return null;
	}
}
