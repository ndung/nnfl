package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i5_fresh_fuel")
public class FreshNuclearFuel {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private FuelAssemblyCharacteristics assembly;
  @Column(name = "serial_number")
  private String serialNumber;
  @Embedded
  private ChemicalForm chemicalForm;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public FuelAssemblyCharacteristics getAssembly() { return assembly; }
  public void setAssembly(FuelAssemblyCharacteristics assembly) { this.assembly = assembly; }
  public String getSerialNumber() { return serialNumber; }
  public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
  public ChemicalForm getChemicalForm() { return chemicalForm; }
  public void setChemicalForm(ChemicalForm chemicalForm) { this.chemicalForm = chemicalForm; }
}
