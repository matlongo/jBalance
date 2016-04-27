package org.erp.accountancy.customqueries;

import org.erp.accountancy.mov.filters.MFYear;
import org.erp.accountancy.mov.filters.MovFilter;
import org.erp.accountancy.utils.Queries;
import org.erp.accountancy.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

public class CQBalance extends CustomQuery {

	@Override
	public Query getQuery(Session session) {
		Query query = session.createQuery(Queries.S_QUERY_BALANCE);
		query.setParameter("anio", Utils.getCurrentYear());
		return query;
	}
	
	@Override
	public MovFilter getFilter() {
		return new MFYear();
	}

	@Override
	public String getParamsAsString() {
		return "" + Utils.getCurrentYear();
	}
	
}
