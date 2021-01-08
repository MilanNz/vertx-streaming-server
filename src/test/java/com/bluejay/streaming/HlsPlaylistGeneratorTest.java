package com.bluejay.streaming;

import com.bluejay.streaming.entity.SegmentDescriptor;
import com.bluejay.streaming.entity.Stream;
import com.bluejay.streaming.entity.StreamQuality;
import com.bluejay.streaming.playlist.HlsPlaylistGenerator;
import com.bluejay.streaming.playlist.PlaylistGenerator;
import com.bluejay.streaming.utils.CollectionHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HlsPlaylistGeneratorTest {

    @Test
    public void testMasterPlaylist() {
        List<SegmentDescriptor> segmentDescriptorList = new ArrayList<>();
        segmentDescriptorList.add(new SegmentDescriptor(1, 6000));
        segmentDescriptorList.add(new SegmentDescriptor(2, 6000));
        segmentDescriptorList.add(new SegmentDescriptor(3, 6000));

        StreamQuality streamQuality = StreamQuality.builder()
                .height(1080)
                .width(1920)
                .codecs(CollectionHelper.putInSet("avc1.42c015", "mp4a.40.2"))
                .segmentsDescriptors(segmentDescriptorList)
                .bandwidth(300000)
                .identifier("pt-groupt-phase-01")
                .build();

        StreamQuality streamQuality2 = StreamQuality.builder()
                .height(1080)
                .width(1920)
                .codecs(CollectionHelper.putInSet("avc1.42c015", "mp4a.40.2"))
                .segmentsDescriptors(segmentDescriptorList)
                .bandwidth(600000)
                .identifier("pt-groupt-phase-01")
                .build();

        StreamQuality streamQualityAO = StreamQuality.builder()
                .height(0)
                .codecs(CollectionHelper.putInSet("avc1.42c015", "mp4a.40.2"))
                .segmentsDescriptors(segmentDescriptorList)
                .bandwidth(96000)
                .identifier("pt-groupt-phase-01")
                .build();

        Stream stream = new Stream();
        stream.setId(1);
        stream.setIdentifier("pt-groupt-phase-01");
        stream.setStreamQualities(CollectionHelper.putInList(streamQuality, streamQuality2, streamQualityAO));

        PlaylistGenerator playlistGenerator = new HlsPlaylistGenerator();
        String masterPlaylist = playlistGenerator.masterPlaylist(stream);

        System.out.println(masterPlaylist);
    }

    @Test
    public void testMediaPlaylist() {
        List<SegmentDescriptor> segmentDescriptorList = new ArrayList<>();
        segmentDescriptorList.add(new SegmentDescriptor(1, 6000));
        segmentDescriptorList.add(new SegmentDescriptor(2, 6000));
        segmentDescriptorList.add(new SegmentDescriptor(3, 6000));

        StreamQuality streamQuality = StreamQuality.builder()
                .height(1080)
                .width(1920)
                .codecs(CollectionHelper.putInSet("avc1.42c015", "mp4a.40.2"))
                .segmentsDescriptors(segmentDescriptorList)
                .bandwidth(300000)
                .identifier("pt-groupt-phase-01")
                .build();

        PlaylistGenerator playlistGenerator = new HlsPlaylistGenerator();
        String mediaPlaylist = playlistGenerator.mediaPlaylist(streamQuality);
        System.out.println(mediaPlaylist);
    }
}
