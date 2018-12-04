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

  protected Collection<Discipline> getDisciplines() { return _disciplines; }
  Discipline getDiscipline(String name) 
    throws DisciplineNotFoundException {
    for (Discipline d: _disciplines) {
      if (name.equals(d.name()))
        return d;
    }

    throw new DisciplineNotFoundException(name);
  }

  Project getProject(String discipline, String project) 
    throws DisciplineNotFoundException, ProjectNotFoundException {
    Discipline d = getDiscipline(discipline);
    return d.getProject(project);
  }

  Survey getSurvey(String discipline, String project) 
    throws DisciplineNotFoundException, ProjectNotFoundException, SurveyNotFoundException {
    Project p = getProject(discipline, project);
    return p.getSurvey();
  }


  public String toString(DisciplinePrinter printer) {
    String repr = super.toString(printer);
    
    Collections.sort(_disciplines, new Comparator<Discipline>() {
        public int compare(Discipline d1, Discipline d2) { 
          Collator c = Collator.getInstance(Locale.getDefault());
          return c.compare(d1.course().name() + d1.name(), d2.course().name() + d2.name());
        }
    });

    for (Discipline d : _disciplines) 
      repr += "\n" + printer.format(d);

    return repr;
  }
}
