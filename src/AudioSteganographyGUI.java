import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Image;
import javax.swing.ImageIcon;


public class AudioSteganographyGUI extends JFrame {

	  	public static int embedJIFX = 0;
	    public static int embedJIFY = 0;
	    public static int extractJIFX = 600;
	    public static int extractJIFY = 0;
	    private JDesktopPane jDesktopPane;
	    private JButton EncloseButton;
	    private JButton DiscloseButton;
	    private JButton ExitButton;
	    private JLabel wallpaperLabel;
	    private Image backgroundImage;


	    public AudioSteganographyGUI() {
	    	loadComponent();
	        setExtendedState(getExtendedState() | 0x6);

	        setResizable(true);
	        setTitle("Audio Steganography");
	        ImageIcon icon = new ImageIcon("");
	        setIconImage(icon.getImage());
	    }

	    private void loadComponent() {

	        jDesktopPane = new JDesktopPane();
	        wallpaperLabel = new JLabel();
	        EncloseButton = new JButton();
	        DiscloseButton = new JButton();
	        ExitButton = new JButton();

	        setDefaultCloseOperation(3);
	        setCursor(new Cursor(0));

	        jDesktopPane.setBackground(new java.awt.Color(255, 255, 255));
	        wallpaperLabel.setIcon(new ImageIcon(getClass().getResource("/images/bg.jpg")));
	        wallpaperLabel.setBounds(0, 0, 728, 800);
	        jDesktopPane.add(wallpaperLabel, JLayeredPane.DEFAULT_LAYER);

	        EncloseButton.setFont(new Font("Tahoma", 0, 14));
	        EncloseButton.setText("Embed Text To Audio");
	        EncloseButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	AudioSteganographyGUI.this.EmbedButtonActionPerformed(evt);
	            }
	        });
	        DiscloseButton.setFont(new Font("Tahoma", 0, 14));
	        DiscloseButton.setText("Extract Text From Audio");
	        DiscloseButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	AudioSteganographyGUI.this.ExtractButtonActionPerformed(evt);
	            }
	        });
	        ExitButton.setText("Exit");
	        ExitButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	AudioSteganographyGUI.this.jButton1ActionPerformed(evt);
	            }
	        });

	        GroupLayout layout = new GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jDesktopPane).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.EncloseButton, -2, 228, -2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.DiscloseButton, -2, 223, -2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, 32767).addComponent(this.ExitButton, -2, 100, -2).addContainerGap()));

	        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jDesktopPane, -1, 408, 32767).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.EncloseButton).addComponent(this.DiscloseButton)).addComponent(this.ExitButton, -2, 30, -2)).addGap(18, 18, 18)));
	    }

	    private void EmbedButtonActionPerformed(ActionEvent evt) {
	        EmbedData embedJIF = new EmbedData();
	        this.jDesktopPane.add(embedJIF);
	        embedJIF.show();
	    }

	    private void ExtractButtonActionPerformed(ActionEvent evt) {
	        ExtractGUI extractJIF = new ExtractGUI();
	        jDesktopPane.add(extractJIF);
	        extractJIF.show();
	    }

	    private void jButton1ActionPerformed(ActionEvent evt) {
	        System.exit(0);
	    }

	    public static void main(String[] args) {
	        try {
	        	 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new AudioSteganographyGUI().setVisible(true);
	            }
	        });
	    }
}
