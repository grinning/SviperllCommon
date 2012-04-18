package org.sviperll.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CLISpec {
    private final Map<String, String> usage = new TreeMap<String, String>();
    private final Map<Character, FlagHandler> shortFlagHandlers = new TreeMap<Character, FlagHandler>();
    private final Map<String, FlagHandler> longFlagHandlers = new TreeMap<String, FlagHandler>();
    private final Map<Character, ParameterHandler> shortParameterHandlers = new TreeMap<Character, ParameterHandler>();
    private final Map<String, ParameterHandler> longParameterHandlers = new TreeMap<String, ParameterHandler>();

    public void add(char c, String s, String description, FlagHandler handler) {
        newFlagOpt(c, description);
        newFlagOpt(s, description);
        shortFlagHandlers.put(c, handler);
        longFlagHandlers.put(s, handler);
    }
    public void add(String s, String description, FlagHandler handler) {
        newFlagOpt(s, description);
        longFlagHandlers.put(s, handler);
    }
    public void add(char c, String s, String description, ParameterHandler handler) {
        newParamOpt(c, description);
        newParamOpt(s, description);
        shortParameterHandlers.put(c, handler);
        longParameterHandlers.put(s, handler);
    }
    public void add(String s, String description, ParameterHandler handler) {
        newParamOpt(s, description);
        longParameterHandlers.put(s, handler);
    }

    private void newFlagOpt(char c, String description) {
        String s = new String(new char[] {c});
        if (usage.put(s, "\t\t -" + s + "\t\t" + description) != null)
            throw new IllegalArgumentException("Option " + s + " already defined!");
    }

    private void newFlagOpt(String s, String description) {
        if (usage.put(s, "\t\t--" + s + "\t\t" + description) != null)
            throw new IllegalArgumentException("Option " + s + " already defined!");
    }

    private void newParamOpt(char c, String description) {
        String s = new String(new char[] {c});
        if (usage.put(s, "\t\t -" + s + " OPTION\t" + description) != null)
            throw new IllegalArgumentException("Option " + s + " already defined!");
    }

    private void newParamOpt(String s, String description) {
        if (usage.put(s, "\t\t--" + s + "=OPTION\t" + description) != null)
            throw new IllegalArgumentException("Option " + s + " already defined!");
    }

    public String[] run(String[] args) {
        List<String> unprocessed = new ArrayList<String>();
        Parser p = new Parser(args);
        for (;;) {
            if (p.isEndOfArgs()) {
                break;
            } else if (p.current().equals("-")) {
                unprocessed.add(p.current());
                p.next();
            } else if (p.current().startsWith("-")) {
                if (p.current().startsWith("--"))
                    processLongOpts(p);
                else
                    processShortOpts(p);
            } else {
                unprocessed.add(p.current());
                p.next();
            }
        }
        String[] res = new String[unprocessed.size()];
        return unprocessed.toArray(res);
    }

    public void usage(Appendable pw) throws IOException {
        for (Map.Entry<String, String> e: usage.entrySet()) {
            pw.append(e.getValue());
            pw.append("\n");
        }
    }

    private void processLongOpts(Parser p) {
        for (Map.Entry<String, FlagHandler> e: longFlagHandlers.entrySet()) {
            if (p.current().equals("--" + e.getKey())) {
                e.getValue().handle();
                p.next();
                return;
            }
        }
        for (Map.Entry<String, ParameterHandler> e: longParameterHandlers.entrySet()) {
            String option = "--" + e.getKey();
            String prefix = option + "=";
            if (p.current().startsWith(prefix)) {
                e.getValue().handle(p.current().substring(prefix.length()));
                p.next();
                return;
            } else if (p.current().equals(option)) {
                p.next();
                e.getValue().handle(p.readParameter(e.getKey()));
                return;
            }
        }
        throw new IllegalArgumentException("Unknown option " + p.current());
    }

    private void processShortOpts(Parser p) {
        char[] c = p.current().toCharArray();
        Map.Entry<Character, ParameterHandler> handler = null;
        for (int i = 1; i < c.length; i++) {
            boolean found = false;
            for (Map.Entry<Character, FlagHandler> e: shortFlagHandlers.entrySet()) {
                if (e.getKey().equals(c[i])) {
                    e.getValue().handle();
                    found = true;
                }
            }
            for (Map.Entry<Character, ParameterHandler> e: shortParameterHandlers.entrySet()) {
                if (e.getKey().equals(c[i])) {
                    if (handler != null)
                        throw new IllegalArgumentException("Expecting parameter for -" + handler.getKey());
                    else
                        handler = e;
                    found = true;
                }
            }
            if (!found)
                throw new IllegalArgumentException("Unknown option -" + c[i]);
        }
        if (handler != null) {
            p.next();
            handler.getValue().handle(p.readParameter(handler.getKey()));
        }
    }

    private class Parser {

        private final String[] args;
        private int i;

        public Parser(String[] args) {
            this.args = args;
            i = 0;
        }

        public boolean isEndOfArgs() {
            return i == args.length;
        }

        public String current() {
            if (!(i < args.length))
                throw new IllegalArgumentException("Expection argument, got end of line");
            return args[i];
        }

        public String readParameter(char option) {
            if (!current().equals("-") && current().startsWith("-"))
                throw new IllegalArgumentException("Expecting parameter for -" + option);
            String res = current();
            next();
            return res;
        }

        public String readParameter(String option) {
            if (!current().equals("-") && current().startsWith("-"))
                throw new IllegalArgumentException("Expecting parameter for --" + option);
            String res = current();
            next();
            return res;
        }

        public void next() {
            i++;
        }
    }

    public interface FlagHandler {
        void handle();
    }

    public interface ParameterHandler {
        void handle(String param);
    }
}
