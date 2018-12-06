package sth;

import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
class SurveyObserver {
  private Survey _survey;
  private Queue<SurveyNotification> _notifs = new LinkedList<>();
  private boolean _subscribed = true; // default

  SurveyObserver(Survey s) {
    _survey = s;
    _survey.addObserver(this);
  }

  void subscribe() { _subscribed = true; }
  void unsubscribe() { _subscribed = false; }

  void update() {
    if (_subscribed) {
      SurveyNotification notif = _survey.genNotification();
      if (notif == null) return;

      try {
        _notifs.add(notif);
      }
      catch (IllegalStateException e) {
        e.printStackTrace();
      }
    }
  }

  Collection<SurveyNotification> flushNotifications() {
    Collection<SurveyNotification> flushed = _notifs;
    _notifs = new LinkedList<>();
    return flushed;
  }

}
