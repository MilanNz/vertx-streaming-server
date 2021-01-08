package com.bluejay.streaming;

import io.vertx.core.Vertx;

public class StreamingApplication {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new StreamerVerticle());
    }
}
