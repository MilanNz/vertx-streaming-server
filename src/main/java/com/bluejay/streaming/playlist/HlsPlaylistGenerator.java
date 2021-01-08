package com.bluejay.streaming.playlist;

import com.bluejay.streaming.entity.SegmentDescriptor;
import com.bluejay.streaming.entity.Stream;
import com.bluejay.streaming.entity.StreamQuality;

import java.util.function.Supplier;

public class HlsPlaylistGenerator implements PlaylistGenerator {
    private static final String MASTER_PLAYLIST_URL_FORMAT = "http://%s/variant/%s/%d";
    private static final String MEDIA_PLAYLIST_URL_FORMAT = "http://%s/segment/%s/%d/%d";
    private Supplier<String> hostSupplier;

    @Override
    public String mediaPlaylist(StreamQuality streamQuality) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#EXTM3U").append("\n");
        stringBuilder.append("#EXT-X-PLAYLIST-TYPE:VOD").append("\n");
        stringBuilder.append("#EXT-X-TARGETDURATION:10").append("\n");
        stringBuilder.append("#EXT-X-VERSION:4").append("\n");
        stringBuilder.append("#EXT-X-MEDIA-SEQUENCE:0").append("\n");

        for (SegmentDescriptor segmentDescriptor : streamQuality.getSegmentsDescriptors()) {
            stringBuilder.append("#EXTINF:").append(segmentDescriptor.getDuration()).append("\n");
            stringBuilder.append(String.format(MEDIA_PLAYLIST_URL_FORMAT,
                    hostSupplier.get(),
                    streamQuality.getIdentifier(),
                    streamQuality.getBandwidth(),
                    segmentDescriptor.getId())).append("\n");
        }

        stringBuilder.append("#EXT-X-ENDLIST");

        return stringBuilder.toString();
    }

    @Override
    public void setHostProvider(Supplier<String> hostSupplier) {
        this.hostSupplier = hostSupplier;
    }

    @Override
    public String masterPlaylist(Stream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#EXTM3U").append("\n");
        stringBuilder.append("#EXT-X-VERSION:4").append("\n");
        stringBuilder.append("#EXT-X-INDEPENDENT-SEGMENTS").append("\n");

        for (StreamQuality streamQuality : stream.getStreamQualities()) {
            stringBuilder.append("#EXT-X-STREAM-INF:")
                    .append("BANDWIDTH=").append(streamQuality.getBandwidth());

            if (streamQuality.getWidth() != 0 && streamQuality.getHeight() != 0) {
                stringBuilder.append(",RESOLUTION=")
                        .append(streamQuality.getWidth()).append("x").append(streamQuality.getHeight());
            }

            stringBuilder.append(",CODECS=\"")
                    .append(String.join(",", streamQuality.getCodecs())).append("\"")
                    .append("\n");

            stringBuilder.append(String.format(MASTER_PLAYLIST_URL_FORMAT,
                    hostSupplier.get(), stream.getIdentifier(), streamQuality.getBandwidth())).append("\n");
        }

        return stringBuilder.toString();
    }
}
