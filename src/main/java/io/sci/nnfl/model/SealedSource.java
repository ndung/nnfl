package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i9_sealed_source")
public class SealedSource {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private SourceDescription description;
  @Embedded
  private SourceActivityInfo activityInfo;
  @Embedded
  private ChemicalForm chemicalForm;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public SourceDescription getDescription() { return description; }
  public void setDescription(SourceDescription description) { this.description = description; }
  public SourceActivityInfo getActivityInfo() { return activityInfo; }
  public void setActivityInfo(SourceActivityInfo activityInfo) { this.activityInfo = activityInfo; }
  public ChemicalForm getChemicalForm() { return chemicalForm; }
  public void setChemicalForm(ChemicalForm chemicalForm) { this.chemicalForm = chemicalForm; }
}
