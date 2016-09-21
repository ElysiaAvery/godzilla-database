import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import java.util.ArrayList;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("godzillas", Godzilla.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/godzilla", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String era = request.queryParams("era");
      String enemy = request.queryParams("enemy");
      Godzilla newGodzilla = new Godzilla(era, enemy);
      newGodzilla.save();
      model.put("godzillas", Godzilla.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/godzilla/:id/medias", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Godzilla godzilla = Godzilla.find(Integer.parseInt(request.params(":id")));
      model.put("medias", Media.all());
      model.put("godzilla", godzilla);
      model.put("template", "templates/godzilla.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/godzilla/:id/medias", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String type = request.queryParams("type");
      String title = request.queryParams("title");
      String description = request.queryParams("description");
      int godzillaId = Integer.parseInt(request.params(":id"));
      Media media = new Media(type, title, description, godzillaId);
      media.save();
      model.put("medias", Godzilla.find(godzillaId).getMedias());
      model.put("template", "templates/medias.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
