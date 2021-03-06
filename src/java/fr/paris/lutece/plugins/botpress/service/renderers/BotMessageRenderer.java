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

package fr.paris.lutece.plugins.botpress.service.renderers;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Map;

/**
 * BotMessageRenderer
 */
public interface BotMessageRenderer
{
    final String FIELD_TYPE = "type";
    final String TYPE_TEXT = "text";
    final String TYPE_FILE = "file";
    final String FIELD_TEXT = "text";
    final String FIELD_URL = "url";

    /**
     * Analyze the response node to tell if this renderer should be invoked
     * 
     * @param nodeResponse
     *            The response node
     * @return true if the renderer should handle the response format otherwise false
     */
    boolean isInvoked( JsonNode nodeResponse );

    /**
     * Render a JSON response as HTML
     * 
     * @param map
     *            The map corresponding to the JSON response
     * @return The rendered HTML
     */
    String render( Map map );

    /**
     * Return the content type corresponding to this renderer
     * 
     * @return The content type
     */
    String getPostContentType( );

    /**
     * Define a list of converters used by the renderer
     * 
     * @param listConverters
     *            The list of converters
     */
    void setConverters( List<Converter> listConverters );

    /**
     * Returns the list of converters
     * 
     * @return converters
     */
    List<Converter> getConverters( );

}
