package hub;

public class DeviceAlreadyRegisteredException extends Exception{

    public DeviceAlreadyRegisteredException(){
        super("The device has already been added to the Register! ");
    }
}
