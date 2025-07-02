package common;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class RestDocsFieldItem {

    private String path = "";
    private JsonFieldType type;
    private String desc = "";
    private List<RestDocsFieldItem> children = new ArrayList<>();
    private State state = State.NONE;

    enum State {
        IGNORED,
        OPTIONAL,
        NONE
    }

    // 생성자 및 of 메서드는 기존과 동일, 단 children null 체크 추가
    public RestDocsFieldItem(String path, JsonFieldType type, String desc, State state, List<RestDocsFieldItem> children) {
        this.path = path;
        this.type = type;
        this.desc = desc;
        this.state = state;
        this.children = (children != null) ? children : new ArrayList<>();
    }

    public RestDocsFieldItem(String path, JsonFieldType type, String desc, State state) {
        this.path = path;
        this.type = type;
        this.desc = desc;
        this.state = state;
    }

    public RestDocsFieldItem(String path, List<RestDocsFieldItem> children) {
        this.path = path;
        this.children = (children != null) ? children : new ArrayList<>();
    }

    public static RestDocsFieldItem of(String path, JsonFieldType type, String desc, State state, RestDocsFieldItem... children) {
        return new RestDocsFieldItem(path, type, desc, state, Arrays.asList(children));
    }

    public static RestDocsFieldItem of(String path, JsonFieldType type, String desc, State state) {
        return new RestDocsFieldItem(path, type, desc, state);
    }

    public static RestDocsFieldItem of(String path, JsonFieldType type, String desc, RestDocsFieldItem... children) {
        return new RestDocsFieldItem(path, type, desc, State.NONE, Arrays.asList(children));
    }

    public static RestDocsFieldItem of(String path, JsonFieldType type, String desc) {
        return new RestDocsFieldItem(path, type, desc, State.NONE);
    }

    public static RestDocsFieldItem of(String path, List<RestDocsFieldItem> children) {
        return new RestDocsFieldItem(path, children);
    }

    public static RestDocsFieldItem of(String path, RestDocsFieldItem... children) {
        return new RestDocsFieldItem(path, Arrays.asList(children));
    }

    public static RestDocsFieldItem of(RestDocsFieldItem... children) {
        return new RestDocsFieldItem("", Arrays.asList(children));
    }

    public FieldDescriptor toField() {
        FieldDescriptor f = fieldWithPath(this.path)
                .type(this.type)
                .description(this.desc);
        switch (this.state) {
            case IGNORED:
                return f.ignored();
            case OPTIONAL:
                return f.optional();
            default:
                return f;
        }
    }

    public List<RestDocsFieldItem> toFlatList(String superPath) {
        List<RestDocsFieldItem> list = new ArrayList<>();
        String combinedPath = this.path;
        if (superPath != null && !"".equals(superPath)) {
            combinedPath = superPath + "." + this.path;
        }
        if (this.type != null || this.children == null || this.children.size() < 1) {
            list.add(new RestDocsFieldItem(combinedPath, this.type, this.desc, this.state, this.children));
        }

        for (RestDocsFieldItem child : children) {
            list.addAll(child.toFlatList(combinedPath)); // 재귀
        }
        return list;
    }

    public List<RestDocsFieldItem> toFlatList() {
        return toFlatList("");
    }

    public List<FieldDescriptor> build() {
        List<FieldDescriptor> list = new ArrayList<>();
        for (RestDocsFieldItem i : toFlatList()) {
            list.add(i.toField());
        }
        return list;
    }


}
