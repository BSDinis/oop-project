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
import java.util.LinkedList;

import java.util.Map;
import java.util.TreeMap;

import java.util.Locale;
import java.text.Collator;
import java.util.Comparator;

import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.SurveyNotFoundException;

class PersonWithDisciplines 
  extends Person 
  implements Serializable {

  private Map<String, LinkedList<Discipline>> _disciplines = new TreeMap<>();

  PersonWithDisciplines(String n, String pN, int id, School s) { super(n, pN, id, s); }

  void addDiscipline(Discipline d) { 
    LinkedList<Discipline> list = _disciplines.get(d.name());
    if (list == null) {
      list = new LinkedList<>();
      list.add(d);
      _disciplines.put(d.name(), list);
    }
    else {
      list.add(d);
    }
  }
  void removeDiscipline(Discipline d) { 
    _disciplines.remove(d.name()); 
  }

  List<Discipline> getDisciplines() { 
    Collection<LinkedList<Discipline>> lists = _disciplines.values();
    List<Discipline> linearized = new LinkedList<>();
    for (LinkedList<Discipline> l : lists)
      linearized.addAll(l);

    return linearized; 
  }

  Discipline getDiscipline(String name) 
    throws DisciplineNotFoundException {
    LinkedList<Discipline> list = _disciplines.get(name);
    if (list == null) 
      throw new DisciplineNotFoundException(name);

    return list.peekFirst();
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

  String print(DisciplinePrinter printer) {
    String repr = super.print(printer);
    
    List<Discipline> disciplineList = getDisciplines();
    Collections.sort(disciplineList, new Comparator<Discipline>() {
        public int compare(Discipline d1, Discipline d2) { 
          Collator c = Collator.getInstance(Locale.getDefault());
          return c.compare(d1.course().name() + d1.name(), d2.course().name() + d2.name());
        }
    });

    for (Discipline d : disciplineList) 
      repr += "\n" + printer.print(d);

    return repr;
  }
}
