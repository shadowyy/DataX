package com.alibaba.datax.core.util;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.ParseException;

import java.util.ListIterator;

/**
 * @author shadowyy
 * @version 2020/6/22 18:26
 */
public class IgnoreUnrecognizedOptionParser extends BasicParser {

    private boolean ignoreUnrecognizedOption;

    public IgnoreUnrecognizedOptionParser(final boolean ignoreUnrecognizedOption) {
        this.ignoreUnrecognizedOption = ignoreUnrecognizedOption;
    }

    @Override
    protected void processOption(final String arg, final ListIterator iter) throws ParseException {
        boolean hasOption = getOptions().hasOption(arg);
        if (hasOption || !ignoreUnrecognizedOption) {
            super.processOption(arg, iter);
        }
    }

}