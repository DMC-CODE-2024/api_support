package com.gl.ceir.panel.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gl.ceir.panel.constant.LogicalDirectoryEnum;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Component
public class QrcodeUtil {
	@Value("${eirs.panel.source.path:}")
	private String basepath;
	
	public Path generateQRCodeImage(String ticketId, String url, int width, int height) throws WriterException, IOException {
		String filepath = basepath + "/" + LogicalDirectoryEnum.qrcode + "/" + ticketId + ".png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filepath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return path;
    }
}
