package org.jbpm.designer.client.shared;

import org.jboss.errai.databinding.client.api.Bindable;
import org.jbpm.designer.client.shared.Variable.VariableType;

@Bindable
public class AssignmentRow {

    private String name;
    private VariableType variableType;
    private String dataType;
    private String customDataType;
    private String processVar;
    private String constant;

    public AssignmentRow() {
    }

    public AssignmentRow(String name, VariableType variableType, String dataType, String customDataType, String processVar, String constant) {
        this.name = name;
        this.variableType = variableType;
        this.dataType = dataType;
        this.customDataType = customDataType;
        this.processVar = processVar;
        this.constant = constant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCustomDataType() {
        return customDataType;
    }

    public void setCustomDataType(String customDataType) {
        this.customDataType = customDataType;
    }

    public String getProcessVar() {
        return processVar;
    }

    public void setProcessVar(String processVar) {
        this.processVar = processVar;
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AssignmentRow other = (AssignmentRow) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
        
    }

    @Override
    public String toString() {
        return "Assignment [name=" + name + ", variableType=" + variableType.toString() + ", dataType=" + dataType + ", customDataType=" + customDataType + ", processVar=" + processVar + ", constant=" + constant + "]";
    }
}