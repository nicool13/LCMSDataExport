package lcmsDataExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class dataExport {

	public dataExport(String dataType, File file, ArrayList<String> data) throws IOException {
		if (dataType.equals("eic")) {
			exportEIC(file, data);
		} else if (dataType.equals("mss")) {
			exportMSS(file);
		} else {
			exportLCC(file, data);
		}
	}


	private static void exportEIC(File file, ArrayList<String> mzs) throws IOException {
		Scanner sc;
		boolean mzFound;
		int counter = 0;
		FileWriter fw;

		while (counter < mzs.size()) {
			mzFound = true;
			sc = new Scanner(file);
			String currentLine = sc.nextLine();
			while (!currentLine.contains("m/z " + mzs.get(counter))) {
				if (sc.hasNextLine()) {
					currentLine = sc.nextLine();
				} else {
					System.out.println("Error: File does not contain requested m/z=" + mzs.get(counter));
					mzFound = false;
					break;
				}
			}
			if (mzFound) {
				fw = new FileWriter(file.toString() + "-" + mzs.get(counter) + ".txt");
				fw.write("m/z " + mzs.get(counter) + " \n");
				for (int i = 0; i < 5; i++) {
					currentLine = sc.nextLine();
				}
				currentLine = sc.nextLine();
				while(!currentLine.equals("")) {
					Scanner linesc = new Scanner(currentLine);
					fw.write(linesc.next()+ "\t");
					fw.write(linesc.next() + "\n");
					linesc.close();
					if (sc.hasNextLine()) {
						currentLine = sc.nextLine();
					} else {
						break;
					}

				}
				fw.close();
			}
			sc.close();
			counter++;
		}
		System.out.println("Export complete.");	
	}

	private static void exportMSS(File file) throws IOException {
		Scanner sc;
		FileWriter fw;
		boolean specFound;
		int counter;

		sc = new Scanner(file);
		specFound = true;
		String currentLine = sc.nextLine();
		while (!currentLine.contains("[Spectrum Process Table]")) {
			if (sc.hasNextLine()) {
				currentLine = sc.nextLine();
			} else {
				specFound = false;
				System.out.println("Error: File does not contain MS Spectrum");
				break;
			}
		}

		if (specFound) {
			String nextLine = sc.nextLine();
			Scanner linesc = new Scanner(nextLine);
			linesc.next();
			linesc.next();
			linesc.next();
			counter = linesc.nextInt();
			linesc.close();

			for (int i = 0; i < counter; i++) {
				while (!currentLine.contains("[MS Spectrum]")) {
					currentLine = sc.nextLine();
				}
				int d = i+1;
				fw = new FileWriter(file.toString() + "-Spectrum" + d + ".txt");
				for (int j = 0; j < 6; j++) {
					fw.write(currentLine);
					if (sc.hasNextLine()) {
						currentLine = sc.nextLine();
					} else {
						break;
					}
				}

				while(!currentLine.equals("")) {
					Scanner lineScan = new Scanner(currentLine);
					fw.write(lineScan.next()+ "\t");
					fw.write(lineScan.next() + "\n");
					lineScan.close();
					if (sc.hasNextLine()) {
						currentLine = sc.nextLine();
					} else {
						break;
					}

				}
				fw.close();
			}
		}	
		sc.close();
		System.out.println("Export complete.");	
	}

	private static void exportLCC(File file, ArrayList<String> wls) throws IOException {
		Scanner sc;
		boolean wlFound;
		int counter = 0;
		FileWriter fw;

		while (counter < wls.size()) {
			wlFound = true;
			sc = new Scanner(file);
			String currentLine = sc.nextLine();
			while (!currentLine.contains("Wavelength(nm)\t" + wls.get(counter) + "nm")) {
				if (sc.hasNextLine()) {
					currentLine = sc.nextLine();
				} else {
					System.out.println("Error: File does not contain requested wavelength=" + wls.get(counter));
					wlFound = false;
					break;
				}
			}		
			
			if (wlFound) {
				fw = new FileWriter(file.toString() + "-" + wls.get(counter) + "nm.txt");
				while(!currentLine.equals("")) {
					fw.write(currentLine + "\n");
					if (sc.hasNextLine()) {
						currentLine = sc.nextLine();
					} else {
						break;
					}
				}
				fw.close();
			}
			sc.close();
			counter++;
		}
		System.out.println("Export complete.");	
	}




}
