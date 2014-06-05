package org.jbpm.designer.server.indexing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import org.junit.Test;
import org.uberfire.commons.data.Pair;

import static org.junit.Assert.fail;

public class BPMN2FileIndexerTest {

    @Test
    public void testVisitor() {
        try {
            BPMN2FileIndexer indexer = new BPMN2FileIndexer();
            StringBuilder bpmn = readStringBuilder( getClass().getResourceAsStream( "hiring.bpmn2" ) );

            Set<Pair<String, String>> indexPairs = indexer.calculateTestPairs( bpmn.toString() );
            System.out.print( indexPairs );

            for ( Pair<String, String> pair : indexPairs ) {
                System.out.println(pair);
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            fail("Test failed: " + e.getMessage() );
        }
    }

    public static StringBuilder readStringBuilder( InputStream in ) throws IOException {
        BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
        StringBuilder out = new StringBuilder( );
        String line;
        String lineSeparator = System.getProperty( "line.separator" );
        line = reader.readLine( );
        if ( line != null ) {
            out.append( line );
            while ( ( line = reader.readLine( ) ) != null ) {
                out.append( lineSeparator );
                out.append( line );
            }
        }
        return out;
    }

}