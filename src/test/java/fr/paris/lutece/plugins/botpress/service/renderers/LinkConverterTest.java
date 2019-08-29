/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.botpress.service.renderers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * LinkConverter Test
 */
public class LinkConverterTest
{

    private static final String INPUT = "hklgfqlj [link1](url1) jlkjlmjjl[link2](url2) fsqg";
    private static final String EXPECTED = "hklgfqlj <a href=\"url1\" target=\"_blank\" >link1</a> jlkjlmjjl<a href=\"url2\" target=\"_blank\" >link2</a> fsqg";

    /**
     * Test of convert method, of class LinkConverter.
     */
    @Test
    public void testConvert( )
    {
        System.out.println( "convertLink" );
        LinkConverter instance = new LinkConverter( );
        String result = instance.convert( INPUT );
        assertEquals( EXPECTED, result );
        System.out.println( result );
    }

}
