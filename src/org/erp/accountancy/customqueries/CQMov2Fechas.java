package org.erp.accountancy.customqueries;

import java.util.Calendar;

import org.erp.accountancy.mov.filters.MF2Fechas;
import org.erp.accountancy.mov.filters.MovFilter;
import org.erp.accountancy.utils.Queries;
import org.erp.accountancy.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

public class CQMov2Fechas extends CustomQuery {

	public static final String S_QUERY_2DATES = "SELECT c FROM Cliente AS c LEFT JOIN c.movimientos AS m WITH m.fecha >= :f1  AND  m.fecha <= :f2";
	
	private Calendar f1, f2;
	
	public CQMov2Fechas(Calendar d1, Calendar d2){
		this.f1 = d1;
		this.f2 = d2;
	}

	@Override
	public Query getQuery(Session session) {
		Query query = session.createQuery(Queries.S_QUERY_2DATES);
		query.setParameter("f1", f1.getTime());
		query.setParameter("f2", f2.getTime());

		return query;
	}

	@Override
	public MovFilter getFilter() {
		return new MF2Fechas(f1, f2);
	}

	@Override
	public String getParamsAsString() {
		return Utils.getDateAsString(f1) + " AL " + Utils.getDateAsString(f2);
	}

}
