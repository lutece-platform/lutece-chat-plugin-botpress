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

package fr.paris.lutece.plugins.botpress.web;

import fr.paris.lutece.plugins.botpress.business.BPBot;
import fr.paris.lutece.plugins.botpress.business.BPBotHome;
import fr.paris.lutece.plugins.botpress.service.BotRegistrationService;
import fr.paris.lutece.plugins.botpress.service.ConverseService;
import fr.paris.lutece.plugins.botpress.service.LanguageService;
import fr.paris.lutece.plugins.chatbot.service.avatar.AvatarRendererService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage BPBot features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageBPBots.jsp", controllerPath = "jsp/admin/plugins/botpress/", right = "BOTPRESS_MANAGEMENT" )
public class BPBotJspBean extends MVCAdminJspBean
{
    // Rights
    public static final String RIGHT_MANAGEBOTPRESSBOTS = "BOTPRESS_MANAGEMENT";

    // Parameters
    private static final String PARAMETER_PAGE_INDEX = "page_index";
    private static final String PARAMETER_ID_BPBOT = "id";

    // Markers
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_STATUS_LIST = "status_list";
    private static final String MARK_LANGUAGES_LIST = "languages_list";
    private static final String MARK_MODES_LIST = "modes_list";
    private static final String MARK_BPBOT_LIST = "bpbot_list";
    private static final String MARK_BPBOT = "bpbot";
    private static final String MARK_VERSIONS_LIST = "versions_list";
    private static final String MARK_AVATAR_RENDERERS = "avatar_renderers_list";

    // Templates
    private static final String TEMPLATE_MANAGE_BPBOTS = "/admin/plugins/botpress/manage_bpbots.html";
    private static final String TEMPLATE_CREATE_BPBOT = "/admin/plugins/botpress/create_bpbot.html";
    private static final String TEMPLATE_MODIFY_BPBOT = "/admin/plugins/botpress/modify_bpbot.html";

    // Properties
    private static final String PROPERTY_DEFAULT_LIST_ITEM_PER_PAGE = "botpress.listItems.itemsPerPage";
    private static final String PROPERTY_PAGE_TITLE_MANAGE_BPBOTS = "botpress.manage_bpbots.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_BPBOT = "botpress.modify_bpbot.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_BPBOT = "botpress.create_bpbot.pageTitle";

    private static final String JSP_MANAGE_BPBOTS = "jsp/admin/plugins/botpress/ManageBPBots.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_BPBOT = "botpress.message.confirmRemoveBPBot";
    private static final String MESSAGE_ERROR_CREATING_BOT = "botpress.message.error.create";
    private static final String MESSAGE_ERROR_MODIFYING_BOT = "botpress.message.error.modify";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "botpress.model.entity.bpbot.attribute.";

    // Views
    private static final String VIEW_MANAGE_BPBOTS = "manageBPBots";
    private static final String VIEW_CREATE_BPBOT = "createBPBot";
    private static final String VIEW_MODIFY_BPBOT = "modifyBPBot";

    // Actions
    private static final String ACTION_CREATE_BPBOT = "createBPBot";
    private static final String ACTION_MODIFY_BPBOT = "modifyBPBot";
    private static final String ACTION_REMOVE_BPBOT = "removeBPBot";
    private static final String ACTION_CONFIRM_REMOVE_BPBOT = "confirmRemoveBPBot";

    // Infos
    private static final String INFO_BPBOT_CREATED = "botpress.info.bpbot.created";
    private static final String INFO_BPBOT_UPDATED = "botpress.info.bpbot.updated";
    private static final String INFO_BPBOT_REMOVED = "botpress.info.bpbot.removed";

    // Session variable to store working values
    private BPBot _bpbot;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * Return a model that contains the list and paginator infos
     * 
     * @param request
     *            The HTTP request
     * @param strBookmark
     *            The bookmark
     * @param list
     *            The list of item
     * @param strManageJsp
     *            The JSP
     * @return The model
     */
    private <T> Map<String, Object> getPaginatedListModel( HttpServletRequest request, String strBookmark, List<T> list, String strManageJsp )
    {
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        int nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_ITEM_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage, nDefaultItemsPerPage );

        UrlItem url = new UrlItem( strManageJsp );
        String strUrl = url.getUrl( );

        // PAGINATOR
        LocalizedPaginator<T> paginator = new LocalizedPaginator<T>( list, _nItemsPerPage, strUrl, PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale( ) );

        Map<String, Object> model = getModel( );

