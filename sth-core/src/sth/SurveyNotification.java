package sth;

public abstract class SurveyNotification {
  private String _disciplineName;
  private String _projectName;
  SurveyNotification(Survey s) {
    _disciplineName = s.disciplineName();
    _projectName = s.projectName();
  }
  public String getDisciplineName() { return _disciplineName; }
  public String getProjectName() { return _projectName; }
}
