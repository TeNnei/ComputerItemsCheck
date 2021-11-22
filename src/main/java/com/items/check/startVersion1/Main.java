package com.items.check.startVersion1;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;

public class Main {
  public static void main (String[] args){
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName(), Main::deployHandler);
  }
  public static void deployHandler(AsyncResult<String> ar) {
    if(ar.succeeded()) {
      System.out.println(ar.result() + " _ deployed!");
    }else{
      ar.cause().printStackTrace();
    }
  }
}
