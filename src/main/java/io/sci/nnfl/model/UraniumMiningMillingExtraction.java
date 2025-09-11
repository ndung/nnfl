package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i2_uranium_mining_milling")
public class UraniumMiningMillingExtraction {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private ChemicalForm chemicalForm;
  @Embedded
  private PhysicalProps physical;
  @Embedded
  private Measurement uraniumConcentration;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public ChemicalForm getChemicalForm() { return chemicalForm; }
  public void setChemicalForm(ChemicalForm chemicalForm) { this.chemicalForm = chemicalForm; }
  public PhysicalProps getPhysical() { return physical; }
  public void setPhysical(PhysicalProps physical) { this.physical = physical; }
  public Measurement getUraniumConcentration() { return uraniumConcentration; }
  public void setUraniumConcentration(Measurement uraniumConcentration) { this.uraniumConcentration = uraniumConcentration; }
}
