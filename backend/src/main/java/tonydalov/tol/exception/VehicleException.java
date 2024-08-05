package tonydalov.tol.exception;

public class VehicleException extends RuntimeException {

    private VehicleException(String message) {
        super(message);
    }

    public VehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    private static final String UNKNOWN_LICENSE = "Unknown vehicle license. Please register your car first";
    private static final String INVALID_TOLL_DATA = "Invalid Toll data";
    private static String ALREADY_REGISTERED(String license) {return  "Vehicle is already registered with license plate: " + license;}

    public static VehicleException unknownLicense() {
        return new VehicleException(UNKNOWN_LICENSE);
    }

    public static VehicleException invalidTollData() {
        return new VehicleException(INVALID_TOLL_DATA);
    }

    public static VehicleException alreadyExists(String licensePlate) {
        return new VehicleException(ALREADY_REGISTERED(licensePlate));
    }
}
