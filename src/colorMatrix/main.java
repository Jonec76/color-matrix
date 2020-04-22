package colorMatrix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.NonWritableChannelException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

 
public class main {
	final static String KEY_FILE_NAME = "data2.txt";
	final static int row=7, col=8;
	
	public static void main(String[] args) {
		int [][] data = new int[row][col]; 
		JLabel[][] arrayBtn = new JLabel[row][col];
		
        JFrame frame = new JFrame("床墊壓力分佈");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
         
        GridLayout grid = new GridLayout(row, col, 10, 10);
        frame.setLayout(grid);
        
        data = readData();

        for(int i=0; i < row; i++) {
        	for(int j=0;j< col;j++) {
	            arrayBtn[i][j] = new JLabel(Integer.toString(data[i][j]), SwingConstants.CENTER);
//	            arrayBtn[i][j].setBackground(singleColor(data[i][j]));
	            arrayBtn[i][j].setBackground(multiColor(data[i][j]));
	            arrayBtn[i][j].setOpaque(true);
	            frame.add(arrayBtn[i][j]);
        	}
        }
        
        frame.setVisible(true);
	}
	
	
	
	
	static int [][] readData(){
		BufferedReader br = null;
		int [][] data = new int[row][col]; 
		try {
        	br = new BufferedReader(new FileReader(KEY_FILE_NAME));
			Vector<String> sb = new Vector<String>();
			String line = br.readLine();
			while (line != null) {
				sb.add(line);
				line = br.readLine();
			}
			for(int i=0;i<sb.size();i++) {
		        String[] arrOfStr = sb.get(i).split(", "); 
		        int j=0;
		        for (String a : arrOfStr) {
		        	try {
		        		data[i][j] = Integer.parseInt(a);
		        	  } catch (NumberFormatException e) {
		        	  }
		        	j++;
		        }
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}
	
	// From light red to dark red, only modify the brightness.
	static Color singleColor(int origValue) {
		double value = 1 - origValue/1024.0;
		int baseR=246, baseG=10, baseB=0;
		int ratioR, ratioG, ratioB;
		
		ratioR = (int)((255-baseR)*value);
		ratioG = (int)((255-baseG)*value);
		ratioB = (int)((255-baseB)*value);
		
		Color color = new Color(baseR+ratioR, baseG+ratioG, baseB+ratioB);
		return color;
	}
	
	// From light yellow to dark red
	// 100% = (255, 0, 0)
	// 75% = (255, 128, 0)
	// 50% = (255, 255, 0)
	// 25% = (255, 255, 128)
	// 0% = (255, 255, 255)
	static Color multiColor(int origValue) {
		int r=255, g, b;
		
		double value = 1 - (origValue/1024.0);
		if(origValue > 512) {
			value = 1 - (origValue-512)/512.0;
			g = (int) (255*value);
			b = 0;
		}else {
			value = 1 - (origValue)/512.0;
			g = 255;
			b = (int) (255*value);
		}
		Color color = new Color(r, g, b);
		return color;
	}
}
