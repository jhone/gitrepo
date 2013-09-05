package com.redsun.platf.testunit;

import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.util.sideutil.PagedResult;
import com.thoughtworks.xstream.XStream;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.StringWriter;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//        "classpath*:test-contacts-servlet.xml"
//})

@Transactional
public abstract class AbstractControllerTest<T extends BaseEntity> extends AbstractTransactionalJUnit4SpringContextTests
 /*AbstractJUnit4SpringContextTests SpringTxTestCase*/ {
   public final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 測試數據源
     */
    @Resource
    DataSource dataSource;

    final String ext = "";//EPApplicationAttributes.EXT_OF_CONTROL;
    protected static MockHttpServletRequest request;
    protected static MockHttpServletResponse response;
    protected HandlerAdapter handlerAdapter;


    private ModelAndView modelAndView;


    protected RequestBuilder requestBuilder;

 /*// Security context
    protected SecurityContext context;
    *//**
     * test user info
     *//*
    //test account
    static final String TEST_USERNAME = "admin";
    static final String TEST_PASSWORD = "1234";*/

    @Override
//    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    @Before
    public void setUp() {
      initRequest();

    }

    public void initRequest() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        handlerAdapter =
                applicationContext.getBean(AnnotationMethodHandlerAdapter.class);

        requestBuilder = new RequestBuilder(request, response);


        Assert.assertNotNull(handlerAdapter);

    }



    /**
     * 將用戶數據通過request.param binder 到entity
     */
    protected void bindDataFromRequestParam(T entity) {

        WebDataBinder binder = new WebDataBinder(entity);
        binder.bind(new MutablePropertyValues(request.getParameterMap()));
    }

    /**
     * 返回空的PagedResult
     *
     * @return PagedResult
     */
    protected PagedResult<T> makeTempPagedResult() {
        PagedResult<T> pagedResult = new PagedResult<T>();

        pagedResult.setOrder("asc");
        pagedResult.setPage(1);
        pagedResult.setRows(35);
        pagedResult.setOrderBy("id");
        pagedResult.setFilters("{}");
        return pagedResult;
    }

    /**
     * RequestBuilder
     *
     * @author dick pan
     */
    public final class RequestBuilder {

        private MockHttpServletRequest request;
        private MockHttpServletResponse response;

        public RequestBuilder(MockHttpServletRequest request, MockHttpServletResponse response) {
            this.request = request;
            this.response = response;
            request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
//            request.setAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE,"list*" );
            System.out.println(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING);
            // set GET as default method
            withMethod("GET");
        }

        public RequestBuilder toUrl(String url) {
            request.setRequestURI(url);
            return this;
        }

        public RequestBuilder withMethod(String method) {
            request.setMethod(method);
            return this;
        }

        public RequestBuilder withParameter(String name, String value) {
            request.addParameter(name, value);
            return this;
        }

        public void jsonPostInit() {
            addJsonHeader();
            request.setMethod(RequestMethod.POST.toString());
        }

        public void jsonGetInit() {
            addJsonHeader();
            request.setMethod(RequestMethod.GET.toString());
        }

        protected void jsonPutInit() {
            addJsonHeader();
            request.setMethod(RequestMethod.PUT.toString());
        }

        protected String toJson(Object o) throws Exception {
            StringWriter sw = new StringWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(sw, o);
            return sw.toString();
        }

        protected String toXml(Object o) throws Exception {
            XStream xstream = new XStream();
            xstream.autodetectAnnotations(true);
            return xstream.toXML(o);
        }

        private void addJsonHeader() {

            request.addHeader("Accept", "application/json, text/javascript, */*");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            response.addHeader("Accept", "application/json, text/javascript, */*");
            response.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
        }

        private void addXmlHeader() {

            request.addHeader("Accept", "application/xml, text/xml, */*");
            request.addHeader("Content-Type", "application/xml; charset=UTF-8");
            response.addHeader("Accept", "application/json, text/javascript, */*");
            response.addHeader("Content-Type", "application/json; charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
        }


        protected void xmlGetInit() {
            addXmlHeader();
            request.setMethod(RequestMethod.GET.toString());
        }

        protected void xmlPostInit() {
            addXmlHeader();
            request.setMethod(RequestMethod.POST.toString());
        }

        protected void xmlPutInit() {
            addXmlHeader();
            request.setMethod(RequestMethod.PUT.toString());
        }

//        private void init() {
//            request = new MockHttpServletRequest();
//            request.setCharacterEncoding("UTF-8");
//            response = new MockHttpServletResponse();
//        }


    }


}
