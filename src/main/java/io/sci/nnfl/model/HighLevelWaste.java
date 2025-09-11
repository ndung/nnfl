package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i8_hlw")
public class HighLevelWaste {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Column(name = "serial_number")
  private String serialNumber;
  @Embedded
  private WastePhysicalCharacteristics physical;
  @Embedded
  private ContainerSpec container;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public String getSerialNumber() { return serialNumber; }
  public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
  public WastePhysicalCharacteristics getPhysical() { return physical; }
  public void setPhysical(WastePhysicalCharacteristics physical) { this.physical = physical; }
  public ContainerSpec getContainer() { return container; }
  public void setContainer(ContainerSpec container) { this.container = container; }
}
