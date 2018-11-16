package sth;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.util.Locale;
import java.text.Collator;
import java.util.Comparator;

import sth.exceptions.DisciplineNotFoundException;

/**
 * Professor implementation.
 */
public class Professor 
  extends Person 
  implements Serializable {

  List<Discipline> _disciplines = new ArrayList<Discipline>();
  Professor(String n, String pN, int id, School s) { super(n, pN, id, s); }

  void addDiscipline(Discipline d) { _disciplines.add(d); }
  void removeDiscipline(Discipline d) { _disciplines.remove(d); }

  Discipline getDiscipline(String name) {
    for (Discipline d: _disciplines) {
      if (name.equals(d.name()))
        return d;
    }

    return null;
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
