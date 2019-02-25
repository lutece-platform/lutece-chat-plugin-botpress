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

import fr.paris.lutece.test.LuteceTestCase;

/**
 * This is the business class test for the object BPBot
 */
public class BPBotBusinessTest extends LuteceTestCase
{
    private static final String BOTKEY1 = "BotKey1";
    private static final String BOTKEY2 = "BotKey2";
    private static final String NAME1 = "Name1";
    private static final String NAME2 = "Name2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final String AVATARURL1 = "AvatarUrl1";
    private static final String AVATARURL2 = "AvatarUrl2";
    private static final String LANGUAGE1 = "Language1";
    private static final String LANGUAGE2 = "Language2";
    private static final int BOTSTATUS1 = 1;
    private static final int BOTSTATUS2 = 2;
    private static final int ISSTANDALONE1 = 1;
    private static final int ISSTANDALONE2 = 2;
    private static final String WELCOMEMESSAGE1 = "WelcomeMessage1";
    private static final String WELCOMEMESSAGE2 = "WelcomeMessage2";
    private static final String SERVERURL1 = "ServerUrl1";
    private static final String SERVERURL2 = "ServerUrl2";
    private static final int APIVERSION1 = 1;
    private static final int APIVERSION2 = 2;

    /**
     * test BPBot
     */
    public void testBusiness( )
    {
        // Initialize an object
        BPBot bPBot = new BPBot( );
        bPBot.setBotKey( BOTKEY1 );
        bPBot.setName( NAME1 );
        bPBot.setDescription( DESCRIPTION1 );
        bPBot.setAvatarUrl( AVATARURL1 );
        bPBot.setLanguage( LANGUAGE1 );
        bPBot.setBotStatus( BOTSTATUS1 );
        bPBot.setIsStandalone( ISSTANDALONE1 );
        bPBot.setWelcomeMessage( WELCOMEMESSAGE1 );
        bPBot.setServerUrl( SERVERURL1 );
        bPBot.setApiVersion( APIVERSION1 );

        // Create test
        BPBotHome.create( bPBot );
        BPBot bPBotStored = BPBotHome.findByPrimaryKey( bPBot.getId( ) );
        assertEquals( bPBotStored.getBotKey( ), bPBot.getBotKey( ) );
        assertEquals( bPBotStored.getName( ), bPBot.getName( ) );
        assertEquals( bPBotStored.getDescription( ), bPBot.getDescription( ) );
        assertEquals( bPBotStored.getAvatarUrl( ), bPBot.getAvatarUrl( ) );
        assertEquals( bPBotStored.getLanguage( ), bPBot.getLanguage( ) );
        assertEquals( bPBotStored.getBotStatus( ), bPBot.getBotStatus( ) );
        assertEquals( bPBotStored.getIsStandalone( ), bPBot.getIsStandalone( ) );
        assertEquals( bPBotStored.getWelcomeMessage( ), bPBot.getWelcomeMessage( ) );
        assertEquals( bPBotStored.getServerUrl( ), bPBot.getServerUrl( ) );
        assertEquals( bPBotStored.getApiVersion( ), bPBot.getApiVersion( ) );

        // Update test
        bPBot.setBotKey( BOTKEY2 );
        bPBot.setName( NAME2 );
        bPBot.setDescription( DESCRIPTION2 );
        bPBot.setAvatarUrl( AVATARURL2 );
        bPBot.setLanguage( LANGUAGE2 );
        bPBot.setBotStatus( BOTSTATUS2 );
        bPBot.setIsStandalone( ISSTANDALONE2 );
        bPBot.setWelcomeMessage( WELCOMEMESSAGE2 );
        bPBot.setServerUrl( SERVERURL2 );
        bPBot.setApiVersion( APIVERSION2 );
        BPBotHome.update( bPBot );
        bPBotStored = BPBotHome.findByPrimaryKey( bPBot.getId( ) );
        assertEquals( bPBotStored.getBotKey( ), bPBot.getBotKey( ) );
        assertEquals( bPBotStored.getName( ), bPBot.getName( ) );
        assertEquals( bPBotStored.getDescription( ), bPBot.getDescription( ) );
        assertEquals( bPBotStored.getAvatarUrl( ), bPBot.getAvatarUrl( ) );
        assertEquals( bPBotStored.getLanguage( ), bPBot.getLanguage( ) );
        assertEquals( bPBotStored.getBotStatus( ), bPBot.getBotStatus( ) );
        assertEquals( bPBotStored.getIsStandalone( ), bPBot.getIsStandalone( ) );
        assertEquals( bPBotStored.getWelcomeMessage( ), bPBot.getWelcomeMessage( ) );
        assertEquals( bPBotStored.getServerUrl( ), bPBot.getServerUrl( ) );
        assertEquals( bPBotStored.getApiVersion( ), bPBot.getApiVersion( ) );

        // List test
        BPBotHome.getBPBotsList( );

        // Delete test
        BPBotHome.remove( bPBot.getId( ) );
        bPBotStored = BPBotHome.findByPrimaryKey( bPBot.getId( ) );
        assertNull( bPBotStored );

    }

}
