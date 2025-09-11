package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class PhysicalFuelForm {
  @Column(name = "fuel_density_g_cm3", precision = 12, scale = 6)
  private BigDecimal densityGPerCm3;
  @Column(name = "phase")
  private String phase;
  @Column(name = "mechanical_props", columnDefinition = "text")
  private String mechanicalProperties;
  @Column(name = "fuel_desc_dims", columnDefinition = "text")
  private String fuelDescriptionAndDimensions;
  @Column(name = "cladding_info")
  private String claddingInfo;
  @Column(name = "serial_numbers")
  private String serialNumbers;

  public BigDecimal getDensityGPerCm3() { return densityGPerCm3; }
  public void setDensityGPerCm3(BigDecimal densityGPerCm3) { this.densityGPerCm3 = densityGPerCm3; }
  public String getPhase() { return phase; }
  public void setPhase(String phase) { this.phase = phase; }
  public String getMechanicalProperties() { return mechanicalProperties; }
  public void setMechanicalProperties(String mechanicalProperties) { this.mechanicalProperties = mechanicalProperties; }
  public String getFuelDescriptionAndDimensions() { return fuelDescriptionAndDimensions; }
  public void setFuelDescriptionAndDimensions(String fuelDescriptionAndDimensions) { this.fuelDescriptionAndDimensions = fuelDescriptionAndDimensions; }
  public String getCladdingInfo() { return claddingInfo; }
  public void setCladdingInfo(String claddingInfo) { this.claddingInfo = claddingInfo; }
  public String getSerialNumbers() { return serialNumbers; }
  public void setSerialNumbers(String serialNumbers) { this.serialNumbers = serialNumbers; }
}
