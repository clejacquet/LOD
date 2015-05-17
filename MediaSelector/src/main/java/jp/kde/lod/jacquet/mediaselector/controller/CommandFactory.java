package jp.kde.lod.jacquet.mediaselector.controller;

/**
 * Created by Clement on 16/05/2015.
 */
public class CommandFactory {
    private CommandFactory() {

    }

    public static <T extends HTMLCommand> T buildHTMLCommand(Class<T> commandClass) {
        return WebContext.getInjector().getInstance(commandClass);
    }

    public static <T extends JSONCommand> T buildJSONCommand(Class<T> commandClass) {
        return WebContext.getInjector().getInstance(commandClass);
    }
}
