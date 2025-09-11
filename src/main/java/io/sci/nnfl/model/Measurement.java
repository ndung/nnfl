package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class Measurement {
  @Column(name = "m_value", precision = 22, scale = 12)
  private BigDecimal value;
  @Column(name = "m_uncertainty", precision = 22, scale = 12)
  private BigDecimal uncertainty;
  @Column(name = "m_units")
  private String units;

  public BigDecimal getValue() { return value; }
  public void setValue(BigDecimal value) { this.value = value; }
  public BigDecimal getUncertainty() { return uncertainty; }
  public void setUncertainty(BigDecimal uncertainty) { this.uncertainty = uncertainty; }
  public String getUnits() { return units; }
  public void setUnits(String units) { this.units = units; }
}
