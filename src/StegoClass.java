import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class StegoClass {
	public boolean feasible = true; // Indicates whether a certain condition is feasible or not

		private AudioInputStream audioInputStream; // Represents an audio input stream
		private AudioFormat sndFormat; // Represents the format of the audio data
		private byte[] audioBytes; // Stores the audio data as bytes
		private byte[] buff; // Buffer used for audio processing
		private byte[] cipherbuff; // Buffer used for encrypted audio data
		private byte[] clearbuff; // Buffer used for decrypted audio data
		private String outFile; // Specifies the output file path
		private char password[]; // Stores the password as a character array
		private PBEKeySpec pbeKeySpec; // Represents a password-based encryption key specification


		/**
		 * Constructs a new instance of StegoClass.
		 * 
		 * @param sndFile  The path of the sound file
		 * @param ptFile   The path of the plaintext file
		 * @param oFile    The output file path
		 * @param pwd      The password as a character array
		 */
		public StegoClass(String sndFile, String ptFile, String oFile, char pwd[]) {
		    password = pwd; // Set the password
		    outFile = oFile; // Set the output file path
		    readSND(sndFile); // Read the sound file
		    feasible = possible(ptFile); // Check feasibility of hiding the data
		}

		/**
		 * Constructs a new instance of StegoClass.
		 * 
		 * @param sndFile  The path of the sound file
		 * @param ptFile   The path of the plaintext file (output file path)
		 * @param pwd      The password as a character array
		 */
		public StegoClass(String sndFile, String ptFile, char pwd[]) {
		    password = pwd; // Set the password
		    outFile = ptFile; // Set the output file path
		    readSND(sndFile); // Read the sound file

		    System.out.println("Pwd " + pwd); // Print the password (for debugging or informational purposes)
		}


		/**
		 * Encodes the ciphertext into the audio file.
		 */
		public void encode() {
		    int k = 0;
		    int i = 1; // Start of plaintext in audioBytes
		    int pt;
		    byte pb;

		    System.out.println("Hiding the ciphertext in AU file ...");

		    // First encode the length of the plaintext
		    pt = cipherbuff.length;
		    for (int j = 1; j <= 32; j++) {
		        if ((pt & 0x80000000) != 0) {
		            // MSB of pt is '1'
		            audioBytes[i] = (byte) (audioBytes[i] | 0x01);
		        } else if ((audioBytes[i] & 0x01) != 0) {
		            // MSB of pt '0' and lsb of audio '1'
		            audioBytes[i] = (byte) (audioBytes[i] >>> 1);
		            audioBytes[i] = (byte) (audioBytes[i] << 1);
		        }
		        pt = (int) (pt << 1);
		        i++;
		    }

		    // Now start encoding the message itself!
		    while (k < cipherbuff.length) {
		        pb = cipherbuff[k];
		        for (int j = 1; j <= 8; j++) {
		            if ((pb & 0x80) != 0) {
		                // MSB of pb is '1'
		                audioBytes[i] = (byte) (audioBytes[i] | 0x01);
		            } else if ((audioBytes[i] & 0x01) != 0) {
		                // MSB of pt '0' and lsb of audio '1'
		                audioBytes[i] = (byte) (audioBytes[i] >>> 1);
		                audioBytes[i] = (byte) (audioBytes[i] << 1);
		            }
		            pb = (byte) (pb << 1);
		            i++;
		        }
		        k++;
		    }

		    System.out.println();

		    // Write the byte array to an audio file
		    File fileOut = new File(outFile);
		    ByteArrayInputStream byteIS = new ByteArrayInputStream(audioBytes);
		    AudioInputStream audioIS = new AudioInputStream(byteIS,
		            audioInputStream.getFormat(), audioInputStream.getFrameLength());
		    
		    if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.AU, audioIS)) {
		        try {
		            AudioSystem.write(audioIS, AudioFileFormat.Type.AU, fileOut);
		            System.out.println("Steganographed AU file is written as " + outFile + "...");
		        } catch (Exception e) {
		            System.err.println("Sound File write error");
		        }
		    }
		}


	    //----------------------------------------------
		/**
		 * Decodes the ciphertext from the audio file.
		 *
		 * @return true if decoding is successful, false otherwise.
		 */
		public boolean decode() {
		    boolean success = true;
		    byte out = 0;
		    int length = 0;
		    int k = 1; // Start of plaintext in audioBytes

		    System.out.println("Retrieving the ciphertext from AU file ....");

		    // First decode the first 32 samples to find the length of the message text
		    length = length & 0x00000000;
		    for (int j = 1; j <= 32; j++) {
		        length = length << 1;
		        if ((audioBytes[k] & 0x01) != 0) {
		            length = length | 0x00000001;
		        }
		        k++;
		    }

		    buff = new byte[length];

		    // Now decode the message!
		    out = (byte) (out & 0x00);
		    for (int i = 0; i < length; i++) {
		        for (int j = 1; j <= 8; j++) {
		            out = (byte) (out << 1);
		            if ((audioBytes[k] & 0x01) != 0) {
		                out = (byte) (out | 0x01);
		            }
		            k++;
		        }
		        buff[i] = out;
		        out = (byte) (out & 0x00);
		    }

		    decrypt();

		    try {
		        System.out.println("Writing the decrypted hidden message to " + outFile + " ...");
		        FileOutputStream outfile = new FileOutputStream(outFile);
		        outfile.write(clearbuff);
		        outfile.close();
		    } catch (Exception e) {
		        System.out.println("Caught Exception: " + e);
		    }

		    return success;
		}


	  //----------------------------------------------
	    // method to read the sound file
		/**
		 * Reads the (AU) sound file and stores the audio data in the audioBytes array.
		 *
		 * @param sndf the path of the sound file to be read.
		 */
		private void readSND(String sndf) {
		    File sndfile = new File(sndf);
		    int totalFramesRead = 0;

		    System.out.println("Reading (AU) sound file ...");

		    try {
		        audioInputStream = AudioSystem.getAudioInputStream(sndfile);
		        int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
		        int numBytes = 154600 * bytesPerFrame; // Set an arbitrary buffer size of 1024 frames
		        audioBytes = new byte[numBytes];

		        try {
		            int numBytesRead = 0;
		            int numFramesRead = 0;

		            // Try to read numBytes bytes from the file.
		            while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
		                // Calculate the number of frames actually read.
		                numFramesRead = numBytesRead / bytesPerFrame;
		                totalFramesRead += numFramesRead;

		                // Here, work with the audio data that's now in the audioBytes array...
		            }
		        } catch (Exception ex) {
		            // Handle the error...
		            System.out.println("Audio file error: " + ex);
		        }
		    } catch (Exception e) {
		        // Handle the error...
		        System.out.println("Audio file error: " + e);
		    }
		}


	    // is it possible to do steganography with current file
		/**
		 * Checks the feasibility of hiding the plaintext in the audio file.
		 *
		 * @param pt the path of the plaintext file.
		 * @return true if it is feasible to hide the plaintext, false otherwise.
		 */
		private boolean possible(String pt) {
		    try {
		        // Accessing the input file
		        System.out.println("Reading the plaintext file: " + pt);
		        FileInputStream fis = new FileInputStream(pt);
		        BufferedInputStream bis = new BufferedInputStream(fis);
		        int len = bis.available();
		        buff = new byte[len];

		        // Read the contents of the plaintext file into the buff byte array
		        while (bis.available() != 0) {
		            len = bis.read(buff);
		        }

		        bis.close();
		        fis.close();

		    } catch (Exception e) {
		        System.out.println("Could Not Read Plain Text. Caught Exception: " + e);
		    }

		    try {
		        // Encrypt the plaintext and check the feasibility of hiding it in the audio file
		        encrypt();

		        if (cipherbuff.length * 8 > audioBytes.length) {
		            // The size of the ciphertext exceeds the available space in the audio file
		            return false;
		        } else {
		            // It is feasible to hide the plaintext in the audio file
		            return true;
		        }
		    } catch (Exception e) {
		        System.out.println("******Exception: " + e);
		    }

		    // Default return value (true)
		    return true;
		}


	  //---------------------------------------------------------
	    // Generated Password relavant to underlying algorithm from characters
	    //---------------------------------------------------------
		/**
		 * Generates a password based on the input value.
		 *
		 * @param inputval the input value used to generate the password.
		 * @return the generated password as a character array.
		 * @throws IOException if an I/O error occurs.
		 */
		private char[] generatePasswd(char[] inputval) throws IOException {
		    char[] lineBuffer;
		    char[] buf;
		    int i;
		    buf = lineBuffer = new char[128];
		    int room = buf.length;
		    int offset = 0;
		    int c;

		    int index = 0;
		    int lenofinputval = inputval.length;
		    System.out.println("Debug: inputval: " + inputval);
		    System.out.println("Debug: lenofinputval: " + lenofinputval);

		    loop:
		    while (index < lenofinputval) {
		        switch (c = inputval[index]) {
		            case -1:
		            case '\n':
		                // Reached the end of input or a new line character
		                System.out.println("Debug: I am in NewLine or -1");
		                break loop;
		            case '\r':
		                // Reached a carriage return character
		                System.out.println("Debug: I am in Carriage Return");
		                index++;
		                int c2 = inputval[index];
		                if ((c2 != '\n') && (c2 != -1)) {
		                    // Carriage return is followed by a character other than new line or end of input
		                    index--;
		                    System.out.println("Debug: I am in Carriage Return IF Block");
		                } else {
		                    break loop;
		                }
		            default:
		                // Store the character in the buffer
		                if (--room < 0) {
		                    // Buffer is full, expand the buffer
		                    buf = new char[offset + 128];
		                    room = buf.length - offset - 1;
		                    System.arraycopy(lineBuffer, 0, buf, 0, offset);
		                    Arrays.fill(lineBuffer, ' ');
		                    lineBuffer = buf;
		                }
		                buf[offset++] = (char) c;
		                break;
		        }//switch
		        index++;
		    }//while

		    if (offset == 0) {
		        // No password generated, return null
		        return null;
		    }

		    // Create a new character array with the generated password
		    char[] ret = new char[offset];
		    System.arraycopy(buf, 0, ret, 0, offset);
		    Arrays.fill(buf, ' ');
		    System.out.println("Password Generated: " + ret);
		    return ret;
		}

	  //---------------------------------------------------------
	    // Reads user password from given input stream.
	    //---------------------------------------------------------

		/**
		 * Reads a password from the provided input stream.
		 *
		 * @param in the input stream from which to read the password.
		 * @return the read password as a character array.
		 * @throws IOException if an I/O error occurs.
		 */
		private char[] readPasswd(InputStream in) throws IOException {
		    char[] lineBuffer;
		    char[] buf;
		    int i;

		    buf = lineBuffer = new char[128];

		    int room = buf.length;
		    int offset = 0;
		    int c;

		    loop:
		    while (true) {
		        switch (c = in.read()) {
		            case -1:
		            case '\n':
		                // Reached the end of input or a new line character
		                break loop;

		            case '\r':
		                // Reached a carriage return character
		                int c2 = in.read();
		                if ((c2 != '\n') && (c2 != -1)) {
		                    // Carriage return is followed by a character other than new line or end of input
		                    if (!(in instanceof PushbackInputStream)) {
		                        // If input stream is not a PushbackInputStream, create one
		                        in = new PushbackInputStream(in);
		                    }
		                    ((PushbackInputStream) in).unread(c2);
		                } else {
		                    break loop;
		                }

		            default:
		                // Store the character in the buffer
		                if (--room < 0) {
		                    // Buffer is full, expand the buffer
		                    buf = new char[offset + 128];
		                    room = buf.length - offset - 1;
		                    System.arraycopy(lineBuffer, 0, buf, 0, offset);
		                    Arrays.fill(lineBuffer, ' ');
		                    lineBuffer = buf;
		                }
		                buf[offset++] = (char) c;
		                break;
		        }//switch
		    }

		    if (offset == 0) {
		        // No password captured, return null
		        return null;
		    }

		    // Create a new character array with the captured password
		    char[] ret = new char[offset];
		    System.arraycopy(buf, 0, ret, 0, offset);
		    Arrays.fill(buf, ' ');
		    System.out.println("Password Captured: " + ret);
		    return ret;
		}

	   //-------------------------------------------------
	    // Password based encryption
	    //-------------------------------------------------
		/**
		 * Encrypts the plaintext message using a password-based encryption algorithm.
		 * The encrypted message is stored in the cipherbuff byte array.
		 */
		private void encrypt() {
		    PBEParameterSpec pbeParamSpec;
		    SecretKeyFactory keyFac;

		    // Salt
		    byte[] salt = {
		        (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
		        (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99
		    };

		    // Iteration count
		    int count = 20;

		    // Create PBE parameter set
		    pbeParamSpec = new PBEParameterSpec(salt, count);

		    // Prompt user for encryption password.
		    // Collect user password as char array (using the
		    // "readPasswd" method from above), and convert
		    // it into a SecretKey object, using a PBE key
		    // factory.
		    try {
		        // Display the entered password for verification
		        System.out.println("Password Verification: " + password);

		        // Create a PBEKeySpec using the entered password
		        PBEKeySpec pbeKeySpec = new PBEKeySpec(password);

		        System.out.println("Encrypting the plaintext message ...");

		        // Generate a secret key from the password using the PBE key factory
		        keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

		        // Create a PBE Cipher
		        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

		        // Initialize the PBE Cipher with the key and parameters
		        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

		        // Encrypt the plaintext message
		        cipherbuff = pbeCipher.doFinal(buff);
		    } catch (Exception ex) {
		        // Handle the error...
		        System.out.println("Caught Exception: " + ex);
		        ex.printStackTrace();
		    }
		}


	   //-------------------------------------------------
	    //Password based decryption
	    //-------------------------------------------------
		/**
		 * Decrypts the ciphertext using a password-based encryption algorithm.
		 * The decrypted message is stored in the clearbuff byte array.
		 */
		private void decrypt() {
		    PBEKeySpec pbeKeySpec;
		    PBEParameterSpec pbeParamSpec;
		    SecretKeyFactory keyFac;

		    // Same salt as in encryption
		    byte[] salt = {
		        (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
		        (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99
		    };

		    // Same iteration count as in encryption
		    int count = 20;

		    // Create PBE parameter set
		    pbeParamSpec = new PBEParameterSpec(salt, count);

		    // Prompt user for encryption password.
		    // Collect user password as char array (using the
		    // "readPasswd" method from above), and convert
		    // it into a SecretKey object, using a PBE key
		    // factory.
		    try {
		        System.out.println();
		        System.out.print("Enter encryption password:  ");

		        // Create a PBEKeySpec using the entered password
		        pbeKeySpec = new PBEKeySpec(password);

		        System.out.println("Decrypting the ciphertext ...");

		        // Generate a secret key from the password using the PBE key factory
		        keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

		        // Create a PBE Cipher
		        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

		        // Initialize the PBE Cipher with the key and parameters
		        pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

		        // Decrypt the ciphertext
		        try {
		            clearbuff = pbeCipher.doFinal(buff);
		        } catch (Exception ex) {
		            // Handle the error if a possible password mismatch occurs
		            System.out.println("Possible password mismatch!!\n");
		            System.out.println("Caught Exception1: " + ex);
		        }
		    } catch (Exception ex) {
		        // Handle the error...
		        System.out.println("Caught Exception2: " + ex);
		    }
		}


}
