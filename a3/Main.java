import device.Device;
import device.SmartCamera;
import device.SmartDevice;
import device.SmartTrafficLight;
import enums.DeviceType;
import hub.DeviceAlreadyRegisteredException;
import hub.DeviceNotFoundException;
import hub.SmartCityHub;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {

        // initialize a generic DateTime for testing purposes
        LocalDate ld = LocalDate.of(2021, 02, 11);
        LocalTime lt = LocalTime.of(22, 12);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);

        // some devices
        Device c1 = new SmartCamera("Camera1", 12, ldt);
        Device c2 = new SmartCamera("Camera2", 13, ldt);
        Device tl1 = new SmartTrafficLight("TL1", 29, ldt);
        Device tl2 = new SmartTrafficLight("TL2", 222, ldt);
        Device tl3IdenticalTotl2 = new SmartTrafficLight("TL2", 222, ldt);


        // the hub
        SmartCityHub sch = new SmartCityHub();

        try {
            sch.register(c1);
            sch.register(c2);
            sch.register(tl1);
            sch.register(tl2);


        } catch (DeviceAlreadyRegisteredException dar) {

        }



        int result = sch.getDeviceQuantityPerType(DeviceType.CAMERA);
        String id1 = c1.getId();
        String id2 = c2.getId();

        try{
            SmartDevice byID = sch.getDeviceById("TFL-TL2-4");
            System.out.println(byID.getName() + byID.getId());
        }catch (DeviceNotFoundException dnf){
            System.out.println("device not found");
        }
        Collection<SmartDevice> collection = sch.getFirstNDevicesByRegistration(3);

        for (SmartDevice s: collection) {
            System.out.println(s.getName());
        }
        Collection<String> collectionPowerConsumption = sch.getTopNDevicesByPowerConsumption(4);

        for (String s : collectionPowerConsumption) {
            System.out.println(s);
        }

        int a = 9;

    }
}
