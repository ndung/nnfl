package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class Dimensions {
  @Column(name = "len", precision = 12, scale = 3)
  private BigDecimal length;
  @Column(name = "wid", precision = 12, scale = 3)
  private BigDecimal width;
  @Column(name = "hei", precision = 12, scale = 3)
  private BigDecimal height;
  @Column(name = "dim_units")
  private String units;

  public BigDecimal getLength() { return length; }
  public void setLength(BigDecimal length) { this.length = length; }
  public BigDecimal getWidth() { return width; }
  public void setWidth(BigDecimal width) { this.width = width; }
  public BigDecimal getHeight() { return height; }
  public void setHeight(BigDecimal height) { this.height = height; }
  public String getUnits() { return units; }
  public void setUnits(String units) { this.units = units; }
}
