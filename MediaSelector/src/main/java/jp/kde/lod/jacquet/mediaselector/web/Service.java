package jp.kde.lod.jacquet.mediaselector.web;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jp.kde.lod.jacquet.mediaselector.Context;
import jp.kde.lod.jacquet.mediaselector.persistence.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Clement on 08/05/2015.
 */
public abstract class Service {
    private static final String LAYOUT_TEMPLATE = "templates/layout.ftl";
    private static final String TEMPLATE_ENCODING = "UTF-8";
    private static final String HOME_URI = "/home";

    private Template freemarkerTemplate;

    public Service() {
        Configuration freemarkerConfiguration = new Configuration();
        freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
        freemarkerConfiguration.setObjectWrapper(new DefaultObjectWrapper());

        try {
            this.freemarkerTemplate = freemarkerConfiguration.getTemplate(LAYOUT_TEMPLATE, TEMPLATE_ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> bind(Map<String, Object> mapping, ViewLayout viewLayout, Map<String, Object> parameters) {
        if (parameters != null) {
            mapping.putAll(parameters);
        }

        mapping.put("title", viewLayout.getTitle());
        mapping.put("home", HOME_URI);
        mapping.put("css_links", viewLayout.getCssLinks());
        mapping.put("header_items", viewLayout.getHeaderItems());
        mapping.put("artifact_name", Context.ARTIFACT_NAME);

        return mapping;
    }

    protected String getView(String viewFile, ViewLayout viewLayout, Map<String, Object> parameters) {
        Map<String, Object> mapping = new HashMap<String, Object>();
        this.bind(mapping, viewLayout, parameters);
        mapping.put("content", viewFile);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintWriter out = new PrintWriter(outputStream);
            this.freemarkerTemplate.process(mapping, out);
            out.close();
            return outputStream.toString();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String buildDefaultBootstrapView(String title, String contentPath) {
        return this.buildDefaultBootstrapView(title, contentPath,(Map<String, Object>) null);
    }

    protected String buildDefaultBootstrapView(String title, String contentPath, Map<String, Object> parameters) {
        ViewLayout layout = ViewLayout.buildBootstrapViewLayout(title);
        String view = this.getView(contentPath, layout, parameters);
        return ((view != null) ? view : "Error!");
    }

    protected String buildDefaultBootstrapView(String title, String contentPath, User user) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("user", user);
        ViewLayout layout = ViewLayout.buildBootstrapViewLayout(title);
        String view = this.getView(contentPath, layout, parameters);
        return ((view != null) ? view : "Error!");
    }

    protected User getAuthenticatedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute("user");
        } else {
            return null;
        }
    }
}
