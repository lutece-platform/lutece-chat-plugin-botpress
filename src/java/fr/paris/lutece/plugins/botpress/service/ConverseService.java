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

import fr.paris.lutece.plugins.botpress.service.renderers.BotMessageRenderer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paris.lutece.plugins.botpress.business.RequestMessage;
import fr.paris.lutece.plugins.chatbot.business.BotPost;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * ConverseService
 */
public final class ConverseService
{

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String NODE_RESPONSES = "responses";
    private static final int VERSION_1 = 1;

    private static final String LOGGER_NAME = "botpress";
    private static final Logger LOGGER = Logger.getLogger( LOGGER_NAME );
    private static ObjectMapper _objectMapper = new ObjectMapper( );

    /** Private constructor */
    private ConverseService( )
    {
    }

    /**
     * Get responses from the bot
     * 
     * @param strMessage
     *            The message from the User
     * @param strConversationId
     *            The conversation ID (or UserID)
     * @param strBotApiEntryPointUrl
     *            The URL of the Bot API
     * @param strErrorMessage
     *            Error message sent by the bot if the connection fails
     * @param locale
     *            The locale The locale
     * @return A list of bot responses
     */
    public static List<BotPost> getBotResponse( String strMessage, String strConversationId, String strBotApiEntryPointUrl, String strErrorMessage,
            Locale locale )
    {
        List<BotPost> listPosts = new ArrayList<>( );
        RequestMessage message = new RequestMessage( strMessage );
        String strUrl = null;
        String strJsonResponsePretty = null;
        try
        {
            String strJsonMessage = _objectMapper.writeValueAsString( message );
            HttpAccess client = new HttpAccess( );
            HashMap<String, String> mapRequestHeaders = new HashMap<>( );
            mapRequestHeaders.put( CONTENT_TYPE, CONTENT_TYPE_JSON );
            HashMap<String, String> mapResponseHeaders = new HashMap<>( );
            strUrl = strBotApiEntryPointUrl + strConversationId;
            String strJsonResponse = client.doPostJSON( strUrl, strJsonMessage, mapRequestHeaders, mapResponseHeaders );
            Object jsonResponse = _objectMapper.readTree( strJsonResponse );
            strJsonResponsePretty = _objectMapper.writerWithDefaultPrettyPrinter( ).writeValueAsString( jsonResponse );
            if ( LOGGER.isDebugEnabled( ) )
            {
                LOGGER.debug( "Message : " + strMessage + "\nResponse : \n" + strJsonResponsePretty );
            }
            parseJsonResponse( strJsonResponse, listPosts );
            return listPosts;
        }
        catch( HttpAccessException | IOException ex )
        {
            StringBuilder sbError = new StringBuilder( );
            sbError.append( "Error getting response from Botpress API : " ).append( ex.getMessage( ) );
            if ( strUrl != null )
            {
                sbError.append( "\n - POST URL : " ).append( strUrl );
            }
            if ( strJsonResponsePretty != null )
            {
                sbError.append( "\n - JSON response : " ).append( strJsonResponsePretty );
            }
            AppLogService.error( sbError.toString( ), ex );

            BotPost post = new BotPost( strErrorMessage );
            listPosts.add( post );

            return listPosts;
        }

    }

    /**
     * Parse the JSON response from the bot
     * 
     * @param strJsonResponse
     *            The JSON response
     * @param listPosts
     *            The list of bot posts
     * @throws IOException
     *             if an error occurs
     */
    static void parseJsonResponse( String strJsonResponse, List<BotPost> listPosts ) throws IOException
    {
        JsonNode rootNode = _objectMapper.readTree( strJsonResponse );
        JsonNode responsesNode = rootNode.path( NODE_RESPONSES );
        Iterator<JsonNode> elements = responsesNode.elements( );
        while ( elements.hasNext( ) )
        {
            JsonNode response = elements.next( );
            BotMessageRenderer renderer = RendererService.getRenderer( response );
            if ( renderer != null )
            {
                BotPost post = new BotPost( renderer.render( _objectMapper.convertValue( response, Map.class ) ), renderer.getPostContentType( ) );
                listPosts.add( post );
            }
        }

    }

    /**
     * Returns the list of managed API versions
     * 
     * @return A list of versions
     */
    public static ReferenceList getApiVersions( )
    {
        ReferenceList list = new ReferenceList( );
        list.addItem( VERSION_1, "BotPress Converse API Version 1" );
        return list;

    }

    /**
     * Build the entry point URL
     * 
     * @param strBotKey
     *            The bot key
     * @param strServerUrl
     *            The server URL
     * @param nApiVersion
     *            The API version number
     * @return The entry point URL
     */
    public static String getBotApiEntryPointUrl( String strBotKey, String strServerUrl, int nApiVersion )
    {
        StringBuilder sbEntryPointUrl = new StringBuilder( );

        switch( nApiVersion )
        {
            case VERSION_1:
                sbEntryPointUrl.append( ( strServerUrl.endsWith( "/" ) ) ? strServerUrl : strServerUrl + "/" ).append( "api/v1/bots/" ).append( strBotKey )
                        .append( "/converse/" );
                break;

            default:
                AppLogService.error( "Invalid Bot Press API version number : " + nApiVersion );
                break;

        }
        return sbEntryPointUrl.toString( );

    }

}
