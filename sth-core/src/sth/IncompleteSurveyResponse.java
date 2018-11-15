package sth;

class IncompleteSurveyResponse {
  private int _hours;
  private String _comment;
  
  IncompleteSurveyResponse(int hours, String comment) {
    _hours = hours;
    _comment = comment;
  }

  public int getHours() { return _hours; }
  public int getComment() { return _comment; }
}
