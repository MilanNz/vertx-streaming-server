package com.bluejay.streaming.playlist;

import com.bluejay.streaming.entity.Stream;
import com.bluejay.streaming.entity.StreamQuality;

public interface PlaylistGenerator {
    String masterPlaylist(Stream stream);
    String mediaPlaylist(StreamQuality streamQuality);
    default String getServerHost() {
        return "localhost";
    }
}
