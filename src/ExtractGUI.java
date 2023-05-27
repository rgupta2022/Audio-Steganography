import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.Graphics;
import java.awt.Image;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ExtractGUI extends JInternalFrame {

	// Variables declaration
	public String encryptedAudioFileName; // Filename of the encrypted audio file
	public String encryptedAudioFileDirectory; // Directory path of the encrypted audio file
	public String encryptedAudioFileString; // String representation of the encrypted audio file
	public String inputPasswordDString; // String representation of the input password
	public String outputTextFileString; // String representation of the output text file
	public Audio audio; // Instance of the Audio class
	private JButton clearPasswordD; // Button for clearing the input password
	private JFileChooser encryptedAudioChooser; // File chooser for selecting the encrypted audio file
	private JButton encryptedAudioFileButton; // Button for choosing the encrypted audio file
	private JLabel encryptedAudioFileLabel; // Label for displaying the selected encrypted audio file
	private JButton extractTextButton; // Button for extracting text from the encrypted audio
	private JLabel extractedTextFromAudioLabel; // Label for displaying the extracted text from the audio
	private JButton finishDButton; // Button for finishing the decryption process
	private JLabel jLabel1; // Label for displaying general information
	private JScrollPane jScrollPane1; // Scroll pane for the text area
	private JLabel outputTextFileLabel; // Label for displaying the output text file
	private JLabel passwordDLabel; // Label for the input password field
	private JTextField passwordDTextField; // Text field for entering the input password
	private JButton playButton; // Button for playing the audio
	private JTextArea showTextArea; // Text area for displaying text
	private JButton stopButton; // Button for stopping the audio playback

    // End of variables declaration

	private Image backgroundImage;

	
	/**
     * Creates new form ExtractGUI.
     * Initializes the components and sets the initial state of the ExtractGUI frame.
     */
	public ExtractGUI() {
		backgroundImage = new ImageIcon("images/wallpaper.jpg").getImage();

		loadComponents();
		ImageIcon icon = new ImageIcon("images/wallpaper.jpg");
		setFrameIcon(icon);
		finishDButton.setEnabled(false);
		stopButton.setEnabled(false);
		playButton.setEnabled(false);

	}

    /**
     * This method is called from within the constructor to initialize the form.
     * Initializes the GUI components.
     */
	private void loadComponents() {
		encryptedAudioChooser = new JFileChooser();
		encryptedAudioFileButton = new JButton();
		passwordDTextField = new JTextField();
		clearPasswordD = new JButton();
		clearPasswordD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		extractTextButton = new JButton();
		encryptedAudioFileLabel = new JLabel();
		passwordDLabel = new JLabel();
		finishDButton = new JButton();
		playButton = new JButton();
		playButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		stopButton = new JButton();
		stopButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		jScrollPane1 = new JScrollPane();
		showTextArea = new JTextArea();
		outputTextFileLabel = new JLabel();
		outputTextFileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		extractedTextFromAudioLabel = new JLabel();
		jLabel1 = new JLabel();

		encryptedAudioChooser.setFileFilter(new AudioFilter());

		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setTitle("Extract Text From Encrypted Audio");
		setPreferredSize(new Dimension(600, 370));
//		addInternalFrameListener(new InternalFrameListener() {
//			public void internalFrameIconified(InternalFrameEvent evt) {
//			}
//
//			public void internalFrameDeiconified(InternalFrameEvent evt) {
//			}
//
//			public void internalFrameDeactivated(InternalFrameEvent evt) {
//			}
//
//			public void internalFrameActivated(InternalFrameEvent evt) {
//			}
//
//			public void internalFrameClosed(InternalFrameEvent evt) {
//				ExtractGUI.this.formInternalFrameClosed(evt);
//			}
//
//			public void internalFrameClosing(InternalFrameEvent evt) {
//			}
//
//			public void internalFrameOpened(InternalFrameEvent evt) {
//			}
//		});
		encryptedAudioFileButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		// this.encryptedAudioFileButton.setIcon(new
		// ImageIcon(getClass().getResource("/images/audioE.png")));
		encryptedAudioFileButton.setText("Encryped Audio File (.au)");
		encryptedAudioFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ExtractGUI.this.encryptedAudioFileButtonActionPerformed(evt);
			}
		});
		passwordDTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ExtractGUI.this.passwordDTextFieldActionPerformed(evt);
			}
		});
		clearPasswordD.setText("Clear");
		clearPasswordD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ExtractGUI.this.clearPasswordDActionPerformed(evt);
			}
		});
		extractTextButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		// this.extractTextButton.setIcon(new
		// ImageIcon(getClass().getResource("/images/extract.png")));
		extractTextButton.setText("Extract Text");
		extractTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ExtractGUI.this.extractTextButtonActionPerformed(evt);
			}
		});
		encryptedAudioFileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		encryptedAudioFileLabel.setText("File Not Choosen");

		passwordDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		passwordDLabel.setText("Password To Decrypt");

		finishDButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		// this.finishDButton.setIcon(new
		// ImageIcon(getClass().getResource("/images/finish.png")));
		finishDButton.setText("Finish");
		finishDButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ExtractGUI.this.finishDButtonActionPerformed(evt);
			}
		});
		// this.playButton.setIcon(new
		// ImageIcon(getClass().getResource("/images/play.png")));
		playButton.setText("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					ExtractGUI.this.playButtonActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// this.stopButton.setIcon(new
		// ImageIcon(getClass().getResource("/images/stop.png")));
		stopButton.setText("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ExtractGUI.this.stopButtonActionPerformed(evt);
			}
		});
		showTextArea.setColumns(20);
		showTextArea.setRows(5);
		jScrollPane1.setColumnHeaderView(this.showTextArea);

		outputTextFileLabel.setText("Output Text File Location");

		extractedTextFromAudioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		extractedTextFromAudioLabel.setText("Extracted Text From Audio");

		// this.jLabel1.setIcon(new
		// ImageIcon(getClass().getResource("/images/rightArrow.png")));
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addGap(185)
							.addComponent(jLabel1)
							.addGap(34))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(clearPasswordD)
								.addGroup(layout.createSequentialGroup()
									.addComponent(playButton)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(stopButton))
								.addComponent(extractTextButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(extractedTextFromAudioLabel, Alignment.LEADING)
								.addComponent(finishDButton, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(layout.createSequentialGroup()
							.addComponent(encryptedAudioFileLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(75)
							.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(passwordDTextField, Alignment.LEADING)
								.addComponent(encryptedAudioFileButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
								.addComponent(passwordDLabel, Alignment.LEADING))))
					.addGap(3))
				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
					.addContainerGap(426, Short.MAX_VALUE)
					.addComponent(outputTextFileLabel)
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(36)
							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
									.addComponent(encryptedAudioFileLabel)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(playButton)
										.addComponent(stopButton)))
								.addComponent(encryptedAudioFileButton, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordDTextField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup()
									.addComponent(clearPasswordD)
									.addGap(3)
									.addComponent(extractTextButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addComponent(outputTextFileLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(Alignment.LEADING, layout.createParallelGroup(Alignment.TRAILING)
											.addComponent(extractedTextFromAudioLabel)
											.addComponent(jLabel1))))
								.addGroup(layout.createSequentialGroup()
									.addGap(49)
									.addComponent(finishDButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))))
						.addGroup(layout.createSequentialGroup()
							.addGap(159)
							.addComponent(passwordDLabel)))
					.addContainerGap())
		);
		getContentPane().setLayout(layout);

		pack();
	}

	protected void passwordDTextFieldActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Handles the action when the encryptedAudioFileButton is clicked. It prompts the user to select an encrypted audio file
	 * using a file chooser dialog. If a file is chosen, the file name, directory, and absolute path are stored for later use.
	 * The selected file information is displayed in the encryptedAudioFileLabel. Additionally, it enables the playButton for
	 * audio playback.
	 *
	 * @param evt the action event triggered by clicking the encryptedAudioFileButton
	 */
	
	private void encryptedAudioFileButtonActionPerformed(ActionEvent evt) {
		int returnVal = this.encryptedAudioChooser.showOpenDialog(this);
		if (returnVal == 0) {
			File file = encryptedAudioChooser.getSelectedFile();
			encryptedAudioFileName = file.getName();
			encryptedAudioFileDirectory = file.getParent();

		encryptedAudioFileString = file.getAbsolutePath();
			encryptedAudioFileLabel.setText(encryptedAudioFileString);
		} else {
			System.out.println("File access cancelled by user.");
		}
		playButton.setEnabled(true);
	}

	/**
	 * Clears the text entered in the passwordDTextField. This method is triggered when the clearPasswordD button is clicked,
	 * resetting the password field to an empty state.
	 *
	 * @param evt the action event triggered by clicking the clearPasswordD button
	 */

	
	private void clearPasswordDActionPerformed(ActionEvent evt) {
		passwordDTextField.setText("");
	}

	/**
	 * Performs the action when the extractTextButton is clicked. It extracts text from the encrypted audio file using the
	 * provided password and displays the extracted text in the showTextArea. If the necessary conditions are not met (audio file
	 * not selected or password not entered), it displays an error message. Additionally, it reads the extracted text from the
	 * output file and sets it in the showTextArea for display.
	 *
	 * @param evt the action event triggered by clicking the extractTextButton
	 */
	
	private void extractTextButtonActionPerformed(ActionEvent evt) {
	    inputPasswordDString = passwordDTextField.getText();

	    if (encryptedAudioFileString != null) {
	        if (!inputPasswordDString.isEmpty()) {
	            // Both audio file and password are provided
	            finishDButton.setEnabled(true);
	            extractTextButton.setEnabled(false);

	            outputTextFileString = encryptedAudioFileDirectory
	                    .concat("/ExtractedText-From-" + encryptedAudioFileName + ".txt");
	            outputTextFileLabel.setText(outputTextFileString);

	            StegoClass d = new StegoClass(
	                    encryptedAudioFileString,
	                    outputTextFileString,
	                    inputPasswordDString.toCharArray()
	            );
	            d.decode();

	            try (BufferedReader br = new BufferedReader(new FileReader(this.outputTextFileString))) {
	                StringBuilder sb = new StringBuilder();
	                String lineRead = br.readLine();

	                while (lineRead != null) {
	                    sb.append(lineRead);
	                    sb.append('\n');
	                    lineRead = br.readLine();
	                }

	                String everything = sb.toString();
	                this.showTextArea.setText(everything);
	            } catch (IOException ex) {
	                Logger.getLogger(ExtractGUI.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        } else {
	            // Display an error pop-up when the password is empty
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Please enter a password.",
	                    "Empty Password",
	                    JOptionPane.ERROR_MESSAGE
	            );
	        }
	    } else {
	        // Display an error pop-up when the file is not selected
	        JOptionPane.showMessageDialog(
	                this,
	                "Please select an audio file.",
	                "File not selected",
	                JOptionPane.ERROR_MESSAGE
	        );
	    }
	}


	/**
	 * Handles the action when the "Finish" button is clicked.
	 * Disposes of the current window.
	 *
	 * @param evt the action event
	 */
	private void finishDButtonActionPerformed(ActionEvent evt) {
	    dispose();
	}


//	private void passwordDTextFieldActionPerformed(ActionEvent evt) {
//	}

	/**
	 * Handles the action when the "Play" button is clicked.
	 * Enables the "Stop" button and disables the "Play" button.
	 * Initializes and plays the audio file.
	 *
	 * @param evt the action event
	 * @throws UnsupportedAudioFileException if the audio file format is not supported
	 * @throws LineUnavailableException if a line cannot be opened or accessed
	 * @throws FileNotFoundException if the audio file is not found
	 * @throws IOException if an I/O error occurs while playing the audio file
	 */
	/**
	 * Handles the action when the "Play" button is clicked.
	 * Enables the "Stop" button and disables the "Play" button.
	 * Initializes and plays the audio file.
	 *
	 * @param evt the action event
	 */
	private void playButtonActionPerformed(ActionEvent evt) {
	    stopButton.setEnabled(true);
	    playButton.setEnabled(false);
	    try {
	        audio = new Audio(this.encryptedAudioFileString);
	        audio.play();
	    } catch (UnsupportedAudioFileException | LineUnavailableException  | IOException ex) {
	        // Handle any exceptions that occur during audio playback
	        ex.printStackTrace();
	        // Display an error message to the user
	        JOptionPane.showMessageDialog(this, "Error playing the audio file.", "Playback Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	/**
	 * Handles the action when the "Stop" button is clicked.
	 * Stops the audio playback, if it is currently playing.
	 * Enables the "Play" button and disables the "Stop" button.
	 *
	 * @param evt the action event
	 */
	/**
	 * Handles the action when the "Stop" button is clicked.
	 * Stops the audio playback, if it is currently playing.
	 * Enables the "Play" button and disables the "Stop" button.
	 *
	 * @param evt the action event
	 */
	private void stopButtonActionPerformed(ActionEvent evt) {
	    try {
	        if (this.audio != null) {
	            // Stop the audio playback
	            this.audio.stop();
	        }
	    } catch (Exception ex) {
	        // Handle any exceptions that occur during audio stopping
	        ex.printStackTrace();
	    }
	    
	    // Enable the "Play" button and disable the "Stop" button
	    this.playButton.setEnabled(true);
	    this.stopButton.setEnabled(false);
	}



	/**
	 * Handles the event when the internal frame is closed.
	 *
	 * @param evt The InternalFrameEvent representing the event of the internal frame being closed.
	 */
	private void formInternalFrameClosed(InternalFrameEvent evt) {
	    try {
	        // Stop the audio playback.
	        this.audio.stop();
	    } catch (Exception ex) {
	        // An exception occurred while stopping the audio. Log or handle the exception appropriately.
	        // In this case, the exception is silently ignored.
	    }
	}


}
