package com.items.check.startVersion1.integration;

import java.time.LocalDate;

/*
Create By Tsoy Vladislav 22.11.2021
 */

public class ChangesNoteDB {
  String id;
  String idItemForDB;
  String target;
  String oldValue;
  String newValue;
  LocalDate dateOfChange;

  public String getId() {
    return id;
  }

  public ChangesNoteDB setId(String id) {
    this.id = id;
    return this;
  }

  public String getIdItemForDB() {
    return idItemForDB;
  }

  public ChangesNoteDB setIdItemForDB(String idItemForDB) {
    this.idItemForDB = idItemForDB;
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

  public LocalDate getDateOfChange() {
    return dateOfChange;
  }

  public ChangesNoteDB setDateOfChange(LocalDate dateOfChange) {
    this.dateOfChange = dateOfChange;
    return this;
  }
}
