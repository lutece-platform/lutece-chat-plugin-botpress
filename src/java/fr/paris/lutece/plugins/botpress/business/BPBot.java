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
package fr.paris.lutece.plugins.botpress.business;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.constraints.Size;

/**
 * This is the business class for the object BPBot
 */
public class BPBot implements Serializable
{
    private static final String DEFAULT_AVATAR_URL = "images/skin/plugins/botpress/default_avatar.png";
    private static final long serialVersionUID = 1L;

    // Variables declarations
    private int _nId;
    @NotEmpty( message = "#i18n{botpress.validation.bpbot.BotKey.notEmpty}" )
    @Size( max = 50, message = "#i18n{botpress.validation.bpbot.BotKey.size}" )
    private String _strBotKey;
    @NotEmpty( message = "#i18n{botpress.validation.bpbot.BotPressKey.notEmpty}" )
    @Size( max = 50, message = "#i18n{botpress.validation.bpbot.BotPressKey.size}" )
    private String _strBotPressKey;
    @NotEmpty( message = "#i18n{botpress.validation.bpbot.Name.notEmpty}" )
    @Size( max = 50, message = "#i18n{botpress.validation.bpbot.Name.size}" )
    private String _strName;
    @NotEmpty( message = "#i18n{botpress.validation.bpbot.Description.notEmpty}" )
    @Size( max = 255, message = "#i18n{botpress.validation.bpbot.Description.size}" )
    private String _strDescription;
    @NotEmpty( message = "#i18n{botpress.validation.bpbot.AvatarUrl.notEmpty}" )
    @Size( max = 255, message = "#i18n{botpress.validation.bpbot.AvatarUrl.size}" )
    private String _strAvatarUrl = DEFAULT_AVATAR_URL;
    @NotEmpty( message = "#i18n{botpress.validation.bpbot.Language.notEmpty}" )
    @Size( max = 50, message = "#i18n{botpress.validation.bpbot.Language.size}" )
    private String _strLanguage;
    private int _nBotStatus;
    private int _nIsStandalone;
    @Size( max = 255, message = "#i18n{botpress.validation.bpbot.WelcomeMessage.size}" )
    private String _strWelcomeMessage;
    @Size( max = 255, message = "#i18n{botpress.validation.bpbot.ErrorMessage.size}" )
    private String _strErrorMessage;
    @NotEmpty( message = "#i18n{botpress.validation.bpbot.ServerUrl.notEmpty}" )
    @Size( max = 255, message = "#i18n{botpress.validation.bpbot.ServerUrl.size}" )
    private String _strServerUrl;
    private int _nApiVersion;
    private String _strAvatarRendererKey;

    /**
     * Returns the Id
     *
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     *
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the BotKey
     *
     * @return The BotKey
     */
    public String getBotKey( )
    {
        return _strBotKey;
    }

    /**
     * Sets the BotKey
     *
     * @param strBotKey
     *            The BotKey
     */
    public void setBotKey( String strBotKey )
    {
        _strBotKey = strBotKey;
    }

    /**
     * Returns the BotPressKey
     *
     * @return The BotPressKey
     */
    public String getBotPressKey( )
    {
        return _strBotPressKey;
    }

    /**
     * Sets the BotPressKey
     *
     * @param strBotPressKey
     *            The BotPressKey
     */
    public void setBotPressKey( String strBotPressKey )
    {
        _strBotPressKey = strBotPressKey;
    }

    /**
     * Returns the Name
     *
     * @return The Name
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Sets the Name
     *
     * @param strName
     *            The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the Description
     *
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     *
     * @param strDescription
     *            The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the AvatarUrl
     *
     * @return The AvatarUrl
     */
    public String getAvatarUrl( )
    {
        return _strAvatarUrl;
    }

    /**
     * Sets the AvatarUrl
     *
     * @param strAvatarUrl
     *            The AvatarUrl
     */
    public void setAvatarUrl( String strAvatarUrl )
    {
        _strAvatarUrl = strAvatarUrl;
    }

    /**
     * Returns the Language
     *
     * @return The Language
     */
    public String getLanguage( )
    {
        return _strLanguage;
    }

    /**
     * Sets the Language
     *
     * @param strLanguage
     *            The Language
     */
    public void setLanguage( String strLanguage )
    {
        _strLanguage = strLanguage;
    }

    /**
     * Returns the BotStatus
     *
     * @return The BotStatus
     */
    public int getBotStatus( )
    {
        return _nBotStatus;
    }

    /**
     * Sets the BotStatus
     *
     * @param nBotStatus
     *            The BotStatus
     */
    public void setBotStatus( int nBotStatus )
    {
        _nBotStatus = nBotStatus;
    }

    /**
     * Returns the IsStandalone
     *
     * @return The IsStandalone
     */
    public int getIsStandalone( )
    {
        return _nIsStandalone;
    }

    /**
     * Sets the IsStandalone
     *
     * @param nIsStandalone
     *            The IsStandalone
     */
    public void setIsStandalone( int nIsStandalone )
    {
        _nIsStandalone = nIsStandalone;
    }

    /**
     * Returns the WelcomeMessage
     *
     * @return The WelcomeMessage
     */
    public String getWelcomeMessage( )
    {
        return _strWelcomeMessage;
    }

    /**
     * Sets the WelcomeMessage
     *
     * @param strWelcomeMessage
     *            The WelcomeMessage
     */
    public void setWelcomeMessage( String strWelcomeMessage )
    {
        _strWelcomeMessage = strWelcomeMessage;
    }

    /**
     * Returns the ErrorMessage
     *
     * @return The ErrorMessage
     */
    public String getErrorMessage( )
    {
        return _strErrorMessage;
    }

    /**
     * Sets the ErrorMessage
     *
     * @param strErrorMessage
     *            The ErrorMessage
     */
    public void setErrorMessage( String strErrorMessage )
    {
        _strErrorMessage = strErrorMessage;
    }

    /**
     * Returns the ServerUrl
     *
     * @return The ServerUrl
     */
    public String getServerUrl( )
    {
        return _strServerUrl;
    }

    /**
     * Sets the ServerUrl
     *
     * @param strServerUrl
     *            The ServerUrl
     */
    public void setServerUrl( String strServerUrl )
    {
        _strServerUrl = strServerUrl;
    }

    /**
     * Returns the ApiVersion
     *
     * @return The ApiVersion
     */
    public int getApiVersion( )
    {
        return _nApiVersion;
    }

    /**
     * Sets the ApiVersion
     *
     * @param nApiVersion
     *            The ApiVersion
     */
    public void setApiVersion( int nApiVersion )
    {
        _nApiVersion = nApiVersion;
    }

    /**
     * Returns the AvatarRendererKey
     *
     * @return The AvatarRendererKey
     */
    public String getAvatarRendererKey( )
    {
        return _strAvatarRendererKey;
    }

    /**
     * Sets the AvatarRendererKey
     *
     * @param strAvatarRendererKey
     *            The AvatarRendererKey
     */
    public void setAvatarRendererKey( String strAvatarRendererKey )
    {
        _strAvatarRendererKey = strAvatarRendererKey;
    }
}
