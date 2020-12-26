package Core;

import java.util.ArrayList;

public class BooleanFunctionFabric {
    public ArrayList<Boolean> selectOperation(String op, ArrayList<Boolean> left, ArrayList<Boolean> right) {
        BooleanFunction function = new BooleanFunction();
        ArrayList<Boolean> result = null;
        switch (op) {
            case "|":
                result = function.lor(left, right);
                break;
            case "+":
                result = function.land(left, right);
                break;
            case "!":
                result = function.lnot(left, right);
                break;
            case "^":
                result = function.lxor(left, right);
                break;
            case "=>":
                result = function.limplies(left, right);
                break;
            case "<=>":
                result = function.lidentity(left, right);
                break;
            default:
                Utilities.HandleException(new IllegalStateException("Unexpected value: " + op));
        }
        return result;
    }
}
