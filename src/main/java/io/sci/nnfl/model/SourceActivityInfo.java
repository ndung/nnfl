package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
public class SourceActivityInfo {
  @Column(name = "src_activity_bq", precision = 22, scale = 8)
  private BigDecimal activityBq;
  @Column(name = "src_ref_date")
  private LocalDate referenceDate;
  @Column(name = "src_neutron_intensity", precision = 22, scale = 8)
  private BigDecimal neutronIntensityPerSec;

  public BigDecimal getActivityBq() { return activityBq; }
  public void setActivityBq(BigDecimal activityBq) { this.activityBq = activityBq; }
  public LocalDate getReferenceDate() { return referenceDate; }
  public void setReferenceDate(LocalDate referenceDate) { this.referenceDate = referenceDate; }
  public BigDecimal getNeutronIntensityPerSec() { return neutronIntensityPerSec; }
  public void setNeutronIntensityPerSec(BigDecimal neutronIntensityPerSec) { this.neutronIntensityPerSec = neutronIntensityPerSec; }
}
