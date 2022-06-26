package com.example.Social_App.Service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(final String s) {
        return true;
    }
}
