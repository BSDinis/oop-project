package sth;

class DisciplineDefaultPrinter implements DisciplinePrinter {
  public String format(Discipline d) {
    return "* " + d.course().name() + " - " + d.name();
  }
}
