/*
 * Copyright (c) 2002-2019, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
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
 * ConverseServiceTest
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
        String strErrorMessage = "Server not available";
        List<BotPost> listPosts = ConverseService.getBotResponse( strMessage, strConversationId, strBotApiEntryPoint, strErrorMessage, locale );
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
