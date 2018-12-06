package sth;

import java.io.Serializable;
public abstract class SurveyNotification 
  implements Serializable {
  private String _disciplineName;
  private String _projectName;
  SurveyNotification(Survey s) {
    _disciplineName = s.disciplineName();
    _projectName = s.projectName();
  }
  public String getDisciplineName() { return _disciplineName; }
  public String getProjectName() { return _projectName; }
  abstract public String print(SurveyNotificationPrinter p);
}
