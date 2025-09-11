package io.sci.nnfl.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
  name = "material_record",
  indexes = {
    @Index(name = "ix_stage", columnList = "stage"),
    @Index(name = "ix_country", columnList = "country_of_origin"),
    @Index(name = "ix_batch", columnList = "batch_id")
})
public class MaterialRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private MaterialStage stage;

  @Embedded
  private GeneralInfo general;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private GeologicalDeposition geologicalDeposition;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private UraniumMiningMillingExtraction uraniumMiningMillingExtraction;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private ConversionEnrichmentFuelProduction conversionEnrichmentFuelProduction;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private MOXFuelFabrication moxFuelFabrication;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private FreshNuclearFuel freshNuclearFuel;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private IrradiatedSpentFuel irradiatedSpentFuel;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private Reprocessing reprocessing;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private HighLevelWaste highLevelWaste;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private SealedSource sealedSource;

  @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private UnsealedSource unsealedSource;

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MorphologyCrystallography> morphology = new ArrayList<>();

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<IsotopeRatio> isotopeRatios = new ArrayList<>();

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<IsotopeActivity> isotopeActivities = new ArrayList<>();

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ElementConcentration> elementConcentrations = new ArrayList<>();

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StableIsotopeMeasurement> stableIsotopes = new ArrayList<>();

  @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProcessInformation> processInfo = new ArrayList<>();

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialStage getStage() { return stage; }
  public void setStage(MaterialStage stage) { this.stage = stage; }
  public GeneralInfo getGeneral() { return general; }
  public void setGeneral(GeneralInfo general) { this.general = general; }
  public GeologicalDeposition getGeologicalDeposition() { return geologicalDeposition; }
  public void setGeologicalDeposition(GeologicalDeposition geologicalDeposition) { this.geologicalDeposition = geologicalDeposition; }
  public UraniumMiningMillingExtraction getUraniumMiningMillingExtraction() { return uraniumMiningMillingExtraction; }
  public void setUraniumMiningMillingExtraction(UraniumMiningMillingExtraction uraniumMiningMillingExtraction) { this.uraniumMiningMillingExtraction = uraniumMiningMillingExtraction; }
  public ConversionEnrichmentFuelProduction getConversionEnrichmentFuelProduction() { return conversionEnrichmentFuelProduction; }
  public void setConversionEnrichmentFuelProduction(ConversionEnrichmentFuelProduction conversionEnrichmentFuelProduction) { this.conversionEnrichmentFuelProduction = conversionEnrichmentFuelProduction; }
  public MOXFuelFabrication getMoxFuelFabrication() { return moxFuelFabrication; }
  public void setMoxFuelFabrication(MOXFuelFabrication moxFuelFabrication) { this.moxFuelFabrication = moxFuelFabrication; }
  public FreshNuclearFuel getFreshNuclearFuel() { return freshNuclearFuel; }
  public void setFreshNuclearFuel(FreshNuclearFuel freshNuclearFuel) { this.freshNuclearFuel = freshNuclearFuel; }
  public IrradiatedSpentFuel getIrradiatedSpentFuel() { return irradiatedSpentFuel; }
  public void setIrradiatedSpentFuel(IrradiatedSpentFuel irradiatedSpentFuel) { this.irradiatedSpentFuel = irradiatedSpentFuel; }
  public Reprocessing getReprocessing() { return reprocessing; }
  public void setReprocessing(Reprocessing reprocessing) { this.reprocessing = reprocessing; }
  public HighLevelWaste getHighLevelWaste() { return highLevelWaste; }
  public void setHighLevelWaste(HighLevelWaste highLevelWaste) { this.highLevelWaste = highLevelWaste; }
  public SealedSource getSealedSource() { return sealedSource; }
  public void setSealedSource(SealedSource sealedSource) { this.sealedSource = sealedSource; }
  public UnsealedSource getUnsealedSource() { return unsealedSource; }
  public void setUnsealedSource(UnsealedSource unsealedSource) { this.unsealedSource = unsealedSource; }
  public List<MorphologyCrystallography> getMorphology() { return morphology; }
  public void setMorphology(List<MorphologyCrystallography> morphology) { this.morphology = morphology; }
  public List<IsotopeRatio> getIsotopeRatios() { return isotopeRatios; }
  public void setIsotopeRatios(List<IsotopeRatio> isotopeRatios) { this.isotopeRatios = isotopeRatios; }
  public List<IsotopeActivity> getIsotopeActivities() { return isotopeActivities; }
  public void setIsotopeActivities(List<IsotopeActivity> isotopeActivities) { this.isotopeActivities = isotopeActivities; }
  public List<ElementConcentration> getElementConcentrations() { return elementConcentrations; }
  public void setElementConcentrations(List<ElementConcentration> elementConcentrations) { this.elementConcentrations = elementConcentrations; }
  public List<StableIsotopeMeasurement> getStableIsotopes() { return stableIsotopes; }
  public void setStableIsotopes(List<StableIsotopeMeasurement> stableIsotopes) { this.stableIsotopes = stableIsotopes; }
  public List<ProcessInformation> getProcessInfo() { return processInfo; }
  public void setProcessInfo(List<ProcessInformation> processInfo) { this.processInfo = processInfo; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }

  public void setStageBlock(Object block) {
    if (block instanceof GeologicalDeposition b) { b.setMaterial(this); this.geologicalDeposition = b; }
    else if (block instanceof UraniumMiningMillingExtraction b) { b.setMaterial(this); this.uraniumMiningMillingExtraction = b; }
    else if (block instanceof ConversionEnrichmentFuelProduction b) { b.setMaterial(this); this.conversionEnrichmentFuelProduction = b; }
    else if (block instanceof MOXFuelFabrication b) { b.setMaterial(this); this.moxFuelFabrication = b; }
    else if (block instanceof FreshNuclearFuel b) { b.setMaterial(this); this.freshNuclearFuel = b; }
    else if (block instanceof IrradiatedSpentFuel b) { b.setMaterial(this); this.irradiatedSpentFuel = b; }
    else if (block instanceof Reprocessing b) { b.setMaterial(this); this.reprocessing = b; }
    else if (block instanceof HighLevelWaste b) { b.setMaterial(this); this.highLevelWaste = b; }
    else if (block instanceof SealedSource b) { b.setMaterial(this); this.sealedSource = b; }
    else if (block instanceof UnsealedSource b) { b.setMaterial(this); this.unsealedSource = b; }
  }
}
