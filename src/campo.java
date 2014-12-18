import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class campo extends JFrame implements KeyListener{
		private Pulsante[][] pulsanti;
		private int punteggio;
		//TODO fare punteggio
		public campo() {
			super("2048 Fatto meglio della dama");
			this.addKeyListener(this);
			this.setLayout(new BorderLayout());
			setSize(400,400);
			getContentPane().setBackground(Color.DARK_GRAY);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			add(dx(), BorderLayout.CENTER);
			creaMenu();
		}
		
		private void creaMenu() {
			JMenuItem Gioco = new JMenuItem("Nuova Partita");
			JMenuItem Licenza = new JMenuItem("Licenza");
			JMenuItem Esci = new JMenuItem("Esci");
			
			JMenu testo = new JMenu("Menu");
			testo.add(Gioco);
			testo.add(Licenza);
			testo.add(Esci);
						
			JMenuBar bar = new JMenuBar();
			bar.add(testo);
		
			Gioco.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO
				}
			});
			Licenza.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					javax.swing.JOptionPane.showMessageDialog(null, "Fatto da Piccoli Leonardo\n"
							+ "l'utilizzo è libero");		
				}
			});
			Esci.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			this.setJMenuBar(bar);
		}
		
		private JPanel dx() {
			JPanel pannello = new JPanel();
			pannello.setSize(400,400);
			pannello.setLayout(new GridLayout());
			pannello.setLayout(new GridLayout(4,4));
			pulsanti = new Pulsante[4][4];
			int aCaso[] = new int[2]; 
			Random random = new Random();
			aCaso[0] = random.nextInt(4);
			aCaso[1] = random.nextInt(4);
			for(int c=0;c<4;c++){
				for(int i=0;i<4;i++) {			
					if(c==aCaso[0]&&i==aCaso[1]) pulsanti[c][i] = new Pulsante(2);
					else pulsanti[c][i] = new Pulsante(0);
					pannello.add(pulsanti[c][i]);
				}		
			}	
			return pannello;
		}
		
		public void nuovo() {
			int p[] = new int[2]; //x e y
			Random random = new Random();
			int cc=0;
			while(cc!=-1 && cc<100) {
			p[0] = random.nextInt(4);
			p[1] = random.nextInt(4);
			if(pulsanti[p[0]][p[1]].prendiStato()==0) {
				pulsanti[p[0]][p[1]].cambiaStato(2);
				cc=-1;
				break;
			}
			else cc++;
			}
			
			if(cc>0) {
				for(int c=0;c<4;c++){
					for(int i=0;i<4;i++) {	
						if(pulsanti[c][i].prendiStato()==0 && cc<0) {
							pulsanti[c][i].cambiaStato(2);
							cc=-5;
						}
					}
				}
			}
				
			if(cc!=-5 && cc>0) {
				if(!controlloFine()) {
					javax.swing.JOptionPane.showMessageDialog( null, "Fine! il tuo punteggio è: " + ritornaPunteggio());
					this.setVisible(false);
					this.dispose();
				}
			}
		}
		
		public void muovi(char verso) {
			if(!controlloFine()) {
				javax.swing.JOptionPane.showMessageDialog( null, "Fine! il tuo punteggio è: " + ritornaPunteggio());
				this.setVisible(false);
				this.dispose();
			}
			String statoIniziale = this.statoCampo();
			switch(verso) {
			case 'w': muoviSu(); break;
			case 'a': muoviSx(); break;
			case 's': muoviGi(); break;
			case 'd': muoviDx(); break;
			}
			ripristinaMark();
			if(this.statoCampo().compareTo(statoIniziale)!=0) this.nuovo();
			this.repaint();
			this.revalidate();
			if(!controlloFine()) {
				javax.swing.JOptionPane.showMessageDialog( null, "Fine! il tuo punteggio è: " + ritornaPunteggio());
				this.setVisible(false);
				this.dispose();
			}
			System.out.println(this.statoCampo());
		}
		
		public String statoCampo() {
			String ret="";
			for(int c=0;c<4;c++){
				for(int i=0;i<4;i++) {
					ret+=pulsanti[c][i].prendiStato()+" ";
				}
				ret+="\n";
			}
			return ret;
		}
		
		public boolean controlloFine() {
			for(int c=0;c<4;c++){
				for(int i=0;i<4;i++) {
					if(pulsanti[c][i].prendiStato()==0) return true;
				}
			}
			int cont=0;
			for(int c=0;c<4;c++){
				for(int i=0;i<4;i++) {
						int stato = pulsanti[c][i].prendiStato();
						int s=-1,g=-1,l=-1,r=-1;
						if(c-1>=0) s=pulsanti[c-1][i].prendiStato();
						if(c+1<4) g=pulsanti[c+1][i].prendiStato();
						if(i-1>=0)	l=pulsanti[c][i-1].prendiStato();
						if(i+1<4) r=pulsanti[c][i+1].prendiStato();
						if(stato!=s && stato!=g && stato!=l && stato!=r) {
							cont++;
						}
					}
				}
			if(cont==16) {
				return false;
			}
			
			return true;
		}
		
		private void muoviSu() {
			int stato1,stato2;
			boolean mark1,mark2;
			for(int c=0;c<4;c++){
				for(int i=0;i<4;i++) {
					if(c-1 >= 0) {
						stato1=pulsanti[c][i].prendiStato();
						stato2=pulsanti[c-1][i].prendiStato();
						mark1=pulsanti[c][i].prendiMark();
						mark2=pulsanti[c-1][i].prendiMark();
						if(stato1==stato2 && stato1!=0) {
							if(mark1 && mark2) {
								pulsanti[c][i].cambiaStato(0);
								pulsanti[c-1][i].cambiaStato(stato1*2);
								punteggio+=stato1*2;
								pulsanti[c-1][i].cambioMark(false);
								pulsanti[c][i].cambioMark(true);
								muoviSu();
							}
						}
						else if(stato1!=0 && stato2==0) {
							pulsanti[c][i].cambiaStato(0);
							pulsanti[c-1][i].cambiaStato(stato1);
							pulsanti[c-1][i].cambioMark(pulsanti[c][i].prendiMark());
							pulsanti[c][i].cambioMark(true);
							muoviSu();
						}
					}
				}
			}
		}
		
		private void muoviGi() {
			int stato1,stato2;
			boolean mark1,mark2;
			for(int c=3;c>=0;c--){
				for(int i=0;i<4;i++) {
					if(c+1 < 4) {
						stato1=pulsanti[c][i].prendiStato();
						stato2=pulsanti[c+1][i].prendiStato();
						mark1=pulsanti[c][i].prendiMark();
						mark2=pulsanti[c+1][i].prendiMark();
						if(stato1==stato2 && stato1!=0) {
							if(mark1 && mark2) {
								pulsanti[c][i].cambiaStato(0);
								pulsanti[c+1][i].cambiaStato(stato1*2);
								punteggio+=stato1*2;
								pulsanti[c+1][i].cambioMark(false);
								pulsanti[c][i].cambioMark(true);
								muoviGi();
							}
						}
						else if(stato1!=0 && stato2==0) {
							pulsanti[c][i].cambiaStato(0);
							pulsanti[c+1][i].cambiaStato(stato1);
							pulsanti[c+1][i].cambioMark(pulsanti[c][i].prendiMark());
							pulsanti[c][i].cambioMark(true);
							muoviGi();
						}
					}
				}
			}
		}
		
		private void muoviSx() {
			int stato1,stato2;
			boolean mark1,mark2;
			for(int c=0;c<4;c++){
				for(int i=0;i<4;i++) {
					if(i-1 >= 0) {
						stato1=pulsanti[c][i].prendiStato();
						stato2=pulsanti[c][i-1].prendiStato();
						mark1=pulsanti[c][i].prendiMark();
						mark2=pulsanti[c][i-1].prendiMark();
						if(stato1==stato2 && stato1!=0) {
							if(mark1 && mark2) {
								pulsanti[c][i].cambiaStato(0);
								pulsanti[c][i-1].cambiaStato(stato1*2);
								punteggio+=stato1*2;
								pulsanti[c][i-1].cambioMark(false);
								pulsanti[c][i].cambioMark(true);
								muoviSx();
							}
						}
						else if(stato1!=0 && stato2==0) {
							pulsanti[c][i].cambiaStato(0);
							pulsanti[c][i-1].cambiaStato(stato1);
							pulsanti[c][i-1].cambioMark(pulsanti[c][i].prendiMark());
							pulsanti[c][i].cambioMark(true);
							muoviSx();
						}
					}
				}
			}
		}
		
		private void muoviDx() {
			int stato1,stato2;
			boolean mark1,mark2;
			for(int c=0;c<4;c++){
				for(int i=3;i>=0;i--) {
					if(i+1 < 4) {
						stato1=pulsanti[c][i].prendiStato();
						stato2=pulsanti[c][i+1].prendiStato();
						mark1=pulsanti[c][i].prendiMark();
						mark2=pulsanti[c][i+1].prendiMark();
						if(stato1==stato2 && stato1!=0) {
							if(mark1 && mark2) {
								pulsanti[c][i].cambiaStato(0);
								pulsanti[c][i+1].cambiaStato(stato1*2);
								punteggio+=stato1*2;
								pulsanti[c][i+1].cambioMark(false);
								pulsanti[c][i].cambioMark(true);
								muoviDx();
							}
						}
						else if(stato1!=0 && stato2==0) {
							pulsanti[c][i].cambiaStato(0);
							pulsanti[c][i+1].cambiaStato(stato1);
							pulsanti[c][i+1].cambioMark(pulsanti[c][i].prendiMark());
							pulsanti[c][i].cambioMark(true);
							muoviDx();
						}
					}
				}
			}
		}
		
		private void ripristinaMark() {
			for(int c=0;c<4;c++){
				for(int i=0;i<4;i++) {
					pulsanti[c][i].cambioMark(true);
				}
			}
		}
		
		public int ritornaPunteggio() {
			return this.punteggio;
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getKeyCode() == KeyEvent.VK_W || arg0.getKeyCode() == KeyEvent.VK_UP) {
				this.muovi('w');
			}
			
			if(arg0.getKeyCode() == KeyEvent.VK_A || arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				this.muovi('a');
			}
			
			if(arg0.getKeyCode() == KeyEvent.VK_S || arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				this.muovi('s');
			}
			
			if(arg0.getKeyCode() == KeyEvent.VK_D || arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.muovi('d');
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			//NULL
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			//NULL
			
		}
}
