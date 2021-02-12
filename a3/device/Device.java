package device;

import enums.DeviceType;

import java.time.Duration;
import java.time.LocalDateTime;


public abstract class Device implements SmartDevice, Comparable<Device> {

    private String name;
    private double powerConsumption;
    private LocalDateTime installationDateTime;
    private static int staticUniqueNumber;
    private int uniqueNumber;


    public Device(String name, double powerConsumption, LocalDateTime installationDateTime) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
        staticUniqueNumber++;
        uniqueNumber = staticUniqueNumber;

    }

    public double calculateTotalPowerConsumption(LocalDateTime ldt) {
        double totalHours = Duration.between(installationDateTime, ldt).toHours();
        double result = totalHours * powerConsumption;
        return result;
    }


    //<short name of device type>-<device name>-<unique number per device type>
    @Override
    public abstract String getId();

    @Override
    public abstract DeviceType getType();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return installationDateTime;
    }

    @Override
    public int compareTo(Device d) {
        return (int) this.calculateTotalPowerConsumption(LocalDateTime.now());

    }

    public int getUniqueNumber() {
        return uniqueNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Device other = (Device) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

}
