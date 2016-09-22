import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
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
      godzilla.calculateAverage();
      model.put("medias", godzilla.getMedias());
      model.put("godzilla", godzilla);
      model.put("template", "templates/godzilla.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/godzilla/:id/medias/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String type = request.queryParams("type");
      String title = request.queryParams("title");
      String description = request.queryParams("description");
      int godzillaId = Integer.parseInt(request.params(":id"));
      Media media = new Media(type, title, description, godzillaId);
      media.save();
      model.put("godzilla", Godzilla.find(godzillaId));
      model.put("template", "templates/godzilla.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/medias/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Media media = Media.find(Integer.parseInt(request.params(":id")));
      media.calculateAverage();
      model.put("media", media);
      model.put("template", "templates/medias.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/media/:id/comment", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Media media = Media.find(Integer.parseInt(request.params(":id")));
      Comment comment = new Comment(request.queryParams("media-comment"), media.getId(), 0);
      comment.save();
      model.put("media", media);
      model.put("template", "templates/medias.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/godzilla/:id/traits/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Godzilla godzilla = Godzilla.find(Integer.parseInt(request.params(":id")));
      model.put("godzilla", godzilla);
      model.put("template", "templates/edit-traits.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/godzilla/:id/traits/edit/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String trait = request.queryParams("edit-traits");
      Godzilla godzilla = Godzilla.find(Integer.parseInt(request.params(":id")));
      godzilla.updateTraits(trait);
      model.put("godzilla", godzilla);
      model.put("template", "templates/godzilla.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/godzilla/:id/rating/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Godzilla godzilla = Godzilla.find(Integer.parseInt(request.params(":id")));
      int userRating = Integer.parseInt(request.queryParams("rating"));
      for (int i = 0; i < Rating.getRatingsArray().length; i++) {
        if(userRating == Rating.getRatingsArray()[i]) {
          Rating rating = new Rating(Rating.getRatingsArray()[i], 0, godzilla.getId());
          rating.save();
        }
      }
      godzilla.calculateAverage();
      model.put("godzilla", godzilla);
      model.put("template", "templates/godzilla.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/media/:id/description/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Media media = Media.find(Integer.parseInt(request.params(":id")));
      model.put("media", media);
      model.put("template", "templates/edit-description.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/media/:id/description/edit/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String description = request.queryParams("edit-description");
      Media media = Media.find(Integer.parseInt(request.params(":id")));
      media.updateDescription(description);
      model.put("media", media);
      model.put("template", "templates/medias.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/media/:mediaId/comment/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Comment comment = Comment.find(Integer.parseInt(request.params(":id")));
      Media media = Media.find(comment.getMediaId());
      model.put("media", media);
      model.put("comment", comment);
      model.put("template", "templates/edit-comments.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/media/:mediaId/comment/:id/edit/new", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      String newComment = request.queryParams("edit-comment");
      Comment comment = Comment.find(Integer.parseInt(request.params(":id")));
      Media media = Media.find(comment.getMediaId());
      model.put("media", media);
      comment.updateComment(newComment);
      model.put("comment", comment);
      model.put("template", "templates/medias.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/media/:id/rating", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      Media media = Media.find(Integer.parseInt(request.params(":id")));
      int userRating = Integer.parseInt(request.queryParams("rating"));
      for (int i = 0; i < Rating.getRatingsArray().length; i++) {
        if(userRating == Rating.getRatingsArray()[i]) {
          Rating rating = new Rating(Rating.getRatingsArray()[i], media.getId(), 0);
          rating.save();
        }
      }
      media.calculateAverage();
      model.put("media", media);
      model.put("template", "templates/medias.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
