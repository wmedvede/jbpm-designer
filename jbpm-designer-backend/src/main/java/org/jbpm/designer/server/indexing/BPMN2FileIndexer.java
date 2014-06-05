package org.jbpm.designer.server.indexing;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.core.io.impl.ReaderResource;
import org.drools.core.xml.SemanticModules;
import org.jbpm.bpmn2.xml.BPMNDISemanticModule;
import org.jbpm.bpmn2.xml.BPMNExtensionsSemanticModule;
import org.jbpm.bpmn2.xml.BPMNSemanticModule;
import org.jbpm.compiler.xml.XmlProcessReader;
import org.jbpm.designer.type.Bpmn2TypeDefinition;
import org.jbpm.process.core.validation.ProcessValidationError;
import org.jbpm.process.core.validation.ProcessValidator;
import org.jbpm.process.core.validation.ProcessValidatorRegistry;
import org.kie.api.definition.process.Process;
import org.kie.api.io.Resource;
import org.kie.workbench.common.services.refactoring.backend.server.util.KObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.commons.data.Pair;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.file.Path;
import org.uberfire.metadata.engine.Indexer;
import org.uberfire.metadata.model.KObject;
import org.uberfire.metadata.model.KObjectKey;

@ApplicationScoped
public class BPMN2FileIndexer implements Indexer {

    private static final Logger logger = LoggerFactory.getLogger( BPMN2FileIndexer.class );

    private static final SemanticModules modules = new SemanticModules();

    static {
        modules.addSemanticModule( new BPMNSemanticModule() );
        modules.addSemanticModule( new BPMNDISemanticModule() );
        modules.addSemanticModule( new BPMNExtensionsSemanticModule() );
    }

    @Inject
    private Bpmn2TypeDefinition bpmn2TypeDefinition;

    @Inject
    @Named( "ioStrategy" )
    protected IOService ioService;

    @Override
    public boolean supportsPath( Path path ) {
        return bpmn2TypeDefinition.accept( Paths.convert( path ) );
    }

    @Override
    public KObject toKObject( Path path ) {

        KObject index = null;

        try {

            String bpmnStr = ioService.readAllString( path );
            XmlProcessReader processReader = new XmlProcessReader( modules, getProjectClassLoader() );
            Reader bpmnReader = new StringReader( bpmnStr );
            Process process;
            List<Process> processes;

            processes = processReader.read( bpmnReader );

            process = ( processes != null && processes.size() > 0 ) ? processes.get( 0 ) : null;
            if ( process != null ) {
                Resource resource = new ReaderResource( new StringReader( bpmnStr ) );
                ProcessValidationError[] errors;

                ProcessValidator validator = ProcessValidatorRegistry.getInstance().getValidator( process, resource );
                errors = validator.validateProcess( process );
                if ( errors.length > 0 ) {
                    logger.error( "The process: " + process.getName() + " has validation errors, but we can still try to index information for well defined nodes." );

                }

                ProcessIndexVisitor visitor = new ProcessIndexVisitor( process );

                index = KObjectUtil.toKObject( path, visitor.visit() );
            } else {
                logger.warn( "No process was found in file: " + path.toUri() );
            }

        } catch ( Exception e ) {
            logger.error( "Unable to index '" + path.toUri().toString() + "'.",
                    e.getMessage() );
        }

        return index;
    }

    @Override
    public KObjectKey toKObjectKey( Path path ) {
        return KObjectUtil.toKObjectKey( path );
    }

    protected ClassLoader getProjectClassLoader() {
        //TODO calculate project classloader properly
        return getClass().getClassLoader();
    }

    //TODO this method here to enable a quick test without having to setup all the Indexer engine stuff....
    //only for POC
    public Set<Pair<String, String>> calculateTestPairs( String bpmnStr ) throws Exception {

        XmlProcessReader processReader = new XmlProcessReader( modules, getProjectClassLoader() );
        Reader bpmnReader = new StringReader( bpmnStr );
        Process process;
        List<Process> processes;

        processes = processReader.read( bpmnReader );

        process = ( processes != null && processes.size() > 0 ) ? processes.get( 0 ) : null;
        if ( process != null ) {
            Resource resource = new ReaderResource( new StringReader( bpmnStr ) );
            ProcessValidationError[] errors;

            ProcessValidator validator = ProcessValidatorRegistry.getInstance().getValidator( process, resource );
            errors = validator.validateProcess( process );
            if ( errors.length > 0 ) {
                logger.error( "The process: " + process.getName() + " has validation errors, but we can still try to index information for well defined nodes." );

            }

            ProcessIndexVisitor visitor = new ProcessIndexVisitor( process );
            return visitor.visit();
        }

        return new HashSet<Pair<String, String>>(  );
    }
}
