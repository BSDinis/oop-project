package sth;

import java.io.Serializable;

class SurveyResponse 
  implements Serializable {

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
