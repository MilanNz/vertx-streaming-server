package com.bluejay.streaming;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import java.io.OutputStream;

public class StreamerVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(StreamerVerticle.class.getSimpleName());

    private HttpServer httpServer = null;

    @Override
    public void start() throws Exception {
        logger.info("## Streamer start");
        httpServer = vertx.createHttpServer();

        StreamerHandler streamerHandler = new StreamerHandler("localhost:8999");

        Router router = Router.router(vertx);
        router.get("/play/:asset_identifier").handler(routingContext -> {
            HttpServerRequest request = routingContext.request();
            String assetIdentifier = request.getParam("asset_identifier");
            routingContext.response().setStatusCode(200).end(streamerHandler.handleMasterPlaylist(assetIdentifier));
        });

        router.get("/variant/:asset_identifier/:quality").handler(routingContext -> {
            HttpServerRequest request = routingContext.request();
            String assetIdentifier = request.getParam("asset_identifier");
            int quality = Integer.parseInt(request.getParam("quality"));
            routingContext.response().setStatusCode(200).end(streamerHandler.handleMediaPlaylist(assetIdentifier, quality));
        });

        router.get("/segment/:asset_identifier/:quality/:segment_id").handler(routingContext -> {
            HttpServerRequest request = routingContext.request();
            String assetIdentifier = request.getParam("asset_identifier");
            int quality = Integer.parseInt(request.getParam("quality"));
            int segmentId = Integer.parseInt(request.getParam("segment_id"));

            Buffer buffer = Buffer.buffer(streamerHandler.handleGetSegment(assetIdentifier, quality, segmentId));
            HttpServerResponse response = routingContext.response();

            response.setStatusCode(200);
            response.putHeader("Content-Length", String.valueOf(buffer.length()));
            response.write(buffer);
            response.end();
        });

        httpServer.requestHandler(router).listen(8999);
    }

    @Override
    public void stop() throws Exception {
        logger.info("## Streamer end");
        if (httpServer != null) {
            httpServer.close();
        }
    }
}
