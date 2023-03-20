package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class StatusBar extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;

	// Añadir los atributos necesarios, si hace falta …
	double _time = 0;
	int _groups = 0;
	JLabel _timeLabel;
	JLabel _groupsLabel;

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));

		_timeLabel = new JLabel("Time: " + _time);
		this.add(_timeLabel);
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);

		_groupsLabel = new JLabel("Groups: " + _groups);
		this.add(_groupsLabel);
		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		this.add(s2);
	}

	// el resto de métodos van aquí…
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		_time = time;
		_timeLabel.setText("Time: " + _time);
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_time = 0;
		_groups = 0;
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		_time = 0;
		_groups = 0;
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groups = groups.size();
		_groupsLabel.setText("Groups: " + _groups);
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
