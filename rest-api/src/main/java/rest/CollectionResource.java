package rest;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class CollectionResource<R extends ResourceSupport> extends ResourceSupport {

    private List<R> items;

    public CollectionResource() {

    }

    @SuppressWarnings("unused")
    public List<R> getItems() {
        return items;
    }

    public void setItems(List<R> entries) {
        this.items = entries;
    }
}
