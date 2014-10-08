package rest;

import db.AbstractRepository;
import db.Entity;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static rest.util.Json.jsonToMap;

public abstract class AbstractController<E extends Entity, R extends ResourceSupport> {

    public static final String DEFAULT_PAGE_SIZE = "50";

    protected final AbstractRepository<E> repository;
    protected final ResourceAssemblerSupport<E, R> assembler;

    public AbstractController(AbstractRepository<E> repository, ResourceAssemblerSupport<E, R> assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> create(@RequestBody String json) {
        E entity = repository.insert(jsonToMap(json));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(getClass()).findById(entity.id())).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = GET)
    @ResponseBody
    public HttpEntity<R> findById(@PathVariable("id") String id) {
        E entity = repository.findById(id);
        R resource = assembler.toResource(entity);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/browse", method = GET)
    @ResponseBody
    public HttpEntity<CollectionResource<R>> browse(@RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize,
                                                    @RequestParam(value = "pageNumber", required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageNumber) {

        List<E> entities = repository.browse(pageSize, pageNumber);

        long count = repository.count();
        int lastPage = (int) Math.ceil((double)count / pageSize);

        CollectionResource<R> resources = new CollectionResource<>();
        resources.setItems(assembler.toResources(entities));

        resources.add(linkTo(methodOn(getClass()).browse(pageSize, pageNumber)).withSelfRel());
        resources.add(linkTo(methodOn(getClass()).browse(pageSize, 1)).withRel("firstPage"));
        resources.add(linkTo(methodOn(getClass()).browse(pageSize, lastPage)).withRel("lastPage"));

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
