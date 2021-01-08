package com.bluejay.streaming.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
public class StreamQuality {
    private String identifier;
    private long bandwidth;
    private int width;
    private int height;
    private Set<String> codecs;
    private List<SegmentDescriptor> segmentsDescriptors;
}
