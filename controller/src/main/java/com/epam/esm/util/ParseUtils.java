package com.epam.esm.util;

import com.epam.esm.exception.IdNotNumberException;
import com.epam.esm.exception.PageNumberNotNumberException;
import com.epam.esm.exception.PageSizeNotNumberException;

public class ParseUtils {
    public static long parseId(String pathId) {
        try {
            return Long.parseLong(pathId);
        } catch (NumberFormatException ex) {
            throw new IdNotNumberException(pathId);
        }
    }

    public static int parsePageNumber(String pageNumber) {
        try {
            return Integer.parseInt(pageNumber);
        } catch (NumberFormatException ex) {
            throw new PageNumberNotNumberException(pageNumber);
        }
    }

    public static int parsePageSize(String pageSize) {
        try {
            return Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            throw new PageSizeNotNumberException(pageSize);
        }
    }
}
