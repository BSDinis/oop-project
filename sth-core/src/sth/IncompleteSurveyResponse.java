package sth;

import java.io.Serializable;

// FIXME : kill this pls

class IncompleteSurveyResponse 
  implements Serializable {
  private int _hours;
  private String _comment;
  
  IncompleteSurveyResponse(int hours, String comment) {
    _hours = hours;
    _comment = comment;
  }

  int getHours() { return _hours; }
  String getComment() { return _comment; }
}
