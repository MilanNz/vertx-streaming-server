package com.bluejay.streaming.playlist;

import com.bluejay.streaming.entity.Stream;
import com.bluejay.streaming.entity.StreamQuality;

import java.util.function.Supplier;

public interface PlaylistGenerator {
    String masterPlaylist(Stream stream);
    String mediaPlaylist(StreamQuality streamQuality);
    void setHostProvider(Supplier<String> hostSupplier);
}
