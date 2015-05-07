package jp.kde.lod.jacquet.mediaselector.web.servlet;

import com.google.inject.Singleton;
import jp.kde.lod.jacquet.mediaselector.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Clement on 28/04/2015.
 */
@Singleton
public class DefaultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/" + Context.ARTIFACT_NAME + "/error?src=" + req.getRequestURI());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/" + Context.ARTIFACT_NAME + "/error?src=" + req.getRequestURI());
    }
}
