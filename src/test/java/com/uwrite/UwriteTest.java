package com.uwrite;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class UwriteTest {

	private final Uwrite client = new Uwrite("xxx", "yyy");

	@Test(expected = IllegalArgumentException.class)
	public void uploadFileNull() throws Exception {
		client.uploadFile((File) null, "doc", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void uploadStreamNull() throws Exception {
		client.uploadFile((InputStream) null, "doc", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void uploadFormatNull() throws Exception {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] { 1, 2, 3 })) {
			client.uploadFile(bais, null, null);
		}
	}

}
