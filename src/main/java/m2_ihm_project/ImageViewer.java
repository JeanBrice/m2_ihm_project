package m2_ihm_project;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageViewer extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8780336039914076327L;
	// Attributes
	private int state, current, timerLevel;
	JPanel imgs_pane, btns_pane;
	JButton backward, play, stop, forward;
	
	private List<ImageIcon> imgs;
	
	// Constructor
	public ImageViewer() {
		super("Image Viewer");
		this.state = 0;
		this.setSize(600, 650);
		this.setLayout(new BorderLayout());
		
		this.timerLevel = 1;
		
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
		this.play.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {}			
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
			public void mouseClicked(MouseEvent e) {
				setState(2);
				behave();
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
	
	public void setState(int i) {
		this.state = i;
	}
	
	public void initImg() {
		// Création des 9 Panels pour les images
		this.imgs = new ArrayList<ImageIcon>();
		for(int i = 1; i < 10; i++) {
			try {
				BufferedImage img = ImageIO.read(new File("img/img_"+i+".jpg"));
				ImageIcon image = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
				this.imgs.add(image);
			} catch (IOException e) {
				System.err.println("Unable to load image");
			}
		}
	}
	
	// Mode liste
	public void showList() {
		//this.initImg();
		this.imgs_pane = new JPanel(new GridLayout(3, 3));
		for(ImageIcon ii: imgs) {
			this.imgs_pane.add(new JLabel(ii));
		}
	}
	
	// Mode visionneuse
	public void showImg() {
		try {
			this.imgs_pane = new JPanel();
			ImageIcon curr = new ImageIcon(ImageIO.read(new File("img/img_"+ this.current +".jpg")));
			this.imgs_pane.add(new JLabel(curr));
			this.add(imgs_pane, BorderLayout.CENTER);
		} catch (IOException e) {
			System.err.println("Unable to load image");
		}
	}
	
	// Timer
	public void justWait(int time) {
		double curr = System.currentTimeMillis();
		while(System.currentTimeMillis() < curr + time) {
			// Just wait
		}
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
	
	// Comportement de l'application selon les états
	public void behave() {
		System.out.println("State: " + this.state);
		// TODO : comportement selon les etats
		switch(this.state) {
		case 0:
			current = 1;
			showList();
			backward.setEnabled(false);
			forward.setEnabled(false);
			stop.setEnabled(false);
			break;
			
		case 1:
			break;
			
		case 2:
			showImg();
			backward.setEnabled(true);
			play.setText("Pause");
			stop.setEnabled(true);
			forward.setEnabled(true);
			//justWait(getTimeToWait());
			if(current < 9)
				current += 1;
			else
				current = 1;
			behave();
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
