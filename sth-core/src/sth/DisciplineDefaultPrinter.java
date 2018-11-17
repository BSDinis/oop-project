package sth;

import java.io.Serializable;
class DisciplineDefaultPrinter implements DisciplinePrinter, Serializable {
  public String format(Discipline d) {
    return "* " + d.course().name() + " - " + d.name();
  }
}
