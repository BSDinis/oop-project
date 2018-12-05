package sth;

class DisciplineToDatafilePrinter 
  implements DisciplinePrinter {

  public String print(Discipline d) {
    return "# " + d.course().name() + "|" + d.name();
  }
}
