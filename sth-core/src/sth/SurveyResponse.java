package sth;

class SurveyResponse {
  private Student _student;
  private int _hours;
  private String _comment;
  
  SurveyResponse(Student student, int hours, String comment) {
    _student = student;
    _hours = hours;
    _comment = comment;
  }

  SurveyResponse(Student student, IncompleteSurveyResponse resp) {
    this(student, resp.getHours(), resp.getComment());
  }
}
