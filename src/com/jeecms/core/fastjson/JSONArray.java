/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeecms.core.fastjson;

import static com.jeecms.core.fastjson.util.TypeUtils.castToBigDecimal;
import static com.jeecms.core.fastjson.util.TypeUtils.castToBigInteger;
import static com.jeecms.core.fastjson.util.TypeUtils.castToBoolean;
import static com.jeecms.core.fastjson.util.TypeUtils.castToByte;
import static com.jeecms.core.fastjson.util.TypeUtils.castToDate;
import static com.jeecms.core.fastjson.util.TypeUtils.castToDouble;
import static com.jeecms.core.fastjson.util.TypeUtils.castToFloat;
import static com.jeecms.core.fastjson.util.TypeUtils.castToInt;
import static com.jeecms.core.fastjson.util.TypeUtils.castToLong;
import static com.jeecms.core.fastjson.util.TypeUtils.castToShort;
import static com.jeecms.core.fastjson.util.TypeUtils.castToSqlDate;
import static com.jeecms.core.fastjson.util.TypeUtils.castToString;
import static com.jeecms.core.fastjson.util.TypeUtils.castToTimestamp;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import com.jeecms.core.fastjson.util.TypeUtils;

/**
 * @author wenshao<szujobs@hotmail.com>
 */
public class JSONArray extends JSON implements List<Object>, JSONAware, Cloneable, RandomAccess, Serializable {

    private static final long  serialVersionUID = 1L;
    private final List<Object> list;
    protected transient Object relatedArray;
    protected transient Type   componentType;

    public JSONArray(){
        this.list = new ArrayList<Object>(10);
    }

    public JSONArray(List<Object> list){
        this.list = list;
    }

    public JSONArray(int initialCapacity){
        this.list = new ArrayList<Object>(initialCapacity);
    }

    /**
     * @since 1.1.16
     * @return
     */
    public Object getRelatedArray() {
        return relatedArray;
    }

    public void setRelatedArray(Object relatedArray) {
        this.relatedArray = relatedArray;
    }

    public Type getComponentType() {
        return componentType;
    }

    public void setComponentType(Type componentType) {
        this.componentType = componentType;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<Object> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    public boolean add(Object e) {
        return list.add(e);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    public boolean addAll(Collection<? extends Object> c) {
        return list.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Object> c) {
        return list.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public void clear() {
        list.clear();
    }

    public Object set(int index, Object element) {
        return list.set(index, element);
    }

    public void add(int index, Object element) {
        list.add(index, element);
    }

    public Object remove(int index) {
        return list.remove(index);
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<Object> listIterator() {
        return list.listIterator();
    }

    public ListIterator<Object> listIterator(int index) {
        return list.listIterator(index);
    }

    public List<Object> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    public Object get(int index) {
        return list.get(index);
    }

    public JSONObject getJSONObject(int index) {
        Object value = list.get(index);

        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }

        return (JSONObject) toJSON(value);
    }

    public JSONArray getJSONArray(int index) {
        Object value = list.get(index);

        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }

        return (JSONArray) toJSON(value);
    }

    public <T> T getObject(int index, Class<T> clazz) {
        Object obj = list.get(index);
        return TypeUtils.castToJavaBean(obj, clazz);
    }

    public Boolean getBoolean(int index) {
        Object value = get(index);

        if (value == null) {
            return null;
        }

        return castToBoolean(value);
    }

    public boolean getBooleanValue(int index) {
        Object value = get(index);

        if (value == null) {
            return false;
        }

        return castToBoolean(value).booleanValue();
    }

    public Byte getByte(int index) {
        Object value = get(index);

        return castToByte(value);
    }

    public byte getByteValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0;
        }

        return castToByte(value).byteValue();
    }

    public Short getShort(int index) {
        Object value = get(index);

        return castToShort(value);
    }

    public short getShortValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0;
        }

        return castToShort(value).shortValue();
    }

    public Integer getInteger(int index) {
        Object value = get(index);

        return castToInt(value);
    }

    public int getIntValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0;
        }

        return castToInt(value).intValue();
    }

    public Long getLong(int index) {
        Object value = get(index);

        return castToLong(value);
    }

    public long getLongValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0L;
        }

        return castToLong(value).longValue();
    }

    public Float getFloat(int index) {
        Object value = get(index);

        return castToFloat(value);
    }

    public float getFloatValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0F;
        }

        return castToFloat(value).floatValue();
    }

    public Double getDouble(int index) {
        Object value = get(index);

        return castToDouble(value);
    }

    public double getDoubleValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0D;
        }

        return castToDouble(value);
    }

    public BigDecimal getBigDecimal(int index) {
        Object value = get(index);

        return castToBigDecimal(value);
    }

    public BigInteger getBigInteger(int index) {
        Object value = get(index);

        return castToBigInteger(value);
    }

    public String getString(int index) {
        Object value = get(index);

        return castToString(value);
    }

    public java.util.Date getDate(int index) {
        Object value = get(index);

        return castToDate(value);
    }

    public java.sql.Date getSqlDate(int index) {
        Object value = get(index);

        return castToSqlDate(value);
    }

    public java.sql.Timestamp getTimestamp(int index) {
        Object value = get(index);

        return castToTimestamp(value);
    }

    @Override
    public Object clone() {
        return new JSONArray(new ArrayList<Object>(list));
    }

    public boolean equals(Object obj) {
        return this.list.equals(obj);
    }

    public int hashCode() {
        return this.list.hashCode();
    }
}
