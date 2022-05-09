package com.recruitment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

  private int id;
  private String unit;
  private String profession;
  private String job;
  private int hireNumber;
  private int resume;
  private int assessment;
  private int hired;
  private String status;
  private int projectID;

  private String  projectName;


  public Plan(String uni, String profession, String job, int hireNumber) {
      this.unit = uni;
      this.profession = profession;
      this.job = job;
      this.hireNumber = hireNumber;
  }
}
