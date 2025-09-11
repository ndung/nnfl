package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i3_conversion_enrichment_fuel")
public class ConversionEnrichmentFuelProduction {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private PhysicalFuelForm physical;
  @Embedded
  private ChemicalForm chemicalForm;
  @Embedded
  private Measurement uraniumConcentration;
  @Embedded
  private ContainerSpec container;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public PhysicalFuelForm getPhysical() { return physical; }
  public void setPhysical(PhysicalFuelForm physical) { this.physical = physical; }
  public ChemicalForm getChemicalForm() { return chemicalForm; }
  public void setChemicalForm(ChemicalForm chemicalForm) { this.chemicalForm = chemicalForm; }
  public Measurement getUraniumConcentration() { return uraniumConcentration; }
  public void setUraniumConcentration(Measurement uraniumConcentration) { this.uraniumConcentration = uraniumConcentration; }
  public ContainerSpec getContainer() { return container; }
  public void setContainer(ContainerSpec container) { this.container = container; }
}
