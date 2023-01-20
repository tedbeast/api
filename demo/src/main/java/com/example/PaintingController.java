package com.example;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;

public class PaintingController {
    
    Javalin app;
    public PaintingService paintingService;
    public PaintingController(){
        app = Javalin.create();
        paintingService = new PaintingService();
    }
    public void startAPI(){
        // endpoint that returns all paintings
        app.get("/painting", ctx -> {
            List<Painting> paintings = paintingService.getAllPaintings();
            ctx.json(paintings);
        });
        // endpoint that adds (POSTS) a new painting - assume a valid painting in my request body
        app.post("/painting", ctx -> {
            ObjectMapper om = new ObjectMapper();
            Painting painting = om.readValue(ctx.body(), Painting.class);
            Painting p = paintingService.insertPainting(painting);
            ctx.json(p);
        });
        app.get("/painting/oldestyear", ctx -> {
            int year = paintingService.getOldestPaintingYear();
            ctx.json(year);
        });
        app.get("/painting/{year}", ctx -> {
            String year_input = ctx.pathParam("year");
            
            try{
                int year = Integer.parseInt(year_input);
                List<Painting> paintingsInYear = paintingService.getAllPaintingMadeInYear(year);
                ctx.json(paintingsInYear);
            }catch(NumberFormatException e){
                ctx.status(400);
            }
        });
        app.start();

        /**
         * other http verbs:
         * get
         * post
         * delete
         * 
         * put (total update - replace everything in a particular item)
         * patch (partial update)
         */

         /**
          * the three layer architecture
          * testing / mockito
          */
    }
}
