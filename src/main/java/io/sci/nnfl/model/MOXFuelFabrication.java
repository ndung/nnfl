package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i4_mox_fabrication")
public class MOXFuelFabrication {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private PhysicalFuelForm physical;
  @Column(name = "serial_number")
  private String serialNumber;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public PhysicalFuelForm getPhysical() { return physical; }
  public void setPhysical(PhysicalFuelForm physical) { this.physical = physical; }
  public String getSerialNumber() { return serialNumber; }
  public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
}
