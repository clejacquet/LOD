package jp.kde.lod.jacquet.mediaselector.controller.servlethandler;

import jp.kde.lod.jacquet.mediaselector.model.domain.User;
import jp.kde.lod.jacquet.mediaselector.controller.HTMLCommand;
import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.mediaselector.view.ViewFactory;
import jp.kde.lod.jacquet.pageprocessing.View;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Clement on 15/05/2015.
 */
public class DefaultServletHandler extends BaseServletHandler {
    private static final String CONNECTION_REDIRECT = "/user/connect";

    private User getAuthenticatedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute(WebContext.getSessionUserKey());
        } else {
            return null;
        }
    }

    @Override
    public View processHTML(HTMLCommand command) {
        if (command.isAuthenticationNeeded()) {
            HttpSession session = super.getRequest().getSession(false);
            if (session == null) {
                try {
                    super.getResponse().sendRedirect(CONNECTION_REDIRECT + "?redirection=" + URLEncoder.encode(command.getRedirectionURL(), "UTF-8"));
                    return ViewFactory.buildEmptyView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        command.setHandler(this);
        View view = command.process();

        if (command.isSessionVisible()) {
            view.addParameter(WebContext.getSessionUserKey(), this.getAuthenticatedUser(super.getRequest()));
        }

        return view;
    }

    @Override
    public JSONObject processJSON(JSONCommand command) {
        command.setHandler(this);
        return command.process();
    }
}
