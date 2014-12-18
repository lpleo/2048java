import java.awt.Color;

import javax.swing.JButton;

public class Pulsante extends JButton {
	private int state;
	private boolean mark;
	public Pulsante (int state) {
		this.state = state;
		this.mark = true;
		this.setEnabled(false);
		Color();
	}
	
	
	private void Color() {
		this.setText(""+state);
		int ris = state;
		while(ris>32) {
			ris = ris/32;
		}
		switch(ris/2){
		case 1: this.setBackground(Color .red); break;
		case 2: this.setBackground(Color .orange); break;
		case 4: this.setBackground(Color .yellow); break;
		case 8: this.setBackground(Color .green); break;
		case 16: this.setBackground(Color .CYAN); break;
		default:  this.setBackground(Color .white); break; }
	}
	
	public void cambiaStato(int state) {
		this.state = state;
		this.Color();
	}
	
	public int prendiStato() {
		return this.state;
	}
	
	public void cambioMark(boolean mark) {
		this.mark=mark;
	}
	
	public boolean prendiMark() {
		return this.mark;
	}
	
}
