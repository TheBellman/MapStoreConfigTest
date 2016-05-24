package net.parttimepolymath.message;

import com.google.common.base.Objects;

public class Details {
    private final String contentType;
    private final String content;

    public Details(final String contentType, final String content) {
        this.contentType = contentType;
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Details details = (Details) o;
        return Objects.equal(contentType, details.contentType) && Objects.equal(content, details.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contentType, content);
    }

    @Override
    public String toString() {
        return "Details{" +
            "contentType='" + contentType + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
