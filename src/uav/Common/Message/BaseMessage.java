/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Common.Message;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseMessage {
    private String id;

    @JsonProperty(value = "Type", defaultValue = "Command")
    private String type;

    @JsonProperty("Name")
    private String name;

    @JsonSerialize(using = ZDTSerializer.class)
    private ZonedDateTime zdt;

    @JsonIgnore
    private ZonedDateTime rzdt;

    @JsonProperty("paramsIds")
    private List<String> paramsIds = new ArrayList<>();

    private Map<String, Object> properties = new HashMap<>();

    @JsonSetter(value = "msgId")
    public void setId(String id) {
        this.id = id;
    }

    @JsonRawValue
    @JsonGetter(value = "msgId")
    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setZdt(ZonedDateTime zdt) {
        this.zdt = zdt;
    }

    public ZonedDateTime getZdt() {
        return zdt;
    }

    public void setRzdt(ZonedDateTime rzdt) {
        this.rzdt = rzdt;
    }

    public ZonedDateTime getRzdt() {
        return rzdt;
    }

    public void setParamsIds(List<String> paramsIds) {
        this.paramsIds = paramsIds;
    }

    public List<String> getParamsIds() {
        return paramsIds;
    }

    @JsonAnySetter
    public void setProperties(String key, Object value) {
        properties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }
}
