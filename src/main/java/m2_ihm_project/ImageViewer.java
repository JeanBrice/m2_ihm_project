package m2_ihm_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class ImageViewer extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8780336039914076327L;
	// Attributes
	private int state, current, timerLevel;
	JPanel imgs_pane, btns_pane;
	JButton backward, play, stop, forward;
	private Timer t;	
	
	private List<JLabel> imgs;
	private List<JPanel> wide_imgs;
	
	// Constructor
	public ImageViewer() {
		super("Image Viewer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.state = 0;
		this.setSize(600, 650);
		this.setLayout(new BorderLayout());
		
		this.timerLevel = 1;
		this.t = new Timer(getTimeToWait(), new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				behave();
			}
		});
		
		this.initImg();
		this.showList();
				
		// Panel gestion boutons
		this.btns_pane = new JPanel();
		this.btns_pane.setLayout(new GridLayout(1, 4));
		
		this.backward = new JButton("<<");
		this.play = new JButton("Start");
		this.stop = new JButton("Stop");
		this.forward = new JButton(">>");
		
		this.btns_pane.add(backward);
		this.btns_pane.add(play);
		this.btns_pane.add(stop);
		this.btns_pane.add(forward);
		
		// Btns listener
		this.stop.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {}			
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
			public void mouseClicked(MouseEvent e) {
				t.stop();
				setState(0);
				behave();
			}
		});
		
		
		this.play.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {}			
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
			public void mouseClicked(MouseEvent e) {
				if(play.getText().equals("Start")) {
					setState(2);
					behave();
				} else {
					setState(1);
					t.stop();
					behave();
				}
			}
		});
		
		this.forward.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {}			
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
			public void mouseClicked(MouseEvent e) {
				speedUp();
			}
		});
		
		this.backward.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {}			
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
			public void mouseClicked(MouseEvent e) {
				speedDown();
			}
		});
		
		// Ajout des composants
		this.add(this.imgs_pane, BorderLayout.CENTER);
		this.add(this.btns_pane, BorderLayout.SOUTH);
		
		// Behaviour
		this.behave();
		
		// Display UI
		this.setVisible(true);

		
	}
	
	public void speedUp() {
		if(this.timerLevel < 3 && this.forward.isEnabled()) {
			if(this.timerLevel == -1) {
				this.timerLevel = 1;
			} else {
				this.timerLevel += 1;
			}
		}
	}
	
	public void speedDown() {
		if(this.timerLevel > -3 && this.backward.isEnabled()) {
			if(this.timerLevel == 1) {
				this.timerLevel = -1;
			} else {
				this.timerLevel -= 1;
			}
		}
	}
	
	public void setState(int i) {
		this.state = i;
	}
	
	public void initImg() {
		// Cr�ation des 9 Panels pour les images
		this.imgs = new ArrayList<JLabel>();
		for(int i = 1; i < 10; i++) {
			try {
				BufferedImage img = ImageIO.read(new File("img/img_"+i+".jpg"));
				ImageIcon image = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
				this.imgs.add(new JLabel(image));
			} catch (IOException e) {
				System.err.println("Unable to load image");
			}
		}
		wide_imgs = new ArrayList<JPanel>();
		for(int i = 1; i < 10; i++) {
			try {
				JPanel jp = new JPanel();
				ImageIcon curr = new ImageIcon(ImageIO.read(new File("img/img_"+ i +".jpg")));
				jp.add(new JLabel(curr));
				wide_imgs.add(jp);
			} catch (IOException e) {
				System.err.println("Unable to load image : " + e.getMessage());
			}
		}
	}
	
	// Mode liste
	public void showList() {
		//this.initImg();
		this.imgs_pane = new JPanel(new GridLayout(3, 3));
		for(final JLabel lab: imgs) {
			lab.addMouseListener(new MouseListener() {				
				public void mouseReleased(MouseEvent e) {}				
				public void mousePressed(MouseEvent e) {}				
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				
				public void mouseClicked(MouseEvent e) {
					for(JLabel l: imgs) {
						l.setBorder(null);
					}
					Border bor = BorderFactory.createLineBorder(Color.BLUE,3);
					lab.setBorder(bor);
					current = imgs.indexOf(lab)+1;
				}
			});
			this.imgs_pane.add(lab);
		}
		this.add(this.imgs_pane, BorderLayout.CENTER);
		this.revalidate();
	}
	
	// Mode visionneuse
	public void showImg() {
		try {
			this.imgs_pane = new JPanel();
			ImageIcon curr = new ImageIcon(ImageIO.read(new File("img/img_"+ this.current +".jpg")));
			this.imgs_pane.add(new JLabel(curr));
			this.add(imgs_pane, BorderLayout.CENTER);
			this.revalidate();
		} catch (IOException e) {
			System.err.println("Unable to load image");
		}
		
	}
	
	// Timer
	public void justWait(int time) {
		this.t.setDelay(time);
		t.start();
	}
	
	// set timer level
	public void setTimerLevel(int i) {
		this.timerLevel = i;
	}
	
	public int getTimeToWait() {
		switch(this.timerLevel) {
		case 1:
			return 3000;
		case 2:
			return 2000;
		case 3:
			return 1000;
		case -1:
			return 3000;
		case -2:
			return 2000;
		case -3:
			return 1000;
		default:
			return 3000;
		}
	}
	
	public void getNext(){
		if(this.timerLevel > 0) {
			if(this.current < 9)
				this.current += 1;
			else
				this.current = 1;
		} else {
			if(this.current > 1)
				this.current -= 1;
			else
				this.current = 9;
		}
	}
	
	// Comportement de l'application selon les �tats
	public void behave() {
		System.out.println("State: " + this.state);
		switch(this.state) {
		case 0:
			t.stop();
			current = 1;
			showList();
			backward.setEnabled(false);
			forward.setEnabled(false);
			stop.setEnabled(false);
			play.setText("Start");
			play.setToolTipText("Start slideshow");
			break;
			
		case 1:
			t.stop();
			backward.setEnabled(false);
			forward.setEnabled(false);
			play.setText("Start");
			play.setToolTipText("Resume slideshow");
			break;
			
		case 2:
			play.setText("Pause");
			play.setToolTipText("Pause slideshow");
			stop.setEnabled(true);
			stop.setToolTipText("Stop slideshow");
			if(this.timerLevel > 2) {
				System.out.println("max. positive speed reached");
				forward.setEnabled(false);
			}
			if(this.timerLevel > -3) {
				backward.setEnabled(true);
				backward.setToolTipText("Slow down or reverse scrolling direction");
			}
			if(this.timerLevel < -2) {
				System.out.println("max. negative speed reached");
				backward.setEnabled(false);
			}
			if(this.timerLevel < 3) {
				forward.setEnabled(true);
				forward.setToolTipText("Speed up");
			}
			showImg();
			getNext();
			justWait(getTimeToWait());
			break;
			
		case 3:
			break;
			
		default:
			break;
		}
	}

	// Main
	public static void main(String[] args) {
		ImageViewer iv = new ImageViewer();
	}

}
