package rest;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rest.product.ProductController;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class RootController {

    @RequestMapping(value = "/api", method = GET)
    @ResponseBody
    public HttpEntity<RootApi> getApiDetails() throws IOException {
        RootApi rootApi = new RootApi("1.0");

        rootApi.add(linkTo(methodOn(ProductController.class).getProductApi()).withRel("product"));

        return new ResponseEntity<>(rootApi, HttpStatus.OK);
    }
}
