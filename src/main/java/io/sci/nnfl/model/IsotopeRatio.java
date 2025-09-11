package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "isotope_ratio",
  indexes = {
    @Index(name = "ix_ratio_mat", columnList = "material_id"),
    @Index(name = "ix_ratio_key", columnList = "system,ratio")
})
public class IsotopeRatio {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "material_id")
  private MaterialRecord material;

  @Column(name = "system", nullable = false)
  private String system;
  @Column(name = "ratio", nullable = false)
  private String ratio;
  @Column(name = "value", precision = 22, scale = 12, nullable = false)
  private BigDecimal value;
  @Column(name = "uncertainty", precision = 22, scale = 12)
  private BigDecimal uncertainty;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public String getSystem() { return system; }
  public void setSystem(String system) { this.system = system; }
  public String getRatio() { return ratio; }
  public void setRatio(String ratio) { this.ratio = ratio; }
  public BigDecimal getValue() { return value; }
  public void setValue(BigDecimal value) { this.value = value; }
  public BigDecimal getUncertainty() { return uncertainty; }
  public void setUncertainty(BigDecimal uncertainty) { this.uncertainty = uncertainty; }
}
