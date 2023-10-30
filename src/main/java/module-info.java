module ws.slink.intervals {

    // required in compile time

    // exported in compile time
    exports ws.slink.intervals;
    exports ws.slink.intervals.exception;

    // opened in run time
    opens ws.slink.intervals.impl;
    opens ws.slink.intervals.tools;

}
