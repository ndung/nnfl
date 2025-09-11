package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stable_isotope",
  indexes = @Index(name = "ix_stable_mat", columnList = "material_id"))
public class StableIsotopeMeasurement {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "material_id")
  private MaterialRecord material;

  @Column(name = "system", nullable = false)
  private String system;
  @Column(name = "value", precision = 22, scale = 12)
  private BigDecimal value;
  @Column(name = "uncertainty", precision = 22, scale = 12)
  private BigDecimal uncertainty;
  @Column(name = "units")
  private String units;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public String getSystem() { return system; }
  public void setSystem(String system) { this.system = system; }
  public BigDecimal getValue() { return value; }
  public void setValue(BigDecimal value) { this.value = value; }
  public BigDecimal getUncertainty() { return uncertainty; }
  public void setUncertainty(BigDecimal uncertainty) { this.uncertainty = uncertainty; }
  public String getUnits() { return units; }
  public void setUnits(String units) { this.units = units; }
}
