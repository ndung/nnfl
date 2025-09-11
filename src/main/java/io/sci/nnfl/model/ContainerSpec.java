package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class ContainerSpec {
  @Column(name = "container_type")
  private String type;
  @Column(name = "container_vol", precision = 18, scale = 6)
  private BigDecimal volume;
  @Column(name = "container_vol_units")
  private String volumeUnits;
  @Embedded
  private Dimensions dimensions;

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public BigDecimal getVolume() { return volume; }
  public void setVolume(BigDecimal volume) { this.volume = volume; }
  public String getVolumeUnits() { return volumeUnits; }
  public void setVolumeUnits(String volumeUnits) { this.volumeUnits = volumeUnits; }
  public Dimensions getDimensions() { return dimensions; }
  public void setDimensions(Dimensions dimensions) { this.dimensions = dimensions; }
}
