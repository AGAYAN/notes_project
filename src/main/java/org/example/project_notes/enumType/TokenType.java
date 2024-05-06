package org.example.project_notes.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenType {
    ACCESS_TOKEN(30*60*1000),
    REFRESH_TOKEN(7*24*60*60*1000);
    private final long time;
}