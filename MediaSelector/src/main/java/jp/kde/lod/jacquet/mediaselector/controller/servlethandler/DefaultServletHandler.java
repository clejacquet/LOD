package jp.kde.lod.jacquet.mediaselector.controller.servlethandler;

import jp.kde.lod.jacquet.mediaselector.model.entity.User;
import jp.kde.lod.jacquet.mediaselector.controller.HTMLCommand;
import jp.kde.lod.jacquet.mediaselector.controller.JSONCommand;
import jp.kde.lod.jacquet.mediaselector.controller.WebContext;
import jp.kde.lod.jacquet.pageprocessing.View;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Clement on 15/05/2015.
 */
public class DefaultServletHandler extends BaseServletHandler {
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
        command.setHandler(this);
        View view = command.process();

        if (command.isAuthenticatedNeeded()) {
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
