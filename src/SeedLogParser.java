import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SeedLogParser {
	public static void main(String[] args) {
		int choice = JOptionPane.showOptionDialog(null, "Which log type are you scanning?", "LogReader", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Amidst2^48", "Portal2Stronghold" }, "Amidst2^48");
		if (choice != JOptionPane.CLOSED_OPTION) {
			JFileChooser fc = new JFileChooser(new File("src/../../"));
			fc.showOpenDialog(null);
			try {
				File f = fc.getSelectedFile();
				PrintWriter log = new PrintWriter("log.txt");
				if (f.exists() && f.isFile()) {
					switch (choice) {
					case 0: {
						String loc = JOptionPane.showInputDialog("What coordinates do you want the stronghold to be at?");
						List<String> lines = Files.readAllLines(f.toPath());
						String seed = "Unknown seed";
						for (int i = 0; i < lines.size(); i++) {
							String line = lines.get(i);
							if (line.startsWith("Starting seed")) {
								seed = line;
							} else if (line.matches(loc)) {
								System.out.println(seed);
								System.out.println(loc);
								log.println(seed);
								log.println(loc);
							}
						}
						break;
					}
					case 1: {
						String eyes = JOptionPane.showInputDialog("How many eyes are you looking for? (Output will be sorted by distance from origin");
						List<String> lines = Files.readAllLines(f.toPath());
						@SuppressWarnings("resource")
						String[][] outputlist = lines.stream().map(a -> a.split(" ")).filter(a -> a[1].matches(eyes)).sorted((a, b) -> {
							int ax = Integer.parseInt(a[2]);
							int az = Integer.parseInt(a[3]);
							int bx = Integer.parseInt(b[2]);
							int bz = Integer.parseInt(b[3]);
							return ax * ax + az * az - bx * bx - bz * bz;
						}).toArray(String[][]::new);
						for (String[] s : outputlist) {
							String output = Arrays.toString(s).replaceAll("[\\[\\],]", "");
							System.out.println(output);
							log.println(output);
						}
					}
					}
				}
				log.flush();
				log.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
