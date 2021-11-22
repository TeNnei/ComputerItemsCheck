package com.items.check.startVersion1.integration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
Create By Tsoy Vladislav 22.11.2021
 */

public class ItemsForDataBaseCheck {
  String id;
  String computerTitle;
  String processor;
  String GPU;
  String RAM;
  String SSD;
  LocalDateTime dateOfLastStatus;

  public String getId() {
    return id;
  }

  public ItemsForDataBaseCheck setId(String id) {
    this.id = id;
    return this;
  }

  public String getComputerTitle() {
    return computerTitle;
  }

  public ItemsForDataBaseCheck setComputerTitle(String computerTitle) {
    this.computerTitle = computerTitle;
    return this;
  }

  public String getProcessor() {
    return processor;
  }

  public ItemsForDataBaseCheck setProcessor(String processor) {
    this.processor = processor;
    return this;
  }

  public String getGPU() {
    return GPU;
  }

  public ItemsForDataBaseCheck setGPU(String GPU) {
    this.GPU = GPU;
    return this;
  }

  public String getRAM() {
    return RAM;
  }

  public ItemsForDataBaseCheck setRAM(String RAM) {
    this.RAM = RAM;
    return this;
  }

  public String getSSD() {
    return SSD;
  }

  public ItemsForDataBaseCheck setSSD(String SSD) {
    this.SSD = SSD;
    return this;
  }

  public LocalDateTime getDateOfLastStatus() {
    return dateOfLastStatus;
  }

  public ItemsForDataBaseCheck setDateOfLastStatus(LocalDateTime dateOfLastStatus) {
    this.dateOfLastStatus = dateOfLastStatus;
    return this;
  }
}
