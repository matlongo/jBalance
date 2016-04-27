package org.erp.accountancy.utils;

/**
 * This class provides a set of queries to be used in order to search specific information
 * in the different parts of the program.
 * @author mathias
 *
 */
public class Queries {

	/**
	 * Query that gets all the clients and their respective movements of the current year.
	 * Specially used when a balance is wanted to be listed.
	 */
	public static final String S_QUERY_BALANCE = 
			  "SELECT DISTINCT c "
			+ "FROM Cliente c INNER JOIN c.movimientos m "
			+ "WHERE YEAR(m.fecha) = :anio";
	
	/**
	 * Query that gets all the clients and their respective movements between two dates.
	 * Specially used when a list of all the movements between two dates is desired.
	 */
	public static final String S_QUERY_2DATES = 
			  "SELECT DISTINCT c "
			+ "FROM Cliente c INNER JOIN c.movimientos m "
			+ "WHERE m.fecha >= :f1  AND  m.fecha <= :f2";
	
	/**
	 * Query that gets all the clients and their respective movements of the current day.
	 * Specially used when a list of all the movements of the current day is desired.
	 */
	public static final String S_QUERY_DAY = 
			  "SELECT DISTINCT c "
			+ "FROM Cliente c INNER JOIN c.movimientos m "
			+ "WHERE m.fecha = :fecha";
	
	/**
	 * Query that gets all the clients and their respective movements of a particular month.
	 * Specially used when a list of all the movements of a particular month is desired.
	 */
	public static final String S_QUERY_MONTH = 
			  "SELECT DISTINCT c "
			+ "FROM Cliente c INNER JOIN c.movimientos m "
			+ "WHERE YEAR(m.fecha) = :anio  AND  MONTH(m.fecha) = :month";
	
	/**
	 * Query that gets all movements of a particular month for a particular client.
	 * Specially used when a summary of a client is needed.
	 */
	public static final String S_QUERY_MONTHLY_ACCOUNT = 
			  "SELECT DISTINCT c "
			+ "FROM Cliente c INNER JOIN c.movimientos m "
			+ "WHERE YEAR(m.fecha) = :anio  AND  MONTH(m.fecha) = :month  AND  c.idCliente = :id";
	
	/**
	 * Query that gets all historic movements.
	 */
	public static final String S_MOVIMIENTOS =
			  "SELECT DISTINCT c "
			+ "FROM Cliente c INNER JOIN c.movimientos m "
			+ "WHERE YEAR(m.fecha) = :anio";
	
	/**
	 * Query that gets all different clients.
	 */
	public static final String S_CLIENTES = 
			  "SELECT DISTINCT c "
			+ "FROM Cliente c";
	
}
