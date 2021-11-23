package com.items.check.startVersion1;

import com.google.gson.Gson;
import com.items.check.startVersion1.DataBaseFolder.DataBaseConnectivity;
import com.items.check.startVersion1.integration.ChangesNoteDB;
import com.items.check.startVersion1.integration.ItemsForDataBaseCheck;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        // Приходит сообщение через REST запрос в формате JSON идет проверка в
        // БД есть ли какие-нибудь записи о компьютере
        DataBaseConnectivity.checkIfNotExist(items).future().compose(result -> {
          // Если записей нет, то запись о компьютере создается
          if(result.size() == 0){
            try {
              // Создание записи в БД
              DataBaseConnectivity.insertForCheck(items);
            } catch (SQLException throwables) {
              context.fail(throwables.getCause());
            }
            // Возвращаем пустой лист на response так как данных о компьютере нет
            return Future.succeededFuture(new ArrayList<>());
          } else {
            // Если записи есть то данный алгоритм проверяет наличие изменения какого либо поля
            List<ChangesNoteDB> listWithChages = new ArrayList<>();
            String computerTitle = result.get(0).getComputerTitle();
            String processor = result.get(0).getProcessor();
            String GPU = result.get(0).getGPU();
            String RAM = result.get(0).getRAM();
            String SSD = result.get(0).getSSD();
            if (!processor.equals(items.getProcessor())){
              ChangesNoteDB changesNoteDB = new ChangesNoteDB()
                .setTarget(computerTitle).setOldValue(processor).setNewValue(items.getProcessor()).setDateOfChange(LocalDateTime.now());
              listWithChages.add(changesNoteDB);
            }
            if (!GPU.equals(items.getGPU())){
              ChangesNoteDB changesNoteDB = new ChangesNoteDB()
                .setTarget(computerTitle).setOldValue(GPU).setNewValue(items.getGPU()).setDateOfChange(LocalDateTime.now());
              listWithChages.add(changesNoteDB);
            }
            if (!RAM.equals(items.getRAM())){
              ChangesNoteDB changesNoteDB = new ChangesNoteDB()
                .setTarget(computerTitle).setOldValue(RAM).setNewValue(items.getRAM()).setDateOfChange(LocalDateTime.now());
              listWithChages.add(changesNoteDB);
            }
            if (!SSD.equals(items.getSSD())){
              ChangesNoteDB changesNoteDB = new ChangesNoteDB()
                .setTarget(computerTitle).setOldValue(SSD).setNewValue(items.getSSD()).setDateOfChange(LocalDateTime.now());
              listWithChages.add(changesNoteDB);
            }
            // Если лист с изменениями пустой тобишь изменений нету
            // То просто записываем в бд Текущее состояние,
            // Хотя для оптимизации, лучше записывать в бд только измененное состояние что-бы в бд
            // Не было мусора ввиде повторяющихся записей
            if (listWithChages.size() == 0){
              try {
                DataBaseConnectivity.insertForCheck(items);
              } catch (SQLException e) {
                e.printStackTrace();
              }
            }
            //Записываем все изменения за сессию в БД
            try {
              DataBaseConnectivity.insertCheckedItem(listWithChages);
            } catch (SQLException e) {
              e.printStackTrace();
            }
            // Возвращаем обратно именно лист с изменениями, так как изменений у одного компьютера может быть несколько
            // Допустим у компьютера может измениться GPU и RAN за одну сессию
            return Future.succeededFuture(listWithChages);
          }
        }).onComplete(ar -> {
          // Возвращаем response в виде JSON только в том случае если есть изменения
          // Если изменений нет в response пустой лист, дабы не нагружать сеть ненужными данными
          if (ar.succeeded()){
            context.response().putHeader(HttpHeaders.CONTENT_TYPE,"application/json;charset=utf-8").end(gson.toJson(ar.result().get(0)));
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
