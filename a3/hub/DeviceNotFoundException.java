package hub;

public class DeviceNotFoundException extends Exception{

    public DeviceNotFoundException(){
        super("No such device exists!");
    }
}
