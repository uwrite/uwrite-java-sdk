package com.uwrite;

import com.uwrite.model.UCheck;
import com.uwrite.model.UFile;
import com.uwrite.model.UPdfReport;
import com.uwrite.model.UType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.Locale;

public class CheckTest {

	private final Uwrite client;

	{
		String key = System.getProperty("key");
		String secret = System.getProperty("secret");
		client = new Uwrite(key, secret);
	}

	private UFile uFile;

	@Before
	public void upload() throws Exception {

		String text = "Carlos Ray \"Chuck\" Norris (born March 10, 1940) is an American martial artist, actor, film producer and screenwriter. After serving in the United States Air Force, he began his rise to fame as a martial artist, and has since founded his own school, Chun Kuk Do.";
		try (ByteArrayInputStream bais = new ByteArrayInputStream(text.getBytes());
		     BufferedInputStream bis = new BufferedInputStream(bais)
		) {
			System.out.println("Uploading document");
			UFile uFile = client.uploadFile(bis, "txt", "chuck-sob4ak");
			System.out.println(uFile);
			this.uFile = uFile;
		}
	}

	@Test
	public void checkDocument() throws Exception {

		UCheck check = client.createCheck(uFile.getId(), UType.WEB, null, null, null);
		System.out.println("Launched check\t" + check);

		while (check.getProgress() < 1.0) {
			check = client.getCheckInfo(check.getId());
			System.out.println("Get info\t" + check);
			Thread.sleep(2000L);
		}
		Assert.assertNotNull(check);
	}

	@Test
	public void generateReport() throws Exception {
		checkDocument();
		uFile = client.getFileInfo(uFile.getId());
		long checkId = uFile.getUChecks()[0].getId();
		System.out.println("Generating report");
		UPdfReport uPdfReport = client.generateReport(checkId, null);
		while (uPdfReport.getStatus().equals("generate")) {
			Thread.sleep(1000L);
			uPdfReport = client.generateReport(checkId, null);
		}
		Assert.assertNotNull(uPdfReport);
		System.out.println(uPdfReport);
	}

	@Test
	public void toggleCitations() throws Exception {
		checkDocument();
		uFile = client.getFileInfo(uFile.getId());
		long checkId = uFile.getUChecks()[0].getId();
		System.out.println("Toggling citations");
		UCheck uCheck = client.toggleCitations(checkId, true, true);
		Assert.assertNotNull(uCheck);
	}

	@After
	public void deleteChecksAndFile() throws Exception {
		uFile = client.getFileInfo(uFile.getId());
		for (UCheck check : uFile.getUChecks()) {
			long id = check.getId();
			try {
				System.out.println("Deleting check with id " + id);
				check = client.deleteCheck(id);
				System.out.println(check);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Deleting file");
		client.deleteFile(uFile.getId());
	}

}
