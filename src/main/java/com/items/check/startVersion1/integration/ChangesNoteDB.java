package com.items.check.startVersion1.integration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
Create By Tsoy Vladislav 22.11.2021
Объект для изменений
 */

public class ChangesNoteDB {
  String id;
  String target;
  String oldValue;
  String newValue;
  LocalDateTime dateOfChange;

  public String getId() {
    return id;
  }

  public ChangesNoteDB setId(String id) {
    this.id = id;
    return this;
  }

  public String getTarget() {
    return target;
  }

  public ChangesNoteDB setTarget(String target) {
    this.target = target;
    return this;
  }

  public String getOldValue() {
    return oldValue;
  }

  public ChangesNoteDB setOldValue(String oldValue) {
    this.oldValue = oldValue;
    return this;
  }

  public String getNewValue() {
    return newValue;
  }

  public ChangesNoteDB setNewValue(String newValue) {
    this.newValue = newValue;
    return this;
  }

  public LocalDateTime getDateOfChange() {
    return dateOfChange;
  }

  public ChangesNoteDB setDateOfChange(LocalDateTime dateOfChange) {
    this.dateOfChange = dateOfChange;
    return this;
  }
}
