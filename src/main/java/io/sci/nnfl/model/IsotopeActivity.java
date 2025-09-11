package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "isotope_activity",
  indexes = {
    @Index(name = "ix_act_mat", columnList = "material_id"),
    @Index(name = "ix_act_isotope", columnList = "isotope")
})
public class IsotopeActivity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "material_id")
  private MaterialRecord material;

  @Column(name = "isotope", nullable = false)
  private String isotope;
  @Column(name = "activity_bq", precision = 22, scale = 8, nullable = false)
  private BigDecimal activityBq;
  @Column(name = "uncertainty_bq", precision = 22, scale = 8)
  private BigDecimal uncertaintyBq;
  @Column(name = "reference_date")
  private LocalDate referenceDate;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public String getIsotope() { return isotope; }
  public void setIsotope(String isotope) { this.isotope = isotope; }
  public BigDecimal getActivityBq() { return activityBq; }
  public void setActivityBq(BigDecimal activityBq) { this.activityBq = activityBq; }
  public BigDecimal getUncertaintyBq() { return uncertaintyBq; }
  public void setUncertaintyBq(BigDecimal uncertaintyBq) { this.uncertaintyBq = uncertaintyBq; }
  public LocalDate getReferenceDate() { return referenceDate; }
  public void setReferenceDate(LocalDate referenceDate) { this.referenceDate = referenceDate; }
}
