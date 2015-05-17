package jp.kde.lod.jacquet.pageprocessing;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Clement on 15/05/2015.
 */
public class FreemarkerLayoutWebView extends LayoutWebView {
    private static final String FREEMARKER_TEMPLATE_ENCODING = "UTF-8";
    private static Configuration FREEMARKER_CONFIGURATION = new Configuration();

    static {
        FREEMARKER_CONFIGURATION.setClassForTemplateLoading(FreemarkerLayoutWebView.class, "/");
        FREEMARKER_CONFIGURATION.setObjectWrapper(new DefaultObjectWrapper());
    }

    private Template freemarkerTemplate;

    @Override
    public void setLayout(Layout layout) {
        super.setLayout(layout);
        try {
            this.freemarkerTemplate = FREEMARKER_CONFIGURATION.getTemplate(layout.getLayoutTemplate(), FREEMARKER_TEMPLATE_ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generate() {
        Layout layout = super.getLayout();
        Map<String, Object> mapping = layout.getMapping(super.getContentKey(), super.getContentPath());
        mapping.put(super.getCssKey(), super.getCssLinks());
        mapping.put(super.getJsKey(), super.getJsLinks());
        mapping.putAll(super.getParameters());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PrintWriter out = new PrintWriter(outputStream);
            this.freemarkerTemplate.process(mapping, out);
            out.close();
            return outputStream.toString();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
