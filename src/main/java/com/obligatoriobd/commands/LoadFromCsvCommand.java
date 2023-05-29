package com.obligatoriobd.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LoadFromCsvCommand {

    @ShellMethod(key = "hello", value = "says hello")
    public String hello() {
        return "Hello, World!";
    }

    @ShellMethod(key = "goodbye", value = "says goodbye")
    public String goodBye() {
        return "GoodBye!";
    }
}
