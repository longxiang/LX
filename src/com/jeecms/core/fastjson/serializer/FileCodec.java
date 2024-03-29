package com.jeecms.core.fastjson.serializer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import com.jeecms.core.fastjson.parser.DefaultJSONParser;
import com.jeecms.core.fastjson.parser.JSONToken;
import com.jeecms.core.fastjson.parser.deserializer.ObjectDeserializer;

public class FileCodec implements ObjectSerializer, ObjectDeserializer {

    public static FileCodec instance = new FileCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
        SerializeWriter out = serializer.getWriter();
        
        if (object == null) {
            out.writeNull();
            return;
        }
        
        File file = (File) object;
        
        serializer.write(file.getPath());
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Object value = parser.parse();

        if (value == null) {
            return null;
        }
        
        String path = (String) value;
        
        return (T) new File(path);
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }
}
