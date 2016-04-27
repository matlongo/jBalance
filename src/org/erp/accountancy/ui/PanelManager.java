package org.erp.accountancy.ui;

import java.awt.CardLayout;

import javax.swing.JPanel;

import org.erp.accountancy.ui.panels.BalancePanel;
import org.erp.accountancy.ui.panels.ClientesPanel;
import org.erp.accountancy.ui.panels.CuentasPanel;
import org.erp.accountancy.ui.panels.HonorariosPanel;
import org.erp.accountancy.ui.panels.ImpuestosPanel;
import org.erp.accountancy.ui.panels.MovimientosPanel;
import org.erp.accountancy.ui.panels.OpcionesPanel;
import org.erp.accountancy.ui.panels.ResumenMovimientosPanel;
import org.erp.accountancy.ui.panels.WelcomePanel;

public class PanelManager {

	public static final String WELCOME_PANEL = "welcome";
	public static final String BALANCE_PANEL = "balance";
	public static final String MOVIMIENTOS_PANEL = "movimientos";
	public static final String RESUMEN_MOVIMIENTOS_PANEL = "resumen-movimientos";
	public static final String CLIENTES_PANEL = "clientes";
	public static final String CUENTAS_PANEL = "cuentas";
	public static final String HONORARIOS_PANEL = "honorarios";
	public static final String IMPUESTOS_PANEL = "impuestos";
	public static final String OPCIONES_PANEL = "opciones";
	
	private JPanel visiblePanel;
	private CardLayout cardLayout;
	private MainFrame mainFrame;
	private BalancePanel balancePanel;
	private WelcomePanel welcomePanel;
	private MovimientosPanel movimientosPanel;
	private ResumenMovimientosPanel resumenMovimientosPanel;
	private ClientesPanel clientesPanel;
	private CuentasPanel cuentasPanel;
	private HonorariosPanel honorariosPanel;
	private ImpuestosPanel impuestosPanel;
	private OpcionesPanel opcionesPanel;
	private static PanelManager instance;
	
	private PanelManager(){
		this.mainFrame = new MainFrame();
		
		this.visiblePanel = new JPanel();
		this.cardLayout = new CardLayout();
		this.visiblePanel.setLayout(cardLayout);
		this.visiblePanel.add(this.getWelcomePanel(), WELCOME_PANEL);
		this.visiblePanel.add(this.getBalancePanel(), BALANCE_PANEL);
		this.visiblePanel.add(this.getMovimientosPanel(), MOVIMIENTOS_PANEL);
		this.visiblePanel.add(this.getResumenMovimientosPanel(), RESUMEN_MOVIMIENTOS_PANEL);
		this.visiblePanel.add(this.getClientesPanel(), CLIENTES_PANEL);
		this.visiblePanel.add(this.getCuentasPanel(), CUENTAS_PANEL);
		this.visiblePanel.add(this.getHonorariosPanel(), HONORARIOS_PANEL);
		this.visiblePanel.add(this.getImpuestosPanel(), IMPUESTOS_PANEL);
		this.visiblePanel.add(this.getOpcionesPanel(), OPCIONES_PANEL);
		
		this.switchPanel("welcome");
		
		this.mainFrame.setPanel(visiblePanel);
	}
	
	public static PanelManager getInstance(){
		if (instance == null){
			instance = new PanelManager();
		}
		return instance;
	}
	
	public void init(){
		mainFrame.setVisible(true);
	}
	
	public MainFrame getMainFrame(){
		return this.mainFrame;
	}
	
	public BalancePanel getBalancePanel(){
		if (balancePanel == null)
			balancePanel = new BalancePanel();
		return balancePanel;
	}
	
	public WelcomePanel getWelcomePanel(){
		if (welcomePanel == null)
			welcomePanel = new WelcomePanel();
		return welcomePanel;
	}
	
	public MovimientosPanel getMovimientosPanel(){
		if (movimientosPanel == null)
			movimientosPanel = new MovimientosPanel();
		return movimientosPanel;
	}
	
	public ResumenMovimientosPanel getResumenMovimientosPanel(){
		if (resumenMovimientosPanel == null)
			resumenMovimientosPanel = new ResumenMovimientosPanel();
		return resumenMovimientosPanel;
	}
	
	public ClientesPanel getClientesPanel(){
		if (clientesPanel == null)
			clientesPanel = new ClientesPanel();
		return clientesPanel;
	}
	
	public CuentasPanel getCuentasPanel(){
		if (cuentasPanel == null)
			cuentasPanel = new CuentasPanel();
		return cuentasPanel;
	}
	
	public HonorariosPanel getHonorariosPanel(){
		if (honorariosPanel == null)
			honorariosPanel = new HonorariosPanel();
		return honorariosPanel;
	}
	
	public ImpuestosPanel getImpuestosPanel(){
		if (impuestosPanel == null)
			impuestosPanel = new ImpuestosPanel();
		return impuestosPanel;
	}
	
	public OpcionesPanel getOpcionesPanel(){
		if (opcionesPanel == null)
			opcionesPanel = new OpcionesPanel();
		return opcionesPanel;
	}
	
	public void switchPanel(String panelToSwitch){
		cardLayout.show(visiblePanel, panelToSwitch);
	}

	public void setNewPanel(JPanel panel, String name) {
		visiblePanel.add(panel, name);
		cardLayout.show(visiblePanel, name);
	}
	
	public void removePanel(JPanel panel){
		visiblePanel.remove(panel);
	}
}
