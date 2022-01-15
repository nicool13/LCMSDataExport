package lcmsDataExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import lcmsDataExport.dataExport;

public class Main {

	public static void main(String[] args) throws IOException {	
		String dataType = "";
		ArrayList<String> mzs = new ArrayList<String>();
		ArrayList<String> wls = new ArrayList<String>();
		//File files = new File("C:\\Users\\730236688\\Desktop\\rando skool\\lcmsDataExport\\bin\\folder");
		File files = new File("C:\\Users\\Knight Group\\Desktop\\lcmsDataProcessing\\Put files here!");
		
		if (args.length == 0) {
			System.out.println("Command Line Examples:\nExtracted Ion Curve: java lcmsDataExport.Main eic 864.00 645.00 1727.00\nMS Spectrum: java lcmsDataExport.Main mss\nLC Chromatogram: lcc 218");
			return;
		} else {
			if (!args[0].equalsIgnoreCase("eic") && !args[0].equalsIgnoreCase("mss") && !args[0].equalsIgnoreCase("lcc")) {
				System.out.println("Command Line Examples:\nExtracted Ion Curve: java lcmsDataExport.Main eic 864.00 645.00 1727.00\nMS Spectrum: java lcmsDataExport.Main mss\nLC Chromatogram: java lcmsDataExport.Main lcc 218nm");
			} else if (args[0].equalsIgnoreCase("eic"))  {
				dataType = "eic";
				for (int i = 1; i < args.length; i++) {
					mzs.add(args[i]);
				}
			} else if (args[0].equalsIgnoreCase("mss")) {
				dataType = "mss";
			} else {
				dataType = "lcc";
				for (int i = 1; i < args.length; i++) {
					wls.add(args[i]);
				}
				
			}
			
		}

		File[] folder = files.listFiles();
		if (folder.length == 0) {
			System.out.println("You need to add at least one file to the 'Put files here!' folder");
			return;
		}
		if (dataType.equals("eic")) {
			for (File f: folder) {
				dataExport export = new dataExport(dataType, f, mzs);
			}
		} else if (dataType.equals("mss")) {
			for (File f: folder) {
				dataExport export = new dataExport(dataType, f, null);
			}
		} else if (dataType.equals("lcc")) {
			for (File f: folder) {
				dataExport export = new dataExport(dataType, f, wls);
			}
		}
		return;
		

	}		

}
