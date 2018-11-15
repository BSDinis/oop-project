package sth;

import java.io.Serializable;

/**
 * Person implementation.
 */
public class Person implements Serializable {
  private static final long serialVersionUID = 201811151238L;

  private String _name;
  private String _phoneNumber;
  private int _id;

  Person(String n, String pN, int id) { _name = n; _phoneNumber = pN; _id = id; }

  public void changePhoneNumber(String pN) { _phoneNumber = pN; }

  public int id() { return _id; }
  public String name() { return _name; }
  public String phoneNumber() { return _phoneNumber; }

  public String toString() { return "<<Person :: to implement>>"; }

}
