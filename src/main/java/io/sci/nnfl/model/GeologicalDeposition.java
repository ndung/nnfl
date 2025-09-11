package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "i1_geological_deposition")
public class GeologicalDeposition {
  @Id
  private Long id;

  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private Geology geology;
  @Embedded
  private Mineralogy mineralogy;
  @Embedded
  private Measurement uraniumConcentration;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public Geology getGeology() { return geology; }
  public void setGeology(Geology geology) { this.geology = geology; }
  public Mineralogy getMineralogy() { return mineralogy; }
  public void setMineralogy(Mineralogy mineralogy) { this.mineralogy = mineralogy; }
  public Measurement getUraniumConcentration() { return uraniumConcentration; }
  public void setUraniumConcentration(Measurement uraniumConcentration) { this.uraniumConcentration = uraniumConcentration; }

  @Embeddable
  public static class Geology {
    @Column(name = "mine_location")
    private String mineLocation;
    @Column(name = "geological_formation")
    private String geologicalFormation;
    @Column(name = "deposit_types")
    private String depositTypes;
    @Column(name = "mining_technique")
    private String miningTechnique;
    @Column(name = "colour")
    private String colour;

    public String getMineLocation() { return mineLocation; }
    public void setMineLocation(String mineLocation) { this.mineLocation = mineLocation; }
    public String getGeologicalFormation() { return geologicalFormation; }
    public void setGeologicalFormation(String geologicalFormation) { this.geologicalFormation = geologicalFormation; }
    public String getDepositTypes() { return depositTypes; }
    public void setDepositTypes(String depositTypes) { this.depositTypes = depositTypes; }
    public String getMiningTechnique() { return miningTechnique; }
    public void setMiningTechnique(String miningTechnique) { this.miningTechnique = miningTechnique; }
    public String getColour() { return colour; }
    public void setColour(String colour) { this.colour = colour; }
  }

  @Embeddable
  public static class Mineralogy {
    @Column(name = "minerals_present", columnDefinition = "text")
    private String mineralsPresent;
    @Column(name = "mineral_chem_comp", columnDefinition = "text")
    private String mineralChemicalComposition;
    @Column(name = "mineral_vol_pct", columnDefinition = "text")
    private String mineralVolumePercentages;

    public String getMineralsPresent() { return mineralsPresent; }
    public void setMineralsPresent(String mineralsPresent) { this.mineralsPresent = mineralsPresent; }
    public String getMineralChemicalComposition() { return mineralChemicalComposition; }
    public void setMineralChemicalComposition(String mineralChemicalComposition) { this.mineralChemicalComposition = mineralChemicalComposition; }
    public String getMineralVolumePercentages() { return mineralVolumePercentages; }
    public void setMineralVolumePercentages(String mineralVolumePercentages) { this.mineralVolumePercentages = mineralVolumePercentages; }
  }
}
