package io.sci.nnfl.model;

import jakarta.persistence.*;

@Embeddable
public class SourceDescription {
  @Column(name = "src_type")
  private String sourceType;
  @Column(name = "src_quantity")
  private String quantity;
  @Column(name = "src_desc_dims", columnDefinition = "text")
  private String descriptionAndDimensions;
  @Column(name = "src_encapsulation")
  private String encapsulationOrCladding;
  @Column(name = "src_serial")
  private String serialNumber;
  @Column(name = "src_photo_ref")
  private String photoRef;
  @Column(name = "src_ship_hist", columnDefinition = "text")
  private String shippingReceivingHistory;

  public String getSourceType() { return sourceType; }
  public void setSourceType(String sourceType) { this.sourceType = sourceType; }
  public String getQuantity() { return quantity; }
  public void setQuantity(String quantity) { this.quantity = quantity; }
  public String getDescriptionAndDimensions() { return descriptionAndDimensions; }
  public void setDescriptionAndDimensions(String descriptionAndDimensions) { this.descriptionAndDimensions = descriptionAndDimensions; }
  public String getEncapsulationOrCladding() { return encapsulationOrCladding; }
  public void setEncapsulationOrCladding(String encapsulationOrCladding) { this.encapsulationOrCladding = encapsulationOrCladding; }
  public String getSerialNumber() { return serialNumber; }
  public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
  public String getPhotoRef() { return photoRef; }
  public void setPhotoRef(String photoRef) { this.photoRef = photoRef; }
  public String getShippingReceivingHistory() { return shippingReceivingHistory; }
  public void setShippingReceivingHistory(String shippingReceivingHistory) { this.shippingReceivingHistory = shippingReceivingHistory; }
}
