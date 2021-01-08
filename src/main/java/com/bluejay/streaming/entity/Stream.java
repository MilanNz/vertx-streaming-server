package com.bluejay.streaming.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Stream {
    private long id;
    private String identifier;
    private List<StreamQuality> streamQualities;
}
