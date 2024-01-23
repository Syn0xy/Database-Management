package view.util;

public interface Observer {
        public void update(Subject subj);
        public void update(Subject subj, Object data);
}
