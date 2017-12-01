package m2_ihm_project;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageViewer extends JFrame{

	private int state;
	private JPanel current;
	
	private List<ImageIcon> imgs;
	
	// Constructor
	public ImageViewer() {
		super("Image Viewer");
		this.state = 0;
		this.setSize(600, 650);
		this.setLayout(new BorderLayout());
		
		imgs = new ArrayList<ImageIcon>();
		
		// Création des 9 Panels pour les images
		for(int i = 1; i < 10; i++) {
			try {
				BufferedImage img = ImageIO.read(new File("img/img_"+i+".jpg"));
				ImageIcon image = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
				imgs.add(image);
			} catch (IOException e) {
				System.err.println("Unable to load image");
			}
		}
		
		JPanel imgs_pane = new JPanel(new GridLayout(3, 3));
		for(ImageIcon ii: imgs) {
			imgs_pane.add(new JLabel(ii));
		}
		
		// Panel gestion boutons
		
		this.add(imgs_pane, BorderLayout.CENTER);
		this.setVisible(true);

		
	}

	public static void main(String[] args) {
		ImageViewer iv = new ImageViewer();
	}

}
