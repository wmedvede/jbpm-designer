package org.jbpm.designer.client.shared;

import java.util.ArrayList;
import java.util.List;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jbpm.designer.client.shared.Variable.VariableType;

@Portable
public class AssignmentData {

    private List<Variable> inputVariables = new ArrayList<Variable>();

    private List<Variable> outputVariables = new ArrayList<Variable>();

    private List<Variable> processVariables = new ArrayList<Variable>();

    private List<Assignment> assignments = new ArrayList<Assignment>();

    private List<String> dataTypes = new ArrayList<String>();

    public AssignmentData() {

    }

    public AssignmentData(String sInputVariables, String sOutputVariables, String sProcessVariables,
            String sAssignments, String sDataTypes) {
        // setDataTypes before variables because these determine whether variable datatypes are custom or not
        setDataTypes(sDataTypes);
        setProcessVariables(sProcessVariables);
        setInputVariables(sInputVariables, dataTypes);
        setOutputVariables(sOutputVariables, dataTypes);
        setAssignments(sAssignments);
    }

    public List<Variable> getInputVariables() {
        return inputVariables;
    }

    public String getInputVariablesString() {
        StringBuilder sb = new StringBuilder();
        for (Variable var : inputVariables) {
            sb.append(var.toString()).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public void setInputVariables(String sInputVariables, List<String> dataTypes) {
        inputVariables.clear();
        if (sInputVariables != null && !sInputVariables.isEmpty()) {
            String[] inputs = sInputVariables.split(",");
            for (String input : inputs) {
                if (!input.isEmpty()) {
                    Variable var = Variable.deserialize(input, Variable.VariableType.INPUT, dataTypes);
                    if (var != null && var.getName() != null && !var.getName().isEmpty()) {
                        inputVariables.add(var);
                    }
                }
            }
        }
    }

    public List<Variable> getOutputVariables() {
        return outputVariables;
    }

    public String getOutputVariablesString() {
        StringBuilder sb = new StringBuilder();
        for (Variable var : outputVariables) {
            sb.append(var.toString()).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public void setOutputVariables(String sOutputVariables, List<String> dataTypes) {
        outputVariables.clear();
        if (sOutputVariables != null && !sOutputVariables.isEmpty()) {
            String[] outputs = sOutputVariables.split(",");
            for (String output : outputs) {
                if (!output.isEmpty()) {
                    Variable var = Variable.deserialize(output, Variable.VariableType.OUTPUT, dataTypes);
                    if (var != null && var.getName() != null && !var.getName().isEmpty()) {
                        outputVariables.add(var);
                    }
                }
            }
        }
    }

    public List<Variable> getProcessVariables() {
        return processVariables;
    }

    public String getProcessVariablesString() {
        StringBuilder sb = new StringBuilder();
        for (Variable var : processVariables) {
            sb.append(var.toString()).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public void setProcessVariables(String sProcessVariables) {
        processVariables.clear();
        if (sProcessVariables != null && !sProcessVariables.isEmpty()) {
            String[] processVars = sProcessVariables.split(",");
            for (String processVar : processVars) {
                if (!processVar.isEmpty()) {
                    Variable var = Variable.deserialize(processVar, Variable.VariableType.PROCESS);
                    if (var != null && var.getName() != null && !var.getName().isEmpty()) {
                        processVariables.add(var);
                    }
                }
            }
        }
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public String getAssignmentsString() {
        StringBuilder sb = new StringBuilder();
        for (Assignment a : assignments) {
            sb.append(a.toString()).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public void setAssignments(String sAssignments) {
        assignments.clear();
        if (sAssignments != null && !sAssignments.isEmpty()) {
            String[] as = sAssignments.split(",");
            for (String a : as) {
                if (!a.isEmpty()) {
                    Assignment ass = Assignment.deserialize(this, a);
                    if (ass != null && ass.getName() != null && !ass.getName().isEmpty()) {
                        assignments.add(ass);
                    }
                }
            }
        }
    }

    public List<String> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(String dataTypes) {
        this.dataTypes.clear();
        if (dataTypes != null && !dataTypes.isEmpty()) {
            String[] dts = dataTypes.split(",");
            for (String dt : dts) {
                if (!dt.isEmpty()) {
                    dt = dt.trim();
                    if (!dt.isEmpty()) {
                        if (dt.contains(":")) {
                            dt = dt.substring(0, dt.indexOf(':'));
                        }
                        if (!dt.isEmpty()) {
                            this.dataTypes.add(dt.trim());
                        }
                    }
                }
            }
        }

    }

    public Variable findProcessVar(String processVarName) {
        if (processVarName == null || processVarName.isEmpty()) {
            return null;
        }
        for (Variable var : processVariables) {
            if (processVarName.equals(var.getName())) {
                return var;
            }
        }
        return null;
    }

    public Variable findVariable(String variableName, VariableType variableType) {
        if (variableName == null || variableName.isEmpty()) {
            return null;
        }
        if (variableType == Variable.VariableType.INPUT) {
            for (Variable var : inputVariables) {
                if (variableName.equals(var.getName())) {
                    return var;
                }
            }
        } else if (variableType == Variable.VariableType.OUTPUT) {
            for (Variable var : outputVariables) {
                if (variableName.equals(var.getName())) {
                    return var;
                }
            }
        }
        return null;
    }

    public void addVariable(Variable variable) {
        if (variable.getVariableType() == VariableType.INPUT) {
            inputVariables.add(variable);
        }
        else if (variable.getVariableType() == VariableType.OUTPUT) {
            outputVariables.add(variable);
        }
        else if (variable.getVariableType() == VariableType.PROCESS) {
            processVariables.add(variable);
        }
    }

    public List<String> getDataTypeNames() {
        return dataTypes;
    }

    public String getDataTypesString() {
        StringBuilder sb = new StringBuilder();
        for (String dataType : dataTypes) {
            sb.append(dataType).append(':').append(dataType).append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public List<String> getProcessVarNames() {
        List<String> processVarNames = new ArrayList<String>();
        for (Variable processVar : processVariables) {
            processVarNames.add(processVar.getName());
        }
        return processVarNames;
    }

    /**
     * Gets a list of AssignmentRows based on the current Assignments
     *
     * @return
     */
    public List<AssignmentRow> getAssignmentRows(VariableType varType) {
        List<AssignmentRow> rows = new ArrayList<AssignmentRow>();
        for (Assignment assignment : assignments) {
            if (assignment.getVariableType() == varType) {
                AssignmentRow row = new AssignmentRow(assignment.getName(), assignment.getVariableType(), assignment.getDataType(),
                        assignment.getCustomDataType(), assignment.getProcessVarName(), assignment.getConstant());
                rows.add(row);
            }
        }
        return rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"dataInputSet\":\"").append(getInputVariablesString()).append("\"").append(",\n");
        sb.append("\"dataOutputSet\":\"").append(getOutputVariablesString()).append("\"").append(",\n");
        sb.append("\"processVars\":\"").append(getProcessVariablesString()).append("\"").append(",\n");
        sb.append("\"assignments\":\"").append(getAssignmentsString()).append("\"").append(",\n");
        sb.append("\"dataTypes\":\"").append(getDataTypesString()).append("\"");

        return sb.toString();
    }
}
