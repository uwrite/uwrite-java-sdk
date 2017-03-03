package com.uwrite.exception;

import java.io.IOException;

public class UwriteApiException extends IOException {

	public UwriteApiException() {
	}

	public UwriteApiException(String message) {
		super(message);
	}

	public UwriteApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public UwriteApiException(Throwable cause) {
		super(cause);
	}
}
