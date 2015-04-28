package org.jbpm.designer.client.shared;

import org.jboss.errai.common.client.protocols.SerializationParts;
import org.jboss.errai.marshalling.client.api.MarshallingSession;
import org.jboss.errai.marshalling.client.api.annotations.ClientMarshaller;
import org.jboss.errai.marshalling.client.api.annotations.ServerMarshaller;
import org.jboss.errai.marshalling.client.api.json.EJObject;
import org.jboss.errai.marshalling.client.api.json.EJValue;
import org.jboss.errai.marshalling.client.marshallers.AbstractNullableMarshaller;

@ClientMarshaller(AssignmentData.class)
@ServerMarshaller(AssignmentData.class)
public class AssignmentDataMarshaller
        extends AbstractNullableMarshaller<AssignmentData>

{
    public AssignmentData doNotNullDemarshall(EJValue o, MarshallingSession ctx) {
        EJObject obj = o.isObject();
        String dataInputSet = obj.get("dataInputSet").isString().stringValue();
        String dataOutputSet = obj.get("dataOutputSet").isString().stringValue();
        String processVars = obj.get("processVars").isString().stringValue();
        String assignments = obj.get("assignments").isString().stringValue();
        String dataTypes = obj.get("dataTypes").isString().stringValue();
        return new AssignmentData(dataInputSet, dataOutputSet, processVars, assignments, dataTypes);
    }

    public String doNotNullMarshall(AssignmentData o, MarshallingSession ctx) {
        return "{\"" + SerializationParts.ENCODED_TYPE + "\":\"" + AssignmentData.class.getName() + "\"," +

                "\"" + SerializationParts.OBJECT_ID + "\":\"" + o.hashCode() + "\"," +
                "\"" + "dataInputSet" + "\":\"" + o.getInputVariablesString() + "\"," +
                "\"" + "dataOutputSet" + "\":\"" + o.getOutputVariablesString() + "\"," +
                "\"" + "processVars" + "\":\"" + o.getProcessVariablesString() + "\"," +
                "\"" + "assignments" + "\":\"" + o.getAssignmentsString() + "\"," +
                "\"" + "dataTypes" + "\":\"" + o.getDataTypesString() + "\"}";
//                o.toString() + "\"}";
    }

    @Override public AssignmentData[] getEmptyArray() {
        return new AssignmentData[0];
    }
}
