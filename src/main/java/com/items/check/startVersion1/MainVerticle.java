package com.items.check.startVersion1;

import com.google.gson.Gson;
import com.items.check.startVersion1.DataBaseFolder.DataBaseConnectivity;
import com.items.check.startVersion1.integration.ChangesNoteDB;
import com.items.check.startVersion1.integration.ItemsForDataBaseCheck;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/*
Create By Tsoy Vladislav 22.11.2021
 */

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.post("/api/v1/check/items").handler(context -> {
      Gson gson = new Gson();
      ItemsForDataBaseCheck items = gson.fromJson(context.getBodyAsString(), ItemsForDataBaseCheck.class);
      try {
        DataBaseConnectivity.checkIfNotExist(items).future().compose(result -> {
          if(result.size() == 0){
            try {
              DataBaseConnectivity.insertForCheck(items);
            } catch (SQLException throwables) {
              context.fail(throwables.getCause());
            }
            return Future.succeededFuture(new ArrayList<>());
          } else {
            ChangesNoteDB changesNoteDB = new ChangesNoteDB();
            for (ItemsForDataBaseCheck items1: result){
              String processor = items1.getProcessor();
              String GPU = items1.getGPU();
              String RAM = items1.getRAM();
              String SSD = items1.getSSD();

            }
            return Future.succeededFuture(Collections.singletonList(changesNoteDB));
          }
        }).onComplete(ar -> {
          if (ar.succeeded()){

          }else {
            context.fail(ar.cause());
          }
        });
      } catch (SQLException throwables) {
        context.fail(throwables.getCause());
      }
    });
    vertx.createHttpServer().requestHandler(router).listen(8080, "0.0.0.0", handler -> {
      if(handler.failed()){
        handler.cause().printStackTrace();
      }
      else{
        System.out.println("Server is started!");
      }
    });
  }
}