        model.put( MARK_NB_ITEMS_PER_PAGE, String.valueOf( _nItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginator );
        model.put( strBookmark, paginator.getPageItems( ) );

        return model;
    }

    /**
     * Build the Manage View
     * 
     * @param request
     *            The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_BPBOTS, defaultView = true )
    public String getManageBPBots( HttpServletRequest request )
    {
        _bpbot = null;
        List<BPBot> listBPBots = BPBotHome.getBPBotsList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_BPBOT_LIST, listBPBots, JSP_MANAGE_BPBOTS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_BPBOTS, TEMPLATE_MANAGE_BPBOTS, model );
    }

    /**
     * Returns the form to create a bpbot
     *
     * @param request
     *            The Http request
     * @return the html code of the bpbot form
     */
    @View( VIEW_CREATE_BPBOT )
    public String getCreateBPBot( HttpServletRequest request )
    {
        _bpbot = ( _bpbot != null ) ? _bpbot : new BPBot( );

        Map<String, Object> model = getModel( );
        model.put( MARK_BPBOT, _bpbot );
        model.put( MARK_STATUS_LIST, BotRegistrationService.getBotsStatusList( getLocale( ) ) );
        model.put( MARK_LANGUAGES_LIST, LanguageService.getLanguages( getLocale( ) ) );
        model.put( MARK_MODES_LIST, BotRegistrationService.getModes( getLocale( ) ) );
        model.put( MARK_VERSIONS_LIST, ConverseService.getApiVersions( ) );
        model.put( MARK_AVATAR_RENDERERS, AvatarRendererService.getAvatarRenderersList() );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_BPBOT, TEMPLATE_CREATE_BPBOT, model );
    }

    /**
     * Process the data capture form of a new bpbot
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_BPBOT )
    public String doCreateBPBot( HttpServletRequest request )
    {
        populate( _bpbot, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _bpbot, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_BPBOT );
        }

        try 
        {
            BPBotHome.create( _bpbot );
        }
        catch( AppException e )
        {
            addError( I18nService.getLocalizedString( MESSAGE_ERROR_CREATING_BOT, getLocale( ) ) );
            return redirectView( request, VIEW_CREATE_BPBOT );
        }
        addInfo( INFO_BPBOT_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_BPBOTS );
    }

    /**
     * Manages the removal form of a bpbot whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_BPBOT )
    public String getConfirmRemoveBPBot( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_BPBOT ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_BPBOT ) );
        url.addParameter( PARAMETER_ID_BPBOT, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_BPBOT, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a bpbot
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage bpbots
     */
    @Action( ACTION_REMOVE_BPBOT )
    public String doRemoveBPBot( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_BPBOT ) );
        BPBotHome.remove( nId );
        addInfo( INFO_BPBOT_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_BPBOTS );
    }

    /**
     * Returns the form to update info about a bpbot
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_BPBOT )
    public String getModifyBPBot( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_BPBOT ) );

        if ( _bpbot == null || ( _bpbot.getId( ) != nId ) )
        {
            _bpbot = BPBotHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_BPBOT, _bpbot );
        model.put( MARK_STATUS_LIST, BotRegistrationService.getBotsStatusList( getLocale( ) ) );
        model.put( MARK_LANGUAGES_LIST, LanguageService.getLanguages( getLocale( ) ) );
        model.put( MARK_MODES_LIST, BotRegistrationService.getModes( getLocale( ) ) );
        model.put( MARK_VERSIONS_LIST, ConverseService.getApiVersions( ) );
        model.put( MARK_AVATAR_RENDERERS, AvatarRendererService.getAvatarRenderersList() );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_BPBOT, TEMPLATE_MODIFY_BPBOT, model );
    }

    /**
     * Process the change form of a bpbot
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_BPBOT )
    public String doModifyBPBot( HttpServletRequest request )
    {
        populate( _bpbot, request, request.getLocale( ) );

        // Check constraints
        if ( !validateBean( _bpbot, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_BPBOT, PARAMETER_ID_BPBOT, _bpbot.getId( ) );
        }
        try
        {
            BPBotHome.update( _bpbot );
        }
        catch( AppException e )
        {
            addError( I18nService.getLocalizedString( MESSAGE_ERROR_MODIFYING_BOT, getLocale( ) ) );
            return redirect( request, VIEW_MODIFY_BPBOT, PARAMETER_ID_BPBOT, _bpbot.getId( ) );
        }
        addInfo( INFO_BPBOT_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_BPBOTS );
    }
}
