package sth;

class DisciplineToDatafilePrinter implements DisciplinePrinter {
  public String format(Discipline d) {
    return "# " + d.course().name() + "|" + d.name();
  }
}
