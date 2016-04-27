package org.erp.accountancy.startup;

import java.util.ArrayList;
import java.util.List;

public class StartUp {

	private static List<StartUpAction> actions;
	
	static{
		actions = new ArrayList<>();
		actions.add(new MonthlyAction());
	}
	
	public static void fireActions(){
		for (StartUpAction action: actions)
			action.activate();
	}
}
