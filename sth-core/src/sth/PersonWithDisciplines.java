/*
 * Both Professor and Student share a lot of functions due to the
 * fact that they are both Persons with disciplines
 *
 * This class avoids code repetition
 */

package sth;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.util.Locale;
import java.text.Collator;
import java.util.Comparator;

import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.SurveyNotFoundException;

class PersonWithDisciplines 
  extends Person 
  implements Serializable {

  private List<Discipline> _disciplines = new ArrayList<Discipline>();

  PersonWithDisciplines(String n, String pN, int id, School s) { super(n, pN, id, s); }

  void addDiscipline(Discipline d) { _disciplines.add(d); }
  void removeDiscipline(Discipline d) { _disciplines.remove(d); }

  Collection<Discipline> disciplines() { return _disciplines; }

  Discipline discipline(String name) 
    throws DisciplineNotFoundException {
    for (Discipline d: _disciplines) {
      if (name.equals(d.name()))
        return d;
    }

    throw new DisciplineNotFoundException(name);
  }

  Project project(String discipline, String project) 
    throws DisciplineNotFoundException, ProjectNotFoundException {
    Discipline d = discipline(discipline);
    return d.project(project);
  }

  Survey survey(String discipline, String project) 
    throws DisciplineNotFoundException, ProjectNotFoundException, SurveyNotFoundException {
    Project p = project(discipline, project);
    return p.survey();
  }

  String print(DisciplinePrinter printer) {
    String repr = super.print(printer);
    
    Collections.sort(_disciplines, new Comparator<Discipline>() {
        public int compare(Discipline d1, Discipline d2) { 
          Collator c = Collator.getInstance(Locale.getDefault());
          return c.compare(d1.course().name() + d1.name(), d2.course().name() + d2.name());
        }
    });

    for (Discipline d : _disciplines) 
      repr += "\n" + printer.print(d);

    return repr;
  }
}
