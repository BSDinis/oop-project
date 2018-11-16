package sth;

import java.io.Serializable;

import java.util.List;
import java.util.LinkedList;

import sth.exceptions.EnrollmentLimitReachedException;

public class Student 
  extends Person 
  implements Serializable {

  Course _course = null;
  List<Discipline> _disciplines = new LinkedList<Discipline>();

  Student(String n, String pN, int id) { super(n, pN, id); }

  Course getCourse() { return _course; }
  void enrollInCourse(Course c) { _course = c; }

  void enrollInDiscipline(Discipline d) 
    throws EnrollmentLimitReachedException { 

    if (_disciplines.size() == 6) throw new EnrollmentLimitReachedException();
    _disciplines.add(d);
  }
}
