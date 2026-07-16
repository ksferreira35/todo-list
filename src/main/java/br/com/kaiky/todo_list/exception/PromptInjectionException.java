package br.com.kaiky.todo_list.exception;

public class PromptInjectionException extends RuntimeException{
    public PromptInjectionException(String message){
        super(message);
    }
}
