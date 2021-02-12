package device;

import enums.DeviceType;

import java.time.LocalDateTime;

public class SmartCamera extends Device {

    private static int quantity = 0;
    public SmartCamera(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        quantity++;

    }

    @Override
    public String getId() {
        String id = DeviceType.CAMERA.getShortName();
        id += "-" + this.getName();
        id += "-" + getUniqueNumber();
        return id;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.CAMERA;
    }

    public static int getCameraQuantity(){
        return quantity;
    }


}
