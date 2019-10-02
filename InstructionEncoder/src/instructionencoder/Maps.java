package instructionencoder;

import java.util.HashMap;

/**
 *
 * @author xande
 */
public abstract class Maps {

    /**
     * Guarda o número de clocks necessário para cada instrução
     */
    public static HashMap<String, Integer> cicles = new HashMap<>();
    
    /**
     * Inicializa o hashmap cicles
     */
    public static void init(){
        cicles.put("mv", 2);
        cicles.put("mvi", 3);
        cicles.put("add", 4);
        cicles.put("sub", 4);
        cicles.put("and", 4);
        cicles.put("slt", 4);
        cicles.put("sll", 4);
        cicles.put("srl", 4);
        //TODO: add new instructions
    }
}
