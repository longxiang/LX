package com.jeecms.core.fastjson;

import static com.jeecms.core.fastjson.JSONStreamContext.ArrayValue;
import static com.jeecms.core.fastjson.JSONStreamContext.PropertyKey;
import static com.jeecms.core.fastjson.JSONStreamContext.PropertyValue;
import static com.jeecms.core.fastjson.JSONStreamContext.StartArray;
import static com.jeecms.core.fastjson.JSONStreamContext.StartObject;

import java.io.Closeable;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

import com.jeecms.core.fastjson.parser.DefaultJSONParser;
import com.jeecms.core.fastjson.parser.Feature;
import com.jeecms.core.fastjson.parser.JSONLexer;
import com.jeecms.core.fastjson.parser.JSONReaderScanner;
import com.jeecms.core.fastjson.parser.JSONToken;
import com.jeecms.core.fastjson.util.IOUtils;
import com.jeecms.core.fastjson.util.TypeUtils;

public class JSONReader implements Closeable {

    private final DefaultJSONParser parser;
    private JSONStreamContext       context;

    public JSONReader(Reader reader){
        this(new JSONReaderScanner(reader));
    }

    public JSONReader(JSONLexer lexer){
        this(new DefaultJSONParser(lexer));
    }

    public JSONReader(DefaultJSONParser parser){
        this.parser = parser;
    }

    public void config(Feature feature, boolean state) {
        this.parser.config(feature, state);
    }

    public void startObject() {
        if (context == null) {
            context = new JSONStreamContext(null, JSONStreamContext.StartObject);
        } else {
            startStructure();
            context = new JSONStreamContext(context, JSONStreamContext.StartObject);
        }

        this.parser.accept(JSONToken.LBRACE, JSONToken.IDENTIFIER);
    }

    public void endObject() {
        this.parser.accept(JSONToken.RBRACE);
        endStructure();
    }

    public void startArray() {
        if (context == null) {
            context = new JSONStreamContext(null, StartArray);
        } else {
            startStructure();

            context = new JSONStreamContext(context, StartArray);
        }
        this.parser.accept(JSONToken.LBRACKET);
    }

    public void endArray() {
        this.parser.accept(JSONToken.RBRACKET);
        endStructure();
    }

    private void startStructure() {
        final int state = context.getState();
        switch (state) {
            case PropertyKey:
                parser.accept(JSONToken.COLON);
                break;
            case PropertyValue:
            case ArrayValue:
                parser.accept(JSONToken.COMMA);
                break;
            case StartArray:
            case StartObject:
                break;
            default:
                throw new JSONException("illegal state : " + context.getState());
        }
    }

    private void endStructure() {
        context = context.getParent();

        if (context == null) {
            return;
        }
        
        final int state = context.getState();
        int newState = -1;
        switch (state) {
            case PropertyKey:
                newState = PropertyValue;
                break;
            case StartArray:
                newState = ArrayValue;
                break;
            case PropertyValue:
            case StartObject:
                newState = PropertyKey;
                break;
            default:
                break;
        }
        if (newState != -1) {
            context.setState(newState);
        }
    }

    public boolean hasNext() {
        if (context == null) {
            throw new JSONException("context is null");
        }

        final int token = parser.getLexer().token();
        final int state = context.getState();
        switch (state) {
            case StartArray:
            case ArrayValue:
                return token != JSONToken.RBRACKET;
            case StartObject:
            case PropertyValue:
                return token != JSONToken.RBRACE;
            default:
                throw new JSONException("illegal state : " + state);
        }
    }

    public void close() {
        IOUtils.close(parser);
    }

    public Integer readInteger() {
        Object object;
        if (context == null) {
            object = parser.parse();
        } else {
            readBefore();
            object = parser.parse();
            readAfter();
        }

        return TypeUtils.castToInt(object);
    }

    public Long readLong() {
        Object object;
        if (context == null) {
            object = parser.parse();
        } else {
            readBefore();
            object = parser.parse();
            readAfter();
        }

        return TypeUtils.castToLong(object);
    }

    public String readString() {
        Object object;
        if (context == null) {
            object = parser.parse();
        } else {
            readBefore();
            object = parser.parse();
            readAfter();
        }

        return TypeUtils.castToString(object);
    }
    
    public <T> T readObject(TypeReference<T> typeRef) {
        return readObject(typeRef.getType());
    }

    public <T> T readObject(Type type) {
        if (context == null) {
            return parser.parseObject(type);
        }

        readBefore();
        T object = parser.parseObject(type);
        readAfter();
        return object;
    }

    public <T> T readObject(Class<T> type) {
        if (context == null) {
            return parser.parseObject(type);
        }

        readBefore();
        T object = parser.parseObject(type);
        readAfter();
        return object;
    }

    public void readObject(Object object) {
        if (context == null) {
            parser.parseObject(object);
            return;
        }

        readBefore();
        parser.parseObject(object);
        readAfter();
    }

    public Object readObject() {
        if (context == null) {
            return parser.parse();
        }

        readBefore();
        Object object;
        switch (context.getState()) {
            case StartObject:
            case PropertyValue:
                object = parser.parseKey();
                break;
            default:
                object = parser.parse();
                break;
        }

        readAfter();
        return object;
    }

    @SuppressWarnings("rawtypes")
    public Object readObject(Map object) {
        if (context == null) {
            return parser.parseObject(object);
        }

        readBefore();
        Object value = parser.parseObject(object);
        readAfter();
        return value;
    }

    private void readBefore() {
        int state = context.getState();
        // before
        switch (state) {
            case PropertyKey:
                parser.accept(JSONToken.COLON);
                break;
            case PropertyValue:
                parser.accept(JSONToken.COMMA, JSONToken.IDENTIFIER);
                break;
            case ArrayValue:
                parser.accept(JSONToken.COMMA);
                break;
            case StartObject:
                break;
            case StartArray:
                break;
            default:
                throw new JSONException("illegal state : " + state);
        }
    }

    private void readAfter() {
        int state = context.getState();
        int newStat = -1;
        switch (state) {
            case StartObject:
                newStat = PropertyKey;
                break;
            case PropertyKey:
                newStat = PropertyValue;
                break;
            case PropertyValue:
                newStat = PropertyKey;
                break;
            case ArrayValue:
                break;
            case StartArray:
                newStat = ArrayValue;
                break;
            default:
                throw new JSONException("illegal state : " + state);
        }
        if (newStat != -1) {
            context.setState(newStat);
        }
    }

}
