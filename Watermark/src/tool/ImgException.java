package tool;

public class ImgException extends RuntimeException {
	public ImgException() {
	}

	public ImgException(String message) {
		super(message);
	}

	private ImgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	private ImgException(String message, Throwable cause) {
		
		super(message, cause);
		
	}

	private ImgException(Throwable cause) {
		
		super(cause);
		
	}
	
}
