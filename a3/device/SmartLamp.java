package device;

import enums.DeviceType;

import java.time.LocalDateTime;

public class SmartLamp extends Device {

    private static int quantity = 0;
    public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        quantity++;
    }

    @Override
    public String getId() {
        String id = DeviceType.LAMP.getShortName();
        id += "-" + this.getName();
        id += "-" + getUniqueNumber();
        return id;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.LAMP;
    }

    public static int getLampQuantity(){
        return quantity;
    }


}
