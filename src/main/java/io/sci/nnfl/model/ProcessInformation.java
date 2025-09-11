package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "process_info",
  indexes = {
    @Index(name = "ix_proc_mat", columnList = "material_id"),
    @Index(name = "ix_proc_type", columnList = "process_type")
})
public class ProcessInformation {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "material_id")
  private MaterialRecord material;

  @Column(name = "process_type")
  private String processType;
  @Column(name = "prod_start")
  private LocalDate productionStart;
  @Column(name = "prod_end")
  private LocalDate productionEnd;
  @Column(name = "site_name")
  private String siteName;

  @Column(name = "site_lat", precision = 10, scale = 7)
  private BigDecimal siteLat;
  @Column(name = "site_lon", precision = 10, scale = 7)
  private BigDecimal siteLon;

  @Column(name = "description", columnDefinition = "text")
  private String description;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public String getProcessType() { return processType; }
  public void setProcessType(String processType) { this.processType = processType; }
  public LocalDate getProductionStart() { return productionStart; }
  public void setProductionStart(LocalDate productionStart) { this.productionStart = productionStart; }
  public LocalDate getProductionEnd() { return productionEnd; }
  public void setProductionEnd(LocalDate productionEnd) { this.productionEnd = productionEnd; }
  public String getSiteName() { return siteName; }
  public void setSiteName(String siteName) { this.siteName = siteName; }
  public BigDecimal getSiteLat() { return siteLat; }
  public void setSiteLat(BigDecimal siteLat) { this.siteLat = siteLat; }
  public BigDecimal getSiteLon() { return siteLon; }
  public void setSiteLon(BigDecimal siteLon) { this.siteLon = siteLon; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
}
