package org.appsquad.pdfhandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Filedownload;

public class DownloadPdf {

	public static void download(String pdfNamewithPath, String fileName) throws IOException{
		FileInputStream fis = new FileInputStream(new File(pdfNamewithPath));
		byte[] ba1 = new byte[1024];

		int baLength;

		ByteArrayOutputStream bios = new ByteArrayOutputStream();

		try {

			try {

				while ((baLength = fis.read(ba1)) != -1) {

					bios.write(ba1, 0, baLength);

				}
			} catch (Exception e1) {

			} finally {

				fis.close();

			}

			final AMedia amedia = new AMedia(fileName, "pdf", "application/pdf", bios.toByteArray());

			Filedownload.save(amedia);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			File xlsFile = new File(pdfNamewithPath);

			if (xlsFile.exists()) {

				FileUtils.forceDelete(xlsFile);

			}
		}
	}
	
	
}
