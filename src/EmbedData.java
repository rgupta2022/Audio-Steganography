import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rahul
 *
 */
public class EmbedData extends JInternalFrame {

	// Holds the name of the input audio file
	public String inputAudioFileName;
	
	// Stores the directory path of the input audio file
	public String inputAudioFileDirectory; 
	
	 // Represents the input audio file as a string
	public String inputAudioFileString;
	
	 // Stores the input text file content as a string
	public static String inputTextFileString;
	
	// Stores the input password as a string
	public String inputPasswordString; 
	
	// Represents the output audio file as a string
	public String outputAudioFileString; 
	
	 // Handles audio-related operations
	private Audio audio;
	
	// Holds the input text from the text area
	public String inputTextAreaString; 
	
	// Keeps track of the embed JIF number
	public static int embedJIFNo = 1; 
	
	 // Stores the P embed JIF number
	private int pEmbedJIFNo;
	
	 // Represents a temporary file
	public File tempFile;
	
	// A horizontal separator component
	private JSeparator HSeparator; 
	
	// Button for clearing the password
	private JButton passwordClearButton; 
	
	 // Button for encrypting the audio
	private JButton encryptAudioButton;
	
	// Button for finishing the operation
	private JButton finishButton; 
	
	 // File chooser for selecting the input audio file
	private JFileChooser inputAudioChooser;
	
	 // Button for opening the audio file
	private JButton openAudioButton;
	
	// Label indicating that a file has not been chosen
	private JLabel fileNotChoosenLabel; 
	
	// Text area for writing text
	private JTextArea writeTexttextarea; 
	
	// File chooser for selecting the input text file
	private JFileChooser inputTextChooser; 
	
	// Button for selecting a text file
	private JButton textFileButton; 
	
	// Label indicating the selected input text file
	private JLabel inputTextFileLabel;
	
	 // First separator component
	private JSeparator jSeparator1;
	
	// Second separator component
	private JSeparator jSeparator2; 
	
	// Label indicating the output audio file
	private JLabel outputAudioFileLabel; 
	
	// Label indicating the input password field
	private JLabel inputPasswordLabel; 
	
	// Text field for entering the password
	private JTextField passwordField;
	
	// Button for playing the audio
	private JButton audioPlayButton; 
	
	// Group for radio buttons
	private ButtonGroup radioButtonGroup; 
	
	// Button for stopping the audio playback
	private JButton audioStopButton; 
	
	// Radio button for selecting a text file input
	private JRadioButton textFileRadioButton;
	
	// Radio button for entering text manually
	private JRadioButton writeTextRadioButton; 


	/**
	 * Constructs an instance of the EmbedData class.
	 * Initializes the components and sets the frame icon.
	 * Disables certain buttons initially.
	 */
	public EmbedData() {
	    initializeComponents();

	    // Set frame icon
	    ImageIcon icon = new ImageIcon("images/embed.png");
	    setFrameIcon(icon);

	    // Disable buttons initially
	    this.finishButton.setEnabled(false);
	    this.audioStopButton.setEnabled(false);
	    this.audioPlayButton.setEnabled(false);
	    this.textFileButton.setEnabled(false);
	}

	/**
	 * Initializes the components of the EmbedData frame.
	 */

