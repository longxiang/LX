package com.jeecms.core.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import com.jeecms.core.fastjson.parser.DefaultJSONParser;

public interface ObjectDeserializer {
    <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName);
    
    int getFastMatchToken();
}
