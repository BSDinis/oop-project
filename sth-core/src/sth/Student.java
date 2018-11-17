package sth;

import java.io.Serializable;

import sth.exceptions.EnrollmentLimitReachedException;

public class Student 
  extends PersonWithDisciplines
  implements Serializable {

  Course _course = null;

  Student(String n, String pN, int id, School s) { super(n, pN, id, s); }

  Course getCourse() { return _course; }
  void enrollInCourse(Course c) { _course = c; }

  void enrollInDiscipline(Discipline d) 
    throws EnrollmentLimitReachedException { 

    if (_disciplines.size() == 6) throw new EnrollmentLimitReachedException();
    addDiscipline(d);
  }

}
