package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i7_reprocessing")
public class Reprocessing {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private PhysicalFuelForm physical;
  @Embedded
  private ChemicalForm chemicalForm;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public PhysicalFuelForm getPhysical() { return physical; }
  public void setPhysical(PhysicalFuelForm physical) { this.physical = physical; }
  public ChemicalForm getChemicalForm() { return chemicalForm; }
  public void setChemicalForm(ChemicalForm chemicalForm) { this.chemicalForm = chemicalForm; }
}
