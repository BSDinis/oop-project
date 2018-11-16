package sth;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.util.Locale;
import java.text.Collator;
import java.util.Comparator;

import sth.exceptions.EnrollmentLimitReachedException;

public class Student 
  extends Person 
  implements Serializable {

  Course _course = null;
  List<Discipline> _disciplines = new ArrayList<Discipline>();

  Student(String n, String pN, int id, School s) { super(n, pN, id, s); }

  Course getCourse() { return _course; }
  void enrollInCourse(Course c) { _course = c; }

  void enrollInDiscipline(Discipline d) 
    throws EnrollmentLimitReachedException { 

    if (_disciplines.size() == 6) throw new EnrollmentLimitReachedException();
    _disciplines.add(d);
  }

  public String toString(DisciplinePrinter printer) {
    String repr = super.toString(printer);
    Collections.sort(_disciplines, new Comparator<Discipline>() {
        public int compare(Discipline d1, Discipline d2) { 
          Collator c = Collator.getInstance(Locale.getDefault());
          return c.compare(d1.name(), d2.name());
        }
    });

    for (Discipline d : _disciplines) 
      repr += "\n" + printer.format(d);

    return repr;
  }
}
