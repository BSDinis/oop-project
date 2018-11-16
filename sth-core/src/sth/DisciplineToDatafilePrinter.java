package sth;

import java.io.Serializable;
class DisciplineToDatafilePrinter 
  implements Serializable, DisciplinePrinter {
  public String format(Discipline d) {
    return "# " + d.course().name() + "|" + d.name();
  }
}
