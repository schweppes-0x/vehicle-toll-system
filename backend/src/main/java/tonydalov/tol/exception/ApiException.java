package tonydalov.tol.exception;

public class ApiException extends RuntimeException{

    public static final String INVALID_API_KEY = "Invalid API-Key";
    public static final String MISSING_API_KEY = "API-Key was not provided";

    public ApiException(String message) {
        super(message);
    }

    public static ApiException missingApiKey() {
        return new ApiException(MISSING_API_KEY);
    }

    public static ApiException invalidApiKey(){
        return new ApiException(INVALID_API_KEY);
    }
}
