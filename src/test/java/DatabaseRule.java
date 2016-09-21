import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
 protected void before() {
   DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/godzilla_test", null, null);
 }

 @Override
 protected void after() {
   try(Connection con = DB.sql2o.open()) {
     String deleteMediasQuery = "DELETE FROM medias *;";
     String deleteGodzillasQuery = "DELETE FROM godzillas *;";
     String deleteRatingsQuery = "DELETE FROM ratings *;";
     con.createQuery(deleteMediasQuery).executeUpdate();
     con.createQuery(deleteGodzillasQuery).executeUpdate();
     con.createQuery(deleteRatingsQuery).executeUpdate();
   }
 }
}
