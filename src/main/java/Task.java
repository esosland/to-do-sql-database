import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private String description;
  private int category_id;

  // public Task(String description, int cat_id) {
  //   this.description = description;
  //   this.category_id = cat_id;
  // }

  public Task(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  // @Override
  public boolean taskEquals(Task otherTask) {
    return this.getDescription().equals(otherTask.getDescription()) &&
            this.getId() == otherTask.getId();
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("description", this.description)
      .executeUpdate()
      .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }
}
