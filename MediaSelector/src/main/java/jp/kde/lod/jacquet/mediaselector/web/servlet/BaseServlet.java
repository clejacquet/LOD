package jp.kde.lod.jacquet.mediaselector.web.servlet;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jp.kde.lod.jacquet.mediaselector.Context;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Clement on 05/05/2015.
 */
public abstract class BaseServlet extends HttpServlet {

    private static final String LAYOUT_TEMPLATE = "templates/layout.ftl";
    private static final String TEMPLATE_ENCODING = "UTF-8";
    private static final String HOME_URI = Context.makeCompleteIRI("/home");
    private static final Logger LOGGER = Logger.getLogger(BaseServlet.class);

    private String title;
    private String content;
    private List<String> cssLinks;
    private Map<String, String> headerItems;
    private Map<String, Object> mapping;

    public BaseServlet() {
        this.cssLinks = new ArrayList<String>();
        this.headerItems = new HashMap<String, String>();
        this.mapping = new HashMap<String, Object>();
    }

    public BaseServlet(String title, String content) {
        this();
        this.title = title;
        this.content = content;

        this.cssLinks.add("/css/bootstrap.min.css");
        this.cssLinks.add("/css/navbar-fixed-top.css");
        this.cssLinks.add("/css/layout.css");

        this.addHeaderItem(Context.makeCompleteIRI("/home"), "Home");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration freemarkerConfiguration = new Configuration();
        freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
        freemarkerConfiguration.setObjectWrapper(new DefaultObjectWrapper());

        Template freemarkerTemplate = null;
        try {
            freemarkerTemplate = freemarkerConfiguration.getTemplate(LAYOUT_TEMPLATE, TEMPLATE_ENCODING);
            PrintWriter out = resp.getWriter();
            this.initMapping(req, resp, this.mapping);
            this.bind();
            freemarkerTemplate.process(this.mapping, out);
            out.close();
            this.mapping.clear();
        } catch (IOException e) {
            LOGGER.error("Unable to process request, error during freemarker template retrieval.", e);
        } catch (TemplateException e) {
            LOGGER.error("Error during template processing", e);
        }

        resp.setContentType("text/html");
    }

    private void bind() {
        this.mapping.put("title", this.title);
        this.mapping.put("home", HOME_URI);
        this.mapping.put("content", this.content);
        this.mapping.put("css_links", this.cssLinks);
        this.mapping.put("header_items", this.headerItems);
        this.mapping.put("artifact_name", Context.ARTIFACT_NAME);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addCssLink(String cssLink) {
        this.cssLinks.add(cssLink);
    }

    public void addHeaderItem(String uri, String title) {
        this.headerItems.put(uri, title);
    }

    protected void initMapping(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> mapping) {

    }
}
