package io.sci.nnfl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "morphology",
  indexes = @Index(name = "ix_morph_mat", columnList = "material_id"))
public class MorphologyCrystallography {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false) @JoinColumn(name = "material_id")
  private MaterialRecord material;

  @Column(name = "lattice_structure")
  private String latticeStructure;
  @Column(name = "aspect_ratio")
  private String aspectRatio;
  @Column(name = "porosity")
  private String porosity;
  @Column(name = "colour")
  private String colour;
  @Column(name = "particle_size")
  private String particleSize;
  @Column(name = "particle_size_dist")
  private String particleSizeDistribution;
  @Column(name = "shape")
  private String shape;
  @Column(name = "surface_features")
  private String surfaceFeatures;
  @Column(name = "pu_homogeneity")
  private String plutoniumHomogeneity;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public String getLatticeStructure() { return latticeStructure; }
  public void setLatticeStructure(String latticeStructure) { this.latticeStructure = latticeStructure; }
  public String getAspectRatio() { return aspectRatio; }
  public void setAspectRatio(String aspectRatio) { this.aspectRatio = aspectRatio; }
  public String getPorosity() { return porosity; }
  public void setPorosity(String porosity) { this.porosity = porosity; }
  public String getColour() { return colour; }
  public void setColour(String colour) { this.colour = colour; }
  public String getParticleSize() { return particleSize; }
  public void setParticleSize(String particleSize) { this.particleSize = particleSize; }
  public String getParticleSizeDistribution() { return particleSizeDistribution; }
  public void setParticleSizeDistribution(String particleSizeDistribution) { this.particleSizeDistribution = particleSizeDistribution; }
  public String getShape() { return shape; }
  public void setShape(String shape) { this.shape = shape; }
  public String getSurfaceFeatures() { return surfaceFeatures; }
  public void setSurfaceFeatures(String surfaceFeatures) { this.surfaceFeatures = surfaceFeatures; }
  public String getPlutoniumHomogeneity() { return plutoniumHomogeneity; }
  public void setPlutoniumHomogeneity(String plutoniumHomogeneity) { this.plutoniumHomogeneity = plutoniumHomogeneity; }
}