    private void initializeComponents() {
    	
    	// Create file choosers
        inputAudioChooser = new JFileChooser();
        inputTextChooser = new JFileChooser();
        
     // Create button group for radio buttons
        radioButtonGroup = new ButtonGroup();
        openAudioButton = new JButton();
        fileNotChoosenLabel = new JLabel();
        textFileButton = new JButton();
        inputTextFileLabel = new JLabel();
        passwordField = new JTextField();
        inputPasswordLabel = new JLabel();
        passwordClearButton = new JButton();
        encryptAudioButton = new JButton();
        finishButton = new JButton();
        HSeparator = new JSeparator();
        audioPlayButton = new JButton();
        audioStopButton = new JButton();
        textFileRadioButton = new JRadioButton();
        writeTextRadioButton = new JRadioButton();
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        outputAudioFileLabel = new JLabel();

        inputAudioChooser.setFileFilter(new AudioFilter());

        inputTextChooser.setFileFilter(new AudioFilter());

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Encrypt Audio File (Embed Text To Audio File)");
        setPreferredSize(new Dimension(600, 475));

        openAudioButton.setFont(new Font("Tahoma", 0, 14));
       
        openAudioButton.setText("Open Audio File");
        openAudioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.inputAudioFileButtonActionPerformed(evt);
            }
        });
        
        fileNotChoosenLabel.setFont(new Font("Tahoma", 0, 12));
        fileNotChoosenLabel.setText("File not choosen");

        textFileButton.setFont(new Font("Tahoma", 0, 14));
        textFileButton.setText("Input Text File (.txt)");
        textFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.inputTextFileButtonActionPerformed(evt);
            }
        });
        inputTextFileLabel.setFont(new Font("Tahoma", 0, 12));
        inputTextFileLabel.setText("File Not Choosen");

        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.passwordTextFieldActionPerformed(evt);
            }
        });
        inputPasswordLabel.setFont(new Font("Tahoma", 0, 12));
        inputPasswordLabel.setText("Password To Encrypt");

        passwordClearButton.setText("Clear");
        passwordClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.clearButtonActionPerformed(evt);
            }
        });
        encryptAudioButton.setFont(new Font("Tahoma", 0, 14));
        encryptAudioButton.setText("Encrypt Audio File");
        encryptAudioButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
            	EmbedData.this.encryptAudioFileButtonMouseClicked(evt);
            }
        });
        encryptAudioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.encryptAudioFileButtonActionPerformed(evt);
            }
        });
        finishButton.setFont(new Font("Tahoma", 0, 14));
        finishButton.setText("Finish");
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.finishEButtonActionPerformed(evt);
            }
        });
        audioPlayButton.setText("Play");
        audioPlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
					try {
						EmbedData.this.playButtonActionPerformed(evt);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
            }
        });
        audioStopButton.setText("Stop");
        audioStopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.stopButtonActionPerformed(evt);
            }
        });

        radioButtonGroup.add(this.textFileRadioButton);
        textFileRadioButton.setFont(new Font("Tahoma", 0, 14));
        textFileRadioButton.setText("Text File");
        textFileRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.textFileRadioButtonActionPerformed(evt);
            }
        });
        radioButtonGroup.add(this.writeTextRadioButton);
        writeTextRadioButton.setFont(new Font("Tahoma", 0, 14));
        writeTextRadioButton.setSelected(true);
        writeTextRadioButton.setText("Write Text");
        writeTextRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	EmbedData.this.writeTextRadioButtonActionPerformed(evt);
            }
        });
        outputAudioFileLabel.setText("Output Audio File Location");
        writeTexttextarea = new JTextArea();
        writeTexttextarea.setColumns(20);
        writeTexttextarea.setRows(5);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(39)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(writeTextRadioButton)
        								.addComponent(textFileButton)
        								.addGroup(layout.createSequentialGroup()
        									.addGap(10)
        									.addGroup(layout.createParallelGroup(Alignment.LEADING)
        										.addComponent(inputPasswordLabel)
        										.addComponent(inputTextFileLabel))))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        								.addComponent(writeTexttextarea)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(encryptAudioButton, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
        									.addComponent(passwordClearButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))))
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(audioPlayButton)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(audioStopButton))
        								.addComponent(fileNotChoosenLabel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        								.addComponent(openAudioButton, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
        								.addComponent(textFileRadioButton)))))
        				.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(45, Short.MAX_VALUE))
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(HSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
        			.addComponent(outputAudioFileLabel)
        			.addGap(164))
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addContainerGap(302, Short.MAX_VALUE)
        			.addComponent(finishButton, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
        			.addGap(125))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(22)
        					.addComponent(fileNotChoosenLabel)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addGap(39)
        							.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(audioPlayButton)
        							.addComponent(audioStopButton))))
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(openAudioButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(textFileRadioButton)))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(27)
        					.addComponent(writeTextRadioButton)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(textFileButton)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(inputTextFileLabel))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(18)
        					.addComponent(writeTexttextarea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(50)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
        						.addComponent(inputPasswordLabel))
        					.addGap(70)
        					.addComponent(HSeparator, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(18)
        					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(encryptAudioButton, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
        						.addComponent(passwordClearButton, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(outputAudioFileLabel)))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(finishButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(36))
        );
        getContentPane().setLayout(layout);

        pack();
    }

    private void passwordTextFieldActionPerformed(ActionEvent evt) {
    }
    
    /**
     * @param evt
     */
    private void inputAudioFileButtonActionPerformed(ActionEvent evt) {
        int returnVal = this.inputAudioChooser.showOpenDialog(this);
        if (returnVal == 0) {
            File file = this.inputAudioChooser.getSelectedFile();
            inputAudioFileName = file.getName();
            inputAudioFileDirectory = file.getParent();

            inputAudioFileString = file.getAbsolutePath();
            fileNotChoosenLabel.setText(inputAudioFileString);
        } else {
            System.out.println("File access cancelled by user.");
        }
        audioPlayButton.setEnabled(true);
    }

    private void inputTextFileButtonActionPerformed(ActionEvent evt) {
        int returnVal = inputTextChooser.showOpenDialog(this);
        if (returnVal == 0) {
            File file = inputTextChooser.getSelectedFile();

            inputTextFileString = file.getAbsolutePath();
            inputTextFileLabel.setText(inputTextFileString);
        } else {
            System.out.println("File access cancelled by user.");
        }
    }

    private void clearButtonActionPerformed(ActionEvent evt) {
        passwordField.setText("");
        writeTexttextarea.setText("");
        
    }
    
   

    private void encryptAudioFileButtonActionPerformed(ActionEvent evt) {
        inputPasswordString = passwordField.getText();
        inputTextAreaString = writeTexttextarea.getText();
        
        Boolean encryptOrNot = null;

        Boolean isWriteTextSelected = writeTextRadioButton.isSelected();
        Boolean isTextFileSelected = inputTextFileString != null;

        // Check if "Write Text" option is selected and the text area is empty
        if (isWriteTextSelected && inputTextAreaString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the text to be written.", "Text Missing", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if "Text File" option is selected and no text file is selected
        if (!isWriteTextSelected && !isTextFileSelected) {
            JOptionPane.showMessageDialog(this, "Please select a text file.", "Text File Missing", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        

        // Check if the audio file is selected
        if (inputAudioFileString == null) {
            JOptionPane.showMessageDialog(this, "Please select an audio file.", "Audio File Missing", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
     // Check password strength
        if (!isStrongPassword(inputPasswordString)) {
            JOptionPane.showMessageDialog(this, "Weak password. Please choose a stronger password.", "Weak Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Rest of the code for encryption...
        Boolean txtS = isWriteTextSelected;

        if (txtS.booleanValue()) {
            if (this.inputTextAreaString.equals("")) {
                encryptOrNot = Boolean.valueOf(false);
            } else {
                encryptOrNot = Boolean.valueOf(true);
                try {
                    this.tempFile = File.createTempFile("AudioSteganographypyTemp" + this.pEmbedJIFNo, ".txt");
                } catch (IOException ex) {
                    Logger.getLogger(EmbedData.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedWriter output = null;
                try {
                    output = new BufferedWriter(new FileWriter(this.tempFile));
                    output.write(this.inputTextAreaString);
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(EmbedData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (inputTextFileString != null) {
            encryptOrNot = Boolean.valueOf(true);
        } else {
            encryptOrNot = Boolean.valueOf(false);
        }



        System.out.println("Audio : " + this.inputAudioFileString + "\nAudioDIR : " + this.inputAudioFileDirectory + "\nFileName : " + this.inputAudioFileName + "\nTextFile : " + inputTextFileString + "\nPassword : " + this.inputPasswordString + "\n" + "Output AudioFile : " + this.outputAudioFileString);
        if ((this.inputAudioFileString != null) && (encryptOrNot.booleanValue()) && (!this.inputPasswordString.equals(""))) {
            this.encryptAudioButton.setEnabled(false);
            this.outputAudioFileString = this.inputAudioFileDirectory.concat("/Encrypted-" + this.inputAudioFileName);
            if (txtS.booleanValue()) {
                inputTextFileString = this.tempFile.getAbsolutePath();
                System.out.println("\nTemp Automatic : " + inputTextFileString);
            }
            this.outputAudioFileLabel.setText(this.outputAudioFileString);

            StegoClass e = new StegoClass(this.inputAudioFileString, inputTextFileString, this.outputAudioFileString, this.inputPasswordString.toCharArray());
            e.encode();
            this.finishButton.setEnabled(true);
            try {
                this.tempFile.deleteOnExit();
            } catch (Exception ex) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "1. Select Audio File\n2. Write Text or Select Text File\n3. Enter Password.", "Oops! Something is missing!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean isStrongPassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*]).{8,}$";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(password);
        return matcher.matches();
    }


    private void encryptAudioFileButtonMouseClicked(MouseEvent evt) {
    }

    private void finishEButtonActionPerformed(ActionEvent evt) {
        try {
            this.tempFile.deleteOnExit();
        } catch (Exception ex) {
        }
        dispose();
    }

    private void playButtonActionPerformed(ActionEvent evt) throws IOException {
        this.audioStopButton.setEnabled(true);
		this.audioPlayButton.setEnabled(false);
		try {
			this.audio = new Audio(this.inputAudioFileString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.audio.play();
    }

    private void stopButtonActionPerformed(ActionEvent evt) {
        try {
            this.audio.stop();
        } catch (Exception ex) {
        }
        this.audioPlayButton.setEnabled(true);
        this.audioStopButton.setEnabled(false);
    }

    private void formInternalFrameClosed(InternalFrameEvent evt) {
        try {
            this.audio.stop();
            this.tempFile.deleteOnExit();
        } catch (Exception ex) {
        }
    }

    private void textFileRadioButtonActionPerformed(ActionEvent evt) {
        this.writeTexttextarea.setEnabled(false);
        this.textFileButton.setEnabled(true);
    }

    private void writeTextRadioButtonActionPerformed(ActionEvent evt) {
        this.writeTexttextarea.setEnabled(true);
        this.textFileButton.setEnabled(false);
    }
}
