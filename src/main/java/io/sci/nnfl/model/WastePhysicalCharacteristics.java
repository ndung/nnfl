package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class WastePhysicalCharacteristics {
  @Column(name = "w_activity", precision = 22, scale = 8)
  private BigDecimal activity;
  @Column(name = "w_activity_units")
  private String units;
  @Column(name = "w_density_g_cm3", precision = 12, scale = 6)
  private BigDecimal densityGPerCm3;
  @Column(name = "w_state_matrix")
  private String stateOrMatrix;
  @Column(name = "w_mass", precision = 18, scale = 6)
  private BigDecimal mass;
  @Column(name = "w_mass_units")
  private String massUnits;
  @Embedded
  private Dimensions dimensions;

  public BigDecimal getActivity() { return activity; }
  public void setActivity(BigDecimal activity) { this.activity = activity; }
  public String getUnits() { return units; }
  public void setUnits(String units) { this.units = units; }
  public BigDecimal getDensityGPerCm3() { return densityGPerCm3; }
  public void setDensityGPerCm3(BigDecimal densityGPerCm3) { this.densityGPerCm3 = densityGPerCm3; }
  public String getStateOrMatrix() { return stateOrMatrix; }
  public void setStateOrMatrix(String stateOrMatrix) { this.stateOrMatrix = stateOrMatrix; }
  public BigDecimal getMass() { return mass; }
  public void setMass(BigDecimal mass) { this.mass = mass; }
  public String getMassUnits() { return massUnits; }
  public void setMassUnits(String massUnits) { this.massUnits = massUnits; }
  public Dimensions getDimensions() { return dimensions; }
  public void setDimensions(Dimensions dimensions) { this.dimensions = dimensions; }
}
