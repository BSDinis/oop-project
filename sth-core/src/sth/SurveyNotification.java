package sth;

public abstract class SurveyNotification {
  private String _disciplineName;
  private String _projectName;
  SurveyNotification(String disciplineName, String projectName) {
    _disciplineName = disciplineName;
    _projectName = projectName;
  }
  public String getDisciplineName() { return _disciplineName; }
  public String getProjectName() { return _projectName; }
}
