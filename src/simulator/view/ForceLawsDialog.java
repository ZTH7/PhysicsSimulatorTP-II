package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;
import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver {
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };

	// TODO en caso de ser necesario, añadir los atributos aquí…
	int _status = 0;
	private JFrame _parent;
	private JComboBox<String> _lawscomb;
	private JComboBox<String> _groupscomb;

	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_parent = (JFrame) parent;
		_ctrl = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		// _forceLawsInfo se usará para establecer la información en la tabla
		_forceLawsInfo = _ctrl.getForceLawsInfo();

		// help
		JLabel help = new JLabel(
				"<html><p>Select a force law and provide values for the parametes in the Value column (default values are used for parametes with no value).</p></html>");

		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		_dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resize columns to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// combBox panel
		JPanel comboBoxPanel = new JPanel();
		comboBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(comboBoxPanel);

		_dataTableModel.setColumnIdentifiers(_headers);
		_lawsModel = new DefaultComboBoxModel<>();
		_forceLawsInfo.forEach(fl -> {
			if (!fl.has("desc"))
				throw new IllegalArgumentException();

			_lawsModel.addElement(fl.getString("desc"));
		});
		_lawscomb = new JComboBox<>(_lawsModel);
		comboBoxPanel.add(_lawscomb);

		_groupsModel = new DefaultComboBoxModel<>();
		_groupscomb = new JComboBox<>(_groupsModel);
		comboBoxPanel.add(_groupscomb);

		// button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonPanel);

		// cancel
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ForceLawsDialog.this.setVisible(false);
			}
		});
		buttonPanel.add(cancelButton);

		// OK
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				ForceLawsDialog.this.setVisible(false);
			}
		});
		buttonPanel.add(okButton);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open() {
		if (_groupsModel.getSize() == 0)
			return _status;

		setLocationRelativeTo(_parent);

		pack();
		setVisible(true);
		return _status;
	}

	// TODO el resto de métodos van aquí…
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		groups.values().forEach(bg -> _groupsModel.addElement(bg.getId()));
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsModel.addElement(g.getId());
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}
}
