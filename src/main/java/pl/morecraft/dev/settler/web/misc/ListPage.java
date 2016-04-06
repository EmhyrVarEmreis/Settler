package pl.morecraft.dev.settler.web.misc;

import java.util.List;

public class ListPage<T> {

    private Long total;
    private List<T> content;

    public ListPage() {
    }

    public ListPage(Long total, List<T> content) {
        this.total = total;
        this.content = content;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

}
