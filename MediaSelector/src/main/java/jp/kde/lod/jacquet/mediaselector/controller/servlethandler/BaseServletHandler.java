package jp.kde.lod.jacquet.mediaselector.controller.servlethandler;

import com.google.inject.Inject;
import jp.kde.lod.jacquet.mediaselector.controller.ServletHandler;
import jp.kde.lod.jacquet.mediaselector.model.DaoProvider;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Clement on 15/05/2015.
 */
public abstract class BaseServletHandler implements ServletHandler {
    private ServletContext context;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Inject
    private DaoProvider daoProvider;

    public BaseServletHandler() {

    }

    public BaseServletHandler(ServletContext context,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
    }

    @Override
    public void setContext(ServletContext context) {
        this.context = context;
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public ServletContext getContext() {
        return this.context;
    }

    @Override
    public HttpServletRequest getRequest() {
        return this.request;
    }

    @Override
    public HttpServletResponse getResponse() {
        return this.response;
    }

    @Override
    public String getAbsolutePath(String relativePath) {
        return this.context.getRealPath(relativePath);
    }

    @Override
    public DaoProvider getDaoProvider() {
        return this.daoProvider;
    }

    public void initDaoProvider() {
        this.daoProvider.getUserDao().setHandler(this);
        this.daoProvider.getMediaDao().setHandler(this);
    }
}
