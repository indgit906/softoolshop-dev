package com.softoolshop.adminpanel.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class UpiQrGenerator {

	//private final static String UPI_ID = "9901534356-2@ibl";
	//private final static String STORE_NM = "Softoolshop";
	private final static String IMG_DIR = "C:" + File.separator + "upload" + File.separator + "images";

	public static void generateUpiQr(String upiId, String name, double amount, String note, String filePath)
			throws WriterException, IOException {

		String upiUrl = String.format("upi://pay?pa=%s&pn=%s&am=%.2f&cu=INR&tn=%s", upiId, name, amount, note);

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(upiUrl, BarcodeFormat.QR_CODE, 400, 400);

		Path path = FileSystems.getDefault().getPath(IMG_DIR + File.separator + filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

		System.out.println("QR Code generated: " + filePath);
	}

//	public static void main(String[] args) throws Exception {
//		generateUpiQr(UPI_ID, STORE_NM, 250.00, "Order 123", "upi_qr.png");
//	}
}
