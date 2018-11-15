package sth;

import java.io.Serializable;
class IncompleteSurveyResponse implements Serializable {
  private int _hours;
  private String _comment;
  
  IncompleteSurveyResponse(int hours, String comment) {
    _hours = hours;
    _comment = comment;
  }

  public int getHours() { return _hours; }
  public String getComment() { return _comment; }
}
