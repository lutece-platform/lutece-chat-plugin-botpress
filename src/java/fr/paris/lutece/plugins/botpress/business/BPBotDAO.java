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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for BPBot objects
 */
public final class BPBotDAO implements IBPBotDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_b_p_bot, bot_key, name, description, avatar_url, avatar_renderer_key, language, bot_status, is_standalone, welcome_message, server_url, api_version FROM botpress_bots WHERE id_b_p_bot = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO botpress_bots ( bot_key, name, description, avatar_url, avatar_renderer_key,language, bot_status, is_standalone, welcome_message, server_url, api_version ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM botpress_bots WHERE id_b_p_bot = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE botpress_bots SET id_b_p_bot = ?, bot_key = ?, name = ?, description = ?, avatar_url = ?, avatar_renderer_key = ?,language = ?, bot_status = ?, is_standalone = ?, welcome_message = ?, server_url = ?, api_version = ? WHERE id_b_p_bot = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_b_p_bot, bot_key, name, description, avatar_url, avatar_renderer_key, language, bot_status, is_standalone, welcome_message, server_url, api_version FROM botpress_bots";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_b_p_bot FROM botpress_bots";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( BPBot bPBot, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, bPBot.getBotKey( ) );
            daoUtil.setString( nIndex++, bPBot.getName( ) );
            daoUtil.setString( nIndex++, bPBot.getDescription( ) );
            daoUtil.setString( nIndex++, bPBot.getAvatarUrl( ) );
            daoUtil.setString( nIndex++, bPBot.getAvatarRendererKey() );
            daoUtil.setString( nIndex++, bPBot.getLanguage( ) );
            daoUtil.setInt( nIndex++, bPBot.getBotStatus( ) );
            daoUtil.setInt( nIndex++, bPBot.getIsStandalone( ) );
            daoUtil.setString( nIndex++, bPBot.getWelcomeMessage( ) );
            daoUtil.setString( nIndex++, bPBot.getServerUrl( ) );
            daoUtil.setInt( nIndex++, bPBot.getApiVersion( ) );

            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                bPBot.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public BPBot load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        BPBot bPBot = null;

        if ( daoUtil.next( ) )
        {
            bPBot = new BPBot( );
            int nIndex = 1;

            bPBot.setId( daoUtil.getInt( nIndex++ ) );
            bPBot.setBotKey( daoUtil.getString( nIndex++ ) );
            bPBot.setName( daoUtil.getString( nIndex++ ) );
            bPBot.setDescription( daoUtil.getString( nIndex++ ) );
            bPBot.setAvatarUrl( daoUtil.getString( nIndex++ ) );
            bPBot.setAvatarRendererKey(daoUtil.getString( nIndex++ ) );
            bPBot.setLanguage( daoUtil.getString( nIndex++ ) );
            bPBot.setBotStatus( daoUtil.getInt( nIndex++ ) );
            bPBot.setIsStandalone( daoUtil.getInt( nIndex++ ) );
            bPBot.setWelcomeMessage( daoUtil.getString( nIndex++ ) );
            bPBot.setServerUrl( daoUtil.getString( nIndex++ ) );
            bPBot.setApiVersion( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free( );
        return bPBot;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( BPBot bPBot, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, bPBot.getId( ) );
        daoUtil.setString( nIndex++, bPBot.getBotKey( ) );
        daoUtil.setString( nIndex++, bPBot.getName( ) );
        daoUtil.setString( nIndex++, bPBot.getDescription( ) );
        daoUtil.setString( nIndex++, bPBot.getAvatarUrl( ) );
        daoUtil.setString( nIndex++, bPBot.getAvatarRendererKey( ) );
        daoUtil.setString( nIndex++, bPBot.getLanguage( ) );
        daoUtil.setInt( nIndex++, bPBot.getBotStatus( ) );
        daoUtil.setInt( nIndex++, bPBot.getIsStandalone( ) );
        daoUtil.setString( nIndex++, bPBot.getWelcomeMessage( ) );
        daoUtil.setString( nIndex++, bPBot.getServerUrl( ) );
        daoUtil.setInt( nIndex++, bPBot.getApiVersion( ) );
        daoUtil.setInt( nIndex, bPBot.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<BPBot> selectBPBotsList( Plugin plugin )
    {
        List<BPBot> bPBotList = new ArrayList<BPBot>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            BPBot bPBot = new BPBot( );
            int nIndex = 1;

            bPBot.setId( daoUtil.getInt( nIndex++ ) );
            bPBot.setBotKey( daoUtil.getString( nIndex++ ) );
            bPBot.setName( daoUtil.getString( nIndex++ ) );
            bPBot.setDescription( daoUtil.getString( nIndex++ ) );
            bPBot.setAvatarUrl( daoUtil.getString( nIndex++ ) );
            bPBot.setAvatarRendererKey( daoUtil.getString( nIndex++ ) );
            bPBot.setLanguage( daoUtil.getString( nIndex++ ) );
            bPBot.setBotStatus( daoUtil.getInt( nIndex++ ) );
            bPBot.setIsStandalone( daoUtil.getInt( nIndex++ ) );
            bPBot.setWelcomeMessage( daoUtil.getString( nIndex++ ) );
            bPBot.setServerUrl( daoUtil.getString( nIndex++ ) );
            bPBot.setApiVersion( daoUtil.getInt( nIndex++ ) );

            bPBotList.add( bPBot );
        }

        daoUtil.free( );
        return bPBotList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdBPBotsList( Plugin plugin )
    {
        List<Integer> bPBotList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            bPBotList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return bPBotList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectBPBotsReferenceList( Plugin plugin )
    {
        ReferenceList bPBotList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            bPBotList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return bPBotList;
    }
}
