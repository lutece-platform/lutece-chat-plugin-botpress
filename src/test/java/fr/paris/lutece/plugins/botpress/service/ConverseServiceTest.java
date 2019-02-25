/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.botpress.service;

import fr.paris.lutece.plugins.chatbot.business.BotPost;
import fr.paris.lutece.test.LuteceTestCase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.junit.Test;

/**
 *
 * @author levy
 */
public class ConverseServiceTest extends LuteceTestCase
{

    private static final String JSON_RESPONSE = "{\n" + "  \"responses\": [\n" + "    {\n" + "      \"type\": \"typing\",\n" + "      \"value\": true\n"
            + "    },\n" + "    {\n" + "      \"type\": \"text\",\n" + "      \"markdown\": true,\n" + "      \"text\": \"May I know your name please?\"\n"
            + "    }\n" + "  ],\n" + "  \"nlu\": {\n" + "    \"language\": \"en\",\n" + "    \"entities\": [],\n" + "    \"intent\": {\n"
            + "      \"name\": \"hello\",\n" + "      \"confidence\": 1\n" + "    },\n" + "    \"intents\": [\n" + "      {\n"
            + "        \"name\": \"hello\",\n" + "        \"confidence\": 1\n" + "      }\n" + "    ]\n" + "  },\n" + "  \"state\": {}\n" + "}";

    /**
     * Test of getBotResponse method, of class ConverseService.
     */
    @Test
    public void testGetBotResponse( )
    {
        System.out.println( "getBotResponse" );
        String strMessage = "Hello";
        String strConversationId = "4654";
        String strBotApiEntryPoint = "https://www.palyni.tk/api/v1/bots/gtmutu/converse/";
        Locale locale = Locale.getDefault( );
        List<BotPost> listPosts = ConverseService.getBotResponse( strMessage, strConversationId, strBotApiEntryPoint, locale );
        for ( BotPost post : listPosts )
        {
            System.err.println( post.getContent( ) );
        }
    }

    @Test
    public void testParseJsonResponse( ) throws IOException
    {
        System.out.println( "parseJsonResponse" );
        List<BotPost> listPosts = new ArrayList<>( );
        ConverseService.parseJsonResponse( JSON_RESPONSE, listPosts );
        for ( BotPost post : listPosts )
        {
            System.err.println( post.getContent( ) );
        }

    }

}
