/*Bruno Veiga - 743514
Lucas Costa - 743563
Luiz Felipe Guimarães - 743570
Thiago Borges - 613770*/

package AST;

public class Parameter extends Variable {
    public Parameter(String name, Type type) {
        super(name, type);
    }

    public Parameter(String name) {
        super(name);
    }
}