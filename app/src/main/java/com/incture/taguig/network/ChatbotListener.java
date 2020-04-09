package com.incture.taguig.network;

/**
 * Created by Nuzha Rukiya on 18/09/27.
 */
public interface ChatbotListener {

    int BOT_CONTEXT = -1;

    /**
     * Shows the floating action button
     * Used when the chat is dismissed
     */
    void showFab();

    /**
     * This function should perform the required action,
     * Depending on the response received
     *
     * @param response from RECAST
     * @param comment the comment string that the user has asked to be added (default : empty string)
     */
    void performAction(String response, String comment);

    /**
     * Used to identify which activity the bot is called from
     *
     * @return integer indicating the calling activity
     */
    int getBotContext();
}
