package org.erp.accountancy.customqueries;

import org.erp.accountancy.mov.filters.MFMes;
import org.erp.accountancy.mov.filters.MovFilter;
import org.erp.accountancy.utils.Queries;
import org.erp.accountancy.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

public class CQMovMes extends CustomQuery {

	private int month;
	
	public CQMovMes(int month){
		this.month = month;
	}
	
	@Override
	public Query getQuery(Session session) {
		Query query = session.createQuery(Queries.S_QUERY_MONTH);
		
		query.setParameter("month", month+1);
		query.setParameter("anio", Utils.getCurrentYear());
		
		return query;
	}

	@Override
	public MovFilter getFilter() {
		return new MFMes(month);
	}

	@Override
	public String getParamsAsString() {
		return Utils.getMonthByInt(month)+" DE "+Utils.getCurrentYear();
	}

}
