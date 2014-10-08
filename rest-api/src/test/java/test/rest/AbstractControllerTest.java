package test.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import rest.Application;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration(classes = {Application.class, TestConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mvc;

    @Before
    public void setup() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }
}
