package org.erp.accountancy.customqueries;

import org.erp.accountancy.mov.filters.MFTrue;
import org.erp.accountancy.mov.filters.MovFilter;
import org.erp.accountancy.utils.Queries;
import org.hibernate.Query;
import org.hibernate.Session;

public class CQClientes extends CustomQuery {

	@Override
	public Query getQuery(Session session) {
		Query query = session.createQuery(Queries.S_CLIENTES);
		return query;
	}
	
	@Override
	public MovFilter getFilter() {
		return new MFTrue();
	}

	@Override
	public String getParamsAsString() {
		return "";
	}

}
