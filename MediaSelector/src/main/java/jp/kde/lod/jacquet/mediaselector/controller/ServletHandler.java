package jp.kde.lod.jacquet.mediaselector.controller;

import jp.kde.lod.jacquet.mediaselector.model.DaoProvider;
import jp.kde.lod.jacquet.mediaselector.model.QueryStorage;
import jp.kde.lod.jacquet.mediaselector.model.UpdateStorage;
import jp.kde.lod.jacquet.pageprocessing.View;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Clement on 15/05/2015.
 */
public interface ServletHandler {
    ServletContext getContext();
    HttpServletRequest getRequest();
    HttpServletResponse getResponse();

    void setContext(ServletContext context);
    void setRequest(HttpServletRequest request);
    void setResponse(HttpServletResponse response);

    View processHTML(HTMLCommand command);
    JSONObject processJSON(JSONCommand command);

    String getAbsolutePath(String relativePath);

    DaoProvider getDaoProvider();
    QueryStorage getQueryStorage();
    UpdateStorage getUpdateStorage();
}
