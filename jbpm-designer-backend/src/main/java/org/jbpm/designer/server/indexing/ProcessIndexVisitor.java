package org.jbpm.designer.server.indexing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jbpm.process.core.context.variable.Variable;
import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.workflow.core.node.HumanTaskNode;
import org.jbpm.workflow.core.node.RuleSetNode;
import org.kie.api.definition.process.Node;
import org.kie.api.definition.process.Process;
import org.uberfire.commons.data.Pair;

public class ProcessIndexVisitor {

    private Process process;

    //TODO, will be replaced by the suggested DefaultIndexBuilder, and generators.
    //Now we simply collect index attributes into the indexElements set.
    private Set<Pair<String, String>> indexElements = new HashSet<Pair<String, String>>();

    public ProcessIndexVisitor( Process process ) {
        this.process = process;
    }

    public Set<Pair<String, String>> visit() {
        visit( ( RuleFlowProcess ) process );
        return indexElements;
    }

    protected void visit( RuleFlowProcess process ) {

        //add some minimal process information to the index (in the case we want)
        indexElements.add( new Pair<String, String>( "bpmn2:process:id", process.getId() ) );
        indexElements.add( new Pair<String, String>( "bpmn2:process:name", process.getName() ) );
        indexElements.add( new Pair<String, String>( "bpmn2:process:packageName", process.getPackageName() ) );

        visitImports( process.getImports() );
        visit( process.getVariableScope() );

        Node[] nodes = process.getNodes();
        if ( nodes != null ) {
            for ( int i = 0; i < nodes.length; i++ ) {
                visit( nodes[ i ] );
            }
        }
    }

    protected void visitImports( List<String> imports ) {
        if ( imports != null ) {
            for ( String importStr : imports ) {
                // type_name is the standard kie-wb-commons attribute to indicate that a given type is being used in this file.
                // don't worry about "*" imports like "kie.somepackage.*" a this moment.
                indexElements.add( new Pair<String, String>( "type_name", importStr ) );
            }
        }
    }

    protected void visit( VariableScope scope ) {
        List<Variable> variables = scope != null ? scope.getVariables() : null;
        if ( variables != null ) {
            for ( Variable variable : variables ) {
                visit( variable );
            }
        }
    }

    protected void visit( Variable variable ) {

        //example of how to store the variables defined in this process in the index. (In the case we want)
        indexElements.add( new Pair<String, String>( "bpmn2:process:variable:name", variable.getName() ) );
        indexElements.add( new Pair<String, String>( "bpmn2:process:variable:type:" + variable.getName(), variable.getType().getStringType() ) );

        //we should declare that the given type is being used by this process.
        indexElements.add( new Pair<String, String>( "type_name", variable.getType().getStringType() ) );
    }

    protected void visit( Node node ) {

        if ( node instanceof RuleSetNode ) {
            visit( ( RuleSetNode ) node );
        } else if ( node instanceof HumanTaskNode ) {
            visit( ( HumanTaskNode ) node );
        }
    }

    protected void visit( RuleSetNode node ) {

        //we don't have a standarization in kie-wb-commons at the moment, but this is an example of how we can
        //store into the index that the given
        indexElements.add( new Pair<String, String>( "ruleflow_group", node.getRuleFlowGroup() ) );
    }

    protected void visit( HumanTaskNode node ) {
        //add whatever we want
    }

}
