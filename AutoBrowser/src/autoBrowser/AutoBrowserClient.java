package autoBrowser;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class AutoBrowserClient {

	public static void main(String[] args) {
		
		String previousFilename = getPreviousFilename();
		String filename = JOptionPane.showInputDialog(null,"Write filename: ", previousFilename);
		if(!filename.contains(".txt")) {
			filename += ".txt";
		}
		
		saveLastFilename(filename);
		
		ArrayList<String> URLs = getURLs(filename);
		
		Random r = new Random();
		for(int i = 0; i < 10; i++) {
			browse(URLs.get(r.nextInt(URLs.size())));
		}
		
	}
	
	private static void browse(String url) {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			System.out.println("HMMM");
		    try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"IOException");
				System.exit(2);
			} catch (URISyntaxException e) {
				JOptionPane.showMessageDialog(null,"Feil med URL");
				System.exit(3);
			}
		}
	}
	
	private static String getPreviousFilename() {
		String previousFilename = ".txt";
		BufferedReader bf1 = null;
		
		try {
			bf1 = new BufferedReader(new FileReader("data.txt"));
			String read = bf1.readLine();
			if(read != null && !read.isEmpty()) {
				previousFilename = read;
			}
			bf1.close();
		} catch (Throwable e) {}
		
		return previousFilename;
		
	}
	
	private static void saveLastFilename(String filename) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));
			bw.write(filename);
			bw.close();
		} catch (IOException e1) {}
	}
	
	private static ArrayList<String> getURLs(String filename) {
		ArrayList<String> URLs = new ArrayList<String>();
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			bf.lines().filter(l -> l.contains("www")).forEach(l -> URLs.add(l));
			bf.close();
		} catch (Throwable e) {}
		
		return URLs;
	}

}