package org.erp.accountancy.customqueries;

import java.util.Calendar;

import org.erp.accountancy.mov.filters.MFDia;
import org.erp.accountancy.mov.filters.MovFilter;
import org.erp.accountancy.utils.Queries;
import org.erp.accountancy.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

public class CQMovDia extends CustomQuery {

	@Override
	public Query getQuery(Session session) {
		Query query = session.createQuery(Queries.S_QUERY_DAY);
		
		Calendar calendar = Utils.getCurrentDate();
		query.setParameter("fecha", calendar.getTime());
		
		return query;
	}

	@Override
	public MovFilter getFilter() {
		return new MFDia();
	}

	@Override
	public String getParamsAsString() {
		return Utils.getCurrentDateAsString();
	}
	
}
