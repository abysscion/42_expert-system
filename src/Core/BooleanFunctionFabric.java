package Core;

public class BooleanFunctionFabric {
    public boolean[] selectOperation(String op, boolean[] left, boolean[] right) {
        BooleanFunction function = new BooleanFunction();
        boolean[] result;
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
                throw new IllegalStateException("Unexpected value: " + op);
        }
        return result;
    }
}
