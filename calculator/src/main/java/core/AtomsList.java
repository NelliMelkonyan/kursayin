package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AtomsList {
    public List<Character> parseAtoms(String expr) {
        ArrayList<Character> lst = new ArrayList<Character>();
        for (int i = 0; i < expr.length(); ++i) {
            char token = expr.charAt(i);
            if (!Constants.isVariable(token)) continue;
            boolean found = false;
            Iterator<Character> i$ = lst.iterator();
            while (i$.hasNext()) {
                char c = i$.next().charValue();
                if (token != c) continue;
                found = true;
                break;
            }
            if (found) continue;
            lst.add(Character.valueOf(token));
        }
        return lst;
    }
}

