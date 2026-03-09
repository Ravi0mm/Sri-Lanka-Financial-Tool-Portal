package com.aurora.srilankafinancialtoolsportal.model.vehicle;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class VehicleDutyRequest {

    @NotNull(message = "CIF value is required")
    @DecimalMin(value = "0.01", message = "CIF value must be greater than 0")
    private BigDecimal cifValue;

    @NotNull(message = "Engine capacity is required")
    @Min(value = 1, message = "Engine capacity must be greater than 0")
    private Integer engineCapacity;

    @NotBlank(message = "Fuel type is required")
    @Pattern(regexp = "Petrol|Diesel|Hybrid|Electric", message = "Select a valid fuel type")
    private String fuelType;

    @NotBlank(message = "Vehicle type is required")
    @Pattern(regexp = "Car|SUV|Van", message = "Select a valid vehicle type")
    private String vehicleType;

    public BigDecimal getCifValue() {
        return cifValue;
    }

    public void setCifValue(BigDecimal cifValue) {
        this.cifValue = cifValue;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
