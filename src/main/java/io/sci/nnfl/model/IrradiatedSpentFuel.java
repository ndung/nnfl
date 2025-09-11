package io.sci.nnfl.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "i6_spent_fuel")
public class IrradiatedSpentFuel {
  @Id
  private Long id;
  @OneToOne @MapsId @JoinColumn(name = "id")
  private MaterialRecord material;

  @Embedded
  private FuelAssemblyCharacteristics assembly;
  @Column(name = "serial_number")
  private String serialNumber;
  @Embedded
  private ChemicalForm chemicalForm;

  @Embedded
  private IrradiationHistory irradiationHistory;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public MaterialRecord getMaterial() { return material; }
  public void setMaterial(MaterialRecord material) { this.material = material; }
  public FuelAssemblyCharacteristics getAssembly() { return assembly; }
  public void setAssembly(FuelAssemblyCharacteristics assembly) { this.assembly = assembly; }
  public String getSerialNumber() { return serialNumber; }
  public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
  public ChemicalForm getChemicalForm() { return chemicalForm; }
  public void setChemicalForm(ChemicalForm chemicalForm) { this.chemicalForm = chemicalForm; }
  public IrradiationHistory getIrradiationHistory() { return irradiationHistory; }
  public void setIrradiationHistory(IrradiationHistory irradiationHistory) { this.irradiationHistory = irradiationHistory; }

  @Embeddable
  public static class IrradiationHistory {
    @Column(name = "reactor_type")
    private String reactorType;
    @Column(name = "burnup", precision = 18, scale = 6)
    private BigDecimal burnup;
    @Column(name = "burnup_units")
    private String burnupUnits;
    @Column(name = "load_date")
    private LocalDate loadDate;
    @Column(name = "discharge_date")
    private LocalDate dischargeDate;
    @Column(name = "radiation_level", columnDefinition = "text")
    private String radiationLevel;

    public String getReactorType() { return reactorType; }
    public void setReactorType(String reactorType) { this.reactorType = reactorType; }
    public BigDecimal getBurnup() { return burnup; }
    public void setBurnup(BigDecimal burnup) { this.burnup = burnup; }
    public String getBurnupUnits() { return burnupUnits; }
    public void setBurnupUnits(String burnupUnits) { this.burnupUnits = burnupUnits; }
    public LocalDate getLoadDate() { return loadDate; }
    public void setLoadDate(LocalDate loadDate) { this.loadDate = loadDate; }
    public LocalDate getDischargeDate() { return dischargeDate; }
    public void setDischargeDate(LocalDate dischargeDate) { this.dischargeDate = dischargeDate; }
    public String getRadiationLevel() { return radiationLevel; }
    public void setRadiationLevel(String radiationLevel) { this.radiationLevel = radiationLevel; }
  }
}
