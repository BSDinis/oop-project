package sth;

import java.io.Serializable;

public class Person 
  implements Serializable, Comparable<Person> {

  private School _school;
  private String _name;
  private String _phoneNumber;
  private int _id;

  Person(String n, String pN, int id, School s) { _name = n; _phoneNumber = pN; _id = id; _school = s;}

  void changePhoneNumber(String pN) { _phoneNumber = pN; }

  int id() { return _id; }
  String name() { return _name; }
  String phoneNumber() { return _phoneNumber; }

  String print(DisciplinePrinter printer) { 
    String descriptor = "";
    if (_school.isRepresentative(id())) descriptor = "DELEGADO";
    else if (_school.isStudent(id())) descriptor = "ALUNO";
    else if (_school.isProfessor(id())) descriptor = "DOCENTE";
    else if (_school.isAdministrative(id())) descriptor = "FUNCIONÁRIO";

    return descriptor + "|" + String.valueOf(id()) + "|" + phoneNumber() + "|" + name();
  }

  public int compareTo(Person other) { return id() - other.id(); }
  public String toString() { 
    return print(new DisciplineDefaultPrinter());
  }
}
