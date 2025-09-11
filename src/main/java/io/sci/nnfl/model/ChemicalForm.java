package io.sci.nnfl.model;

import jakarta.persistence.*;

@Embeddable
public class ChemicalForm {
  @Column(name = "compound_name")
  private String compoundName;
  @Column(name = "stoichiometry_deviation")
  private String stoichiometryDeviation;

  public String getCompoundName() { return compoundName; }
  public void setCompoundName(String compoundName) { this.compoundName = compoundName; }
  public String getStoichiometryDeviation() { return stoichiometryDeviation; }
  public void setStoichiometryDeviation(String stoichiometryDeviation) { this.stoichiometryDeviation = stoichiometryDeviation; }
}
