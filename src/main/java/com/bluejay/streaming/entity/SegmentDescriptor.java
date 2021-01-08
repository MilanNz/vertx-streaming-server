package com.bluejay.streaming.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SegmentDescriptor {
    private long id;
    private double duration;
}
