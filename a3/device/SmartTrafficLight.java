package device;

import enums.DeviceType;

import java.time.LocalDateTime;

public class SmartTrafficLight extends Device{

    private static int quantity = 0;
    public SmartTrafficLight(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);
        quantity++;
    }

    @Override
    public String getId() {
        String id = DeviceType.TRAFFIC_LIGHT.getShortName();
        id += "-" + this.getName();
        id += "-" + getUniqueNumber();
        return id;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.TRAFFIC_LIGHT;
    }

    public static int getTrafficLightQuantity(){
        return quantity;
    }
}

