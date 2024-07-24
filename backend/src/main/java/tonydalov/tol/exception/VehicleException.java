package tonydalov.tol.exception;

public class VehicleException extends RuntimeException {

    private VehicleException(String message) {
        super(message);
    }

    public VehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    public static VehicleException unknownLicense() {
        return new VehicleException("Unknown vehicle license. Please register your car first");
    }

    public static VehicleException invalidTollData() {
        return new VehicleException("Invalid Toll data");
    }

    public static VehicleException alreadyExists(String licensePlate) {
        return new VehicleException("Vehicle is already registered with license plate: " + licensePlate);
    }
}
