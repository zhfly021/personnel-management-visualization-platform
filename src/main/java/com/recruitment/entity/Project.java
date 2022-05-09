package com.recruitment.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

  private int id;
  private String name;
  private String releaseTime;
  private String completeTime;
  private String status;

  public Project(String name, String releaseTime, String status) {

    this.name = name;
    this.releaseTime = releaseTime;
    this.status = status;

  }
}
