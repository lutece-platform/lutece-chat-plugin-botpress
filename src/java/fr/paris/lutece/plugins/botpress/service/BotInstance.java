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

import fr.paris.lutece.plugins.botpress.business.BPBot;
import fr.paris.lutece.plugins.chatbot.business.BotPost;
import fr.paris.lutece.plugins.chatbot.service.bot.AbstractChatBot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * BotInstance
 */
public final class BotInstance extends AbstractChatBot
{
    // Variables declarations

    private String _strBotApiEntryPointUrl;
    private String _strErrorMessage;

    /**
     * Constructor
     * 
     * @param bot
     *            The BotPress bot
     */
    public BotInstance( BPBot bot )
    {
        super( );

        setKey( bot.getBotKey( ) );
        setName( bot.getName( ) );
        setDescription( bot.getDescription( ) );
        List listLanguages = new ArrayList<>( );
        listLanguages.add( bot.getLanguage( ) );
        setListAvailableLanguages( listLanguages );
        setAvatarUrl( bot.getAvatarUrl( ) );
        setStandalone( bot.getIsStandalone( ) != 0 );
        setWelcomeMessage( bot.getWelcomeMessage( ) );
        setAvatarRendererKey( bot.getAvatarRendererKey( ) );
        _strBotApiEntryPointUrl = ConverseService.getBotApiEntryPointUrl( bot.getBotPressKey( ), bot.getServerUrl( ), bot.getApiVersion( ) );
        _strErrorMessage = bot.getErrorMessage( );

    }

    /**
     * Returns the BotApiEntryPointUrl
     *
     * @return The BotApiEntryPointUrl
     */
    public String getBotApiEntryPointUrl( )
    {
        return _strBotApiEntryPointUrl;
    }

    /**
     * Sets the BotApiEntryPointUrl
     *
     * @param strBotApiEntryPointUrl
     *            The BotApiEntryPointUrl
     */
    public void setBotApiEntryPointUrl( String strBotApiEntryPointUrl )
    {
        _strBotApiEntryPointUrl = strBotApiEntryPointUrl;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<BotPost> processUserMessage( String strMessage, String strConversationId, Locale locale )
    {
        return ConverseService.getBotResponse( strMessage, strConversationId, _strBotApiEntryPointUrl, _strErrorMessage, locale );
    }

    /**
     * Reset the conversation
     *
     * @param strConversationId
     *            The conversation ID
     */
    @Override
    public void reset( String strConversationId )
    {

    }

}
