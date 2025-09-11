package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class PhysicalProps {
  @Column(name = "density_g_cm3", precision = 12, scale = 6)
  private BigDecimal densityGPerCm3;

  public BigDecimal getDensityGPerCm3() { return densityGPerCm3; }
  public void setDensityGPerCm3(BigDecimal densityGPerCm3) { this.densityGPerCm3 = densityGPerCm3; }
}
