package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "element_concentration",
  indexes = @Index(name = "ix_elem_mat", columnList = "material_id"))
public class ElementConcentration {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "material_id")
  private MaterialRecord material;

  @Column(name = "element", nullable = false)
  private String element;
  @Column(name = "concentration", precision = 22, scale = 12)
  private BigDecimal concentration;
  @Column(name = "uncertainty", precision = 22, scale = 12)
  private BigDecimal uncertainty;
  @Column(name = "units")
  private String units;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public String getElement() { return element; }
  public void setElement(String element) { this.element = element; }
  public BigDecimal getConcentration() { return concentration; }
  public void setConcentration(BigDecimal concentration) { this.concentration = concentration; }
  public BigDecimal getUncertainty() { return uncertainty; }
  public void setUncertainty(BigDecimal uncertainty) { this.uncertainty = uncertainty; }
  public String getUnits() { return units; }
  public void setUnits(String units) { this.units = units; }
}
