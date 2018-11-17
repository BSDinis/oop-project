package sth;

import java.io.Serializable;

public class Person 
  implements Serializable {
  private static final long serialVersionUID = 201811151238L;

  private School _school;
  private String _name;
  private String _phoneNumber;
  private int _id;

  Person(String n, String pN, int id, School s) { _name = n; _phoneNumber = pN; _id = id; _school = s;}

  public void changePhoneNumber(String pN) { _phoneNumber = pN; }

  public int id() { return _id; }
  public String name() { return _name; }
  public String phoneNumber() { return _phoneNumber; }

  public String toString() { 
    return toString(new DisciplineDefaultPrinter());
  }

  public String toString(DisciplinePrinter printer) { 
    String descriptor = "";
    if (_school.isRepresentative(id())) descriptor = "DELEGADO";
    else if (_school.isStudent(id())) descriptor = "ALUNO";
    else if (_school.isProfessor(id())) descriptor = "DOCENTE";
    else if (_school.isAdministrative(id())) descriptor = "FUNCIONÁRIO";

    return descriptor + "|" + String.valueOf(id()) + "|" + phoneNumber() + "|" + name();
  }
}
