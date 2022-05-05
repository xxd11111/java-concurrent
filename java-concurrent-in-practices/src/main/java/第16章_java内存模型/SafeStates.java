package 第16章_java内存模型;

import java.util.*;

import net.jcip.annotations.*;

/**
 * 本质上还是不同的对象，只是内容保证了一致，并且不会相互干扰
 */
@ThreadSafe
public class SafeStates {
    private final Map<String, String> states;

    public SafeStates() {
        states = new HashMap<String, String>();
        states.put("alaska", "AK");
        states.put("alabama", "AL");
        /*...*/
        states.put("wyoming", "WY");
    }

    public String getAbbreviation(String s) {
        return states.get(s);
    }

    public Map<String, String> getStates() {
        return states;
    }
}
