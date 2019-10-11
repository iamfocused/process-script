package com.isaac.processscript.time

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

static def formatTimeStamp(def strTimeStamp) {
    if (!strTimeStamp instanceof String) {
        throw new RuntimeException("format time stamp param must be String")
    }

    if (!Pattern.matches("^[0-9]*\$", strTimeStamp)) {
        throw new RuntimeException("String param must be numbers")
    }
    DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.ofInstant(Instant.ofEpochMilli(strTimeStamp.toLong()), ZoneId.systemDefault()))
}

println(formatTimeStamp("1561910400000"))
