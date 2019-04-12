package com.zkyong.demo.tess4j;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Tess4j {
	public static void main(String[] args) {
		File folder = new File("C:\\Users\\zkyong\\Desktop\\Tpic");
		File[] files = folder.listFiles();

		Tesseract tessreact = new Tesseract();
		tessreact.setDatapath("E:\\OCR\\Tess4J\\tessdata");
		tessreact.setLanguage("chi_sim");
		
		for (File file : files) {
			String path = file.getAbsolutePath();
//			if (path.endsWith("jpg") || path.endsWith("png") || path.endsWith("gif")) {
			if (path.endsWith("11.png")) {
				try {
					String result = tessreact.doOCR(file);
					System.out.println(path + ":");
					System.out.println(result);
					System.out.println("=====================");
				} catch (TesseractException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
