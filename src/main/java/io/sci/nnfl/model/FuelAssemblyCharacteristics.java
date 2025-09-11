package io.sci.nnfl.model;

import jakarta.persistence.*;

@Embeddable
public class FuelAssemblyCharacteristics {
  @Column(name = "assm_desc_dims", columnDefinition = "text")
  private String descriptionAndDimensions;
  @Column(name = "assm_cladding_info")
  private String claddingInfo;
  @Column(name = "assm_structure")
  private String assemblyStructure;
  @Column(name = "surface_oxide_thickness")
  private String surfaceOxideThickness;
  @Column(name = "coating_info")
  private String coatingInfo;

  public String getDescriptionAndDimensions() { return descriptionAndDimensions; }
  public void setDescriptionAndDimensions(String descriptionAndDimensions) { this.descriptionAndDimensions = descriptionAndDimensions; }
  public String getCladdingInfo() { return claddingInfo; }
  public void setCladdingInfo(String claddingInfo) { this.claddingInfo = claddingInfo; }
  public String getAssemblyStructure() { return assemblyStructure; }
  public void setAssemblyStructure(String assemblyStructure) { this.assemblyStructure = assemblyStructure; }
  public String getSurfaceOxideThickness() { return surfaceOxideThickness; }
  public void setSurfaceOxideThickness(String surfaceOxideThickness) { this.surfaceOxideThickness = surfaceOxideThickness; }
  public String getCoatingInfo() { return coatingInfo; }
  public void setCoatingInfo(String coatingInfo) { this.coatingInfo = coatingInfo; }
}
