package io.sci.nnfl.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Embeddable
public class GeneralInfo {
  @Column(name = "data_record_date")
  private LocalDate dataRecordDate;
  @Column(name = "custodian_name")
  private String custodianName;
  @Column(name = "custodian_address", columnDefinition = "text")
  private String custodianAddress;
  @Column(name = "analytical_lab_name")
  private String analyticalLabName;
  @Column(name = "analytical_lab_id")
  private String analyticalLabId;
  @Column(name = "date_of_analysis")
  private LocalDate dateOfAnalysis;

  @Column(name = "country_of_origin")
  private String countryOfOrigin;

  @Column(name = "producer_name")
  private String producerName;
  @Column(name = "producer_address", columnDefinition = "text")
  private String producerAddress;
  @Column(name = "supplier_name")
  private String supplierName;
  @Column(name = "supplier_address", columnDefinition = "text")
  private String supplierAddress;
  @Column(name = "batch_id")
  private String batchId;
  @Column(name = "supplier_process_date")
  private LocalDate supplierProcessDate;
  @Column(name = "shipper_carrier_info", columnDefinition = "text")
  private String shipperCarrierInfo;
  @Column(name = "receiver_info", columnDefinition = "text")
  private String receiverInfo;
  @Column(name = "data_evaluation_info", columnDefinition = "text")
  private String dataEvaluationInfo;
  @Column(name = "characteristic_variation", columnDefinition = "text")
  private String characteristicVariation;
  @Column(name = "info_acquisition_date")
  private LocalDate informationAcquisitionDate;
  @Column(name = "used_archived_information")
  private Boolean usedArchivedInformation;

  public LocalDate getDataRecordDate() { return dataRecordDate; }
  public void setDataRecordDate(LocalDate dataRecordDate) { this.dataRecordDate = dataRecordDate; }
  public String getCustodianName() { return custodianName; }
  public void setCustodianName(String custodianName) { this.custodianName = custodianName; }
  public String getCustodianAddress() { return custodianAddress; }
  public void setCustodianAddress(String custodianAddress) { this.custodianAddress = custodianAddress; }
  public String getAnalyticalLabName() { return analyticalLabName; }
  public void setAnalyticalLabName(String analyticalLabName) { this.analyticalLabName = analyticalLabName; }
  public String getAnalyticalLabId() { return analyticalLabId; }
  public void setAnalyticalLabId(String analyticalLabId) { this.analyticalLabId = analyticalLabId; }
  public LocalDate getDateOfAnalysis() { return dateOfAnalysis; }
  public void setDateOfAnalysis(LocalDate dateOfAnalysis) { this.dateOfAnalysis = dateOfAnalysis; }
  public String getCountryOfOrigin() { return countryOfOrigin; }
  public void setCountryOfOrigin(String countryOfOrigin) { this.countryOfOrigin = countryOfOrigin; }
  public String getProducerName() { return producerName; }
  public void setProducerName(String producerName) { this.producerName = producerName; }
  public String getProducerAddress() { return producerAddress; }
  public void setProducerAddress(String producerAddress) { this.producerAddress = producerAddress; }
  public String getSupplierName() { return supplierName; }
  public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
  public String getSupplierAddress() { return supplierAddress; }
  public void setSupplierAddress(String supplierAddress) { this.supplierAddress = supplierAddress; }
  public String getBatchId() { return batchId; }
  public void setBatchId(String batchId) { this.batchId = batchId; }
  public LocalDate getSupplierProcessDate() { return supplierProcessDate; }
  public void setSupplierProcessDate(LocalDate supplierProcessDate) { this.supplierProcessDate = supplierProcessDate; }
  public String getShipperCarrierInfo() { return shipperCarrierInfo; }
  public void setShipperCarrierInfo(String shipperCarrierInfo) { this.shipperCarrierInfo = shipperCarrierInfo; }
  public String getReceiverInfo() { return receiverInfo; }
  public void setReceiverInfo(String receiverInfo) { this.receiverInfo = receiverInfo; }
  public String getDataEvaluationInfo() { return dataEvaluationInfo; }
  public void setDataEvaluationInfo(String dataEvaluationInfo) { this.dataEvaluationInfo = dataEvaluationInfo; }
  public String getCharacteristicVariation() { return characteristicVariation; }
  public void setCharacteristicVariation(String characteristicVariation) { this.characteristicVariation = characteristicVariation; }
  public LocalDate getInformationAcquisitionDate() { return informationAcquisitionDate; }
  public void setInformationAcquisitionDate(LocalDate informationAcquisitionDate) { this.informationAcquisitionDate = informationAcquisitionDate; }
  public Boolean getUsedArchivedInformation() { return usedArchivedInformation; }
  public void setUsedArchivedInformation(Boolean usedArchivedInformation) { this.usedArchivedInformation = usedArchivedInformation; }
}
