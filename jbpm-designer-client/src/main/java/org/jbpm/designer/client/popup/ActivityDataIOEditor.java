package org.jbpm.designer.client.popup;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import org.jbpm.designer.client.shared.AssignmentRow;
import org.jbpm.designer.client.shared.Variable.VariableType;
import org.uberfire.ext.widgets.common.client.common.popups.BaseModal;

@Dependent
public class ActivityDataIOEditor extends BaseModal {

    @Inject
    private ActivityDataIOEditorWidget inputAssignmentsWidget;

    @Inject
    private ActivityDataIOEditorWidget outputAssignmentsWidget;

    private Button btnOK;

    private Button btnCancel;


    public ActivityDataIOEditor() {
        super();
        
        this.setWidth((double) Window.getClientWidth() * 0.6D + "px");
    }

    @PostConstruct
    public void init() {
        setTitle("Data I/O Editor");

        inputAssignmentsWidget.setVariableType(VariableType.INPUT);
        this.add(inputAssignmentsWidget);

        outputAssignmentsWidget.setVariableType(VariableType.OUTPUT);
        this.add(outputAssignmentsWidget);

        btnOK = new Button( "OK" );
        btnOK.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                Window.alert(inputAssignmentsWidget.getAssignmentsAsString());
                Window.alert(outputAssignmentsWidget.getAssignmentsAsString());
                hide();
            }
        });
        this.add(btnOK);

        btnCancel = new Button( "Cancel" );
        btnCancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                hide();
            }
        });
        this.add(btnCancel);

    }

    @Override
    public void onShow(Event e) {
    }

    @Override
    public void onHide(Event e) {
    }

    public void setInputAssignmentRows(List<AssignmentRow> inputAssignmentRows) {
        inputAssignmentsWidget.setData(inputAssignmentRows);
    }

    public void setOutputAssignmentRows(List<AssignmentRow> outputAssignmentRows) {
        outputAssignmentsWidget.setData(outputAssignmentRows);
    }

    public void setDataTypes(List<String> dataTypes) {
        inputAssignmentsWidget.setDataTypes(dataTypes);
        outputAssignmentsWidget.setDataTypes(dataTypes);
    }

    public void setProcessVariables(List<String> processVariables) {
        inputAssignmentsWidget.setProcessVariables(processVariables);
        outputAssignmentsWidget.setProcessVariables(processVariables);
    }

    //   public void hide() {
 //       Window.alert(activityDataIOEditorWidget.getAssignmentsAsString());
 //       super.hide();
 //   }
}
